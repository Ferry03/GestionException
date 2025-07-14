import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

class OverdueBooksException extends Exception {
    public OverdueBooksException(String message) {
        super(message);
    }
}


class Library {
    private List<Book> books;
    private List<User> users;
    private Map<String, Loan> activeLoans; // ISBN -> Prêt

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        activeLoans = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void borrowBook(String isbn, String userId, LocalDate currentDate) throws BookNotAvailableException, OverdueBooksException {
        // Vérifier si l'utilisateur existe
        User user = findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé : " + userId);
        }

        for (Loan loan : user.getLoans()) {
            if (loan.isOverdue(currentDate)) {
                throw new OverdueBooksException("L'utilisateur " + user.getName() + " a des livres en retard.");
            }
        }

        Book book = findBookByIsbn(isbn);
        if (book == null) {
            throw new BookNotAvailableException("Livre non trouvé : ISBN " + isbn);
        }
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Le livre " + book.getTitle() + " n'est pas disponible.");
        }


        book.setAvailable(false);
        Loan loan = new Loan(book, user, currentDate);
        user.addLoan(loan);
        activeLoans.put(isbn, loan);
        System.out.println("Livre " + book.getTitle() + " emprunté par " + user.getName() + " jusqu'au " + loan.getDueDate());
    }

    public void returnBook(String isbn, LocalDate currentDate) throws BookNotAvailableException {
        Loan loan = activeLoans.get(isbn);
        if (loan == null) {
            throw new BookNotAvailableException("Aucun prêt actif pour le livre avec ISBN : " + isbn);
        }

        Book book = loan.getBook();
        User user = loan.getUser();
        book.setAvailable(true);
        user.removeLoan(loan);
        activeLoans.remove(isbn);

        if (loan.isOverdue(currentDate)) {
            System.out.println("Livre " + book.getTitle() + " retourne par " + user.getName() + " (EN RETARD).");
        } else {
            System.out.println("Livre " + book.getTitle() + " retourne par " + user.getName() + ".");
        }
    }

    private Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new Book("Le Petit Prince", "12345"));
        library.addBook(new Book("1984", "67890"));

        library.addUser(new User("U001", "Alice"));
        library.addUser(new User("U002", "Bob"));


        LocalDate today = LocalDate.of(2025, 7, 14);
        LocalDate overdueDate = LocalDate.of(2025, 7, 1);

        try {

            System.out.println("Test 1 : Emprunt d'un livre disponible");
            library.borrowBook("12345", "U001", today);


            System.out.println("\nTest 2 : Emprunt d'un livre déjà emprunte");
            library.borrowBook("12345", "U002", today);
        } catch (BookNotAvailableException | OverdueBooksException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        try {

            System.out.println("\nTest 3 : Emprunt avec livre en retard");
            library.borrowBook("67890", "U001", overdueDate.plusDays(15));
        } catch (BookNotAvailableException | OverdueBooksException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        try {

            System.out.println("\nTest 4 : Retour d'un livre");
            library.returnBook("12345", today);
        } catch (BookNotAvailableException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        try {

            System.out.println("\nTest 5 : Retour d'un livre non emprunte");
            library.returnBook("12345", today);
        } catch (BookNotAvailableException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}