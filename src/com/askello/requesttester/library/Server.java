package com.askello.requesttester.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by askello on 19.10.2015.
 */
public class Server
{
    public static String doRequestAndGetResponse(String url, HashMap<String, String> data) throws IOException {
        URL server = new URL(url);
        URLConnection connection = server.openConnection();
        connection.setDoOutput(true);

        // prepare output data
        String outputData = "";
        for(HashMap.Entry<String, String> entry : data.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            value = URLEncoder.encode(value, "UTF-8");

            if(!outputData.equals(""))
                outputData += "&";
            outputData += key+"="+value;
        }

        // send data to server
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(outputData);
        out.close();

        // get data from server
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputData = "", line = null;
        while ((line = in.readLine()) != null)
            inputData += line;
        in.close();

        return inputData;
    }
}
