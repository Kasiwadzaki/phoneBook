package vadim;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import vadim.models.Contact;
import vadim.models.Person;
import vadim.services.ContactService;
import vadim.services.PersonService;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan
public class ContactServiceTest {
    @Autowired
    ContactService contactService;
    @Autowired
    PersonService personService;

    @Test
    @Sql("/personWithContacts.sql")
    public void getPersonContacts() {
        Person person = personService.getPerson(1L);
        List<Contact> contacts = contactService.getPersonContacts(person.getId());
        assertEquals(3, contacts.size());
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void getPersonContact() {
        Contact expected = Contact.builder()
                .id(2)
                .name("Valera")
                .phoneNumber("2345")
                .person(personService.getPerson(1L))
                .build();
        Contact actual = contactService.getContact(1L, 2L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void getPersonContactError() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> contactService.getContact(1L, 10L));
        assertExceptionMessage("No such contact exists", exception);
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void createContact() {
        Contact expected = Contact.builder()
                .name("Andrey")
                .phoneNumber("09876")
                .person(personService.getPerson(1L))
                .build();
        Contact actual = contactService.createContact(1L, expected);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void createContactErrorDataAndPersonIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> contactService.createContact(1L, null));
        assertExceptionMessage("Illegal arguments", exception);
        exception = assertThrows(IllegalArgumentException.class,
                () -> contactService.createContact(null, new Contact()));
        assertExceptionMessage("Illegal arguments", exception);
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void createContactErrorNameIsNull() {
        Contact contact = Contact.builder()
                .name(null)
                .phoneNumber("1")
                .build();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> contactService.createContact(1L, contact));
        assertExceptionMessage("Name cannot be empty", exception);
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void createContactErrorPhoneNumberIsNull() {
        Contact contact = Contact.builder()
                .name("Andrey")
                .phoneNumber(null)
                .build();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> contactService.createContact(1L, contact));
        assertExceptionMessage("Phone number cannot be empty", exception);
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void deleteContact() {
        contactService.deleteContact(1L, 2L);
        assertEquals(2, contactService.getPersonContacts(1L).size());
    }

    @Test
    @Sql("/personWithContacts.sql")
    public void updateContact() {
        Contact contact = contactService.updateContact(1L, 2L, Contact.builder()
                .name("Sergey")
                .phoneNumber("56784")
                .build());
        assertEquals(contact, contactService.getContact(1L, 2L));
    }

    private static void assertExceptionMessage(String expected, Exception exception) {
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expected));
    }
}
