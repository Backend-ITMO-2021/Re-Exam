package com.chatty.oauth;

import com.chatty.model.User;
import com.chatty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> oAuth2UserInfo = oAuth2User.getAttributes();
        System.out.println(oAuth2UserInfo);
        Optional<User> user = userRepository.findByEmail((String) oAuth2UserInfo.get("email"));

        if (user.isEmpty()) {
            this.registerNewUser(oAuth2UserInfo);
        } else {
            this.updateExistingUser(user.get());
        }
        return oAuth2User;
    }

    private void registerNewUser(Map<String, Object> oAuth2UserInfo) {
        User newUser = new User();
        System.out.println(oAuth2UserInfo);
        newUser.setEmail((String) oAuth2UserInfo.get("email"));
        newUser.setName((String) oAuth2UserInfo.get("name"));
        userRepository.save(newUser);
    }

    private void  updateExistingUser(User user) {
        userRepository.save(user);
    }
}
