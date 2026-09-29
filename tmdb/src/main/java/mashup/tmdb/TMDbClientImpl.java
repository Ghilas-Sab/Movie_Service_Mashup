package mashup.tmdb;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mashup.tmdb.dto.CharacterDto;
import mashup.tmdb.dto.MovieInfoDto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TMDbClientImpl implements MovieInformationClient {

    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String API_KEY_ENV_VAR = "TMDB_API_KEY";

    private final HttpClient httpClient;
    private final Gson gson;
    private final String apiKey;

    public TMDbClientImpl() {
        this(System.getenv(API_KEY_ENV_VAR));
    }

    TMDbClientImpl(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Missing TMDb API key: set the " + API_KEY_ENV_VAR + " environment variable");
        }
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @Override
    public MovieInfoDto findMovieInformation(String title) throws MovieInfoNotFoundException {
        int movieId = searchMovieId(title);

        CompletableFuture<MovieDetails> detailsFuture = getAsync(buildUri("/movie/" + movieId, Map.of()))
                .thenApply(body -> gson.fromJson(body, MovieDetails.class));
        CompletableFuture<CreditsResponse> creditsFuture =
                getAsync(buildUri("/movie/" + movieId + "/credits", Map.of()))
                        .thenApply(body -> gson.fromJson(body, CreditsResponse.class));

        MovieDetails details = detailsFuture.join();
        CreditsResponse credits = creditsFuture.join();

        List<CastMember> cast = credits.cast == null ? List.of() : credits.cast;
        List<CharacterDto> characters = cast.stream()
                .map(castMember -> getAsync(buildUri("/person/" + castMember.id, Map.of()))
                        .thenApply(body -> gson.fromJson(body, PersonDetails.class))
                        .thenApply(person -> toCharacterDto(castMember, person)))
                .map(CompletableFuture::join)
                .toList();

        return toMovieInfoDto(details, characters);
    }

    private int searchMovieId(String title) throws MovieInfoNotFoundException {
        String body = get(buildUri("/search/movie", Map.of("query", title)));
        SearchResponse response = gson.fromJson(body, SearchResponse.class);
        if (response == null || response.results == null || response.results.isEmpty()) {
            throw new MovieInfoNotFoundException("No movie found with title: " + title);
        }
        return response.results.get(0).id;
    }

    private CharacterDto toCharacterDto(CastMember castMember, PersonDetails person) {
        URL imageUrl = castMember.profilePath != null ? toUrl(IMAGE_BASE_URL + castMember.profilePath) : null;
        return new CharacterDto(castMember.character, castMember.name, imageUrl,
                person.birthday, person.deathday, person.placeOfBirth);
    }

    private MovieInfoDto toMovieInfoDto(MovieDetails details, List<CharacterDto> characters) {
        List<String> genres = details.genres == null
                ? List.of()
                : details.genres.stream().map(genre -> genre.name).toList();
        URL posterUrl = details.posterPath != null ? toUrl(IMAGE_BASE_URL + details.posterPath) : null;
        return new MovieInfoDto(details.title, genres, posterUrl, extractYear(details.releaseDate), characters);
    }

    private static URL toUrl(String value) {
        try {
            return URI.create(value).toURL();
        } catch (MalformedURLException | IllegalArgumentException e) {
            throw new TMDbClientException("Malformed URL: " + value, e);
        }
    }

    private static int extractYear(String releaseDate) {
        if (releaseDate == null || releaseDate.length() < 4) {
            return 0;
        }
        return Integer.parseInt(releaseDate.substring(0, 4));
    }

    private URI buildUri(String path, Map<String, String> queryParams) {
        StringBuilder uri = new StringBuilder(API_BASE_URL).append(path).append("?api_key=").append(apiKey);
        queryParams.forEach((key, value) -> uri.append('&')
                .append(key)
                .append('=')
                .append(URLEncoder.encode(value, StandardCharsets.UTF_8)));
        return URI.create(uri.toString());
    }

    private String get(URI uri) {
        try {
            HttpResponse<String> response = httpClient.send(
                    HttpRequest.newBuilder(uri).GET().build(), HttpResponse.BodyHandlers.ofString());
            return checkStatus(uri, response);
        } catch (IOException e) {
            throw new TMDbClientException("Failed to call TMDb API: " + uri, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TMDbClientException("Interrupted while calling TMDb API: " + uri, e);
        }
    }

    private CompletableFuture<String> getAsync(URI uri) {
        return httpClient
                .sendAsync(HttpRequest.newBuilder(uri).GET().build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> checkStatus(uri, response));
    }

    private static String checkStatus(URI uri, HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            throw new TMDbClientException("Unexpected TMDb response status " + response.statusCode() + " for " + uri);
        }
        return response.body();
    }

    private static class SearchResponse {
        List<SearchResult> results;
    }

    private static class SearchResult {
        int id;
    }

    private static class MovieDetails {
        String title;
        List<Genre> genres;
        @SerializedName("poster_path")
        String posterPath;
        @SerializedName("release_date")
        String releaseDate;
    }

    private static class Genre {
        String name;
    }

    private static class CreditsResponse {
        List<CastMember> cast;
    }

    private static class CastMember {
        int id;
        String name;
        String character;
        @SerializedName("profile_path")
        String profilePath;
    }

    private static class PersonDetails {
        String birthday;
        String deathday;
        @SerializedName("place_of_birth")
        String placeOfBirth;
    }
}
