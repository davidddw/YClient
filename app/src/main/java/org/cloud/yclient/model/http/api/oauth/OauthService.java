package org.cloud.yclient.model.http.api.oauth;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/23
 */

public interface OauthService {
    @POST("oauth/token")
    Observable<OauthToken> getAccessToken(@Query("grant_type") String grantType, @Query("username") String username, @Query("password") String password);
}
