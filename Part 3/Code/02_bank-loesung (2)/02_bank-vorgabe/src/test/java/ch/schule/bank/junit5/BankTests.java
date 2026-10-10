package ch.schule.bank.junit5;

import ch.schule.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests für die Klasse 'Bank'. */
public class BankTests {
    private Bank bank;

    @BeforeEach
    void setUp() { bank = new Bank(); }

    @Test
    public void testCreate() {
        assertEquals("S-1000", bank.createSavingsAccount());
        assertEquals("Y-1001", bank.createPromoYouthSavingsAccount());
        assertEquals("P-1002", bank.createSalaryAccount(-5000));
        assertEquals("P-1003", bank.createSalaryAccount(0));
        assertNull(bank.createSalaryAccount(1));
        assertEquals("S-1004", bank.createSavingsAccount()); // null verbraucht keine Nummer
    }

    @Test
    public void testDeposit() {
        String id = bank.createSavingsAccount();
        assertTrue(bank.deposit(id, 1, 500));
        assertEquals(500, bank.getBalance(id));
        assertFalse(bank.deposit("X-0", 1, 500));
        assertFalse(bank.deposit(id, 1, -1));
    }

    @Test
    public void testWithdraw() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 1, 500);
        assertTrue(bank.withdraw(id, 2, 200));
        assertEquals(300, bank.getBalance(id));
        assertFalse(bank.withdraw(id, 2, 301));
        assertFalse(bank.withdraw("X-0", 2, 1));
        assertEquals(0, bank.getBalance("X-0"));
    }

    @Test
    public void testPrint() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 0, 10_000_000L);
        assertTrue(TestUtil.capture(() -> bank.print(id)).startsWith("Kontoauszug 'S-1000'"));
        assertEquals("", TestUtil.capture(() -> bank.print("X-0")));
    }

    @Test
    public void testMonthlyPrint() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 0, 10_000_000L);
        assertTrue(TestUtil.capture(() -> bank.print(id, 1970, 1)).contains("Monat: 1.1970"));
        assertEquals("", TestUtil.capture(() -> bank.print("X-0", 1970, 1)));
    }

    @Test
    public void testBalance() {
        assertEquals(0, bank.getBalance());
        String a = bank.createSavingsAccount();
        String b = bank.createSavingsAccount();
        bank.deposit(a, 1, 300);
        bank.deposit(b, 1, 200);
        assertEquals(-500, bank.getBalance()); // Bank schuldet den Kunden Geld
    }

    private void fill() {
        for (int i = 1; i <= 6; i++) {
            String id = bank.createSavingsAccount();
            bank.deposit(id, 1, i * 100L);
        }
    }

    @Test
    public void testTop5() {
        fill();
        String[] l = TestUtil.capture(bank::printTop5).split("\n");
        assertEquals(5, l.length);
        assertEquals("S-1005: 600", l[0]);
        assertEquals("S-1001: 200", l[4]);
        // weniger als 5 Konten
        Bank small = new Bank();
        small.createSavingsAccount();
        assertEquals(1, TestUtil.capture(small::printTop5).split("\n").length);
    }

    @Test
    public void testBottom5() {
        fill();
        String[] l = TestUtil.capture(bank::printBottom5).split("\n");
        assertEquals(5, l.length);
        assertEquals("S-1000: 100", l[0]);
        assertEquals("S-1004: 500", l[4]);
    }

    @Test
    public void testAccountProperty() {
        assertNull(bank.getAccount());
    }
}
