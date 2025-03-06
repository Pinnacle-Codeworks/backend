package com.markguiang.backend.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    //in memory for now
    List<User> users;

    public List<User> findAll() {
        return users;
    }
    public Boolean addUser(User user) {
        users.add(user);
        return true;
    }
}
