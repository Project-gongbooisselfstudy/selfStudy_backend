package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.User;
import com.selfStudy_backend.helper.constants.SocialLoginType;
import com.selfStudy_backend.service.social.SocialOauth;

public interface OauthServices {
    void request(SocialLoginType socialLoginType);
    String requestAccessToken(SocialLoginType socialLoginType, String code);
    SocialOauth findSocialOauthByType(SocialLoginType socialLoginType);
    String saveUser(User user);

}
