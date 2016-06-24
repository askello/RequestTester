package com.askello.requesttester.library;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by user_5 on 24.06.2016.
 */
public class Request {

    private URL url;
    private RequestMethod method;
    private HashMap<String, String> params;
    private HashMap<String, File> files;

    public Request() {
        this.method = RequestMethod.POST;
        this.params = new HashMap<String, String>();
        this.files = new HashMap<String, File>();
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addParam(String key, int value) {
        params.put(key, String.valueOf(value));
    }

    public void addParams(HashMap<String, String> params) {

    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void addFile(String key, File file) {
        files.put(key, file);
    }

}
