import java.time.LocalDate;

public class Loan {
    private Book book;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public Loan(Book book, User user, LocalDate borrowDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(14); // Prêt de 14 jours
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isOverdue(LocalDate currentDate) {
        return currentDate.isAfter(dueDate);
    }
}