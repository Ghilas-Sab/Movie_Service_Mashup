package mashup.thrift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import mashup.model.MovieModel;
import mashup.model.MovieModelImpl;
import mashup.thrift.generated.MovieDto;
import mashup.thrift.generated.MovieService;
import mashup.thrift.generated.ServiceMovieNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieServiceThriftImplTest {

    private MovieService.Iface service;

    @BeforeEach
    void setUp() {
        MovieModel model = new MovieModelImpl();
        service = new MovieServiceThriftImpl(model);
    }

    @Test
    void findMovieByTitleThrowsWhenMovieIsUnknown() {
        assertThrows(ServiceMovieNotFoundException.class, () -> service.findMovieByTitle("The Matrix"));
    }

    @Test
    void addMovieThenFindMovieByTitleReturnsIt() throws Exception {
        service.addMovie(new MovieDto("The Matrix", (short) 1999, "2024-03-10", (short) 9));

        MovieDto movie = service.findMovieByTitle("The Matrix");

        assertEquals("The Matrix", movie.getTitle());
        assertEquals(1999, movie.getYear());
        assertEquals("2024-03-10", movie.getVisualisationDate());
        assertEquals(9, movie.getPoints());
    }

    @Test
    void addMovieTwiceReturnsTheMostRecentVisualisation() throws Exception {
        service.addMovie(new MovieDto("The Matrix", (short) 1999, "2024-03-10", (short) 9));
        service.addMovie(new MovieDto("The Matrix", (short) 1999, "2025-01-05", (short) 10));

        MovieDto movie = service.findMovieByTitle("The Matrix");

        assertEquals("2025-01-05", movie.getVisualisationDate());
        assertEquals(10, movie.getPoints());
    }

    @Test
    void findMoviesByYearReturnsOnlyMatchingMovies() throws Exception {
        service.addMovie(new MovieDto("The Matrix", (short) 1999, "2024-03-10", (short) 9));
        service.addMovie(new MovieDto("Fight Club", (short) 1999, "2024-04-01", (short) 8));
        service.addMovie(new MovieDto("Inception", (short) 2010, "2024-05-01", (short) 9));

        List<MovieDto> movies = service.findMoviesByYear((short) 1999);

        assertEquals(2, movies.size());
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("The Matrix")));
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("Fight Club")));
    }
}
