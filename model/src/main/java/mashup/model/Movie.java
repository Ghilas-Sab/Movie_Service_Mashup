package mashup.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Movie {

    private final String title;
    private short year;
    private final List<VisualisationInfo> visualisationInfo = new ArrayList<>();

    public Movie(String title, short year) {
        this.title = Objects.requireNonNull(title);
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public short getYear() {
        return year;
    }

    void setYear(short year) {
        this.year = year;
    }

    public List<VisualisationInfo> getVisualisationInfo() {
        return Collections.unmodifiableList(visualisationInfo);
    }

    void addVisualisationInfo(VisualisationInfo info) {
        visualisationInfo.add(info);
    }
}
