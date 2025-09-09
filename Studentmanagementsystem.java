import  java.util.ArrayList;
import java.util.Scanner;
class Student{
    int id;
    String name;
    double marks;
    Student(int id, String name,double marks){
        this.id =id;
        this .name =name;
        this. marks =marks;
    }
    public String toString(){
        return "ID: " + id +" ,Name : "+ name + ",Marks :"+ marks;
    }
}
public class Studentmanagementsystem {
    static ArrayList <Student > studentList=new ArrayList<>();
    static Scanner scanner =new Scanner(System .in);
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n ---Student Data---");
            System.out.println("1. Add Student");
            System.out.println("2. Veiw Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out .println("Enter your choice") ;
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> System.out.println("Exiting...") ;
                default ->System.out.println("Invalid choice!") ;



            }
        }while(choice != 5);

        }
    static void addStudent() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Marks :");
        double marks =Double.parseDouble (scanner.next());
        studentList.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    static void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
        } else {
            System.out.println("\n-- Student List --");
            for (Student s : studentList) {
                System.out.println(s);
            }
        }
    }

    static void updateStudent() {
        System.out.print("Enter ID of student to update: ");
        int id = scanner.nextInt();
        boolean found = false;

        for (Student s : studentList) {
            if (s.id == id) {
                scanner.nextLine(); // consume newline
                System.out.print("Enter new Name: ");
                s.name = scanner.nextLine();
                System.out.print("Enter new Marks: ");
                s.marks = scanner.nextInt();
                found = true;
                System.out.println("Student updated successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    static void deleteStudent() {
        System.out.print("Enter ID of student to delete: ");
        int id = scanner.nextInt();
        boolean removed = studentList.removeIf(s -> s.id == id);
        if (removed) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student with ID " + id + " not found.");

        }
    }
}