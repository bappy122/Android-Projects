package com.example.ak_bappy.json;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;


/**
 * Created by BAPPY on 5/3/2016.
 */
public class WebServiceHelper
{
    public WebServiceHelper()
    {

    }

    public String getJSONData(String url)
    {
        String response = null;

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        try
        {
            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        }
        catch(Exception e)
        {
            e.printStackTrace();;
        }
        return response;
    }
}
