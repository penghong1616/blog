package com.ph.dao;

import com.ph.pojo.Pager;
import com.ph.pojo.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author penghong
 * @date 2020/9/179:32
 */
@Repository
public class UserDao{
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession(){
       return sessionFactory.getCurrentSession();
    }
    public Pager<User> findAll(){
        Query<User> query = getSession().createQuery("from User");
        query.setFirstResult(1).setMaxResults(10);
        List<User> datas = query.list();
        Pager<User> pager = new Pager<User>();
        pager.setDatas(datas);
        pager.setOffset(0);
        pager.setSize(10);
        return  pager;
    }
    public  User saveUser(User user){
        getSession().save(user);
        return user;
    }
    public void updateUser(User user){
        getSession().update(user);
    }
    public void deleteUser(User user){
        getSession().delete(user);
    }
}
