package vadim.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vadim.models.Contact;
import vadim.models.Person;
import vadim.repositories.ContactRepository;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PersonService personService;

    public List<Contact> getPersonContacts(Long id) {
        Person person = personService.getPerson(id);
        return contactRepository.getAllByPerson(person)
                .orElseThrow(() -> new IllegalArgumentException("No such contacts exists"));
    }

    public Contact getContact(Long personId, Long contactId) {
        Person person = personService.getPerson(personId);
        return contactRepository.getContactByPersonAndId(person, contactId)
                .orElseThrow(() -> new IllegalArgumentException("No such contact exists"));
    }

    public Contact createContact(Long personId, Contact newContactDetails) {
        if (newContactDetails == null || personId == null) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        if (newContactDetails.getName() == null || newContactDetails.getName().trim().equals("")) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (newContactDetails.getPhoneNumber() == null || newContactDetails.getPhoneNumber().trim().equals("")) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        Person person = personService.getPerson(personId);
        Contact contact = Contact.builder()
                .name(newContactDetails.getName())
                .phoneNumber(newContactDetails.getPhoneNumber())
                .person(person)
                .build();
        contactRepository.save(contact);
        return contact;
    }

    public void deleteContact(Long personId, Long contactId) {
        Contact contact = getContact(personId, contactId);
        contactRepository.delete(contact);
    }

    public Contact updateContact(Long personId, Long contactId, Contact updatedData) {
        Contact contact = getContact(personId, contactId);
        if (updatedData.getName() != null && !updatedData.getName().trim().equals("")) {
            contact.setName(updatedData.getName());
        }
        if (updatedData.getPhoneNumber() != null && !updatedData.getPhoneNumber().trim().equals("")) {
            contact.setPhoneNumber(updatedData.getPhoneNumber());
        }
        contactRepository.save(contact);
        return contact;
    }
}
