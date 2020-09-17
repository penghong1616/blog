import com.ph.mapper.UserMapper;
import com.ph.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author penghong
 * @date 2020/9/1615:33
 */
public class UserMapperTest {
    @Test
    public void save(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-dao.xml");
        UserMapper userMapper = context.getBean(UserMapper.class);
        userMapper.queryUser(1);
        //User user=new User();
        //user.setUserName("ph");
        //userMapper.saveUser(user);
       // System.out.println(user.getId());
    }
}
