package org.cloud.yclient.model.http.api.oauth;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/23
 */

public class OauthToken {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String TOKEN_TYPE = "token_type";
    private static final String AUTHORIZATION = Base64.encodeToString("@{TODO}".getBytes(), Base64.NO_WRAP);

    @SerializedName(ACCESS_TOKEN)
    private String tokenType;
    @SerializedName(REFRESH_TOKEN)
    private String refreshToken;
    @SerializedName(TOKEN_TYPE)
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String bearer() {
        return String.format("Bearer %s", this.getAccessToken());
    }

    public static String basic() {
        return String.format("Basic %s", AUTHORIZATION);
    }

}
