import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String name;
    private List<Loan> loans;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.loans = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }
}