package chinriku.spotifyshuffledplaylist.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    public static final String CLIENT_ID;
    public static final String CLIENT_SECRET;
    public static final String REDIRECT_URI;

    static {
        Dotenv env = null;
        try {
            env = Dotenv.load();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (env != null) {
                CLIENT_ID = env.get("CLIENT_ID");
                CLIENT_SECRET = env.get("CLIENT_SECRET");
                REDIRECT_URI = env.get("REDIRECT_URI");
            } else {
                CLIENT_ID = CLIENT_SECRET = REDIRECT_URI = null;
            }
        }
    }
}
