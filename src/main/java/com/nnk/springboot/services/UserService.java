package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    public User insert(User user) ;


    List<User> findAllUsers();


    public void delete(User user);

    public User findUser(Integer id);

    public boolean existsById(int id);


}
