package com.askello.requesttester.library.request;

import java.io.IOException;

/**
 * Created by askello on 05.07.2016.
 */
public class GetRequest extends Request {

    @Override
    protected void setUpConnection() throws IOException {
        setUrl(url + "?" + urlEncodedParams());

        super.setUpConnection();
        connection.setRequestMethod("GET");
    }

    @Override
    public void sendData() throws IOException {
        // do nothing
    }

}
