package com.yevhenii.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepository {

    @Autowired
    MongoTemplate template;


    public DBObject findUserByUsername(String username) {
        return template.getCollection("users").findOne(new BasicDBObject("name", username));
    }

    public List findAll(){
        return template.getCollection("users").find().toArray();
    }

    public DBObject createUser(String username, String password, List<String> roles) {
        DBObject newUser = new BasicDBObject();
        newUser.put("name", username);
//        variant with open password storage
//        newUser.put("password", password);
        newUser.put("password", password);
        newUser.put("roles", roles);
        template.getCollection("users").save(newUser);
        return template.getCollection("users").findOne(newUser);
    }

}
