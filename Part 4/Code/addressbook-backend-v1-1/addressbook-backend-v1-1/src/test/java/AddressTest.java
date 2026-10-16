import ch.tbz.m450.repository.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address;
    private Date sampleDate;

    @BeforeEach
    void setUp() {
        sampleDate = new Date();
        address = new Address(1, 0, "Max", "Mustermann", "123456789", sampleDate);
    }

    @Test
    void testGettersAndAllArgsConstructor() {
        assertEquals(1, address.getId());
        assertEquals(0, address.getPriority());
        assertEquals("Max", address.getFirstname());
        assertEquals("Mustermann", address.getLastname());
        assertEquals("123456789", address.getPhonenumber());
        assertEquals(sampleDate, address.getRegistrationDate());
    }

    @Test
    void testSettersAndNoArgsConstructor() {
        // Testen des leeren Konstruktors und der Setter
        Address emptyAddress = new Address();

        emptyAddress.setId(2);
        emptyAddress.setPriority(1);
        emptyAddress.setFirstname("Erika");
        emptyAddress.setLastname("Musterfrau");
        emptyAddress.setPhonenumber("987654321");
        emptyAddress.setRegistrationDate(sampleDate);

        assertEquals(2, emptyAddress.getId());
        assertEquals(1, emptyAddress.getPriority());
        assertEquals("Erika", emptyAddress.getFirstname());
        assertEquals("Musterfrau", emptyAddress.getLastname());
        assertEquals("987654321", emptyAddress.getPhonenumber());
        assertEquals(sampleDate, emptyAddress.getRegistrationDate());
    }
}