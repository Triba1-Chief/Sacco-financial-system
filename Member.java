import java.util.*;
import java.time.*;


public class Member {
    private String aId;
    private String aName;
    private Gender aGender;
    private LocalDate aDateOfBirth;
    private int aAge;
    private static int registrationFee = 1000; //Kenyan Shillings
    private LocalDate aDateOfRegistration;
    private static final Map<String,Member> AMEMBERSLIST = new HashMap<>();


    public Member(String pName, Gender pGender, LocalDate pDateOfBirth) {
        assert pName != null && pGender != null && pDateOfBirth != null;

        if (calculateAge(pDateOfBirth) > 35 || calculateAge(pDateOfBirth) < 18) {
            throw new IllegalArgumentException("Member must be 18 years old or older");
        }

        this.aId = UUID.randomUUID().toString();
        this.aName = pName;
        this.aGender = pGender;
        this.aAge = calculateAge(pDateOfBirth);
        this.aDateOfBirth = pDateOfBirth;
        this.aDateOfRegistration = LocalDate.now();

        AMEMBERSLIST.put(this.aId, this);
    }

    public static void removeMember(String pMemberId) {
        assert pMemberId != null && AMEMBERSLIST.containsKey(pMemberId): "Invalid Member ID";
        AMEMBERSLIST.remove(pMemberId);
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


    //Add notify for observers/visitors
    public void setRegistrationDate(LocalDate newRegistrationDate) {
        //Get sacco details to limit change of dates for registration
        assert newRegistrationDate != null: "Registration date cannot be null";
        this.aDateOfRegistration = newRegistrationDate;
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
        if (calculateAge(newDateOfBirth) > 35 || calculateAge(newDateOfBirth) < 18) {
            throw new IllegalArgumentException("Member must between 18 and 35 years old");
        }

        //What happens when they reach age limit? Capture bounds of age by the sacco details
        this.aAge = calculateAge(newDateOfBirth);
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

    private int calculateAge(LocalDate pDateOfBirth) {
        return Period.between(pDateOfBirth, LocalDate.now()).getYears();
    }
}
