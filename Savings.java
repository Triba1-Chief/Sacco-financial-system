import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The Savings class manages group fixed deposits and calculates interest revenue,
 * supporting month-by-month saving, historical queries, and share-based year-end dividend distribution.
 */


public class Savings {
    private final String aSavingId;
    private final double depositAmount;  // Amount put in fixed deposit for this month
    private double interestRate = 0.006; // monthly interest
    private static final Map<String, Savings> ASAVINGSLIST = new ConcurrentHashMap<>();

    /**
     * Constructs a savings record for the current month, using current unborrowed group money.
     */
    public Savings() {
        this.aSavingId = LocalDate.now().getMonth().toString() + "-" + LocalDate.now().getYear();

        // Amount is (total contributions - total loans out) as of now
        this.depositAmount = Math.max(0, ContributionCommands.getInstance().displayContribution()
                - LoanCommands.getInstance().totalAmountBorrowed());
        ASAVINGSLIST.put(aSavingId, this);
    }

    public String getSavingsTime() {
        return aSavingId;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    /**
     * Interest earned for THIS month’s deposit (simple, not compounded yet).
     */
    public double fixedDepositsInterest() {
        return depositAmount * interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public static Map<String, Savings> getSavingsList() {
        return Collections.unmodifiableMap(ASAVINGSLIST);
    }

    @Override
    public String toString() {
        return "Time(Month-Year): " + aSavingId + " | " +
                "Interest rate per month: " + interestRate + " | " +
                "Fixed Deposits: " + depositAmount + " | " +
                "Interest earned this month: " + fixedDepositsInterest();
    }
}
