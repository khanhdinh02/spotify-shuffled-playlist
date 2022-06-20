# spotify-suffled-playlist
Create a playlist by suffling an original playlist.
## Development
This project is developed with JDK 1.8 and Java EE 7.
### Libraries
- JSTL 1.2.1
- [Spotify Web API Java Client 7.1.0](https://mvnrepository.com/artifact/se.michaelthelin.spotify/spotify-web-api-java/7.1.0)
- [Dotenv Java](https://mvnrepository.com/artifact/io.github.cdimascio/dotenv-java/2.2.4)
### Setup
In folder `src/java/` (the same folder as `.env.example`), create a file named `.env` following this example:
```
CLIENT_ID=YOUR_CLIENT_ID
CLIENT_SECRET=YOUR_CLIENT_SECRET
```
You get your Client ID and Client Secret from your [Spotify Application](https://developer.spotify.com/dashboard/applications)
