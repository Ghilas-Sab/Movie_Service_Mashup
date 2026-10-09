package mashup.tmdb.dto;

import java.net.URL;
import java.util.Objects;

public class CharacterDto {

    private final String characterName;
    private final String actorName;
    private final URL imageUrl;
    private final String birthday;
    private final String deathday;
    private final String placeOfBirth;

    public CharacterDto(String characterName, String actorName, URL imageUrl,
                         String birthday, String deathday, String placeOfBirth) {
        this.characterName = characterName;
        this.actorName = actorName;
        this.imageUrl = imageUrl;
        this.birthday = birthday;
        this.deathday = deathday;
        this.placeOfBirth = placeOfBirth;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getActorName() {
        return actorName;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterDto that)) return false;
        return Objects.equals(characterName, that.characterName)
                && Objects.equals(actorName, that.actorName)
                && Objects.equals(imageUrlKey(), that.imageUrlKey())
                && Objects.equals(birthday, that.birthday)
                && Objects.equals(deathday, that.deathday)
                && Objects.equals(placeOfBirth, that.placeOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterName, actorName, imageUrlKey(), birthday, deathday, placeOfBirth);
    }

    /**
     * java.net.URL#equals/hashCode can trigger DNS resolution; compare the
     * string form instead so DTO equality never touches the network.
     */
    private String imageUrlKey() {
        return imageUrl == null ? null : imageUrl.toString();
    }

    @Override
    public String toString() {
        return "CharacterDto{" +
                "characterName='" + characterName + '\'' +
                ", actorName='" + actorName + '\'' +
                ", imageUrl=" + imageUrl +
                ", birthday='" + birthday + '\'' +
                ", deathday='" + deathday + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                '}';
    }
}
