package ch.schule.bank.junit5;

import ch.schule.PromoYouthSavingsAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Tests für das Promo-Jugend-Sparkonto. */
public class PromoYouthSavingsAccountTests {
    @Test
    public void test() {
        PromoYouthSavingsAccount a = new PromoYouthSavingsAccount("Y-1");
        assertTrue(a.deposit(1, 10_000));
        assertEquals(10_100, a.getBalance());  // 1% Bonus
        a.deposit(1, 99);
        assertEquals(10_199, a.getBalance());  // Bonus 0 (ganzzahlig abgerundet)
        assertFalse(a.deposit(1, -100));
        assertTrue(a.withdraw(2, 199));        // erbt Sparkonto-Verhalten
        assertFalse(a.withdraw(2, 10_001));
    }
}
