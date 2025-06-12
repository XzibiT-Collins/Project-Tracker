package com.project.tracker.authentication.oauth2Service;

import com.project.tracker.models.Users;
import com.project.tracker.models.authmodels.Role;
import com.project.tracker.repositories.UsersRepository;
import com.project.tracker.sortingEnums.UserRolesEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UsersRepository usersRepository;

    public CustomOidcUserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OIDC User Service called");
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        if(!usersRepository.existsByEmail(email)){
            //create new user if user does not exist
            System.out.println("Creating new user: " + email);
            Users user = Users.builder()
                    .email(email)
                    .name(name)
                    .password("N/A")
                    .role(Role.builder().name((UserRolesEnum.ROLE_CONTRACTOR.toString())).build())
                    .build();
            user = usersRepository.save(user);
            System.out.println("User created: " + user.getEmail());
        }
        Set<GrantedAuthority> authorities = new HashSet<>(oidcUser.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(UserRolesEnum.ROLE_CONTRACTOR.toString()));

        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }
}
