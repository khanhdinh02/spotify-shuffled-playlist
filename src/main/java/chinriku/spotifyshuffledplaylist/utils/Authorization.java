package chinriku.spotifyshuffledplaylist.utils;

import java.io.IOException;
import java.net.URI;
import java.util.Random;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

public class Authorization {

    private static final String CLIENT_ID = Config.CLIENT_ID;
    private static final String CLIENT_SECRET = Config.CLIENT_SECRET;
    private static final URI REDIRECT_URI = SpotifyHttpManager.makeUri(Config.REDIRECT_URI);

    public static URI getCodeURI(String state) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(REDIRECT_URI)
                .build();
        AuthorizationCodeUriRequest request = spotifyApi.authorizationCodeUri()
                .state(state)
                .scope("playlist-modify-private")
                .build();
        URI uri = request.execute();
        return uri;
    }

    public static AuthorizationCodeCredentials getAccessRefreshToken(String code)
            throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(REDIRECT_URI)
                .build();
        AuthorizationCodeRequest request = spotifyApi.authorizationCode(code).build();
        AuthorizationCodeCredentials credentials = request.execute();
        return credentials;
    }

    public static AuthorizationCodeCredentials refreshAccessToken(String refreshToken)
            throws IOException, SpotifyWebApiException, ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRefreshToken(refreshToken)
                .build();
        AuthorizationCodeRefreshRequest request = spotifyApi.authorizationCodeRefresh().build();
        AuthorizationCodeCredentials credentials = request.execute();
        return credentials;
    }

    public static String generateState() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 16;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
