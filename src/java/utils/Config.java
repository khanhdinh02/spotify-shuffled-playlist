package utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    public static final String CLIENT_ID;
    public static final String CLIENT_SECRET;

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
            } else {
                CLIENT_ID = CLIENT_SECRET = null;
            }
        }
    }
}
