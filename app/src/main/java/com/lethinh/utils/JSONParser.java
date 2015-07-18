package com.lethinh.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Thinh on 06/07/2015.
 */
public class JSONParser {

    private JSONObject jsonObject = null;
    private InputStream is = null;
    private String json="";

    public JSONParser() {

    }

    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
        try {
            if (method == "POST") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse= httpClient.execute(httpPost);
                HttpEntity httpEntity= httpResponse.getEntity();
                is= httpEntity.getContent();
            }else if(method=="GET"){
                DefaultHttpClient httpClient= new DefaultHttpClient();
                String paramString= URLEncodedUtils.format(params,"utf-8");
                url+="?"+paramString;
                HttpGet httpGet= new HttpGet(url);
                HttpResponse httpResponse= httpClient.execute(httpGet);
                HttpEntity httpEntity=httpResponse.getEntity();
                is=httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader= new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuffer buffer=new StringBuffer("");
            String line="";
            while((line=reader.readLine())!=null){
                buffer.append(line);
            }
            is.close();
            json=buffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;


    }
}
