package com.ph.mapper;

import com.ph.pojo.User;

import javax.persistence.Entity;

/**
 * @author penghong
 * @date 2020/9/1614:39
 */
public interface UserMapper {
    /**
     * 保存用户
     *
     * @param user 用户
     * @return 写回主键
     */
    User saveUser(User user);

    User queryUser(Integer id);


}
