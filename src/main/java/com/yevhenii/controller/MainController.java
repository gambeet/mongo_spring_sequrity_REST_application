package com.yevhenii.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String refPage(@RequestParam("token") String token, Model model){
        model.addAttribute("token", token);
        return "page";
    }
}
