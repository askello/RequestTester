package net.askello.requesttester.library;

/**
 * Created by askello on 17.07.2016.
 */
public class Cookie {

    private String name;
    private String value;
    private String expires;
    private String MaxAge;
    private String path;
    private String domain;
    private String comment;
    private int version;
    private boolean secure;
    private boolean HttpOnly;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie(String cookieHeader) {
        String[] fields = cookieHeader.split("; ");

        for(String s : fields) {
            String[] pair = s.split("=");

            if(pair[0].equals("expires")) {
                expires = pair[1];
            }
            else if(pair[0].equals("Max-Age")) {
                MaxAge = pair[1];
            }
            else if(pair[0].equals("path")) {
                path = pair[1];
            }
            else if(pair[0].equals("domain")) {
                domain = pair[1];
            }
            else if(pair[0].equals("HttpOnly")) {
                HttpOnly = true;
            }
            else if(pair[0].equals("secure")) {
                secure = true;
            }
            else {
                name = pair[0];
                value = pair[1];
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getExpires() {
        return expires;
    }

    public String getMaxAge() {
        return MaxAge;
    }

    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }

    public String getComment() {
        return comment;
    }

    public int getVersion() {
        return version;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isHttpOnly() {
        return HttpOnly;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(name).append('=').append(value);
        if(expires!=null) str.append("; expires=").append(expires);
        if(MaxAge!=null) str.append("; Max-Age=").append(MaxAge);
        if(path!=null) str.append("; path=").append(path);
        if(domain!=null) str.append("; domain=").append(domain);
        if(secure) str.append("; secure");
        if(HttpOnly) str.append("; HttpOnly");

        return str.toString();
    }

}
