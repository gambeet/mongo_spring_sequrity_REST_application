package com.yevhenii.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yevhenii on 29.11.2017.
 */

@Controller
@RequestMapping("/random")
public class RandomFieldsController {

    private static Logger LOGGER = LoggerFactory.getLogger(RandomFieldsController.class);

    @Autowired
    MongoTemplate template;

    ExecutorService threadPool = Executors.newCachedThreadPool();

    @RequestMapping()
    public String getRandomForm(Model model) {

        int r = Math.abs(new Random().nextInt()) % 10 + 1;
        System.out.println(r);
        model.addAttribute("random", r);
        return "index";
    }

    private static final Object monitor = new Object();


    @RequestMapping("/add")
    public String add(@RequestParam Map<String, String> form) {
        System.out.println(form.size());
        DBObject object = new BasicDBObject();
        template.getCollection("test").save(object);
        LOGGER.info("new item _id= " + object.get("_id").toString());
        BasicDBObject query = new BasicDBObject("_id", new ObjectId(object.get("_id").toString()));
        AtomicInteger current = new AtomicInteger(0);
        for (int i = 0; i < form.keySet().size(); i++) {
            String key = form.keySet().toArray()[i].toString();
            int finalI = i;
            threadPool.submit(() -> {
                DBObject item;
                synchronized (monitor) {
                    while (current.get() < finalI) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    item = template.getCollection("test").find(query).toArray().get(0);
                    item.put(key, form.get(key));
                    LOGGER.info(key + " " + form.get(key));
                    template.getCollection("test").save(item);
                    LOGGER.info("current= " + current.incrementAndGet());
                    monitor.notifyAll();
                }
            });
        }

        return "redirect:/random";
    }

    @RequestMapping("/get")
    public String get(Model model) {
        System.out.println(template.getCollection("test").count());
        List<Map> list = new ArrayList<>();
        DBCollection test = template.getCollection("test");
        List<DBObject> dbObjects = test.find().toArray();
        dbObjects.forEach(obj -> {
            list.add(obj.toMap());
        });
        model.addAttribute("list", list);
        return "list";
    }
}
