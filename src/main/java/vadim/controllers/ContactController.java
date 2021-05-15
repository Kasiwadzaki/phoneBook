package vadim.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vadim.models.Contact;
import vadim.services.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/persons/{personId}/contacts")
public class ContactController {
    @Autowired
    ContactService contactService;

    @GetMapping
    public List<Contact> getPersonContacts(@PathVariable("personId") Long personId) {
        return contactService.getPersonContacts(personId);
    }

    @PostMapping
    public Contact createContact(@PathVariable("personId") Long personId, @RequestBody Contact contact) {
        return contactService.createContact(personId, contact);
    }

    @GetMapping("/{contactId}")
    public Contact getContact(@PathVariable("personId") Long personId, @PathVariable("contactId") Long contactId) {
        return contactService.getContact(personId, contactId);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContact(@PathVariable("personId") Long personId, @PathVariable("contactId") Long contactId) {
        contactService.deleteContact(personId, contactId);
    }

    @PutMapping("/{contactId}")
    public Contact updateContact(@PathVariable("personId") Long personId,
                                 @PathVariable("contactId") Long contactId, @RequestBody Contact updatedData) {
        return contactService.updateContact(personId, contactId, updatedData);
    }
}
