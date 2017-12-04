package com.yevhenii.service;

import com.yevhenii.repository.UsersRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yevhenii on 04.12.2017.
 */
@Service
public class UsersService {
    @Autowired
    private UsersRepository userRepository;

    public JSONObject getUserByUsername(String username) {
        JSONObject jsonObject = new JSONObject(userRepository.findUserByUsername(username).toMap());
        System.out.println(jsonObject);
        return jsonObject;
    }

    public List getAllUsers(){
        return userRepository.findAll();
    }
}
