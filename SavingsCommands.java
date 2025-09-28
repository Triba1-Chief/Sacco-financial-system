import java.util.Map;

public class SavingsCommands {
    /**
     * Get total (simple) group savings interest for a given year.
     */
    public static double totalFixedDepositsYear(int year) {
        return Savings.getSavingsList().values().stream()
                .filter(s -> s.getSavingsTime().endsWith("-" + year))
                .mapToDouble(Savings::fixedDepositsInterest)
                .sum();
    }

    /**
     * Compute a member’s total contributions for that year.
     */
    public static double memberContributionForYear(String memberId, int year) {
        return Contribution.getContributionList().values().stream()
                .filter(c -> c.getMemberId().equals(memberId))
                .filter(c -> c.getDate().getYear() == year)
                .mapToDouble(Contribution::getAmount)
                .sum();
    }

    /**
     * Compute group total contributions for that year.
     */
    public static double groupContributionForYear(int year) {
        return Contribution.getContributionList().values().stream()
                .filter(c -> c.getDate().getYear() == year)
                .mapToDouble(Contribution::getAmount)
                .sum();
    }

    /**
     * Returns the share of fixed deposit interest for a member for specified year (for dividend).
     */
    public static double memberShareOfSavings(String memberId, int year) {
        double memberContributions = memberContributionForYear(memberId, year);
        double groupContributions = groupContributionForYear(year);
        double totalSavingsInterest = totalFixedDepositsYear(year);

        if (groupContributions == 0) return 0;
        return (memberContributions / groupContributions) * totalSavingsInterest;
    }

    /**
     * Compound interest calculation (if needed for multi-period).
     */
    public static double compoundInterest(double principal, double monthlyRate, int months) {
        return principal * Math.pow(1 + monthlyRate, months);
    }

    /**
     * Year-End Dividend and Interest Report.
     */
    public static void yearEndReport(int year) {
        double totalInterest = totalFixedDepositsYear(year);
        System.out.println("Year-End Total Savings Interest: " + totalInterest);
        Map<String, Member> members = Member.getMemberList();
        members.values().forEach(member -> {
            double share = memberShareOfSavings(member.getId(), year);
            System.out.println("Member " + member.getName() + " dividend share: " + share);
        });
    }
}
