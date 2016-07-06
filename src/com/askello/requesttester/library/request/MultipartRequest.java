package com.askello.requesttester.library.request;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static java.lang.System.currentTimeMillis;
import static java.net.URLConnection.guessContentTypeFromName;

/**
 * Created by user_5 on 24.06.2016.
 */
public class MultipartRequest extends Request {


    private StringBuilder outputData;
    private String boundary;
    private final String CRLF = "\r\n";

    public MultipartRequest() {
        super();

        this.outputData = new StringBuilder();
        this.boundary = "RequestBoundary" + currentTimeMillis();
    }


    @Override
    protected void setUpConnection() throws IOException {
        super.setUpConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setUseCaches(false);
    }

    @Override
    protected void sendData() throws IOException {
        prepareParamsToOutput();
        prepareFilesToOutput();
        endTheOutput();

        System.out.println(outputData);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(outputData.toString());
        out.close();
    }

    private void prepareParamsToOutput() throws IOException {
        for(HashMap.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            outputData
                    .append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(CRLF)
                    .append("Content-Type: text/plain; charset=").append(charset).append(CRLF)
                    .append(CRLF)
                    .append(value).append(CRLF);
        }
    }

    private void prepareFilesToOutput() throws IOException {
        for (HashMap.Entry<String, File> entry : files.entrySet()) {
            String key = entry.getKey();
            File file = entry.getValue();

            String fileName = file.getName();
            String fileData = getFileContent(file);

            outputData
                    .append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"").append(key).append("\"; filename=\"").append(fileName).append("\"").append(CRLF)
                    .append("Content-Type: ").append(guessContentTypeFromName(fileName)).append(CRLF)
                    .append("Content-Transfer-Encoding: binary").append(CRLF)
                    .append(CRLF)
                    .append(fileData).append(CRLF);
        }
    }

    private void endTheOutput() {
        outputData.append("--").append(boundary).append("--").append(CRLF);
    }

    private String getFileContent(File file) throws IOException {
        StringBuilder data = new StringBuilder();

        // get file bytes
        FileInputStream imageInFile = new FileInputStream(file);
        byte imageData[] = new byte[(int) file.length()];
        imageInFile.read(imageData);

        for (byte b : imageData) {
            data.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        return data.toString();
    }

}
