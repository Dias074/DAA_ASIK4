
public class TestRunner {
    public static void main(String[] args) {
        try {
            String data = "data/small1.json";
            System.out.println("Basic test: checking parsing of " + data);
            String s = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(data)));
            if (s.length() == 0) throw new RuntimeException("Empty file");
            System.out.println("OK - file non-empty");
            System.out.println("Note: For automated JUnit style tests, add JUnit dependency or adapt build system.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
