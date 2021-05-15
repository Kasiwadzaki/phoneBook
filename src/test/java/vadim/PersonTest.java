package vadim;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import vadim.models.Person;
import vadim.services.PersonService;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
public class PersonTest {

    @Autowired
    private PersonService personService;

    @Test
    @Sql("/twoPersons.sql")
    public void getAllPersons() {
        List<Person> personList = personService.getAllPersons();
        assertEquals(2, personList.size());
    }

    @Test
    @Sql("/twoPersons.sql")
    public void getOnePerson() {
        Person person = personService.getPerson(2L);
        assertEquals("Max", person.getName());
    }

    @Test
    @Sql("/twoPersons.sql")
    public void getPersonError() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> personService.getPerson(10L));
        assertExceptionMessage("No such user exists", exception);
    }

    @Test
    public void createPerson() {
        Person person = personService.createPerson(Person.builder()
                .name("Max")
                .phoneNumber("45678")
                .build());
        assertEquals(person, personService.getPerson(1L));
    }

    @Test
    public void createPersonErrorDataIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> personService.createPerson(null));
        assertExceptionMessage("Illegal arguments", exception);
    }

    @Test
    public void createPersonErrorNameIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> personService.createPerson(Person.builder()
                        .name(null)
                        .phoneNumber("1")
                        .build()));
        assertExceptionMessage("Name cannot be empty", exception);
    }

    @Test
    public void createPersonErrorPhoneNumberIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> personService.createPerson(Person.builder()
                        .name("Vadim")
                        .phoneNumber(null)
                        .build()));
        assertExceptionMessage("Phone number cannot be empty", exception);
    }

    @Test
    @Sql("/twoPersons.sql")
    public void updatePerson() {
        Person person = personService.updatePerson(1L, Person.builder()
                .name("Vadim")
                .phoneNumber("5678")
                .build());
        assertEquals(person, personService.getPerson(1L));
    }

    @Test
    @Sql("/twoPersons.sql")
    public void deletePerson() {
        List<Person> persons = personService.getAllPersons();
        personService.deletePerson(persons.get(1).getId());
        assertEquals(1, personService.getAllPersons().size());
    }

    private static void assertExceptionMessage(String expected, Exception exception) {
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expected));
    }
}