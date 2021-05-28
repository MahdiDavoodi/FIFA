package davoodi.mahdi.oufa.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import davoodi.mahdi.oufa.components.Club;
import davoodi.mahdi.oufa.components.League;

public class JsonParser {

    public static List<Club> clubsJson(InputStream inputStream) {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        try {
            while (bufferedInputStream.available() != 0)
                stringBuilder.append((char) bufferedInputStream.read());
            bufferedInputStream.close();
            return clubsJson(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return clubsJson("");
        }

    }

    public static List<Club> clubsJson(String jsonString) {
        List<Club> clubs = new ArrayList<>();
        if (jsonString.isEmpty()) return clubs;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Club club = new Club();
                club.setClubID(jsonObject.getInt(Club.KEY_ID));
                club.setClubName(jsonObject.getString(Club.KEY_NAME));
                club.setClubWealth(jsonObject.getLong(Club.KEY_WEALTH));
                club.setClubMT(jsonObject.getInt(Club.KEY_MT));
                club.setClubTM(jsonObject.getInt(Club.KEY_TM));
                club.setClubChampions(jsonObject.getInt(Club.KEY_CHAMPIONS));
                club.setClubEurope(jsonObject.getInt(Club.KEY_EUROPE));
                club.setClubGolden(jsonObject.getInt(Club.KEY_GOLDEN));
                club.setClubClass(jsonObject.getString(Club.KEY_CLASS));
                // Zero means it does not have any owner!
                club.setClubOwner(0);
                clubs.add(club);
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return clubs;
    }

    public static List<League> leaguesJson(InputStream inputStream) {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        try {
            while (bufferedInputStream.available() != 0)
                stringBuilder.append((char) bufferedInputStream.read());
            bufferedInputStream.close();
            return leaguesJson(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return leaguesJson("");
        }
    }

    public static List<League> leaguesJson(String jsonString) {
        List<League> leagues = new ArrayList<>();
        if (jsonString.isEmpty()) return leagues;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                League league = new League();
                league.setLeagueID(jsonObject.getInt(League.KEY_ID));
                league.setLeagueName(jsonObject.getString(League.KEY_NAME));
                league.setLeagueNumber(jsonObject.getInt(League.KEY_NUMBER));
                leagues.add(league);
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return leagues;
    }
}
