import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {

    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean issued;

    public Book(int bookId, String title, String author, String category, boolean issued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.issued = issued;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isIssued() { return issued; }

    public void markAsIssued() { issued = true; }
    public void markAsReturned() { issued = false; }

    public void displayBookDetails() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title  : " + title);
        System.out.println("Author : " + author);
        System.out.println("Category: " + category);
        System.out.println("Issued : " + (issued ? "Yes" : "No"));
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    public String toFileString() {
        return bookId + ";" + title + ";" + author + ";" + category + ";" + issued;
    }

    public static Book fromFileString(String s) {
        String[] p = s.split(";");
        return new Book(
                Integer.parseInt(p[0]),
                p[1],
                p[2],
                p[3],
                Boolean.parseBoolean(p[4])
        );
    }
}

class Member {

    private int memberId;
    private String name;
    private String email;
    private List<Integer> issuedBooks = new ArrayList<>();

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public int getMemberId() { return memberId; }

    public void addIssuedBook(int bookId) {
        issuedBooks.add(bookId);
    }

    public void returnIssuedBook(int bookId) {
        issuedBooks.remove(Integer.valueOf(bookId));
    }

    public String toFileString() {
        return memberId + ";" + name + ";" + email + ";" + issuedBooks.toString();
    }

    public static Member fromFileString(String s) {
        String[] p = s.split(";", 4);
        Member m = new Member(Integer.parseInt(p[0]), p[1], p[2]);
        if (p.length == 4 && p[3].length() > 2) {
            String books = p[3].substring(1, p[3].length() - 1);
            if (!books.isEmpty()) {
                for (String x : books.split(", ")) {
                    m.addIssuedBook(Integer.parseInt(x));
                }
            }
        }
        return m;
    }
}

class Main {

    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();
    private Set<String> categories = new HashSet<>();

    private final String BOOK_FILE = "books.txt";
    private final String MEMBER_FILE = "members.txt";

    Scanner sc = new Scanner(System.in);

    public Main() {
        loadBooks();
        loadMembers();
    }

    void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                Book b = Book.fromFileString(line);
                books.put(b.getBookId(), b);
                categories.add(b.getCategory());
            }
        } catch (Exception e) {}
    }

    void loadMembers() {
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                Member m = Member.fromFileString(line);
                members.put(m.getMemberId(), m);
            }
        } catch (Exception e) {}
    }

    void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (Book b : books.values()) {
                bw.write(b.toFileString());
                bw.newLine();
            }
        } catch (Exception e) {}
    }

    void saveMembers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEMBER_FILE))) {
            for (Member m : members.values()) {
                bw.write(m.toFileString());
                bw.newLine();
            }
        } catch (Exception e) {}
    }

    public void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        Book b = new Book(id, title, author, category, false);
        books.put(id, b);
        categories.add(category);
        saveBooks();
        System.out.println("Book added.");
    }

    public void addMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        Member m = new Member(id, name, email);
        members.put(id, m);
        saveMembers();
        System.out.println("Member added.");
    }

    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        Book b = books.get(bookId);
        Member m = members.get(memberId);
        if (b == null || m == null) {
            System.out.println("Invalid book or member ID.");
            return;
        }
        if (b.isIssued()) {
            System.out.println("Book already issued!");
            return;
        }
        b.markAsIssued();
        m.addIssuedBook(bookId);
        saveBooks();
        saveMembers();
        System.out.println("Book issued successfully.");
    }

    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        Book b = books.get(bookId);
        Member m = members.get(memberId);
        if (b == null || m == null) {
            System.out.println("Invalid book or member.");
            return;
        }
        b.markAsReturned();
        m.returnIssuedBook(bookId);
        saveBooks();
        saveMembers();
        System.out.println("Book returned.");
    }

    public void searchBooks() {
        sc.nextLine();
        System.out.print("Search by title/author/category: ");
        String key = sc.nextLine().toLowerCase();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(key) ||
                    b.getAuthor().toLowerCase().contains(key) ||
                    b.getCategory().toLowerCase().contains(key)) {
                b.displayBookDetails();
                System.out.println("------------------");
            }
        }
    }

    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        int choice = sc.nextInt();
        if (choice == 1) Collections.sort(list);
        else list.sort(Comparator.comparing(Book::getAuthor));
        for (Book b : list) {
            b.displayBookDetails();
            System.out.println("------------------");
        }
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\n=== City Library Digital Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: addBook(); break;
                case 2: addMember(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: searchBooks(); break;
                case 6: sortBooks(); break;
                case 7:
                    saveBooks();
                    saveMembers();
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 7);
    }

    public static void main(String[] args) {
        Main lm = new Main();
        lm.menu();
    }
}