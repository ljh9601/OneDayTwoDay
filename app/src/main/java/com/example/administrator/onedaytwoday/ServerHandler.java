package com.example.administrator.onedaytwoday;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerHandler {

    private String prop;
    private String url;

    private HashMap<String, String> datakey;

    public ServerHandler(final HashMap<String, String> data, String url) {
        this.url = url;
        datakey = data;
        prop = getBody(datakey);
    }
    public ServerHandler(String url){
        this.url = url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setProp(final HashMap<String, String> data){
        prop = getBody(data);
    }


    public String getBody(final Map<String, String> postData) {
        String body = "";

        Iterator<String> keyIter = postData.keySet().iterator();
        ArrayList<String> keyList = new ArrayList<String>();
        while (keyIter.hasNext()) {
            String key = (String) keyIter.next();
            keyList.add(key);
        }
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            String value = postData.get(key);

            if (i < (keyList.size() - 1)) {
                body += key + "=" + value + "&";
            } else {
                body += key + "=" + value;
            }
        }
        return body;
    }

    public void POST(final Handler handler){
        Thread t = new Thread(){
            @Override
            public void run(){
                String json = POST();
                if(json == null){
                    json = "";
                }

                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("json", json);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };
        t.start();
    }

    public void GET(final Handler handler){
        Thread t = new Thread() {
            @Override
            public void run() {
                String json = GET();
                if(json == null) {
                    json = "";
                }
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("json", json);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };
        t.start();
    }

    public String GET(){
        String html = new String();
        try {
            URL targetUrl = new URL(url);

            HttpURLConnection urlConnection = (HttpURLConnection) targetUrl.openConnection();

            if (urlConnection != null) {
                urlConnection.setUseCaches(false);
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while (true) {
                        String buf = br.readLine();
                        if (buf == null)
                            break;
                        html += buf;
                    }
                    br.close();
                    urlConnection.disconnect();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        } finally {
        }
        return html;
    }

    public String POST() {
        String html = new String();

        try {
            URL target = new URL(url);
            HttpURLConnection http = (HttpURLConnection) target.openConnection();
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            OutputStreamWriter os = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(os);
            writer.write(prop);
            writer.flush();
            InputStreamReader reader = new InputStreamReader(http.getInputStream());
            BufferedReader br = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                builder.append(temp + "\n");
            }
            html = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }
}