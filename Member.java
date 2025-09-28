import java.util.*;
import java.time.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Represents a member of the SACCO. Each member has a maximum age limit and must be
 * within a valid age range to register. The Member class also tracks personal details
 * and manages a list of registered members.
 **/


public class Member {
    private static int MIN_AGE = 18;
    private static int MAX_AGE = 35;

    private final String aId;
    private String aName;
    private Gender aGender;
    private LocalDate aDateOfBirth;
    private int aAge;
    private static int registrationFee = 1000; //Kenyan Shillings(for this case)
    private final LocalDate aDateOfRegistration;
    private String aStatus;
    private static final Map<String,Member> AMEMBERSLIST = new ConcurrentHashMap<>(); //Synchronize details


    public Member(String pName, Gender pGender, LocalDate pDateOfBirth) {
        assert pName != null && pGender != null && pDateOfBirth != null;
        validateAge(pDateOfBirth);

        this.aId = UUID.randomUUID().toString();
        this.aName = pName;
        this.aGender = pGender;
        this.aAge = Period.between(pDateOfBirth, LocalDate.now()).getYears();
        this.aDateOfBirth = pDateOfBirth;
        this.aDateOfRegistration = LocalDate.now();
        this.aStatus = "Active";

        AMEMBERSLIST.put(this.aId, this);
    }

    public static int getRegistrationFee() {
        return registrationFee;
    }

    public String toString() {
        return "ID: " + this.getId() + " Name: " + this.getName();
    }

    public String getName() {
        return this.aName;
    }

    public Gender getGender() {
        return this.aGender;
    }

    public int getAge() {
        return this.aAge;
    }

    public LocalDate getDateOfBirth() {
        return this.aDateOfBirth;
    }

    public String getId() {
        return this.aId;
    }

    public static Map<String, Member> getMemberList() {
        return Collections.unmodifiableMap(AMEMBERSLIST);
    }

    public LocalDate getDateOfRegistration() {
        return this.aDateOfRegistration;
    }

    public String getStatus() {
        return this.aStatus;
    }

    public void deactivate() {
        this.aStatus = "Inactive";
    }

    public static int getMaxAge() {
        return MAX_AGE;
    }

    public static int getMinAge() {
        return MIN_AGE;
    }

    public static void setMaxAge(int newMaxAge) {
        assert newMaxAge > 0;
        MAX_AGE = newMaxAge;
    }

    public static void setMinAge(int newMinAge) {
        assert newMinAge > 0;
        MIN_AGE = newMinAge;
    }

    public void setName(String newName) {
        assert newName != null: "Name cannot be null";
        this.aName = newName;
    }

    public void setGender(Gender newGender) {
        assert newGender != null: "Gender cannot be null";
        this.aGender = newGender;
    }

    public void setDateOfBirth(LocalDate newDateOfBirth) {
        assert newDateOfBirth != null: "Date of birth cannot be null";
        validateAge(newDateOfBirth);

        this.aAge = Period.between(newDateOfBirth, LocalDate.now()).getYears();
        this.aDateOfBirth = newDateOfBirth;
    }

    public static void setRegistrationFee(int newRegistrationFee) {
        assert newRegistrationFee > 0: "Registration fee must be greater than 0";
        registrationFee = newRegistrationFee;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Member) {
            Member m = (Member) o;
            return this.aId.equals(m.aId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aId);
    }

    /**
     * Validates the age based on the date of birth.
     */
    private void validateAge(LocalDate pDateOfBirth) {
        int calculatedAge = Period.between(pDateOfBirth, LocalDate.now()).getYears();
        if (calculatedAge < MIN_AGE || calculatedAge > MAX_AGE) {
            throw new IllegalArgumentException(
                    "Member must be between " + MIN_AGE + " and " + MAX_AGE + " years old"
            );
        }
    }
}
