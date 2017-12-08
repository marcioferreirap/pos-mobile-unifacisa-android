package com.marcioferreirap.chucknorrisfactsapp.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.marcioferreirap.chucknorrisfactsapp.model.Fact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FactsParser {
    public static final String FACTS_URL_RANDOM = "https://api.chucknorris.io/jokes/random";

    public static Fact getRamdom() throws IOException {
        String url = String.format(FACTS_URL_RANDOM);
        String json = getResponse(url);
        Gson gson = new Gson();
        Fact result = gson.fromJson(json, Fact.class);
        return result;
    }

    private static String getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static boolean hasConnection(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}
