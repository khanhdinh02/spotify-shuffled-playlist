package dtos;

import java.util.LinkedList;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

public class WeightedPlaylist extends LinkedList<WeightedTrack> {
    
    private final Playlist playlist;
    
    public WeightedPlaylist(Playlist playlist) {
        super();
        this.playlist = playlist;
    }
    
    public void shuffle() {
        this.generateWeight();
        this.sort(null);
    }
    
    public void generateWeight() {
        this.forEach(track -> {
            track.setWeight(Math.random());
        });
    }
    
    public Playlist getPlaylist() {
        return playlist;
    }
}
