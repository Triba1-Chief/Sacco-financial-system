public enum LoanTypes {
    EMERGENCY(0.3, 1,1),
    SHORT_LOAN(0.6, 1,2),
    NORMAL_LOAN(1.0, 3,3),
    DEVELOPMENT_LOAN(1.4, 4,5);

    private double aInterestRate;
    private int aRepaymentPeriod;
    private int aMaxLoanAmount;

    private LoanTypes(double pInterestRate, int pRepaymentPeriod, int pMaxLoanAmount) {
        this.aInterestRate = pInterestRate/100; //Converts it into the decimal equivalent
        this.aRepaymentPeriod = pRepaymentPeriod;
        this.aMaxLoanAmount = pMaxLoanAmount; //scalar multiplier in relation to shares
    }

    public static LoanTypes getLoanType(String pLoanType) {
        assert pLoanType != null: "Loan Type cannot be null";
        
        for (LoanTypes l : LoanTypes.values()) {
            if (l.name().equalsIgnoreCase(pLoanType)) {
                return l;
            }
        }
        throw new IllegalArgumentException("Invalid Loan Type");
    }

    public double getInterestRate() {
        return this.aInterestRate;
    }

    public int getRepaymentPeriod() {
        return this.aRepaymentPeriod;
    }

    public int getMaxLoanAmount() {
        return this.aMaxLoanAmount;
    }

    public void setMaxLoanAmount(int pMaxLoanAmount) {
        this.aMaxLoanAmount = pMaxLoanAmount;
    }

    public void setInterestRate(LoanTypes pLoan, double pInterestRate) {
        assert pLoan != null: "Loan cannot be null";
        pLoan.aInterestRate = pInterestRate;
    }

    public void setRepaymentPeriod(LoanTypes pLoan, int pRepaymentPeriod) {
        assert pLoan != null:"Loan cannot be null";
        pLoan.aRepaymentPeriod = pRepaymentPeriod;
    }


}
