package com.askello.requesttester.library.request;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import static java.lang.System.currentTimeMillis;

/**
 * Created by user_5 on 24.06.2016.
 */
public abstract class Request {

    protected URL url;
    protected HashMap<String, String> params;
    protected HashMap<String, File> files;

    protected String response;

    protected String charset;
    protected final String CRLF = "\r\n";

    protected HttpURLConnection connection;

    public Request() {
        this.params = new HashMap<String, String>();
        this.files = new HashMap<String, File>();

        this.charset = "UTF-8";
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setUrl(String link) throws MalformedURLException {
        setUrl(new URL(link));
    }

    public String getResponse() {
        return response;
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addParam(String key, int value) {
        addParam(key, String.valueOf(value));
    }

    public void addParams(HashMap<String, String> params) {
        for(String key : params.keySet()) {
            addParam(key, params.get(key));
        }
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void addFile(String key, File file) {
        files.put(key, file);
    }

    public void addFile(String key, String pathToFile) {
        addFile(key, new File(pathToFile));
    }

    public void addFiles(HashMap<String, File> files) {
        for(String key : files.keySet()) {
            addFile(key, files.get(key));
        }
    }

    public void setFiles(HashMap<String, File> files) {
        this.files = files;
    }

    public void execute() throws IOException {
        // start connection
        setUpConnection();

        // send data to server
        sendData();

        // get response from server
        readResponse();
    }

    protected void setUpConnection() throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setDoOutput(true);
        connection.setDoInput(true);
    }

    protected abstract void sendData() throws IOException;

    public void readResponse() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputData = "", line = null;
        while ((line = in.readLine()) != null)
            inputData += line;
        in.close();

        this.response = inputData;
    }

    public String urlEncodedParams() throws IOException {
        String encodedString = "";

        for(HashMap.Entry<String, String> entry : params.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            value = URLEncoder.encode(value, "UTF-8");

            if(!encodedString.equals(""))
                encodedString += "&";
            encodedString += key+"="+value;
        }

        return encodedString;
    }

    public static String GET(String url, HashMap<String, String> params) throws IOException {
        Request request = new GetRequest();
        request.setUrl(url);
        request.setParams(params);
        request.execute();
        return request.getResponse();
    }

    public static String POST(String url, HashMap<String, String> params) throws IOException {
        Request request = new PostRequest();
        request.setUrl(url);
        request.setParams(params);
        request.execute();
        return request.getResponse();
    }

    public static String MULTIPART(String url, HashMap<String, String> params, HashMap<String, File> files) throws IOException {
        Request request = new MultipartRequest();
        request.setUrl(url);
        request.setParams(params);
        request.setFiles(files);
        request.execute();
        return request.getResponse();
    }

}
