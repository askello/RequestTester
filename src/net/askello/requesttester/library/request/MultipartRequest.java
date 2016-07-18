package net.askello.requesttester.library.request;

import java.io.*;

import static java.lang.System.currentTimeMillis;
import static java.net.URLConnection.guessContentTypeFromName;

/**
 * Created by user_5 on 24.06.2016.
 */
public class MultipartRequest extends Request {

    private OutputStream outputStream;
    private PrintWriter writer;

    private static final String CRLF = "\r\n";
    private final String boundary = "SuperMegaBoundary" + currentTimeMillis();

    @Override
    protected void setUpConnection() throws IOException {
        super.setUpConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setUseCaches(false);

        outputStream = connection.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
    }

    public void sendData() throws IOException {
        // print params
        for(String key : params.keySet()) {
            addFormField(key, params.get(key));
        }

        // print files
        for(String key : files.keySet()) {
            addFilePart(key, files.get(key));
        }

        finish();
    }

    public void addFormField(final String name, final String value) {
        writer.append("--").append(boundary).append(CRLF)
                .append("Content-Disposition: form-data; name=\"").append(name)
                .append("\"").append(CRLF)
                .append("Content-Type: text/plain; charset=").append(charset)
                .append(CRLF).append(CRLF).append(value).append(CRLF);
    }

    public void addFilePart(final String fieldName, final File uploadFile)
            throws IOException {
        final String fileName = uploadFile.getName();
        writer.append("--").append(boundary).append(CRLF)
                .append("Content-Disposition: form-data; name=\"")
                .append(fieldName).append("\"; filename=\"").append(fileName)
                .append("\"").append(CRLF).append("Content-Type: ")
                .append(guessContentTypeFromName(fileName)).append(CRLF)
                .append("Content-Transfer-Encoding: binary").append(CRLF)
                .append(CRLF);

        writer.flush();
        outputStream.flush();
        try (final FileInputStream inputStream = new FileInputStream(uploadFile);) {
            final byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }

        writer.append(CRLF);
    }

    public void addHeaderField(String name, String value) {
        writer.append(name).append(": ").append(value).append(CRLF);
    }

    public void finish() throws IOException {
        writer.append("--").append(boundary).append("--")
                .append(CRLF);
        writer.close();
    }

}
