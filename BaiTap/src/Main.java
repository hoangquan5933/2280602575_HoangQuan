import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManager manager = new BookManager();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Thêm sách");
            System.out.println("2. Xóa sách");
            System.out.println("3. Sửa sách");
            System.out.println("4. Xuất danh sách");
            System.out.println("5. Tìm sách chứa 'Lập trình'");
            System.out.println("6. Lấy K sách giá <= P");
            System.out.println("7. Tìm sách theo tác giả");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Mã sách: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Tên sách: ");
                    String title = sc.nextLine();
                    System.out.print("Tác giả: ");
                    String author = sc.nextLine();
                    System.out.print("Giá: ");
                    double price = sc.nextDouble();
                    manager.addBook(new Book(id, title, author, price));
                    break;

                case 2:
                    System.out.print("Nhập mã sách cần xóa: ");
                    manager.removeBook(sc.nextInt());
                    break;

                case 3:
                    System.out.print("Mã sách cần sửa: ");
                    int uid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Tên mới: ");
                    String utitle = sc.nextLine();
                    System.out.print("Tác giả mới: ");
                    String uauthor = sc.nextLine();
                    System.out.print("Giá mới: ");
                    double uprice = sc.nextDouble();
                    manager.updateBook(uid, utitle, uauthor, uprice);
                    break;

                case 4:
                    manager.showAll();
                    break;

                case 5:
                    manager.findLapTrinh();
                    break;

                case 6:
                    System.out.print("Nhập K: ");
                    int k = sc.nextInt();
                    System.out.print("Nhập P: ");
                    double p = sc.nextDouble();
                    manager.findByPrice(k, p);
                    break;

                case 7:
                    System.out.print("Nhập số tác giả: ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    List<String> authors = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Tác giả " + (i+1) + ": ");
                        authors.add(sc.nextLine());
                    }
                    manager.findByAuthors(authors);
                    break;

                case 0:
                    System.exit(0);
                default:
                    System.out.println("Sai lựa chọn!");
            }
        }
    }
}
