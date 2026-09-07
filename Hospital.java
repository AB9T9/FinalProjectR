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
    

    @Override
    public String getStatus() {
        
        return null;
    }
    
}
