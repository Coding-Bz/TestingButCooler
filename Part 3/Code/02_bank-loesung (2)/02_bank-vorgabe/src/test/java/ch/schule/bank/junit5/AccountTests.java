package ch.schule.bank.junit5;

import ch.schule.Account;
import ch.schule.Booking;
import ch.schule.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests für die Klasse Account (über SavingsAccount instanziert, da abstract). */
public class AccountTests {
    private Account acc;

    @BeforeEach
    void setUp() { acc = new SavingsAccount("S-1"); }

    @Test
    public void testInit() {
        assertEquals("S-1", acc.getId());
        assertEquals(0, acc.getBalance());
        assertTrue(acc.canTransact(0));
    }

    @Test
    public void testDeposit() {
        assertTrue(acc.deposit(1, 1000));
        assertEquals(1000, acc.getBalance());
        assertTrue(acc.deposit(1, 0));
        assertFalse(acc.deposit(1, -1));
        assertFalse(acc.deposit(0, 100));   // Datum in der Vergangenheit
        assertEquals(1000, acc.getBalance());
    }

    @Test
    public void testWithdraw() {
        acc.deposit(1, 1000);
        assertTrue(acc.withdraw(2, 400));
        assertEquals(600, acc.getBalance());
        assertFalse(acc.withdraw(2, -5));
        assertFalse(acc.withdraw(1, 10));   // Datum in der Vergangenheit
        assertEquals(600, acc.getBalance());
    }

    @Test
    public void testReferences() {
        assertNull(acc.getBooking());
        Booking b = new Booking(1, 1);
        acc.setBooking(b);
        assertSame(b, acc.getBooking());
    }

    @Test
    public void testCanTransact() {
        acc.deposit(10, 100);
        assertTrue(acc.canTransact(10));
        assertTrue(acc.canTransact(11));
        assertFalse(acc.canTransact(9));
    }

    @Test
    public void testPrint() {
        acc.deposit(0, 10_000_000L);
        acc.withdraw(1, 5_000_000L);
        String[] l = TestUtil.capture(acc::print).split("\n");
        assertEquals("Kontoauszug 'S-1'", l[0]);
        assertEquals(4, l.length);
        assertTrue(l[2].startsWith("01.01.1970"));
        assertTrue(l[3].startsWith("02.01.1970"));
        assertTrue(l[3].contains("-50"));
    }

    @Test
    public void testMonthlyPrint() {
        // Banktag 0 = Januar 1970, Tag 30 = Februar 1970, Tag 60 = März 1970
        acc.deposit(5, 10_000_000L);
        acc.deposit(35, 10_000_000L);
        acc.deposit(65, 10_000_000L);
        String[] feb = TestUtil.capture(() -> acc.print(1970, 2)).split("\n");
        assertEquals("Kontoauszug 'S-1' Monat: 2.1970", feb[0]);
        assertEquals(3, feb.length);               // Header(2) + 1 Buchung
        assertTrue(feb[2].contains("200"));   // Saldo enthält Vormonat
        assertEquals(2, TestUtil.capture(() -> acc.print(1971, 1)).split("\n").length);
    }
}
