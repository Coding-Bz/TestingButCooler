package ch.tbz.m450.consult;

public class AID {

    public int run(String a1, String a2) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\elifb\\.python-portable\\python3.15.0\\python\\python.exe",
                    "src/main/java/ch/tbz/m450/Consult/AID.py",
                    a1, a2
            );
            Process p = pb.start();
            String out = new String(p.getInputStream().readAllBytes()).trim();
            return Integer.parseInt(out);
        } catch (Exception e) {
            return 0;
        }
    }
}
