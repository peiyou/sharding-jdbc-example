package com.xintanyun.service.impl;

import com.xintanyun.entity.User;
import com.xintanyun.mapper.UserMapper;
import com.xintanyun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectByAll() {
        return userMapper.selectAll();
    }

    @Override
    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int insertBatch(List<User> users) {
        return userMapper.insertList(users);
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public User selectByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User selectByUserIdForLock(Long userId) {
        Example example = new Example(User.class);
        example.setForUpdate(true);
        example.createCriteria()
                .andEqualTo("id", userId);
        return userMapper.selectOneByExample(example);

    }
}
