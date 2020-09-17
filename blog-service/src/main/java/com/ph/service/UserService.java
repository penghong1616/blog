package com.ph.service;

import com.ph.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * @author penghong
 * @date 2020/9/16
 */
@Service
public class UserService {
    private  static final Logger log= LoggerFactory.getLogger(UserService.class);
    /**
     *功能描述
     * @author qqg
     * @date 2020/9/16
     * @param user 用户登录信息
     * @return  登录信息
     */
    public User login(User user){

        return user;
    }
    public  Integer login(Integer id){
        log.info("{}进行登录",id);
        return id;
    }
}
