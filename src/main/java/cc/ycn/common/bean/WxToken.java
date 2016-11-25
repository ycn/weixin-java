package cc.ycn.common.bean;

import java.io.Serializable;

/**
 * Created by andy on 16/11/24.
 */
public class WxToken implements Serializable {

    private String token;
    private long expiresIn; // second

    public WxToken() {

    }

    public WxToken(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
