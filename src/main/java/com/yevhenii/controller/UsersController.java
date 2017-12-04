package com.yevhenii.controller;

import com.yevhenii.service.UsersService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService service;
    @RequestMapping(value = "/get/{user}")
    public JSONObject get(@PathVariable String user){
        return service.getUserByUsername(user);
    }

    @RequestMapping(value = "/get")
    public List getAll(){
        return service.getAllUsers();
    }

}
