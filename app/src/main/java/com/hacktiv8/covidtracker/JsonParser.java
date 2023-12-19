package com.hacktiv8.covidtracker;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public static CovidData parseCovidData(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            CovidData covidData = new CovidData();
            covidData.updated = jsonObject.getLong("updated");
            covidData.cases = jsonObject.getLong("cases");
            covidData.todayCases = jsonObject.getInt("todayCases");
            covidData.deaths = jsonObject.getLong("deaths");
            covidData.todayDeaths = jsonObject.getInt("todayDeaths");
            covidData.recovered = jsonObject.getLong("recovered");
            covidData.todayRecovered = jsonObject.getInt("todayRecovered");
            covidData.active = jsonObject.getLong("active");
            covidData.critical = jsonObject.getInt("critical");

            return covidData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}