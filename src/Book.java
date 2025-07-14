public class Book {
    private String title;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}