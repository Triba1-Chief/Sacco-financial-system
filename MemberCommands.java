import java.time.LocalDate;
import java.util.List;


/**
 * Represents commands that can be applied to Members namely registering, deregistering,
 * getting their shares and contributions, and calculating total registration fees.
 **/


public class MemberCommands {
    private static final MemberCommands INSTANCE = new MemberCommands();

    private MemberCommands() {
    }

    public static MemberCommands getInstance() {
        return INSTANCE;
    }

    public void registerMember(String pName, Gender pGender, LocalDate pDateOfBirth) {
        Member member = new Member(pName, pGender, pDateOfBirth);
        System.out.println("Registered Member " + member);
    }

    public void deactivateMember(String pMemberId) {
        assert pMemberId != null && Member.getMemberList().containsKey(pMemberId) : "Invalid Member ID";

        System.out.println("Total amount to be given: " + calculateContributions(pMemberId));
        Member.getMemberList().get(pMemberId).deactivate();
    }

    public int getTotalRegistrationFees() {
        return Member.getMemberList().size() * Member.getRegistrationFee();
    }

    //Shares will be individual contributions/total group contributions
    public double getShares(String pMemberId) {
        assert Member.getMemberList().containsKey(pMemberId) : "Invalid Member ID";
        assert Member.getMemberList().get(pMemberId).getStatus().equals("Active") : "Member is not active";

        double totalShares = calculateContributions(pMemberId)/ContributionCommands.getInstance().displayContribution();
        System.out.println("Total shares: " + totalShares);
        return totalShares;
    }

    private double calculateContributions (String pMemberId) {
        double totalContributions = 0;

        List<Contribution> contributions = ContributionCommands.getInstance().displayContribution(pMemberId);
        for (Contribution c : contributions) {
            totalContributions += c.getAmount();
        }

        return totalContributions;
    }
}
