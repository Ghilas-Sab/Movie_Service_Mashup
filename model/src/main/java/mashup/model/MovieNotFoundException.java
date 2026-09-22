package mashup.model;

public class MovieNotFoundException extends Exception {

    public MovieNotFoundException(String title) {
        super("Movie not found: " + title);
    }
}
