import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to Your SACCO Management System!");

        while (running) {
            System.out.println("\n---- Main Menu ----");
            System.out.println("1. Register Member");
            System.out.println("2. Make Contribution");
            System.out.println("3. Apply for Loan");
            System.out.println("4. Make Repayment");
            System.out.println("5. View Member Details");
            System.out.println("6. View Contributions");
            System.out.println("7. View Loans");
            System.out.println("8. View Repayments");
            System.out.println("9. Savings & Dividends Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter gender (MALE/FEMALE/OTHER): ");
                    Gender gender = Gender.valueOf(sc.nextLine().toUpperCase());
                    System.out.print("Enter date of birth (yyyy-mm-dd): ");
                    LocalDate dob = LocalDate.parse(sc.nextLine());
                    MemberCommands.getInstance().registerMember(name, gender, dob);
                }
                case 2 -> {
                    System.out.print("Enter Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.print("Enter contribution amount: ");
                    double amt = sc.nextDouble(); sc.nextLine();
                    new Contribution(memberId, amt);
                }
                case 3 -> {
                    System.out.print("Enter Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.print("Enter Loan Type (EMERGENCY, SHORT_LOAN, NORMAL_LOAN, DEVELOPMENT_LOAN): ");
                    LoanTypes type = LoanTypes.getLoanType(sc.nextLine().toUpperCase());
                    System.out.print("Enter Loan Amount: ");
                    double amount = sc.nextDouble(); sc.nextLine();
                    System.out.print("Enter Guarantor1 ID: ");
                    String g1 = sc.nextLine();
                    System.out.print("Enter Guarantee Amount from Guarantor1: ");
                    double g1amt = sc.nextDouble(); sc.nextLine();
                    System.out.print("Enter Guarantor2 ID: ");
                    String g2 = sc.nextLine();
                    System.out.print("Enter Guarantee Amount from Guarantor2: ");
                    double g2amt = sc.nextDouble(); sc.nextLine();
                    try {
                        new Loan(memberId, type, amount, g1, g2, g1amt, g2amt);
                        System.out.println("Loan application successful.");
                    } catch (Exception e) {
                        System.out.println("Loan application failed: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print("Enter Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.print("Enter Loan ID: ");
                    String loanId = sc.nextLine();
                    System.out.print("Enter Repayment Amount: ");
                    double amount = sc.nextDouble(); sc.nextLine();
                    try {
                        RepaymentCommands.getInstance().registerRepayment(memberId, loanId, amount);
                    } catch (Exception e) {
                        System.out.println("Repayment failed: " + e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.print("Enter Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.println(Member.getMemberList().get(memberId));
                }
                case 6 -> {
                    ContributionCommands.getInstance().displayContribution();
                }
                case 7 -> {
                    Loan.getLoanList().values().forEach(System.out::println);
                }
                case 8 -> {
                    RepaymentCommands.getInstance().displayAllRepayments();
                }
                case 9 -> {
                    System.out.print("Enter year for report: ");
                    int year = sc.nextInt(); sc.nextLine();
                    SavingsCommands.yearEndReport(year);
                }
                case 0 -> {
                    running = false;
                    System.out.println("Exiting. Thank you for using the SACCO system!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}

