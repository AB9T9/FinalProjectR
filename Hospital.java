import java.util.*;
import java.io.*;

public class Hospital implements Trackable {
    private ArrayList <Person> people;
    private HashMap <String,String []>reports;

    public Hospital ()
    {
        people = new ArrayList<>();
        reports= new HashMap<>();
    }
    // patient add method'
    public void addPatient(Patient p)
    {
        people.add(p);
    }
    public void addDoctor (Doctor d)
    {
        people.add(d);
    }
    public Patient searchPatient (String patientID) throws InvalidPatientException
    {
        
            for(Person p : people)
            {
                if( p instanceof Patient && ((Patient)p).getPatientID().equals(patientID))
                {
                    return (Patient)p;
                }
            } throw new InvalidPatientException("patient not found "+patientID);
        
    }
    public Doctor searchDoctor(String doctorID)
    {
        for(Person p:people)
        {
            if(p instanceof Doctor && ((Doctor)p).getDoctorID().equals(doctorID))
            {
                return (Doctor)p;
            }
        }
        return null;
    }
// login method 
public Doctor doctorLogin(String doctorID)
{
    return  searchDoctor(doctorID);
}
public Patient patientLogin(String patientId,String password) throws InvalidPatientException{
    Patient p = searchPatient(patientId);
    if(!p.getPassword().equals(password))
    {
        throw new InvalidPatientException("Incorrect Password for Patient ID : "+patientId);

    }
    return p;
}
public String createReport(String doctorID,String patientID,String testName)
{
    String reportID = "Report" + (reports.size()+1);
    String data [] = {patientID,doctorID,testName,"",java.time.LocalDateTime.now().toString(),
        "Pending","","","","","false"
    };
    reports.put(reportID, data);
    try {
        Patient p = searchPatient(patientID);
        p.addReportID(reportID);
    } catch (InvalidPatientException e) {
        System.out.println("Warning "+e.getMessage());
    }
    return reportID;
    
}
public void updateReport(String reportID, String result, String status) {
        String[] r = reports.get(reportID);
        if (r != null) {
            r[3] = result;
            r[5] = status;
        }
    }

 public void addPrescription(String reportID, String medicine, String dosage, String days, String advice) {
        String[] r = reports.get(reportID);
        if (r != null) {
            r[6] = medicine;
            r[7] = dosage;
            r[8] = days;
            r[9] = advice;
        }
    }
    //report search 
        public String[] searchReport(String reportID) {
        return reports.get(reportID);
    }
    public void displayReport(String reportID) {
        String[] r = reports.get(reportID);
        if (r == null) {
            System.out.println("Report not found: " + reportID);
            return;
        }
        System.out.println("Report " + reportID
                + " | Patient: " + r[0]
                + " | Doctor: " + r[1]
                + " | Test: " + r[2]
                + " | Result: " + r[3]
                + " | Date: " + r[4]
                + " | Status: " + r[5]
                + " | Medicine: " + r[6] + " " + r[7]
                + " | Days: " + r[8]
                + " | Advice: " + r[9]
                + " | Read: " + r[10]);
    }

    @Override
    public String getStatus() {
        int patientCount = 0;
        for (Person p : people) {
            if (p instanceof Patient) patientCount++;
        }
        int totalReports = reports.size();
        int pending = 0;
        for (String[] r : reports.values()) {
            if (r[5].equals("Pending") || r[5].equals("Processing")) pending++;
        }
        int completed = totalReports - pending;
        return patientCount + " patients, " + totalReports + " reports total ("
                + pending + " pending, " + completed + " completed)";
    }

    public ArrayList<Person> getPeople() {
        return people;
    }
   
    
}
