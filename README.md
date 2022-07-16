# spotify-shuffled-playlist
Create a playlist by shuffling an original playlist.
## Development
This project uses Maven and requires at least JDK 1.8
### Setup
In folder `src\main\resources` (the same folder as `.env.example`), create a file named `.env` following this example:
```
CLIENT_ID=YOUR_CLIENT_ID
CLIENT_SECRET=YOUR_CLIENT_SECRET
REDIRECT_URI=
```
You get your Client ID and Client Secret from your [Spotify Application](https://developer.spotify.com/dashboard/applications).
Redirect URI in your Spotify Application and in your .env file must be the same.
