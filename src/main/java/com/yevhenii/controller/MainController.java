package com.yevhenii.controller;

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

    @RequestMapping
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping("/page")
    public String refPage(@RequestParam("token") String token, Model model) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("info.properties")));
        int ms = Integer.valueOf(props.getProperty("js.request.freq", "5000"));
        System.out.println(ms);
        model.addAttribute("time", ms);
        model.addAttribute("token", token);
        return "page";
    }
}
