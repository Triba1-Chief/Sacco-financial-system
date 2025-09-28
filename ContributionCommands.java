import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


/**
 * The ContributionCommands class provides methods to manage and display contributions.
 * This class follows a Singleton design pattern to ensure a single instance is available throughout the application.
 * It allows querying contributions based on various criteria such as year, member, month, or combinations thereof,
 * and calculates total contributions for active members.
 */


public class ContributionCommands {
    private static final int INSTITUTION_FOUNDATION_YEAR = 2000;
    private static final String STATUS_ACTIVE = "Active";
    private double totalContributions = 0;
    private static final ContributionCommands INSTANCE = new ContributionCommands();


    private ContributionCommands() {}

    public static ContributionCommands getInstance() {
        return INSTANCE;
    }

    //Change 2000 into institution foundation year
    private List<Contribution> filterContributionsByYearAndPredicate(int year, Predicate<Contribution> predicate) {
        assert year > INSTITUTION_FOUNDATION_YEAR : "Invalid year";
        return Contribution.getContributionList().values().stream()
                .sorted(Comparator.comparing(Contribution::getDate)) // Sort contributions by date
                .filter(c -> c.getDate().getYear() == year)
                .filter(predicate)
                .peek(System.out::println)
                .toList();
    }

    //Shows all existing records for active members and calculates total contributions
    public double displayContribution() {
        totalContributions = 0;

        Contribution.getContributionList().values().forEach(c -> {
            if (c != null && Member.getMemberList().get(c.getMemberId()).getStatus().equals(STATUS_ACTIVE) ) {
                System.out.println(c);
                totalContributions += c.getAmount();
            }
        });

        System.out.println("Total contributions: " + totalContributions);
        return totalContributions;
    }

    //Show all existing records per year
    public List<Contribution> displayContribution(int pYear) {
        return filterContributionsByYearAndPredicate(pYear, contribution -> true);
    }

    //Show all existing records for a member
    public List<Contribution> displayContribution(String pMemberId) {
        assert Member.getMemberList().containsKey(pMemberId):"Invalid Member ID";

        return Contribution.getContributionList().values().stream()
                .filter(contribution -> contribution.getMemberId().equals(pMemberId))
                .peek(System.out::println)
                .toList();
    }

    //Show all existing records for each month in a certain year or for a member in a certain year
    public List<Contribution> displayContribution(String object, int pYear) {
        assert object != null && !object.isEmpty() && pYear > 2000 : "Invalid year";


        if (Member.getMemberList().containsKey(object)) {
            return filterContributionsByYearAndPredicate(pYear, contribution ->
                    contribution.getMemberId().equals(object));
        }
        else {
            return filterContributionsByYearAndPredicate(pYear, contribution ->
                    contribution.getDate().getMonth().toString().equalsIgnoreCase(object));
        }
    }

    //Show all existing records for a member in a certain month for a certain year
    public List<Contribution> displayContribution(String pMemberId, String pMonth, int pYear) {
        assert Member.getMemberList().containsKey(pMemberId):"Invalid Member ID";

        return filterContributionsByYearAndPredicate(pYear, contribution ->
                        contribution.getMemberId().equals(pMemberId) &&
                        contribution.getDate().getMonth().toString().equalsIgnoreCase(pMonth));
    }
}
