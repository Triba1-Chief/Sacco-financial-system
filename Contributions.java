import java.time.LocalDate;
import java.util.*;


/**
 * Represents a contribution made by a member at the SACCO.
 **/


public class Contribution {
    private static double MIN_AMOUNT = 500;
    private static final Map<String, Contribution> ACONTRIBUTIONSLIST = new HashMap<>();
    private final String aContributionId;
    private final double aAmount;
    private final LocalDate aDate;
    private String aMemberId;
    

    public Contribution(String pMemberId, double pAmount) {
        assert Member.getMemberList().containsKey(pMemberId):"Invalid Member ID";

        if (pAmount < MIN_AMOUNT) {
            throw new IllegalArgumentException("Amount must be greater than 500");
        }

        if (Member.getMemberList().get(pMemberId).getStatus().equals("Inactive")) {
            throw new IllegalArgumentException("Member is no longer in the society");
        }

        if (Member.getMemberList().get(pMemberId).getAge() > Member.getMaxAge()) {
            MemberCommands.getInstance().deactivateMember(pMemberId);
            throw new IllegalArgumentException("Member is beyond the age limit of the society.");
        }

        this.aContributionId = UUID.randomUUID().toString();
        this.aAmount = pAmount;
        this.aMemberId = pMemberId;
        this.aDate = LocalDate.now();
        ACONTRIBUTIONSLIST.put(aContributionId,this);
    }

    public static void removeContribution(String pContributionId) {
        assert ACONTRIBUTIONSLIST.containsKey(pContributionId):"Invalid Contribution ID";
        ACONTRIBUTIONSLIST.remove(pContributionId);
    }

    public double getAmount() {
        return this.aAmount;
    }

    public LocalDate getDate() {
        return this.aDate;
    }
    
    public String getMemberId() {
        return this.aMemberId;
    }
    public String getContributionId() {
        return this.aContributionId;
    }

    public String toString() {
        return  "Contribution ID: " + this.getContributionId() +
                " Member: " + Member.getMemberList().get(this.aMemberId).getName() +
                " Amount: " + this.getAmount() +
                " Date: " + this.getDate();
    }
    
    public static double getMinAmount() {
        return MIN_AMOUNT;
    }

    public static Map<String, Contribution> getContributionList() {
        return Collections.unmodifiableMap(ACONTRIBUTIONSLIST);
    }

    public static void setMinAmount(double newMinAmount) {
        assert newMinAmount > 0: "Minimum amount must be greater than 0";
        MIN_AMOUNT = newMinAmount;
    }

    public void setMemberId(String newMemberId) {
        assert Member.getMemberList().containsKey(newMemberId):"Invalid Member ID";
        this.aMemberId = newMemberId;
    }

    public boolean equals(Object o) {
        if (o instanceof Contribution c) {
            return this.aContributionId.equals(c.aContributionId);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(aContributionId);
    }
}
