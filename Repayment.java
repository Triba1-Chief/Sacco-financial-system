import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Represents payment structure for the loans
 **/


public class Repayment {
    private final String aRepaymentId;
    private final String aMemberId;
    private final String aLoanId;
    private final double aRepaymentAmount;
    private final LocalDate aRepaymentDate;
    private static final Map<String,Repayment> AREPAYMENTSLIST = new ConcurrentHashMap<>();


    public Repayment(String aMemberId, String aLoanId, double aRepaymentAmount) {
        assert aRepaymentAmount > 0: "Invalid Repayment Amount";
        assert Member.getMemberList().containsKey(aMemberId): "Invalid Member ID";
        assert Loan.getLoanList().containsKey(aLoanId): "Invalid Loan ID";

        this.aRepaymentId = UUID.randomUUID().toString();
        this.aMemberId = aMemberId;
        this.aLoanId = aLoanId;
        this.aRepaymentAmount = aRepaymentAmount;
        this.aRepaymentDate = LocalDate.now();
        AREPAYMENTSLIST.put(aRepaymentId,this);
    }

    public static Map<String, Repayment> getRepaymentsList() {
        return AREPAYMENTSLIST;
    }

    public String getaMemberID() {
        return aMemberId;
    }

    public String getLoanId() { return this.aLoanId; }

    public double getaRepaymentAmount() {
        return aRepaymentAmount;
    }

    public LocalDate getaRepaymentDate() {
        return aRepaymentDate;
    }

}
