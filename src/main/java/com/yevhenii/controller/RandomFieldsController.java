package com.yevhenii.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Yevhenii on 29.11.2017.
 */

@Controller
@RequestMapping("random")
public class RandomFieldsController {

    @Autowired
    MongoTemplate template;

    @RequestMapping("/")
    public String getRandomForm(Model model){

        int r = Math.abs(new Random().nextInt())%10 + 1;
        System.out.println(r);
        model.addAttribute("random", r);
        return "index";
    }

    @RequestMapping("/add")
    public String add(@RequestParam Map<String, String> form){
        System.out.println(form.size());
        DBObject object = new BasicDBObject();
        form.keySet().forEach(key -> {
            object.put(key, form.get(key));
        });
        template.getCollection("test").insert(object, WriteConcern.MAJORITY);
        return "redirect:/";
    }

    @RequestMapping("/get")
    public String get(Model model){
        System.out.println(template.getCollection("test").count());
        List<Map> list = new ArrayList<>();
        DBCollection test = template.getCollection("test");
        List<DBObject> dbObjects = test.find().toArray();
        dbObjects.forEach(obj->{
            list.add(obj.toMap());
        });
        model.addAttribute("list", list);
        return "list";
    }
}
