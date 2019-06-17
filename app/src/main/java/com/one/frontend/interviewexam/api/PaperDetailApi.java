package com.one.frontend.interviewexam.api;

import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;
import com.one.frontend.interviewexam.model.PaperDetail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PaperDetailApi {
    //private static final String baseUrl ="http://192.168.25.1:8809/getPaperList";
    private static final String baseUrl = "http://192.168.191.1:8809/getPaperList";
    public List<PaperDetail> fetchPaperDetail(){
        List<PaperDetail> paperDetails = new ArrayList<PaperDetail>();
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if(code==200){
                InputStream in = connection.getInputStream();
                byte[] data = readFromStream(in);
                String result = new String(data,"UTF-8");
                paperDetails = parseResult(result);
                Log.i("log detail",paperDetails.toString());
            }else{
                Log.e("network","get paper detail error");
            }
        } catch (MalformedURLException e) {
            Log.e("exception",e.toString());
        } catch (IOException e) {
            Log.e("exception",e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paperDetails;
    }

    private ArrayList<PaperDetail> parseResult(String content) throws JSONException {
        ArrayList<PaperDetail> paperDetails = new ArrayList<PaperDetail>();
        JSONArray array = new JSONArray(content);
        for (int i = 0; i < array.length(); i++) {
            JSONObject results = (JSONObject) array.get(i);
            PaperDetail paperDetail = new PaperDetail();
            paperDetail.setId(results.getInt("id"));
            paperDetail.setName(results.getString("name"));
            paperDetail.setDescription(results.getString("description"));
            paperDetail.setCount(results.getInt("count"));
            paperDetail.setTime(results.getString("time"));
            paperDetail.setType(results.getString("type"));
            paperDetails.add(paperDetail);
        }
        return paperDetails;
    }

    private byte[] readFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len ;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}
