import java.util.*;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();
    private Random random = new Random();
    
    public String generateEmployeeCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String randomLetters = "" + letters.charAt(random.nextInt(26)) + letters.charAt(random.nextInt(26));
        String randomNumbers = String.format("%04d", random.nextInt(10000));
        return randomLetters + "-" + randomNumbers;
    }

    public void insertEmployee(String name, String gender, String position) {
    	String newCode;
    	Object checkCode = null;
		do {
    	    newCode = generateEmployeeCode();
    	} while (employees.stream().anyMatch(e -> e.code.equals(checkCode)));
    	String code = newCode;
        
        int salary = switch (position) {
            case "Manager" -> 8000000;
            case "Supervisor" -> 6000000;
            case "Admin" -> 4000000;
            default -> 0;
        };

        employees.add(new Employee(newCode, name, gender, position, salary));
        System.out.println("Berhasil menambahkan Karyawan dengan id " + newCode + " ");
      
        checkAndGiveBonus(position);
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("Belum ada data karyawan.");
            return;
        }
        
        employees.sort(Comparator.comparing(e -> e.name)); 

        System.out.println("===========================================================================================");
        System.out.printf("| %-3s | %-12s | %-20s | %-12s | %-10s | %-12s |\n",
                          "No", "Kode Karyawan", "Nama Karyawan", "Jenis Kelamin", "Jabatan", "Gaji Karyawan");
        System.out.println("===========================================================================================");

        int no = 1; 
        for (Employee e : employees) {
            System.out.printf("| %-3d | %-12s | %-20s | %-12s | %-10s | Rp. %,10d |\n",
                              no++, e.code, e.name, e.gender, e.position, e.salary);
        }
        System.out.println("===========================================================================================");
    }
    public void updateEmployee() {
        if (employees.isEmpty()) {
            System.out.println("Belum ada data karyawan.");
            return;
        }      
        employees.sort(Comparator.comparing(e -> e.name));

        System.out.println("===========================================================================================");
        System.out.printf("| %-3s | %-12s | %-20s | %-12s | %-12s | %-12s |\n", 
                          "No", "Kode Karyawan", "Nama Karyawan", "Jenis Kelamin", "Jabatan", "Gaji Karyawan");
        System.out.println("===========================================================================================");

        int index = 1;
        for (Employee e : employees) {
            System.out.printf("| %-3d | %-12s | %-20s | %-12s | %-12s | Rp. %,10d |\n", 
                              index++, e.code, e.name, e.gender, e.position, e.salary);
        }
        System.out.println("===========================================================================================");

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nInput nomor urutan karyawan yang ingin diupdate: ");
        
        int choice;
        while (!scanner.hasNextInt()) {  
            System.out.println("Harap masukkan angka!");
            scanner.next();
        }
        choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice < 1 || choice > employees.size()) {
            System.out.println("Nomor tidak valid.");
            return;
        }

        Employee selectedEmployee = employees.get(choice - 1);

        System.out.printf("Input nama karyawan [>= 3]: ", selectedEmployee.name);
        String newName = scanner.nextLine();
        if (!newName.equals("0") && newName.length() >= 3) selectedEmployee.name = newName;
    
        System.out.printf("Input jenis kelamin [Laki-laki | Perempuan]: ", selectedEmployee.gender);
        String newGender = scanner.nextLine();
        if (!newGender.equals("0") && (newGender.equals("Laki-laki") || newGender.equals("Perempuan"))) {
            selectedEmployee.gender = newGender;
        }

        System.out.printf("Input jabatan [Manager | Supervisor | Admin]: ", selectedEmployee.position);
        String newPosition = scanner.nextLine();
        if (!newPosition.equals("0")) {
            selectedEmployee.position = newPosition;
            selectedEmployee.salary = switch (newPosition) {
                case "Manager" -> 8_000_000;
                case "Supervisor" -> 6_000_000;
                case "Admin" -> 4_000_000;
                default -> selectedEmployee.salary;
            };
        }

        System.out.println("\nBerhasil mengupdate karyawan dengan id " + selectedEmployee.code);
    }





    public void deleteEmployee() {
        if (employees.isEmpty()) {
            System.out.println("Belum ada data karyawan.");
            return;
        }

        employees.sort(Comparator.comparing(e -> e.name));

        System.out.println("===========================================================================================");
        System.out.printf("| %-3s | %-12s | %-20s | %-12s | %-12s | %-12s |\n", 
                          "No", "Kode Karyawan", "Nama Karyawan", "Jenis Kelamin", "Jabatan", "Gaji Karyawan");
        System.out.println("===========================================================================================");

        int index = 1;
        for (Employee e : employees) {
            System.out.printf("| %-3d | %-12s | %-20s | %-12s | %-12s | Rp. %,10d |\n", 
                              index++, e.code, e.name, e.gender, e.position, e.salary);
        }
        System.out.println("===========================================================================================");

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nInput nomor urutan karyawan yang ingin dihapus: ");

        int choice;
        while (!scanner.hasNextInt()) {  
            System.out.println("Harap masukkan angka!");
            scanner.next();
        }
        choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice < 1 || choice > employees.size()) {
            System.out.println("Nomor tidak valid.");
            return;
        }

        
        Employee removedEmployee = employees.remove(choice - 1);
        System.out.println("\nKaryawan dengan nama " + removedEmployee.name + " dan ID " + removedEmployee.code + " berhasil dihapus.");
    }


    public void checkAndGiveBonus(String position) {
        long count = employees.stream().filter(e -> e.position.equals(position)).count();
        
        if (count % 3 == 1 && count > 3) { 
            double bonusPercentage = switch (position) {
                case "Manager" -> 0.10;
                case "Supervisor" -> 0.075;
                case "Admin" -> 0.05;
                default -> 0;
            };

           
            List<Employee> eligibleEmployees = employees.stream()
                .filter(e -> e.position.equals(position))
                .limit(count - 1) 
                .toList();

            System.out.println("Bonus " + (bonusPercentage * 100) + "% telah diberikan kepada kepada karyawan dengan id: ");
            for (Employee emp : eligibleEmployees) {
                emp.salary += emp.salary * bonusPercentage;
                System.out.println("- " + emp.code);
            }
        }
    }
}
