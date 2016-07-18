package net.askello.requesttester.library;

import java.io.File;

/**
 * Created by askello on 05.05.2016.
 */
public class Param {
    private String key;
    private String value;
    private File file;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Param(String key, File file) {
        this.key = key;
        this.file = file;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file==null ? key+"="+value : key+"="+file.getName();
    }
}
