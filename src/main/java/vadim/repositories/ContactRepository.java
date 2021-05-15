package vadim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadim.models.Contact;
import vadim.models.Person;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> getContactByPersonAndId(@NotNull Person person, long id);

    Optional<List<Contact>> getAllByPerson(@NotNull Person person);
}
