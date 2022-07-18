package chinriku.spotifyshuffledplaylist.dtos;

import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.User;

public class UserInfo {

    private String id;
    private String name;
    private Image[] images;
    private String url;

    public UserInfo() {
    }

    public UserInfo(String id, String name, Image[] images, String url) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.url = url;
    }

    public UserInfo(User spotifyUser) {
        this.id = spotifyUser.getId();
        this.name = spotifyUser.getDisplayName();
        this.images = spotifyUser.getImages();
        this.url = spotifyUser.getExternalUrls().get("spotify");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
