package ch.schule.bank.junit5;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/** Hilfsklasse: fängt System.out eines Runnables ab. */
final class TestUtil {
    private TestUtil() {}

    static String capture(Runnable r) {
        PrintStream old = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try { r.run(); } finally { System.setOut(old); }
        return out.toString().replace("\r\n", "\n");
    }
}
