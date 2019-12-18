package com.xintanyun.service;

import com.xintanyun.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectByAll();

    int insert(User user);

    int insertBatch(List<User> users);

    int updateById(User user);

    User selectByUserId(Long userId);

    User selectByUserIdForLock(Long userId);
}
