package mashup.tmdb;

import mashup.tmdb.dto.MovieInfoDto;

public interface MovieInformationClient {

    MovieInfoDto findMovieInformation(String title) throws MovieInfoNotFoundException;
}
