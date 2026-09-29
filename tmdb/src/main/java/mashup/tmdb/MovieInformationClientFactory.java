package mashup.tmdb;

public final class MovieInformationClientFactory {

    private static final MovieInformationClientFactory INSTANCE = new MovieInformationClientFactory();

    private final MovieInformationClient client;

    private MovieInformationClientFactory() {
        this.client = new TMDbClientImpl();
    }

    public static MovieInformationClient getClient() {
        return INSTANCE.client;
    }
}
