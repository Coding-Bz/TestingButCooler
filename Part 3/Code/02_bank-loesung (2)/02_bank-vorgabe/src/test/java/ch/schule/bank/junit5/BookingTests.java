package ch.schule.bank.junit5;

import ch.schule.Booking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests für die Klasse Booking. */
public class BookingTests {
    @Test
    public void testInitialization() {
        Booking b = new Booking(100, 5000);
        assertEquals(100, b.getDate());
        assertEquals(5000, b.getAmount());
        assertEquals(-5000, new Booking(0, -5000).getAmount());
    }

    @Test
    public void testPrint() {
        // 1.1.1970, 100.00 Fr. = 10'000'000 mRp (Zahlenformat ist locale-abhängig -> nur Teile prüfen)
        Booking b = new Booking(0, 10_000_000L);
        String line = TestUtil.capture(() -> b.print(0));
        assertTrue(line.startsWith("01.01.1970"));
        assertTrue(line.contains("100"));
    }
}
