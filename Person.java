

public abstract class Person {

    private String name;
    private int age;
    private String gender;
    private String phone;
    private String address;


    public Person() {
        this.name = "Unknown";
        this.age = 0;
        this.gender = "Unspecified";
        this.phone = "N/A";
        this.address = "N/A";
    }

    
    public Person(String name, int age, String gender, String phone, String address) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

  
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }


    public abstract void displayInfo();

    
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Gender: " + gender
                + ", Phone: " + phone + ", Address: " + address;
    }
}
