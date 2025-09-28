import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class provides a set of commands related to loan operations in a sacco management system.
 * It includes methods for determining loan approval, checking guarantor validity,
 * verifying loan amount constraints, calculating the total amount borrowed by members,
 * and determining the repayment amount for loans with interest.
 * The class operates as a singleton and provides shared functionality to be used across the system.
 */


public class LoanCommands {
    private static final LoanCommands INSTANCE = new LoanCommands();
    private static final String STATUS_INACTIVE = "Inactive";
    private static final String LOAN_APPROVED = "Approved";
    private double totalAmountLentOut = 0;

    public static LoanCommands getInstance() {
        return INSTANCE;
    }


    public boolean isApproved(String pMemberId) {
        //Check 1: Member is active
        if (Member.getMemberList().get(pMemberId).getStatus().equals(STATUS_INACTIVE)) {
            System.out.println("Member is not active");
            return false;
        }

        //Check 2: Meets contribution threshold. Strictly speaking this version isn't necessary as members are required
        // to contribute every month. This solution assumes otherwise.
        List<Contribution> checkMember = ContributionCommands.getInstance().displayContribution(pMemberId);
        if (hasContributedSixConsecutiveMonths(checkMember)) {
            System.out.println("Member doesn't have 6 consecutive months of contribution");
            return false;
        }

        return true;
    }

    public boolean guarantorCheck(LoanTypes pLoanType, String pGuarantor1, String pGuarantor2) {
        //Check 3: Guarantors are members
        if (!(Member.getMemberList().containsKey(pGuarantor1) && Member.getMemberList().containsKey(pGuarantor2))) {
            System.out.println("Guarantor(s) do not exist");
            return false;
        }

        //Check 4: Guarantors are current members
        if (Member.getMemberList().get(pGuarantor1).getStatus().equals(STATUS_INACTIVE) ||
                Member.getMemberList().get(pGuarantor2).getStatus().equals(STATUS_INACTIVE)) {
            System.out.println("Guarantor(s) are not members of the society");
        }

        //Check 5: Guarantors are "free" to guarantee loan
        if (Loan.getGuarantors().contains(Member.getMemberList().get(pGuarantor1)) ||
                Loan.getGuarantors().contains(Member.getMemberList().get(pGuarantor2))) {
            System.out.println("Guarantor(s) is guaranteeing another loan");
            return false;
        }

        //Check 6: Different guarantors
        if (Member.getMemberList().get(pGuarantor1).equals(Member.getMemberList().get(pGuarantor2))) {
            System.out.println("Guarantor(s) are the same");
            return false;
        }

        //Check 7: Age limits for guarantors
        if (Member.getMemberList().get(pGuarantor1).getAge() + pLoanType.getRepaymentPeriod() > Member.getMaxAge() ||
                Member.getMemberList().get(pGuarantor2).getAge() + pLoanType.getRepaymentPeriod() > Member.getMaxAge()){
            System.out.println("Guarantor(s) age prevents them from guaranteeing loan");
            return false;
        }

        return true;
    }

    public boolean amountCheck(String pMemberId, LoanTypes pLoanType, double pAmountBorrowed, double pGuarantor1Amount,
                               double pGuarantor2Amount) {
        assert pLoanType != null: "Loan type cannot be null";
        assert pAmountBorrowed > 0: "Amount cannot be zero or negative";

        //Check 8: Member's age will be within age limit after repayment
        if (Member.getMemberList().get(pMemberId).getAge() + pLoanType.getRepaymentPeriod() > Member.getMaxAge()) {
            System.out.println("Member's age will be beyond the age limit after repayment");
            return false;
        }

        //Check 9: Amount complies to loan requirements
        double memberShares = MemberCommands.getInstance().getShares(pMemberId);

        if (pAmountBorrowed > (pLoanType.getMaxLoanAmount()*memberShares)) {
            System.out.println("Loan amount exceeds maximum amount loanable");
            return false;
        }

        if ((pGuarantor1Amount + pGuarantor2Amount) < (pAmountBorrowed - memberShares)) {
            System.out.println("Loan amount exceeds amount available to guarantee");
            return false;
        }

        return true;
    }

    // Compound interest formula for n months: A = P*(1 + r)^n
    public double amountToReturn(double pAmountBorrowed, LoanTypes pLoanType) {
        int n = pLoanType.getRepaymentPeriod() * 12; // assuming years
        double r = pLoanType.getInterestRate(); // monthly rate
        return pAmountBorrowed * Math.pow(1+r, n);
    }

    //Total amount lent out by sacco
    public double totalAmountBorrowed() {
        totalAmountLentOut = 0;

        if (!(Loan.getLoanList().isEmpty())) {
            Loan.getLoanList().values().forEach(loan -> {
                if (loan != null && loan.getStatus().equals(LOAN_APPROVED)) {
                    totalAmountLentOut += loan.getAmountBorrowed();
                }
            });
        }

        return totalAmountLentOut;
    }

    private boolean hasContributedSixConsecutiveMonths(List<Contribution> contributions) {
        var months = contributions.stream()
                .map(c -> YearMonth.from(c.getDate()))
                .sorted()
                .distinct()
                .toList();

        int streak = 1;
        for (int i = 1; i < months.size(); i++) {
            if (months.get(i - 1).plusMonths(1).equals(months.get(i))) {
                streak++;
                if (streak >= 6) return true;
            } else {
                streak = 1;
            }
        }
        return false;
    }
}
