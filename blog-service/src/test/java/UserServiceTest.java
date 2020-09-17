import com.ph.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {
    @Test
    public  void login(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService userService= (UserService) context.getBean("userService");
        context.getBean("appListener");
        Assert.assertSame(1,userService.login(1));
    }
}
