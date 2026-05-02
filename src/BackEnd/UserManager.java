/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import FrontEnd.LoginScreen;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ryano
 */
public class UserManager {
    public static Student[] userArr;
    public static boolean loaded = false;
    private static Student currentUser;
    
    public static int countUsers(String inSchoolCode){
        int count = 0;
        try{
            Scanner scFile = new Scanner(new File("Users"+inSchoolCode+".txt"));
            while(scFile.hasNext()){
                count++;
                scFile.nextLine();
            }
            scFile.close();
            return count;
        } catch (FileNotFoundException f){
            System.out.println("Couldnt Find User Info file\n"+f);
        }
        return -1;
    }
    
    public static boolean loadInfo(String inSchoolCode){
        int size = countUsers(inSchoolCode);
        int count = 0;
        if (size <=0) {
            return false;
        }
        userArr = new Student[size];
        try{
            Scanner scFile = new Scanner(new File("Users"+inSchoolCode+".txt"));
            while(scFile.hasNext()){
                Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter("#");
                String name = scLine.next();
                String surname = scLine.next();
                String email = scLine.next();
                String password = scLine.next();
                String DOB = scLine.next();
                boolean isAdmin = scLine.nextBoolean();
                if (!isAdmin) {
                    int grade = scLine.nextInt();
                    userArr[count] = new Student(name, surname, email, password, DOB, grade, isAdmin);
                    count++;
                }
                else if(isAdmin){
                    scLine.next();
                    String subject = scLine.next();
                    userArr[count] = new Teacher(name, surname, email, password, DOB, -1, isAdmin, subject);
                    count++;
                }
                scLine.close();
            }
            scFile.close();
        }catch(FileNotFoundException f){
            System.out.println("Couldnt Find User Info on LoadInfo Method\n"+f);
        }
        loaded = true;
        return true;
    }
    public static Student checkInfo(String inEmail, String inPassword, String inSchoolCode){
        if (!loaded) {
            loadInfo(inSchoolCode);
        }
        for (int i = 0; i < userArr.length; i++) {
            if (inEmail.equals(userArr[i].getEmail()) && inPassword.equals(userArr[i].getPassword())) {
                return userArr[i];
            }
        }
        return null;
    }
    
    public static void setCurrentUser(Student inUser){//used to keep info of instance
        currentUser = inUser;
    }
    public static Student getCurrentUser(){
        return currentUser;
    }
}
