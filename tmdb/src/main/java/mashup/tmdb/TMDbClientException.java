package mashup.tmdb;

/**
 * Unchecked exception for technical failures (network, unexpected HTTP status,
 * malformed response, ...) when calling the TMDb API. Distinct from
 * {@link MovieInfoNotFoundException}, which is the expected business outcome
 * when a title simply does not match any movie.
 */
public class TMDbClientException extends RuntimeException {

    public TMDbClientException(String message) {
        super(message);
    }

    public TMDbClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
