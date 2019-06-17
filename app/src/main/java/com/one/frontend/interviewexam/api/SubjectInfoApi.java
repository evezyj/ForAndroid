package com.one.frontend.interviewexam.api;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.one.frontend.interviewexam.model.SubjectInfo;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SubjectInfoApi {

    //private static final String baseUrl ="http://192.168.25.1:8809/getPaperSubjects?pdId=";
    private static final String baseUrl = "http://192.168.191.1:8809/getPaperSubjects?pdId=";
    public SubjectInfo getSubjectInfo(int pdId){
        SubjectInfo subjectInfo = new SubjectInfo();
        String url = baseUrl + pdId;
        Log.e("url",url);
        try {
            URL urlForApi = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlForApi.openConnection();
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if(code==200){
                InputStream in = connection.getInputStream();
                byte[] data = readFromStream(in);
                String result = new String(data,"UTF-8");
                subjectInfo = parseResult(result);
                Log.i("log detail",subjectInfo.toString());
            }else{
                Log.e("network","get paper detail error");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return subjectInfo;
    }

    private SubjectInfo parseResult(String result) throws JSONException {
        SubjectInfo subjectInfo = JSON.parseObject(result,SubjectInfo.class);
        Log.e("result",result);
        Log.e("result subjectInfo",subjectInfo.toString());
        return subjectInfo;
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
