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
  

    

    @Override
    public String getStatus() {
        
        return null;
    }
    
}
