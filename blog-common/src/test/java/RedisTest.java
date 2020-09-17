import com.ph.common.servcie.RedissonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author penghong
 * @date 2020/9/1714:55
 */
public class RedisTest {

    @Test
    public void set(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        RedissonService redissonService=context.getBean(RedissonService.class);
        //redissonService.setValue("name","ph");
        System.out.println(redissonService.getValue("name").toString());
    }
}
