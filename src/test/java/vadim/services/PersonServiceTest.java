package vadim.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import vadim.models.Person;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
@Sql("/twoPersons.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void getAllPersons() {
        List<Person> personList = personService.getAllPersons();
        assertEquals(2, personList.size());
    }

    @Test
    public void getOnePerson() {
        Person person = personService.getPerson(2L);
        assertEquals("Max", person.getName());
    }

    @Test
    public void getPersonError() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> personService.getPerson(100L));
        assertExceptionMessage("No such user exists", exception);
    }

    @Test
    public void createPerson() {
        System.out.println(personService.getAllPersons());
        Person person = personService.createPerson(Person.builder()
                .name("Max")
                .phoneNumber("45678")
                .build());
        System.out.println(personService.getAllPersons());
        assertEquals(person, personService.getPerson(3L));
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
    public void updatePerson() {
        Person person = personService.updatePerson(1L, Person.builder()
                .name("Vadim")
                .phoneNumber("5678")
                .build());
        assertEquals(person, personService.getPerson(1L));
    }

    @Test
    public void deletePerson() {
        List<Person> persons = personService.getAllPersons();
        personService.deletePerson(persons.get(1).getId());
        assertEquals(1, personService.getAllPersons().size());
    }

    @Test
    public void findPerson() {
        List<Person> persons = personService.findPersonsWhoContainsName("a");
        assertEquals(2, persons.size());
    }

    private static void assertExceptionMessage(String expected, Exception exception) {
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expected));
    }
}