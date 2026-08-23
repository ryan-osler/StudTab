/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author ryano
 */
public class Teacher extends Student
{
    private String subject;
    public Teacher (String inName, String inSurname, String inEmail, String inPassword, String inDOB, int inGrade,boolean inAdmin, String inSubject){
        super (inName, inSurname, inEmail, inPassword, inDOB, -1, inAdmin);
        subject = inSubject;
    }
    @Override
    public String getSubject(){//overriden method from parent class
        return subject;
    }
    
    
    public String printTeacher(){//does not override as method is different name in parent class
        return name+"#"+surname+"#"+email+"#"+password+"#"+DOB.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+"#"+isAdmin+"#-1#"+subject;
    }
    
}
