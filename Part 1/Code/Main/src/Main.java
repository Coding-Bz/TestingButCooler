public class Main {
    public static void main(String[] args) {
        TestCalculatePrice test = new TestCalculatePrice();
        boolean result = test.test_calculate_price();
        if (result) {
            System.out.println("Test Passed");
            return;
        }
        System.out.println("Test Failed");
    }

}
