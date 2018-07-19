package {{packageName}};

import {{MybatisModelQuatifiedName}};
import {{MybatisMapperQuatifiedName}};
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private TestModelMapper testModelMapper;
    @Test
    public void testMapper(){
        TestModel testModel = new TestModel();
        testModel.setAge(1);
        testModel.setCreatedAt(new Date());
        testModel.setUpdatedAt(new Date());
        testModel.setUserName("lalal");
        testModelMapper.insert(testModel);
    }
}