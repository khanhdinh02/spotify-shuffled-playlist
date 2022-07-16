package chinriku.spotifyshuffledplaylist.dtos;

import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class WeightedTrack implements Comparable<WeightedTrack> {

    private final PlaylistTrack playlistTrack;
    private double weight;

    public WeightedTrack(PlaylistTrack playlistTrack) {
        this.playlistTrack = playlistTrack;
        this.weight = Math.random();
    }

    @Override
    public int compareTo(WeightedTrack o) {
        if (this.getWeight() < o.getWeight()) {
            return -1;
        } else if (this.getWeight() == o.getWeight()) {
            return 0;
        } else {
            return 1;
        }
    }

    public PlaylistTrack getPlaylistTrack() {
        return playlistTrack;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
