package ch.schule.bank.junit5;

import ch.schule.SalaryAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests der Klasse SalaryAccount.
 *
 * @author elifbcan
 * @version 2.0
 */
public class SalaryAccountTests
{
    /**
     * Der Test.
     */
    @Test
    public void test()
    {
        SalaryAccount acc = new SalaryAccount("P-1", -10000);

        acc.deposit(1, 500);

        assertTrue(acc.withdraw(2, 10500));

        assertEquals(-10000, acc.getBalance());

        assertFalse(acc.withdraw(3, 1));

        assertEquals(-10000, acc.getBalance());
    }
}