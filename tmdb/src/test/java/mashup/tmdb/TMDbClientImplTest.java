package mashup.tmdb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TMDbClientImplTest {

    @Test
    void constructorRejectsMissingApiKey() {
        assertThrows(IllegalStateException.class, () -> new TMDbClientImpl((String) null));
        assertThrows(IllegalStateException.class, () -> new TMDbClientImpl(""));
        assertThrows(IllegalStateException.class, () -> new TMDbClientImpl("   "));
    }
}
