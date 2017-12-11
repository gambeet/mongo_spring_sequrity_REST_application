package com.yevhenii.controller;

import com.yevhenii.configuration.filters.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Yevhenii on 05.12.2017.
 */
@Controller
@RequestMapping
public class MainController {

    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @Value("${security.header.name:Authorization}")
    private String securityHeaderName;

    @RequestMapping("/login")
    public ResponseEntity loginPage(@RequestParam("username") String username,
                                   @RequestParam("password") String password){
        try {
            String retVal = tokenAuthenticationService.getTokenByCreds(username, password);
            return ResponseEntity.ok().header(securityHeaderName, retVal).body(retVal);
        } catch(AuthenticationCredentialsNotFoundException acnfe) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @RequestMapping
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping("/page")
    public String refPage(@RequestParam("token") String token, Model model) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("info.properties")));
        int ms = Integer.valueOf(props.getProperty("js.request.freq", "5000"));
        model.addAttribute("time", ms);
        model.addAttribute("token", token);
        return "page";
    }
}
