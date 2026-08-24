
public class TestPhase2 {
    public static void main(String[] args) {

        System.out.println("===== Doctor =====");
        Doctor d1 = new Doctor("Dr. Rahim", 40, "Male", "01700000000", "Dhaka",
                "DOC101", "Dr. Rahim", "Cardiology");
        d1.displayInfo();
        System.out.println(d1.getStatus()); 

        System.out.println("\n===== Patient =====");
        Patient p1 = new Patient("Karim", 25, "Male", "01800000000", "Chittagong",
                "PAT201", "pass123", "B+", "2026-08-24");
        p1.displayInfo();
        p1.viewReport();            
        p1.viewReport("RPT301");   

        System.out.println("\n===== SeniorDoctor (GrandChild) =====");
        SeniorDoctor sd1 = new SeniorDoctor("Dr. Karim Uddin", 55, "Male", "01900000000", "Sylhet",
                "DOC102", "Dr. Karim Uddin", "Neurology", 20, 1500.0);
        sd1.displayInfo();    
        System.out.println(sd1.getStatus()); 

        System.out.println("\n===== Polymorphism check (Person reference) =====");
        Person[] people = { d1, p1, sd1 };
        for (Person person : people) {
            person.displayInfo(); 
        }
    }
}
