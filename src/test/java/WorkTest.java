
import com.zime.ojdemo.OjdemoApplication;
import com.zime.ojdemo.service.WorkService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OjdemoApplication.class)
public class WorkTest {
    @Autowired
    WorkService workService;

    @Test
    public void test(){
        workService.getworkrank(-2017509374);
    }
}
