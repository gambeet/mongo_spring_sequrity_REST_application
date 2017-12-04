package com.yevhenii.service;

import com.mongodb.BasicDBList;
import com.yevhenii.repository.UsersRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Yevhenii on 04.12.2017.
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        JSONObject user = new JSONObject(userRepository.findUserByUsername(username).toMap());
        Set roles = Arrays.stream(((BasicDBList) user.get("roles")).toArray()).collect(Collectors.toSet());
        List<GrantedAuthority> authorities = buildUserAuthority(roles);

        return buildUserForAuthentication(user, authorities);

    }

    // Converts local user object to org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(JSONObject user,
                                            List<GrantedAuthority> authorities) {
        return new User((String) user.get("name"), (String) user.get("password"),
                true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<String> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (String userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }
}
