package com.one.frontend.interviewexam.api;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.one.frontend.interviewexam.model.SubjectInfo;
import com.one.frontend.interviewexam.model.UserInfo;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserInfoApi {
    //private String baseUrl = "http://192.168.25.1:8809/user";
    private String baseUrl = "http://192.168.191.1:8809/user";
    private String NETWORK_POST_JSON = "POST_JSON";

    public int saveUserBaseInfo(UserInfo userInfo){
        URL urlForApi = null;
        baseUrl = baseUrl + "/add";
        String requestBody = null; //请求体
        int result = 0;
        try {
            urlForApi = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) urlForApi.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json;charset=utf-8");
            conn.connect();
            requestBody = JSON.toJSONString(userInfo);
            Log.i("log detail requestBody",requestBody);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            bw.write(requestBody);
            bw.close();

            int code = conn.getResponseCode();
            if(code==200){
                InputStream in = conn.getInputStream();
                byte[] data = readFromStream(in);
                result = Integer.parseInt(new String(data,"UTF-8"));
                Log.i("log detail result",String.valueOf(result));

            }else{
                Log.e("network","save user base info error");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public UserInfo findUserInfo(int id){

        UserInfo userInfo = new UserInfo();
        baseUrl = baseUrl + "/findById?userId="+id;
        Log.i("user info api ", "baseUrl: "+baseUrl);
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
            coon.setRequestMethod("GET");
            int code = coon.getResponseCode();
            if(code==200){
                InputStream in = coon.getInputStream();
                byte[] data = readFromStream(in);
                String result = new String(data,"UTF-8");
                userInfo = parseResult(result);
                Log.i("log detail userInfo",userInfo.toString());
            }else{
                Log.e("user info api", "error coon "+coon.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    private UserInfo parseResult(String result) {
        UserInfo userInfo = JSON.parseObject(result,UserInfo.class);
        return userInfo;
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
