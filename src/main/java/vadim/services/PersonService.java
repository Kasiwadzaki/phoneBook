package vadim.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vadim.models.Contact;
import vadim.models.Person;
import vadim.repositories.PersonRepository;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ContactService contactService;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPerson(Long id) {
        return personRepository.getPersonById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user exists"));
    }

    public Person createPerson(Person newPersonDetails) {
        if (newPersonDetails == null) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        if (newPersonDetails.getName() == null || newPersonDetails.getName().trim().equals("")) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (newPersonDetails.getPhoneNumber() == null || newPersonDetails.getPhoneNumber().trim().equals("")) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        Person person = Person.builder()
                .name(newPersonDetails.getName())
                .phoneNumber(newPersonDetails.getPhoneNumber())
                .build();
        save(person);
        return person;
    }

    public void deletePerson(Long id) {
        Person person = getPerson(id);
        for (Contact contact : person.getContacts()) {
            contactService.deleteContact(id, contact.getId());
        }
        personRepository.delete(person);
    }

    public Person updatePerson(Long id, Person updatedData) {
        Person person = getPerson(id);
        if (updatedData.getName() != null && !updatedData.getName().trim().equals("")) {
            person.setName(updatedData.getName());
        }
        if (updatedData.getPhoneNumber() != null && !updatedData.getName().trim().equals("")) {
            person.setPhoneNumber(updatedData.getPhoneNumber());
        }
        save(person);
        return person;
    }

    public void save(Person person) {
        personRepository.save(person);
    }
}