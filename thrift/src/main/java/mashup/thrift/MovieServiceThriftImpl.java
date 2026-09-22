package mashup.thrift;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.thrift.TException;

import mashup.model.Movie;
import mashup.model.MovieModel;
import mashup.model.MovieModelFactory;
import mashup.model.MovieNotFoundException;
import mashup.model.VisualisationInfo;
import mashup.thrift.generated.MovieDto;
import mashup.thrift.generated.MovieService;
import mashup.thrift.generated.ServiceMovieNotFoundException;

public class MovieServiceThriftImpl implements MovieService.Iface {

    private final MovieModel movieModel;

    public MovieServiceThriftImpl() {
        this(MovieModelFactory.getModel());
    }

    public MovieServiceThriftImpl(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    @Override
    public void addMovie(MovieDto movie) throws TException {
        movieModel.addMovie(movie.getTitle(), movie.getYear(),
                LocalDate.parse(movie.getVisualisationDate()), movie.getPoints());
    }

    @Override
    public MovieDto findMovieByTitle(String title) throws ServiceMovieNotFoundException, TException {
        try {
            return toDto(movieModel.findMovieByTitle(title));
        } catch (MovieNotFoundException e) {
            throw new ServiceMovieNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<MovieDto> findMoviesByYear(short year) throws TException {
        List<MovieDto> result = new ArrayList<>();
        for (Movie movie : movieModel.findMoviesByYear(year)) {
            result.add(toDto(movie));
        }
        return result;
    }

    private static MovieDto toDto(Movie movie) {
        VisualisationInfo latest = latestVisualisation(movie);
        return new MovieDto(movie.getTitle(), movie.getYear(),
                latest.getVisualisationDate().toString(), latest.getPunctuation());
    }

    private static VisualisationInfo latestVisualisation(Movie movie) {
        return movie.getVisualisationInfo().stream()
                .max(Comparator.comparing(VisualisationInfo::getVisualisationDate))
                .orElseThrow(() -> new IllegalStateException("Movie without visualisation info: " + movie.getTitle()));
    }
}
