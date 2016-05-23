package adec.tech.atms;

import adec.tech.atms.config.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jonathan Leijendekker
 *         Date: 3/9/2016
 *         Time: 4:10 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AtmsApplication.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class AtmsTest {

    @Test
    public void enumTest() {
        System.out.println(Constants.Status.getStatus(1));
    }

}
