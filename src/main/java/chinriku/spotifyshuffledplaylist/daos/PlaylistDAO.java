package chinriku.spotifyshuffledplaylist.daos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

public class PlaylistDAO {

    public static Playlist getPlaylist(String accessToken, String id)
            throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(id)
                .fields("collaborative,description,external_urls,followers,href"
                        + ",id,images,name,owner,public,snapshot_id,type,uri")
                .build();
        return getPlaylistRequest.execute();
    }

    public static Playlist createPlaylist(String accessToken, String userId)
            throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        CreatePlaylistRequest request = spotifyApi.createPlaylist(userId, Long.toString(System.currentTimeMillis()))
                .public_(false)
                .build();
        return request.execute();
    }

    public static ArrayList<PlaylistTrack> getPlaylistTracks(String accessToken, String id)
            throws IOException, SpotifyWebApiException, ParseException {
        ArrayList<PlaylistTrack> list = new ArrayList<>();
        int limit = 100;
        int count = 0;

        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        Paging<PlaylistTrack> paging;
        do {
            GetPlaylistsItemsRequest request = spotifyApi.getPlaylistsItems(id)
                    .additionalTypes("track,episode")
                    .limit(limit)
                    .offset(limit * count++)
                    .build();
            paging = request.execute();
            list.addAll(Arrays.asList(paging.getItems()));
        } while (paging.getNext() != null);

        return list;
    }

    public static void addTracks(String accessToken, String playlistId, String[] trackUris)
            throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        int num = trackUris.length;
        if (num > 0) {
            int setsOf100 = (num - 1) / 100;
            for (int i = 0; i <= setsOf100; i++) {
                String[] arrayOf100 = Arrays.copyOfRange(trackUris, 100 * i,
                        (i < setsOf100) ? 100 * (i + 1) : 100 * i + (num - 1) % 100 + 1);
                JsonArray jsonArray = new Gson().toJsonTree(arrayOf100).getAsJsonArray();
                AddItemsToPlaylistRequest request = spotifyApi.addItemsToPlaylist(playlistId, jsonArray).build();
                request.execute();
            }
        }
    }
}
