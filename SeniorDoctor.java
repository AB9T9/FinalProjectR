
public class SeniorDoctor extends Doctor {

    private int yearsOfExperience;
    private double consultationFee;

  
    public SeniorDoctor() {
        super();
        this.yearsOfExperience = 0;
        this.consultationFee = 0.0;
    }

    public SeniorDoctor(String name, int age, String gender, String phone, String address,
                         String doctorID, String doctorName, String specialization,
                         int yearsOfExperience, double consultationFee) {
        super(name, age, gender, phone, address, doctorID, doctorName, specialization);
        this.yearsOfExperience = yearsOfExperience;
        this.consultationFee = consultationFee;
    }

   
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }


    @Override
    public void displayInfo() {
        super.displayInfo(); 
        System.out.println("Years of Experience: " + yearsOfExperience
                + ", Consultation Fee: " + consultationFee);
    }
    @Override
    public String getStatus() {
        return super.getStatus() + " (Senior, " + yearsOfExperience + " yrs experience)";
    }
    public void approveDischarge() {
   
    }
}
