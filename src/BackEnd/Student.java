/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ryano
 */
public class Student {
    String name;
    String surname;
    String email;
    String password;
    LocalDate DOB;
    private int grade;
    boolean isAdmin;
    private int age;
    
    public Student(String inName, String inSurname, String inEmail, String inPassword, String inDOB, int inGrade, boolean inAdmin){
        name = inName;
        surname = inSurname;
        email = inEmail;
        password = inPassword;
        DOB = LocalDate.parse(inDOB, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        grade = inGrade;
        getAge();
        isAdmin = inAdmin;
    }
    private void getAge(){
        age = Period.between(DOB, LocalDate.now()).getYears();
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getDOB(){
        return DOB.toString();
    }
    public int getGrade(){
        return grade;
    }
    public String printStudent(){
        return name+"#"+surname+"#"+email+"#"+password+"#"+DOB.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+"#"+isAdmin+"#"+grade;
    }
    public boolean getIsAdmin(){
        return isAdmin;
    }
}
