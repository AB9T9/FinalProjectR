
import java.util.ArrayList;

public class Patient extends Person {

    
    private String patientID;
    private String password;
    private String bloodGroup;
    private String registrationDate;
    private ArrayList<String> reportIDs; // only reportID otheres in hospital Hasmap

    
    public Patient() {
        super();
        this.patientID = "P000";
        this.password = "";
        this.bloodGroup = "Unknown";
        this.registrationDate = "N/A";
        this.reportIDs = new ArrayList<>();
    }

    public Patient(String name, int age, String gender, String phone, String address,
                   String patientID, String password, String bloodGroup, String registrationDate) {
        super(name, age, gender, phone, address); // Person constructor call
        this.patientID = patientID;
        this.password = password;
        this.bloodGroup = bloodGroup;
        this.registrationDate = registrationDate;
        this.reportIDs = new ArrayList<>();
    }

    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getRegistrationDate() { return registrationDate; }

    public ArrayList<String> getReportIDs() { return reportIDs; }
    public void addReportID(String reportID) { this.reportIDs.add(reportID); }

    // Abstract method override 
    @Override
    public void displayInfo() {
        System.out.println("---- Patient Info ----");
        System.out.println(super.toString());
        System.out.println("Patient ID: " + patientID + ", Blood Group: " + bloodGroup
                + ", Registration Date: " + registrationDate
                 + ", Total Reports: " + reportIDs.size());
    }

    public Patient login(Hospital hospital,String password) throws InvalidPatientException{
        return hospital.patientLogin(this.patientID, password);
    }
    public void viewReport(Hospital hospital) {
        for(String id:reportIDs)
        {
            hospital.displayReport(id);
        }
    }
    public void viewReport(String reportID) {
        
    }
    public void viewHistory() {
    }
    public void changePassword() {
    }
}
