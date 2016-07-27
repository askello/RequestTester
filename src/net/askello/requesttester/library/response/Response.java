package net.askello.requesttester.library.response;

import net.askello.requesttester.library.common.Cookie;
import net.askello.requesttester.library.common.Param;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by askello on 15.07.2016.
 */
public class Response {

    private HttpURLConnection connection;

    private String content;
    private ArrayList<Cookie> cookies;
    private ArrayList<Param> headers;

    public Response(HttpURLConnection connection) throws IOException {
        this.connection = connection;
        prepareContent();
        prepareHeadersAndCookies();
    }

    public int getCode() throws IOException {
        return connection.getResponseCode();
    }

    public ArrayList<Cookie> getCookies() {
        return cookies;
    }

    public ArrayList<Param> getHeaders() {
        return headers;
    }

    public String getContent() {
        return content;
    }

    public void prepareContent() throws IOException {
        content = new String();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line = null;
        while ((line = in.readLine()) != null)
            content += line;
        in.close();
    }

    private void prepareHeadersAndCookies() {
        headers = new ArrayList<Param>();
        cookies = new ArrayList<Cookie>();

        for (int i = 0;; i++) {
            String headerName = connection.getHeaderFieldKey(i);
            String headerValue = connection.getHeaderField(i);

            if (headerName == null && headerValue == null) break;

            headers.add(new Param(headerName, headerValue));

            // cookies
            if ("Set-Cookie".equalsIgnoreCase(headerName)) {
                cookies.add(new Cookie(headerValue));
            }
        }
    }

    @Override
    public String toString() {
        return content;
    }

}
