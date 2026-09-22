package mashup.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieModelImplTest {

    private MovieModel model;

    @BeforeEach
    void setUp() {
        model = new MovieModelImpl();
    }

    @Test
    void findMovieByTitleThrowsWhenMovieIsUnknown() {
        assertThrows(MovieNotFoundException.class, () -> model.findMovieByTitle("The Matrix"));
    }

    @Test
    void addMovieThenFindMovieByTitleReturnsIt() throws MovieNotFoundException {
        model.addMovie("The Matrix", (short) 1999, LocalDate.of(2024, 3, 10), (short) 9);

        Movie movie = model.findMovieByTitle("The Matrix");

        assertEquals("The Matrix", movie.getTitle());
        assertEquals(1999, movie.getYear());
        assertEquals(1, movie.getVisualisationInfo().size());
        assertEquals(9, movie.getVisualisationInfo().get(0).getPunctuation());
    }

    @Test
    void addMovieTwiceWithSameTitleUpdatesTheExistingMovie() throws MovieNotFoundException {
        model.addMovie("The Matrix", (short) 1999, LocalDate.of(2024, 3, 10), (short) 9);
        model.addMovie("The Matrix", (short) 1999, LocalDate.of(2025, 1, 5), (short) 10);

        Movie movie = model.findMovieByTitle("The Matrix");

        assertEquals(2, movie.getVisualisationInfo().size());
    }

    @Test
    void findMoviesByYearReturnsOnlyMatchingMovies() {
        model.addMovie("The Matrix", (short) 1999, LocalDate.of(2024, 3, 10), (short) 9);
        model.addMovie("Fight Club", (short) 1999, LocalDate.of(2024, 4, 1), (short) 8);
        model.addMovie("Inception", (short) 2010, LocalDate.of(2024, 5, 1), (short) 9);

        List<Movie> movies = model.findMoviesByYear((short) 1999);

        assertEquals(2, movies.size());
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("The Matrix")));
        assertTrue(movies.stream().anyMatch(movie -> movie.getTitle().equals("Fight Club")));
    }
}
