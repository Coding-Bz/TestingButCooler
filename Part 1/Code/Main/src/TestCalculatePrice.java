public class TestCalculatePrice {

CalculatePrice c = new CalculatePrice();
    public boolean test_calculate_price() {
        double price;
        boolean test_ok = true;

        // Neither extras nor any kind of discount
        price = c.calculatePrice(20000, 0, 0, 0, 0);
        if (price != 20000) {
            System.out.println("Test 1 failed: expected 20000, got " + price);
            test_ok = false;
        }

        // If we exactly have 3 discounts
        price = c.calculatePrice(20000, 0, 1000, 3, 0);
        if (price != 20900) {
            System.out.println("Test 2 failed: expected 20900, got " + price);
            test_ok = false;
        }

        // If we exactly have 5 discounts
        price = c.calculatePrice(20000, 0, 1000, 5, 0);
        if (price != 20850) {
            System.out.println("Test 3 failed: expected 20850, got " + price);
            test_ok = false;
        }
        return test_ok;
    }}
