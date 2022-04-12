package com.selfStudy_backend.service.social;

import com.selfStudy_backend.helper.constants.SocialLoginType;

public interface SocialOauth {
    String getOauthRedirectURL();

    String requestAccessToken(String code);

    default SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
        } else if (this instanceof NaverOauth) {
            return SocialLoginType.NAVER;
        } else {
            return null;
        }
    }
}
