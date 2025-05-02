package com.store.seafoodveggies.service;

import com.store.seafoodveggies.entity.Address;
import com.store.seafoodveggies.entity.User;

public interface UserService {
     public User saveUser(User user);
     User findByUserName(String userName);
     void deleteUser(Long userId);
     User updateUser(Long id, User updatedUser);
}
