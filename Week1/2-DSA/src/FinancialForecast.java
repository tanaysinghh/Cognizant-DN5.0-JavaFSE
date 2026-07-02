// Exercise 7: Financial Forecasting
// Predicts a future value by applying a growth rate year over year, using recursion.
public class FinancialForecast {

    // futureValue = presentValue * (1 + rate) ^ years, computed recursively.
    static double futureValue(double presentValue, double growthRate, int years) {
        // base case: no more years to grow
        if (years == 0) {
            return presentValue;
        }
        // grow one year, then recurse on the rest
        return futureValue(presentValue * (1 + growthRate), growthRate, years - 1);
    }

    public static void main(String[] args) {
        double startValue = 1000.0;   // starting amount
        double rate = 0.08;           // 8% growth per year
        int years = 5;

        double result = futureValue(startValue, rate, years);
        System.out.printf("Value after %d years: %.2f%n", years, result);
    }
}

/*
Complexity notes:
- This recursion runs once per year, so time complexity is O(n) where n = number of years.
- Plain recursion here just repeats a single multiplication, so it's fine.
- If a forecast recomputed overlapping sub-results (like naive Fibonacci does), we'd
  optimize with memoization (caching results) or an iterative loop to avoid repeated work.
*/
