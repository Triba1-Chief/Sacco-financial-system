import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * The RepaymentCommands class manages repayment operations for loans in the SACCO system.
 * It supports creating repayments, listing repayments by member or loan,
 * and reporting on outstanding loan balances.
 */


public class RepaymentCommands {
    private static final RepaymentCommands INSTANCE = new RepaymentCommands();

    private RepaymentCommands() {}

    public static RepaymentCommands getInstance() {
        return INSTANCE;
    }

    /**
     * Registers a new repayment for a specific member's loan.
     */
    public void registerRepayment(String memberId, String loanId, double amount) {
        assert Member.getMemberList().containsKey(memberId): "Invalid Member ID";
        assert Loan.getLoanList().containsKey(loanId): "Invalid Loan ID";
        assert amount > 0: "Repayment amount must be positive";

        // Member status check
        Member member = Member.getMemberList().get(memberId);
        if (!member.getStatus().equals("Active")) {
            throw new IllegalArgumentException("Member is not active. Unable to make repayment.");
        }

        // Optionally: prevent late repayment (if payment period passed)
        Loan loan = Loan.getLoanList().get(loanId);
        if (loan.getDate().plusYears(loan.getLoanType().getRepaymentPeriod()).isBefore(LocalDate.now())) {
            System.out.println("Warning: Repayment is late compared to agreed repayment period.");
            // You can choose to block or just flag this scenario.
        }

        // Prevent repayment if loan period expired (block if needed, else just warn as above)
        // if (loan.getDate().plusYears(loan.getLoanType().getRepaymentPeriod()).isBefore(LocalDate.now())) {
        //     throw new IllegalArgumentException("The repayment period for this loan has expired.");
        // }

        // Outstanding balance check—do not allow repayment if fully repaid
        double outstanding = getOutstandingBalanceForLoan(loanId);
        if (outstanding <= 0) {
            throw new IllegalArgumentException("Loan is already fully repaid. No further repayments allowed.");
        }

        // Prevent duplicate repayments by same member for the same loan today
        boolean alreadyRepaidToday = Repayment.getRepaymentsList().values().stream()
                .anyMatch(r -> r.getLoanId().equals(loanId)
                        && r.getaMemberID().equals(memberId)
                        && r.getaRepaymentDate().equals(LocalDate.now()));
        if (alreadyRepaidToday) {
            throw new IllegalArgumentException(
                    "Member has already made a repayment for this loan today."
            );
        }

        // Preventing overpayment
        double actualRepayAmount = Math.min(amount, outstanding);

        Repayment repayment = new Repayment(memberId, loanId, actualRepayAmount);
        System.out.println("Repayment registered: " + repayment);

        if (actualRepayAmount < amount) {
            System.out.println("Warning: Amount exceeded outstanding balance. Only the remaining balance was processed.");
        }
    }


    /**
     * Lists all repayments made by a member.
     */
    public List<Repayment> displayRepaymentsByMember(String memberId) {
        assert Member.getMemberList().containsKey(memberId): "Invalid Member ID";
        List<Repayment> repayments = Repayment.getRepaymentsList().values().stream()
                .filter(r -> r.getaMemberID().equals(memberId))
                .peek(System.out::println)
                .collect(Collectors.toList());
        System.out.println("Total repayments by member: " +
                repayments.stream().mapToDouble(Repayment::getaRepaymentAmount).sum());
        return repayments;
    }

    /**
     * Lists all repayments made for a loan.
     */
    public List<Repayment> displayRepaymentsByLoan(String loanId) {
        assert Loan.getLoanList().containsKey(loanId): "Invalid Loan ID";
        List<Repayment> repayments = Repayment.getRepaymentsList().values().stream()
                .filter(r -> r.getLoanId().equals(loanId))
                .peek(System.out::println)
                .collect(Collectors.toList());
        System.out.println("Total repayments for loan: " +
                repayments.stream().mapToDouble(Repayment::getaRepaymentAmount).sum());
        return repayments;
    }

    /**
     * Calculates the total amount repaid for a loan.
     */
    public double getTotalRepaidForLoan(String loanId) {
        assert Loan.getLoanList().containsKey(loanId): "Invalid Loan ID";
        return Repayment.getRepaymentsList().values().stream()
                .filter(r -> r.getLoanId().equals(loanId))
                .mapToDouble(Repayment::getaRepaymentAmount)
                .sum();
    }

    /**
     * Returns the outstanding balance for a loan.
     */
    public double getOutstandingBalanceForLoan(String loanId) {
        assert Loan.getLoanList().containsKey(loanId): "Invalid Loan ID";
        double totalToReturn = Loan.getLoanList().get(loanId).getReturnAmount();
        double totalRepaid = getTotalRepaidForLoan(loanId);
        double outstanding = totalToReturn - totalRepaid;
        System.out.println("Outstanding balance for loan " + loanId + ": " + outstanding);
        return outstanding;
    }

    /**
     * Prints details for all repayments in the system.
     */
    public void displayAllRepayments() {
        Repayment.getRepaymentsList().values().forEach(System.out::println);
    }
}

