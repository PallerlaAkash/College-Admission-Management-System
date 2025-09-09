import service.AdmissionService;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdmissionService service = new AdmissionService();

        while (true) {
            System.out.println("\n--- College Admission System ---");
            System.out.println("1. Register Student");
            System.out.println("2. Apply for Course");
            System.out.println("3. Process Applications");
            System.out.println("4. Export Admission List");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> service.registerStudent();
                case 2 -> service.applyForCourseMenu();
                case 3 -> service.processApplications();
                case 4 -> service.exportAdmissionsToCSV();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
