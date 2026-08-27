/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Represents a student user account. Stores personal details, login
 * credentials, date of birth (with age calculated from it), and grade.
 * Teacher extends this class, overriding admin-specific behaviour such
 * as getSubject().
 *
 * @author ryano
 */
public class Student {
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected LocalDate DOB;
    private int grade;
    protected boolean isAdmin;
    private int age;
    
    /**
     * Constructs a new Student, parsing the supplied date of birth string
     * into a LocalDate and calculating the student's current age from it.
     *
     * @param inName the student's first name
     * @param inSurname the student's surname
     * @param inEmail the student's email address, used as their unique identifier
     * @param inPassword the student's login password
     * @param inDOB the student's date of birth as a string, formatted "dd-MM-yyyy"
     * @param inGrade the student's grade/year level
     * @param inAdmin whether this user has admin (teacher) privileges
     */
    public Student(String inName, String inSurname, String inEmail, String inPassword, String inDOB, int inGrade, boolean inAdmin){//constructor method
        name = inName;
        surname = inSurname;
        email = inEmail;
        password = inPassword;
        DOB = LocalDate.parse(inDOB, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        grade = inGrade;
        calculateAge();
        isAdmin = inAdmin;
    }
    
    /**
     * Calculates this student's current age in whole years based on the
     * difference between their date of birth and today's date, storing
     * the result in the age field.
     */
    private void calculateAge(){//self explanatory, calculates age
        age = Period.between(DOB, LocalDate.now()).getYears();
    }
    
    /**
     * @return the student's email address
     */
    public String getEmail(){//all accessor methods
        return email;
    }
    
    /**
     * @return the student's login password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * @return the student's first name
     */
    public String getName(){
        return name;
    }
    
    /**
     * @return the student's surname
     */
    public String getSurname(){
        return surname;
    }
    
    /**
     * @return the student's date of birth, formatted as "dd-MM-yyyy"
     */
    public String getDOB(){
        return DOB.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
    /**
     * @return the student's grade/year level
     */
    public int getGrade(){
        return grade;
    }
    
    /**
     * Formats this student's details into the "#"-delimited line format
     * used by UserManager when writing/reading the Users data file.
     *
     * @return a single "#"-delimited string: name#surname#email#password#DOB#isAdmin#grade
     */
    public String printStudent(){//prints the students details in the file format
        return name+"#"+surname+"#"+email+"#"+password+"#"+DOB.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+"#"+isAdmin+"#"+grade;
    }
    
    /**
     * @return true if this user has admin (teacher) privileges, false otherwise
     */
    public boolean getIsAdmin(){
        return isAdmin;
    }
    
    /**
     * Students do not have a teaching subject. This is overridden in the
     * Teacher subclass to return the actual subject; here it signals
     * that the call is invalid for a plain Student.
     *
     * @return the literal string "Error", since students have no subject
     */
    public String getSubject() {//students cannot have subjects. method overriden in child class
        return "Error";
    }
}
