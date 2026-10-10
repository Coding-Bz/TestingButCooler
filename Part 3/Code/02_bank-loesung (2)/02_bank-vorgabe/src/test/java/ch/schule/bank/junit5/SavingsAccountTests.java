package ch.schule.bank.junit5;

import ch.schule.SavingsAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests für die Klasse SavingsAccount. */
public class SavingsAccountTests {
    @Test
    public void test() {
        SavingsAccount a = new SavingsAccount("S-1");
        a.deposit(1, 1000);
        assertFalse(a.withdraw(2, 1001));      // kein Überziehen
        assertEquals(1000, a.getBalance());
        assertTrue(a.withdraw(2, 1000));       // genau auf 0 erlaubt
        assertEquals(0, a.getBalance());
        assertFalse(a.withdraw(2, -1));
    }
}
