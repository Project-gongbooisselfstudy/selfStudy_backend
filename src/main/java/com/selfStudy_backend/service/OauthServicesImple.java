package com.selfStudy_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfStudy_backend.domain.User;
import com.selfStudy_backend.helper.constants.SocialLoginType;
import com.selfStudy_backend.repository.JDBCUserRepository;
import com.selfStudy_backend.service.social.SocialOauth;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class OauthServicesImple implements OauthServices{
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;
    private final JDBCUserRepository jdbcUserRepository;

    public OauthServicesImple(List<SocialOauth> socialOauthList, HttpServletResponse response, JDBCUserRepository jdbcUserRepository) {
        this.socialOauthList = socialOauthList;
        this.response = response;
        this.jdbcUserRepository = jdbcUserRepository;
    }
    @Override
    public void request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try{
            response.sendRedirect(redirectURL);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.requestAccessToken(code);
    }

    @Override
    public SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("알 수 없는 SocialLoginType입니다."));
    }

    @Override
    public String saveUser(User user) {
        jdbcUserRepository.saveUser(user);
        return user.getG_id();
    }

    public static JsonNode getGoogleUserInfo(String autorize_code) {
        final String RequestUrl = "https://www.googleapis.com/oauth2/v2/userinfo/?access_token="+autorize_code;

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet get = new HttpGet(RequestUrl);

        JsonNode returnNode = null;

        // add header
        get.addHeader("Authorization", "Bearer " + autorize_code);

        try {
            final HttpResponse response = client.execute(get);
            final int responseCode = response.getStatusLine().getStatusCode();

            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());

            System.out.println("\nSending 'GET' request to URL : " + RequestUrl);
            System.out.println("Response Code : " + responseCode);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
        }
        return returnNode;
    }

}
