/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets;

import io.github.cdimascio.dotenv.Dotenv;
import se.michaelthelin.spotify.SpotifyApi;

/**
 *
 * @author Chin Riku
 */
public class test {
    public static String HOST;
    public static void main(String[] args) {
        try {
            Dotenv env = Dotenv.load();
            System.out.println(env.get("HOST", "ChinRiku"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
