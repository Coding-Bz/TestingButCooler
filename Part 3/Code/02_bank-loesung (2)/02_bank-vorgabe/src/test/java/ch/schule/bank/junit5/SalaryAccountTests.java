package ch.schule.bank.junit5;

import ch.schule.SalaryAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests der Klasse SalaryAccount. */
public class SalaryAccountTests {
    @Test
    public void test() {
        SalaryAccount a = new SalaryAccount("P-1", -1000);
        assertTrue(a.withdraw(1, 1000));       // genau bis Limite
        assertEquals(-1000, a.getBalance());
        assertFalse(a.withdraw(1, 1));         // Limite überschritten
        assertTrue(a.deposit(2, 500));
        assertTrue(a.withdraw(2, 500));
        assertFalse(a.withdraw(2, -1));        // negativer Betrag
        assertFalse(a.withdraw(1, 0));         // Datum in der Vergangenheit
    }
}
