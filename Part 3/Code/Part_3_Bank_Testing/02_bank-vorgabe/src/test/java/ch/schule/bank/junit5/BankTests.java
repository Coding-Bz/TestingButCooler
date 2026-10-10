package ch.schule.bank.junit5;

import ch.schule.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ch.schule.bank.junit5.TestUtil.capture;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests für die Klasse 'Bank'.
 *
 * @author elifbcan
 * @version 2.0
 */
public class BankTests {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    /**
     * Tests to create new Accounts
     */
    @Test
    public void testCreate() {
        assertNotNull(bank);

        assertEquals("S-1000", bank.createSavingsAccount());

        assertEquals("Y-1001", bank.createPromoYouthSavingsAccount());

        assertEquals("P-1002", bank.createSalaryAccount(-1000));

        assertNull(bank.createSalaryAccount(1000));
    }

    /**
     * Testet das Einzahlen auf ein Konto.
     */
    @Test
    public void testDeposit() {
        String id = bank.createSavingsAccount();

        assertTrue(bank.deposit(id, 1, 1000));

        assertEquals(1000, bank.getBalance(id));

        assertFalse(bank.deposit("Enderson", 3, 4));

    }

    /**
     * Testet das Abheben von einem Konto.
     */
    @Test
    public void testWithdraw() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 1, 1000);

        assertTrue(bank.withdraw(id, 2, 400));

        assertEquals(600, bank.getBalance(id));

        assertFalse(bank.withdraw(id, 3, 10000));

        assertFalse(bank.withdraw("X-9", 3, 10));
    }

    /**
     * Experimente mit print().
     */
    @Test
    public void testPrint() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 0, 1000);

        String out = capture(() -> bank.print(id));

        assertTrue(out.contains("Kontoauszug 'S-1000'"));

        assertEquals("", capture(() -> bank.print("X-9")));
    }

    /**
     * Experimente mit print(year, month).
     */
    @Test
    public void testMonthlyPrint() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 5, 1000);

        String out = capture(() -> bank.print(id, 1970, 1));

        assertTrue(out.contains("Kontoauszug 'S-1000'"));

        assertTrue(out.contains("Monat: 1.1970"));
    }

    /**
     * Testet den Gesamtkontostand der Bank.
     */
    @Test
    public void testBalance() {
        assertEquals(0, bank.getBalance());

        String id1 = bank.createSavingsAccount();
        String id2 = bank.createSavingsAccount();
        bank.deposit(id1, 1, 1000);
        bank.deposit(id2, 1, 2000);

        assertEquals(-3000, bank.getBalance());

        assertEquals(0, bank.getBalance("X-9"));
    }

    private void createSixAccounts() {
        for (int i = 1; i <= 6; i++) {
            String id = bank.createSavingsAccount();
            bank.deposit(id, 1, i * 100);
        }
    }

    /**
     * Tested die Ausgabe der "top 5" konten.
     */
    @Test
    public void testTop5() {
        createSixAccounts();

        String out = capture(() -> bank.printTop5());

        assertTrue(out.contains("S-1005: 600"));

        assertFalse(out.contains("S-1000: 100"));
    }

    /**
     * Tested die Ausgabe der "top 5" konten.
     */
    @Test
    public void testBottom5() {
        createSixAccounts();

        String out = capture(() -> bank.printBottom5());

        assertTrue(out.contains("S-1000: 100"));

        assertFalse(out.contains("S-1005: 600"));
    }

}
