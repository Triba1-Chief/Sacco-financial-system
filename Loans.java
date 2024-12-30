import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



/**
 * The Loans class represents a loan taken by a member with specified attributes like
 * loan type, borrowed amount, guarantors, and their associated amounts. The class includes
 * immutable fields for loan details, methods to retrieve loan information, and static methods
 * to manage loan records.
 *
 * This class enforces certain rules during the creation of a loan, including member approval,
 * guarantor checks, and amount validity, to ensure that only valid loans are created. Once a loan
 * is created, its primary information cannot be changed.
 *
 * Key responsibilities of this class are:
 * - Managing the details of a loan such as loan type, borrowed amount, guarantors, and amounts.
 * - Storing and retrieving loan data using static collections.
 * - Ensuring immutability for most of the loan attributes.
 *
 * The class uses UUIDs to uniquely identify each loan instance and relies on external validation
 * commands from the LoanCommands class for creation criteria.
 */


public class Loans {
    private final String aLoanId;
    private final String aMemberId;
    private final LoanTypes aLoanType;
    private final double aAmountBorrowed;
    private final double aReturnAmount;
    private final Member aGuarantor1;
    private final Member aGuarantor2;
    private final double aGuarantor1Amount;
    private final double aGuarantor2Amount;
    private final LocalDate aDate; //Once approved it can't be changed
    private String status;
    private static List<Member> aGuarantors;
    private static final Map<String,Loans> ALOANLIST = new ConcurrentHashMap<>();


    public Loans(String pMemberId, LoanTypes pLoanType, double pAmountBorrowed, String pGuarantor1, String pGuarantor2,
                 double pGuarantor1Amount, double pGuarantor2Amount) {

        if (!(LoanCommands.getInstance().isApproved(pMemberId) &&
                LoanCommands.getInstance().guarantorCheck(pLoanType, pGuarantor1, pGuarantor2) &&
                LoanCommands.getInstance().amountCheck
                        (pMemberId,pLoanType,pAmountBorrowed,pGuarantor1Amount,pGuarantor2Amount))){
            throw new IllegalArgumentException("Loan not approved");
        }

        this.aLoanId = UUID.randomUUID().toString();
        this.aMemberId = pMemberId;
        this.aLoanType = pLoanType;
        this.aAmountBorrowed = pAmountBorrowed;
        this.aReturnAmount = LoanCommands.getInstance().amountToReturn(pAmountBorrowed,pLoanType);
        this.aGuarantor1 = Member.getMemberList().get(pGuarantor1);
        this.aGuarantor2 = Member.getMemberList().get(pGuarantor2);
        this.aGuarantor1Amount = pGuarantor1Amount;
        this.aGuarantor2Amount = pGuarantor2Amount;
        this.aDate = LocalDate.now();
        this.status = "Approved";
        aGuarantors.add(aGuarantor1);
        aGuarantors.add(aGuarantor2);
        ALOANLIST.put(aLoanId,this);
    }

    public static List<Member> getGuarantors() {
        return Collections.unmodifiableList(aGuarantors);
    }

    public static Map<String, Loans> getLoanList() {
        return ALOANLIST;
    }

    public String getLoanId() {
        return this.aLoanId;
    }

    public String getMemberId() {
        return this.aMemberId;
    }

    public LoanTypes getLoanType() {
        return this.aLoanType;
    }

    public double getAmountBorrowed() {
        return this.aAmountBorrowed;
    }

    public double getReturnAmount() {
        return this.aReturnAmount;
    }

    public double getGuarantor1Amount() {
        return this.aGuarantor1Amount;
    }

    public double getGuarantor2Amount() {
        return this.aGuarantor2Amount;
    }

    public LocalDate getDate() {
        return this.aDate;
    }

    public String getStatus() {
        return this.status;
    }

    public String toString() {
        return  "Member: " + Member.getMemberList().get(this.aMemberId).getName() +
                " Loan Type: " + this.getLoanType() +
                " Amount Borrowed: " + this.getAmountBorrowed() +
                " Amount Guaranteed: " + (this.getGuarantor1Amount() + this.getGuarantor2Amount()) +
                " Return Amount: " + this.getReturnAmount() +
                " Guarantor 1: " + this.aGuarantor1.getName() +
                " Guarantor 2: " + this.aGuarantor2.getName() +
                " Date: " + this.getDate();
    }

    public boolean equals(Object o) {
        if (o instanceof Loans l) {
            return this.aLoanId.equals(l.aLoanId);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(aLoanId);
    }
}
