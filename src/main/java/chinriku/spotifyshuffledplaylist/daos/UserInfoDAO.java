package chinriku.spotifyshuffledplaylist.daos;

import chinriku.spotifyshuffledplaylist.dtos.UserInfo;
import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

public class UserInfoDAO {

    public static UserInfo getCurrentUserInfo(String accessToken)
            throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
        User user = request.execute();
        UserInfo userInfo = new UserInfo(user);
        return userInfo;
    }
}
