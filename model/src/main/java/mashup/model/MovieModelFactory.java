package mashup.model;

public final class MovieModelFactory {

    private static final MovieModel INSTANCE = new MovieModelImpl();

    private MovieModelFactory() {
    }

    public static MovieModel getModel() {
        return INSTANCE;
    }
}
