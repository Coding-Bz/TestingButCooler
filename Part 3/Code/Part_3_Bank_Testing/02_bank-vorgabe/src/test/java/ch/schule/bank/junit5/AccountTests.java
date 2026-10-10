package ch.schule.bank.junit5;

import ch.schule.Account;
import ch.schule.Booking;
import ch.schule.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static ch.schule.bank.junit5.TestUtil.capture;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests für die Klasse Account.
 *
 * @author elifbcan
 * @version 2.0
 */


public class AccountTests {

    private Account acc;

    @BeforeEach
    void setUp() {
        acc = new SavingsAccount("S-1");
    }

    /**
     * Tested die Initialisierung eines Kontos.
     */
    @Test
    public void testInit() {
        assertNotNull(acc);

        assertEquals("S-1", acc.getId());

        assertEquals(0, acc.getBalance());

        assertTrue(acc.canTransact(0));
    }

    /**
     * Testet das Einzahlen auf ein Konto.
     */
    @Test
    public void testDeposit() {
        assertTrue(acc.deposit(1, 1029));
        assertEquals(1029, acc.getBalance());

        assertTrue(acc.deposit(2, 1));
        assertEquals(1030, acc.getBalance());

        assertTrue(acc.deposit(3, 10));
        assertEquals(1040, acc.getBalance());

        assertFalse(acc.deposit(4, -15));

        assertFalse(acc.deposit(-4, 1));

        assertFalse(acc.deposit(0, 15));
    }

    /**
     * Testet das Abheben von einem Konto.
     */
    @Test
    public void testWithdraw() {
        acc.deposit(1, 1040);

        assertTrue(acc.withdraw(2, 1029));

        assertEquals(11, acc.getBalance());

        assertTrue(acc.withdraw(3, 1));

        assertEquals(10, acc.getBalance());

        assertTrue(acc.withdraw(4, 10));

        assertEquals(0, acc.getBalance());

        assertFalse(acc.withdraw(4, -15));

        assertFalse(acc.withdraw(-4, 1));

        assertFalse(acc.withdraw(5, 15));

    }

    /**
     * Tests the reference from SavingsAccount
     */
    @Test
    public void testReferences() {
        Booking booking = new Booking(1, 100);
        acc.setBooking(booking);

        assertSame(booking, acc.getBooking());
    }

    /**
     * teste the canTransact Flag
     */
    @Test
    public void testCanTransact() {
        assertTrue(acc.canTransact(5));

        acc.deposit(10, 100);

        assertTrue(acc.canTransact(10));

        assertTrue(acc.canTransact(11));

        assertFalse(acc.canTransact(9));

        assertFalse(acc.deposit(9, 100));

        assertFalse(acc.deposit(-12, 100));
    }

    /**
     * Experimente mit print().
     */
    @Test
    public void testPrint() {
        acc.deposit(0, 100000);

        acc.withdraw(1, 50000);

        String out = capture(() -> acc.print());

        assertTrue(out.contains("Kontoauszug 'S-1'"));
        assertTrue(out.contains("01.01.1970"));
        assertTrue(out.contains("02.01.1970"));
    }

    /**
     * Experimente mit print(year,month).
     */
    @Test
    public void testMonthlyPrint() {
        acc.deposit(5, 100000);
        acc.deposit(35, 200000);

        String out = capture(() -> acc.print(1970, 1));

        assertTrue(out.contains("Monat: 1.1970"));
        assertTrue(out.contains("06.01.1970"));
        assertFalse(out.contains("06.02.1970"));
    }

}
