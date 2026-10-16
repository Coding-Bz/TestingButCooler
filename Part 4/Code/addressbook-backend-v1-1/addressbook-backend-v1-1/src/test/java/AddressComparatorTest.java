import ch.tbz.m450.repository.Address;
import ch.tbz.m450.util.AddressComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AddressComparatorTest {

    private AddressComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new AddressComparator();
    }

    @Test
    void testCompareByDefaultFirstname() {
        Address a1 = new Address(1, null, "Zoe", "Smith", "123", new Date());
        Address a2 = new Address(2, null, "Adam", "Smith", "123", new Date());

        int result = comparator.compare(a1, a2);
        assertTrue(result > 0);
    }

    @Test
    void testCompareByPriorityLastname() {
        Address a1 = new Address(1, 1, "John", "Brown", "123", new Date());
        Address a2 = new Address(2, 1, "John", "Smith", "123", new Date());

        int result = comparator.compare(a1, a2);
        assertTrue(result < 0);
    }
}