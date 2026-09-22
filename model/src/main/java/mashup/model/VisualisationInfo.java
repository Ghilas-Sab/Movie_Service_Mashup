package mashup.model;

import java.time.LocalDate;
import java.util.Objects;

public class VisualisationInfo {

    private final LocalDate visualisationDate;
    private final short punctuation;

    public VisualisationInfo(LocalDate visualisationDate, short punctuation) {
        if (punctuation < 0 || punctuation > 10) {
            throw new IllegalArgumentException("punctuation must be between 0 and 10");
        }
        this.visualisationDate = Objects.requireNonNull(visualisationDate);
        this.punctuation = punctuation;
    }

    public LocalDate getVisualisationDate() {
        return visualisationDate;
    }

    public short getPunctuation() {
        return punctuation;
    }
}
