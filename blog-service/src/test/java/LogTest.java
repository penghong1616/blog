import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static Logger log= LoggerFactory.getLogger(LogTest.class);
    @Test
    public void say(){
        log.info("Hello,World!!");
    }
}
