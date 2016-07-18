package net.askello.requesttester.library.request;

import net.askello.requesttester.library.response.Response;

import java.io.*;
import java.net.*;
import java.util.HashMap;

/**
 * Created by user_5 on 24.06.2016.
 */
public abstract class Request {

    protected URL url;
    protected HashMap<String, String> params;
    protected HashMap<String, String> cookies;
    protected HashMap<String, File> files;

    protected Response response;

    protected String charset;

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

    public Response getResponse() {
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

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public void addCookie(String key, int value) {
        addCookie(key, String.valueOf(value));
    }

    public void addCookies(HashMap<String, String> cookies) {
        for(String key : cookies.keySet()) {
            addCookie(key, cookies.get(key));
        }
    }

    public void setCookies(HashMap<String, String> cookies) {
        this.cookies = cookies;
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

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void execute() throws IOException {
        // start connection
        setUpConnection();

        // send data to server
        sendData();

        // get response from server
        response = new Response(connection);
    }

    protected void setUpConnection() throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        //connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setDoOutput(true);
        connection.setDoInput(true);
    }

    protected abstract void sendData() throws IOException;

    public String encodeMapToUrl(HashMap<String, String> data) throws IOException {
        String encodedString = "";

        for(HashMap.Entry<String, String> entry : data.entrySet())
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

    public static Response GET(String url, HashMap<String, String> params) throws IOException {
        Request request = new GetRequest();
        request.setUrl(url);
        request.setParams(params);
        request.execute();
        return request.getResponse();
    }

    public static Response POST(String url, HashMap<String, String> params) throws IOException {
        Request request = new PostRequest();
        request.setUrl(url);
        request.setParams(params);
        request.execute();
        return request.getResponse();
    }

    public static Response MULTIPART(String url, HashMap<String, String> params, HashMap<String, File> files) throws IOException {
        Request request = new MultipartRequest();
        request.setUrl(url);
        request.setParams(params);
        request.setFiles(files);
        request.execute();
        return request.getResponse();
    }

}