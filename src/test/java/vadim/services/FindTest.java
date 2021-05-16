package vadim.services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import vadim.models.Person;
import vadim.services.PersonService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan
public class FindTest {

    @Autowired
    PersonService personService;

    private static Stream<Arguments> namesForPersonContainsName() {
        return Stream.of(
                Arguments.of(List.of("Vadim", "Andrey", "Clement"), "ad", 1),
                Arguments.of(List.of("Vadim", "Andrey", "Clement"), "a", 2),
                Arguments.of(List.of("Vadim", "Vladislav", "Vladimir"), "vla", 2),
                Arguments.of(List.of("Vadim", "Vladislav", "Vladimir"), "rr", 0),
                Arguments.of(List.of("Vadim Konstantinovich", "Vladislav Valerievich", "Vladimir Glebov"), "mk", 0),
                Arguments.of(List.of("Vadim Konstantinovich", "Vladislav Valerievich", "Vladimir Glebovich"), "ich", 3)
        );
    }

    @ParameterizedTest
    @MethodSource("namesForPersonContainsName")
    void personContainsName(List<String> names, String string, int expected) {
        List<Person> persons = names.stream()
                .map(name -> Person.builder()
                        .name(name)
                        .build())
                .collect(Collectors.toList());
        List<Person> personContainsName = personService.personsContainsName(persons, string);
        assertEquals(expected, personContainsName.size());
    }
}
