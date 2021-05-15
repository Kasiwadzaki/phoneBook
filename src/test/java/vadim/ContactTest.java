package vadim;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import vadim.services.ContactService;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
public class ContactTest {
    @Autowired
    ContactService contactService;

}
