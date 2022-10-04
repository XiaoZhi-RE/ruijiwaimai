import com.xiaozhi.MyApplication;
import com.xiaozhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = MyApplication.class)
@RunWith(SpringRunner.class)
public class TestOne {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;



    @Test
    public void test() {

    }
}
