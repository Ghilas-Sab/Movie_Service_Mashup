package mashup.tmdb;

import mashup.tmdb.dto.CharacterDto;
import mashup.tmdb.dto.MovieInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Hits the real TMDb API. Skipped unless TMDB_API_KEY is set in the 
 * environment, so it never breaks the build for teammates without a key.
 */
@EnabledIfEnvironmentVariable(named = "TMDB_API_KEY", matches = ".+")
class TMDbClientImplIntegrationTest {

    @Test
    void findsMovieInformationForTheMatrix() throws MovieInfoNotFoundException {
        MovieInformationClient client = MovieInformationClientFactory.getClient();

        MovieInfoDto info = client.findMovieInformation("The Matrix");
        System.out.println(info);

        assertEquals("The Matrix", info.getTitle());
        assertEquals(1999, info.getYear());
        assertTrue(info.getGenres().contains("Action"));
        assertNotNull(info.getPosterUrl());
        assertFalse(info.getCharacters().isEmpty());

        CharacterDto neo = info.getCharacters().stream()
                .filter(c -> "Keanu Reeves".equals(c.getActorName()))
                .findFirst()
                .orElseThrow();
        assertEquals("Neo", neo.getCharacterName());
        assertEquals("1964-09-02", neo.getBirthday());
        assertNotNull(neo.getImageUrl());
    }
}
