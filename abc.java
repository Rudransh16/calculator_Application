import java.util.Scanner;
import java.util.InputMismatchException;


class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}


class Student {
    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    public Student(int rollNumber, String studentName, int[] marks) throws InvalidMarksException {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }


    public double calculateAverage() {
        int sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        return sum / 3.0;
    }

    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.print("Marks: ");
        for (int mark : marks) {
            System.out.print(mark + " ");
        }
        double avg = calculateAverage();
        System.out.println("\nAverage: " + avg);
        System.out.println("Result: " + (avg >= 40 ? "Pass" : "Fail"));
    }

    public int getRollNumber() {
        return rollNumber;
    }
}

class ResultManager {
    private Student[] students = new Student[100];
    private int studentCount = 0;
    private Scanner scanner = new Scanner(System.in);

    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = scanner.nextInt();
            }

            Student student = new Student(roll, name, marks);
            students[studentCount++] = student;
            System.out.println("Student added successfully. Returning to main menu...\n");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input type. Please enter numeric values.\n");
            scanner.nextLine(); // clear buffer
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage() + "\n");
        }
    }

    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = scanner.nextInt();
            boolean found = false;

            for (int i = 0; i < studentCount; i++) {
                if (students[i].getRollNumber() == roll) {
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student with Roll Number " + roll + " not found.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input type.");
            scanner.nextLine(); // clear buffer
        } finally {
            System.out.println("Search completed.\n");
        }
    }

    public void mainMenu() {
        int choice = 0;
        do {
            System.out.println("===== Student Result Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Show Student Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        showStudentDetails();
                        break;
                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.\n");
                scanner.nextLine(); // clear buffer
            }
        } while (choice != 3);

        scanner.close();
    }

    public static void main(String[] args) {
        ResultManager manager = new ResultManager();
        manager.mainMenu();
    }
}