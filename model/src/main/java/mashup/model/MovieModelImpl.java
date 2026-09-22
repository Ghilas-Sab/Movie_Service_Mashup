package mashup.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieModelImpl implements MovieModel {

    private final List<Movie> movies = new ArrayList<>();

    @Override
    public void addMovie(String title, short year, LocalDate visualisationDate, short punctuation) {
        Movie movie = findByTitle(title);
        if (movie == null) {
            movie = new Movie(title, year);
            movies.add(movie);
        } else {
            movie.setYear(year);
        }
        movie.addVisualisationInfo(new VisualisationInfo(visualisationDate, punctuation));
    }

    @Override
    public Movie findMovieByTitle(String title) throws MovieNotFoundException {
        Movie movie = findByTitle(title);
        if (movie == null) {
            throw new MovieNotFoundException(title);
        }
        return movie;
    }

    @Override
    public List<Movie> findMoviesByYear(short year) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getYear() == year) {
                result.add(movie);
            }
        }
        return result;
    }

    private Movie findByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
}
