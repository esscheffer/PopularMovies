package com.scheffer.erik.popularmovies.MovieDatabaseApi;

import android.net.Uri;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.scheffer.erik.popularmovies.MovieDatabaseApi.ApiConstants.MOVIES_DATABASE_API_KEY;
import static com.scheffer.erik.popularmovies.MovieDatabaseApi.ApiConstants.MOVIES_DATABASE_BASE_URL;

public class ApiConnection {

    public static List<Movie> getMovies(SearchCriteria criteria) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) buildUrl(criteria).openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return extractMoviesList(scanner.next());
            } else {
                return new ArrayList<>();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return new ArrayList<>();
    }

    private static URL buildUrl(SearchCriteria criteria) {
        Uri builtUri = Uri.parse(MOVIES_DATABASE_BASE_URL + criteria.getCriteria()).buildUpon()
                .appendQueryParameter("api_key", MOVIES_DATABASE_API_KEY)
                .build();
        URL url = null;
        try {
            Log.i("URL", builtUri.toString());
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private static List<Movie> extractMoviesList(String result) throws JSONException {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        JSONObject jsonObject = new JSONObject(result);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        return gson.fromJson(jsonObject.getJSONArray("results").toString(), type);
    }
}
