package com.project.tracker.authentication.oauth2Service;

import com.project.tracker.models.Users;
import com.project.tracker.models.authmodels.Role;
import com.project.tracker.repositories.UsersRepository;
import com.project.tracker.sortingEnums.UserRolesEnum;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UsersRepository usersRepository;

    public CustomOauth2UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("OauthUser: " + oAuth2User.getAttributes());

        //extract user email from oAuth2User
        String email = oAuth2User.getAttribute("email");

        //check if the user already exists in our database
        if(!usersRepository.existsByEmail(email)){
            //create new user if user does not exist
            System.out.println("Creating new user: " + email);
            Users user = Users.builder()
                    .email(email)
                    .name(oAuth2User.getAttribute("name"))
                    .password("N/A")
                    .role(Role.builder().name((UserRolesEnum.ROLE_CONTRACTOR.toString())).build())
                    .build();
            user = usersRepository.save(user);
            System.out.println("User created: " + user.getEmail());
        }
        return oAuth2User;
    }
}
