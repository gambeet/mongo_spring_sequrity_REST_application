package com.yevhenii.controller;

import com.yevhenii.service.UsersService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    private static Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersService service;
    @RequestMapping(value = "/get/{user}")
    public JSONObject get(@PathVariable String user){
        JSONObject userByUsername = service.getUserByUsername(user);
        return service.getUserByUsername(user);
    }

    @RequestMapping(value = "/get")
    public List getAll(){
        return service.getAllUsers();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    String createUser(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam("roles") String roles) {
        LOGGER.info("POST Params: user="+ username + ", password="+ password +", roles=" + roles);
        service.createUser(username, password, Arrays.stream(roles.split(",")).collect(Collectors.toList()));
        return Optional.ofNullable(service.getUserByUsername(username)).orElse(new JSONObject()).toString();
    }

}
