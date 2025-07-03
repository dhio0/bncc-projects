import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Insert Data Karyawan");
            System.out.println("2. View Data Karyawan");
            System.out.println("3. Update Data Karyawan");
            System.out.println("4. Delete Data Karyawan");
            System.out.println("5. Exit");
            System.out.print("Pilih menu: ");

            int choice;
            while (!scanner.hasNextInt()) {  
                System.out.println("Harap masukkan angka!");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    String name;
                    do {
                        System.out.print("Input nama karyawan [>= 3]: ");
                        name = scanner.nextLine();
                    } while (!name.matches("[a-zA-Z ]{3,}")); 

                    String gender;
                    do {
                        System.out.print("Input jenis kelamin [Laki-laki/Perempuan]: ");
                        gender = scanner.nextLine();
                    } while (!gender.equals("Laki-laki") && !gender.equals("Perempuan"));

                    String position;
                    do {
                        System.out.print("Input jabatan [Manager/Supervisor/Admin]: ");
                        position = scanner.nextLine();
                    } while (!position.equals("Manager") && !position.equals("Supervisor") && !position.equals("Admin"));

                    manager.insertEmployee(name, gender, position);
                }
                case 2 -> manager.viewEmployees();
                case 3 -> manager.updateEmployee();
                case 4 -> manager.deleteEmployee(); 
                case 5 -> {
                    System.out.println("Program selesai.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
