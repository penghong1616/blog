import com.ph.dao.UserDao;
import com.ph.pojo.Pager;
import com.ph.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author penghong
 * @date 2020/9/179:37
 */
public class UserDaoTest {
    ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-dao.xml");
    UserDao userDao= (UserDao) context.getBean("userDao");
    @Test
    public void findAll(){

        Pager<User> pager=userDao.findAll();
        List<User> list=pager.getDatas();
        for (User user:list){
            System.out.println(user.toString());
        }
    }
    @Test
    public void save(){
        User user;
        user = new User();
        user.setUserName("penghong");
        userDao.saveUser(user);
        System.out.println(user.getId());
    }
    @Test
    public void updateUser(){
        User user=new User();
        user.setUserName("p1");
        user.setId(2);
        userDao.updateUser(user);
    }
    @Test
    public  void deleteUser(){
        User user=new User();
        user.setId(2);
        userDao.deleteUser(user);
    }
}
