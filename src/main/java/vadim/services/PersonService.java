package vadim.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vadim.models.Contact;
import vadim.models.Person;
import vadim.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        for (Contact contact : contactService.getPersonContacts(person.getId())) {
            contactService.deleteContact(person.getId(), contact.getId());
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

    public List<Person> findPersonsWhoContainsName(String name) {
        List<Person> persons = personRepository.findAll();
        return personsContainsName(persons, name);
    }

    List<Person> personsContainsName(List<Person> persons, String name) {
        return persons.stream()
                .filter(p -> p.getName().trim().toLowerCase().contains(name.trim().toLowerCase()))
                .collect(Collectors.toList());
    }
}
