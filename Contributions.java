import java.time.LocalDate;
import java.util.*;



public class Contributions {
    private static int MIN_AMOUNT = 500;
    private static final Map<String, Contributions> ACONTRIBUTIONSLIST = new HashMap<>();
    private final String aContributionId;
    private final int aAmount;
    private final LocalDate aDate;
    private String aMemberId;
    


    public Contributions(String pMemberId, int pAmount) {
        assert Member.getMemberList().containsKey(pMemberId):"Invalid Member ID";

        if (pAmount < MIN_AMOUNT) {
            throw new IllegalArgumentException("Amount must be greater than 500");
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

    public int getAmount() {
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
    
    public static int getMinAmount() {
        return MIN_AMOUNT;
    }

    public static Map<String, Contributions> getContributionList() {
        return Collections.unmodifiableMap(ACONTRIBUTIONSLIST);
    }

    public static void setMinAmount(int newMinAmount) {
        assert newMinAmount > 0: "Minimum amount must be greater than 0";
        MIN_AMOUNT = newMinAmount;
    }

    public void setMemberId(String newMemberId) {
        assert Member.getMemberList().containsKey(newMemberId):"Invalid Member ID";
        this.aMemberId = newMemberId;
    }

    public boolean equals(Object o) {
        if (o instanceof Contributions c) {
            return this.aContributionId.equals(c.aContributionId);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(aContributionId);
    }
}
