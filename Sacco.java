import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Sacco { //Singleton Class

    private static final Sacco INSTANCE = new Sacco();
    private String aName;
    private int aYearFounded;
    private List<String> owners;
    private double officeExpenses; //percentage retained

    private Sacco() {
        owners = new ArrayList<>();
        officeExpenses = 0.1;
    }

    public static Sacco getInstance() {
        return INSTANCE;
    }

    public String getName() {
        return aName;
    }

    public void setName(String name) {
        assert name != null && !name.isEmpty() : "Name cannot be null or empty";
        aName = name;
    }

    public int getYearFounded() {
        return aYearFounded;
    }

    public void setYearFounded(int pYearFounded) {
        assert pYearFounded > 2000 : "Invalid year";
        aYearFounded = pYearFounded;
    }

    public double getOfficeExpenses() {
        return officeExpenses;
    }

    public void setOfficeExpenses(double officeExpenses) {
        assert officeExpenses > 0 : "Office expenses cannot be negative";
        this.officeExpenses = officeExpenses;
    }

    public List<String> getOwners() {
        owners.forEach(System.out::println);
        return Collections.unmodifiableList(owners);
    }

    public void addOwners(String owner) {
        assert owner != null && !owner.isEmpty() : "Owner cannot be null or empty";
        owners.add(owner);
    }

    public void removeOwners(String owner) {
        assert owner != null && !owner.isEmpty() : "Owner cannot be null or empty";
        owners.remove(owner);
    }

    public Map<String, Member> getMembers() {
        Member.getMemberList().forEach((s, member) -> System.out.println(member));
        return Member.getMemberList();
    }
}
