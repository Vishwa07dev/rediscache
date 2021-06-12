package com.vishwa.caching.redis.controller;

import com.vishwa.caching.redis.dao.UserDao;
import com.vishwa.caching.redis.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

  @Autowired
  private UserDao userDao ;


  @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 12000")
  @GetMapping(value = "/{userId}")
  public User getUser(@PathVariable String userId) {
    return userDao.findById(Long.valueOf(userId)).get();
  }

  @CachePut(value = "users", key = "#user.id")
  @PutMapping("/update")
  public User updatePersonByID(@RequestBody User user) {
    userDao.save(user);
    return user;
  }

}
