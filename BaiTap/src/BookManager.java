import java.util.*;

public class BookManager {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book b) {
        books.add(b);
    }

    public void removeBook(int id) {
        books.removeIf(b -> b.getId() == id);
    }

    public void updateBook(int id, String title, String author, double price) {
        for (Book b : books) {
            if (b.getId() == id) {
                b.setTitle(title);
                b.setAuthor(author);
                b.setPrice(price);
                return;
            }
        }
        System.out.println("Không tìm thấy sách!");
    }

    public void showAll() {
        if (books.isEmpty()) {
            System.out.println("Danh sách rỗng!");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    // 5. Tìm sách có tiêu đề chứa "Lập trình"
    public void findLapTrinh() {
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains("lập trình")) {
                System.out.println(b);
            }
        }
    }

    // 6. Lấy tối đa K sách có giá <= P
    public void findByPrice(int k, double p) {
        int count = 0;
        for (Book b : books) {
            if (b.getPrice() <= p) {
                System.out.println(b);
                count++;
                if (count == k) break;
            }
        }
    }

    // 7. Tìm sách theo danh sách tác giả
    public void findByAuthors(List<String> authors) {
        for (Book b : books) {
            if (authors.contains(b.getAuthor())) {
                System.out.println(b);
            }
        }
    }
}
