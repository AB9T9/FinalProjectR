

public class Doctor extends Person implements Trackable {

  
    private String doctorID;
    private String doctorName;
    private String specialization;
    private int pendingReportCount; 

    
    public Doctor() {
        super();
        this.doctorID = "D000";
        this.doctorName = "Unknown";
        this.specialization = "General";
        this.pendingReportCount = 0;
    }

   
    public Doctor(String name, int age, String gender, String phone, String address,
                  String doctorID, String doctorName, String specialization) {
        super(name, age, gender, phone, address); // Person-এর constructor কল
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.pendingReportCount = 0;
    }

   
    public String getDoctorID() { return doctorID; }
    public void setDoctorID(String doctorID) { this.doctorID = doctorID; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getPendingReportCount() { return pendingReportCount; }
   
    public void setPendingReportCount(int pendingReportCount) { this.pendingReportCount = pendingReportCount; }

    // Abstract method override
    @Override
    public void displayInfo() {
        System.out.println("---- Doctor Info ----");
        System.out.println(super.toString());
        System.out.println("Doctor ID: " + doctorID + ", Name: " + doctorName
                + ", Specialization: " + specialization);
    }

    // Trackable interface method
    @Override
    public String getStatus() {
        return "Dr. " + doctorName + " has " + pendingReportCount + " pending report(s)";
    }
  
  // in phase 3/4....................
    public Doctor login(Hospital hospital) {
        return hospital.doctorLogin(this.doctorID);
    }
    public void addPatient(Hospital hospital,Patient p) {
        hospital.addPatient(p);
    }
    public void createReport() {
    }
    public void uploadReport() {
    }
    public void writePrescription() {
    }
}
