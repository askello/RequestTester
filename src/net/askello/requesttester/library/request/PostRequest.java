package net.askello.requesttester.library.request;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by askello on 06.07.2016.
 */
public class PostRequest extends Request {

    @Override
    protected void setUpConnection() throws IOException {
        super.setUpConnection();
        connection.setRequestMethod("POST");
    }

    @Override
    public void sendData() throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(encodeMapToUrl(params));
        out.close();
    }

}
