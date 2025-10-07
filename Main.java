import java.util.Scanner;
class calc{
    public int addition(int num1,int num2){
        return num1+num2;
    }
    public double addition(double num1,double num2){
        return num1+num2;
    }
    public int addition(int num1,int num2,int num3){
        return num1+num2+num3;
    }
    public int subtraction(int num1,int num2){
        return num1-num2;
    }
    public int multiplication(int num1,int num2){
        return num1*num2;
    }
    public int divide(int num1,int num2){
        if (num2==0){
            System.out.println("Error division can't with 0 denominator");
            return 0;
        }
        return num1/num2;
    }
}
class user {
    Scanner sc = new Scanner(System.in);
    calc cal = new calc();

    public void addition() {
        System.out.println("\nSelect Addition Type:");
        System.out.println("1. Add two integers");
        System.out.println("2. Add two doubles");
        System.out.println("3. Add three integers");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("enter first number: ");
                int num1 = sc.nextInt();
                System.out.println("enter second number: ");
                int num2 = sc.nextInt();
                System.out.println("addition of two integer numbers is " + cal.addition(num1, num2));
                break;
            case 2:
                System.out.println("enter first number");
                double num3 = sc.nextDouble();
                System.out.println("enter second number");
                double num4 = sc.nextDouble();
                System.out.println("addition of two double numbers are " + cal.addition(num3, num4));
                break;
            case 3:
                System.out.println("enter first number: ");
                int num5 = sc.nextInt();
                System.out.println("enter second number: ");
                int num6 = sc.nextInt();
                System.out.println("enter third number: ");
                int num7 = sc.nextInt();
                System.out.println("addition of three integer numbers are " + cal.addition(num5, num6, num7));
                break;
            default:
                System.out.println("pls enter a valid choice");
        }
    }
    public void sub() {
        System.out.print("Enter first number: ");
        int num1 = sc.nextInt();
        System.out.print("Enter second number: ");
        int num2 = sc.nextInt();
        System.out.println("subtraction of two integer numbers are " + cal.subtraction(num1, num2));
    }

    public void multiply() {
        System.out.print("Enter first number: ");
        int num1 = sc.nextInt();
        System.out.print("Enter second number: ");
        int num2 = sc.nextInt();
        System.out.println("multiplication of two integer numbers are " + cal.multiplication(num1, num2));
    }

    public void division() {
        System.out.print("Enter first number: ");
        int num1 = sc.nextInt();
        System.out.print("Enter second number: ");
        int num2 = sc.nextInt();
        if (num2!=0){
            System.out.println("division of two integer numbers are " + cal.divide(num1, num2));
        }
    }

    public void mainMenu() {
        while (true) {
            System.out.println("Welcome to the Calculator Application");
            System.out.println("1. Add Numbers");
            System.out.println("2. Subtract Numbers");
            System.out.println("3. Multiply Numbers");
            System.out.println("4. Divide Numbers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                    addition();
                    break;
                case 2:
                    sub();
                    break;
                case 3:
                    multiply();
                    break;
                case 4:
                    division();
                    break;
                case 5:
                    System.out.println("thanks for using our calculator");
                    return;
                default:
                    System.out.println("invalid choice pls enter valid choice");
            }
        }
    }
    public static void main(String[] args) {
        user ue = new user();
        ue.mainMenu();
    }
}