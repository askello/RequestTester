package net.askello.requesttester.library.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by askello on 17.07.2016.
 */
public class Cookie {

    private String key;
    private String value;
    private String expires;
    private String MaxAge;
    private String path;
    private String domain;
    private String comment;
    private String version;
    private boolean secure;
    private boolean HttpOnly;

    public Cookie() {
        this.path = "/";
        this.secure = false;
        this.HttpOnly = false;

        setLifeTime(2592000); // 30 days
    }

    public Cookie(String key, String value) {
        super();
        this.key = key;
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
            else if(pair[0].equals("comment")) {
                comment = pair[1];
            }
            else if(pair[0].equals("version")) {
                version = pair[1];
            }
            else if(pair[0].equals("HttpOnly")) {
                HttpOnly = true;
            }
            else if(pair[0].equals("secure")) {
                secure = true;
            }
            else {
                key = pair[0];
                value = pair[1];
            }
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public void setMaxAge(String maxAge) {
        MaxAge = maxAge;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public void setHttpOnly(boolean httpOnly) {
        HttpOnly = httpOnly;
    }

    public String getKey() {
        return key;
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

    public String getVersion() {
        return version;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isHttpOnly() {
        return HttpOnly;
    }

    public void setLifeTime(int seconds) {
        this.MaxAge = String.valueOf(seconds);

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
        sdf.applyPattern("E, dd-MMM-yyyy HH:mm:ss z");
        Long time = (new Date().getTime()) + (seconds * 24);
        Date expiresDate = new Date(time);

        this.expires = sdf.format(expiresDate);
        this.MaxAge = String.valueOf(seconds);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(key).append('=').append(value);
        if(expires!=null) str.append("; expires=").append(expires);
        if(MaxAge!=null) str.append("; Max-Age=").append(MaxAge);
        if(path!=null) str.append("; path=").append(path);
        if(domain!=null) str.append("; domain=").append(domain);
        if(secure) str.append("; secure");
        if(HttpOnly) str.append("; HttpOnly");

        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookie cookie = (Cookie) o;

        if (secure != cookie.secure) return false;
        if (HttpOnly != cookie.HttpOnly) return false;
        if (key != null ? !key.equals(cookie.key) : cookie.key != null) return false;
        if (value != null ? !value.equals(cookie.value) : cookie.value != null) return false;
        if (expires != null ? !expires.equals(cookie.expires) : cookie.expires != null) return false;
        if (MaxAge != null ? !MaxAge.equals(cookie.MaxAge) : cookie.MaxAge != null) return false;
        if (path != null ? !path.equals(cookie.path) : cookie.path != null) return false;
        if (domain != null ? !domain.equals(cookie.domain) : cookie.domain != null) return false;
        if (comment != null ? !comment.equals(cookie.comment) : cookie.comment != null) return false;
        return version != null ? version.equals(cookie.version) : cookie.version == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (expires != null ? expires.hashCode() : 0);
        result = 31 * result + (MaxAge != null ? MaxAge.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (secure ? 1 : 0);
        result = 31 * result + (HttpOnly ? 1 : 0);
        return result;
    }
}
