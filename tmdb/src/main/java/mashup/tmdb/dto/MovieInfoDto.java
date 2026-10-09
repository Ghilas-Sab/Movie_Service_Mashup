package mashup.tmdb.dto;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class MovieInfoDto {

    private final String title;
    private final List<String> genres;
    private final URL posterUrl;
    private final int year;
    private final List<CharacterDto> characters;

    public MovieInfoDto(String title, List<String> genres, URL posterUrl, int year,
                         List<CharacterDto> characters) {
        this.title = title;
        this.genres = genres;
        this.posterUrl = posterUrl;
        this.year = year;
        this.characters = characters;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public URL getPosterUrl() {
        return posterUrl;
    }

    public int getYear() {
        return year;
    }

    public List<CharacterDto> getCharacters() {
        return characters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieInfoDto that)) return false;
        return year == that.year
                && Objects.equals(title, that.title)
                && Objects.equals(genres, that.genres)
                && Objects.equals(posterUrlKey(), that.posterUrlKey())
                && Objects.equals(characters, that.characters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genres, posterUrlKey(), year, characters);
    }

    /**
     * java.net.URL#equals/hashCode can trigger DNS resolution; compare the
     * string form instead so DTO equality never touches the network.
     */
    private String posterUrlKey() {
        return posterUrl == null ? null : posterUrl.toString();
    }

    @Override
    public String toString() {
        return "MovieInfoDto{" +
                "title='" + title + '\'' +
                ", genres=" + genres +
                ", posterUrl=" + posterUrl +
                ", year=" + year +
                ", characters=" + characters +
                '}';
    }
}
