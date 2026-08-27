/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import FrontEnd.LoginScreen;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles loading, saving, and managing all Student/Teacher user accounts
 * for a given school. Reads from and writes to a flat "#"-delimited text
 * file located at data/{schoolCode}/Users. Also keeps track of which user
 * is currently logged in via setCurrentUser/getCurrentUser.
 *
 * @author ryano
 */
public class UserManager {//establish fields
    public static Student[] userArr;
    public static boolean loaded = false;
    private static Student currentUser;
    
    public static int countUsers(String inSchoolCode){//counts users in file. obviosly integer for number of users
        int count = 0;
        try{
            Scanner scFile = new Scanner(new File("data/" + inSchoolCode + "/Users"));
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
    
    /**
     * Loads all user records from the Users file for the given school code into
     * userArr. Each line is split on "#" and parsed into either a Student or a
     * Teacher object depending on the isAdmin flag stored in that line.
     *
     * File format per line:
     * name#surname#email#password#DOB#isAdmin#grade (Student)
     * name#surname#email#password#DOB#isAdmin#-#subject (Teacher)
     *
     * @param inSchoolCode the school code used to locate the correct data folder
     * @return true once loading has completed (note: currently returns true even
     *         if the file was not found - see FileNotFoundException branch below)
     */
    public static boolean loadInfo(String inSchoolCode){//loads into array. return type used to validate if info is correctly loaded
        int size = countUsers(inSchoolCode);
        int count = 0;
        if (size <=0) {
            return false;
        }
        userArr = new Student[size];
        try{
            Scanner scFile = new Scanner(new File("data/" + inSchoolCode + "/Users"));
            while(scFile.hasNext()){//this is basic oop principles.
                Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter("#");
                String name = scLine.next();
                String surname = scLine.next();
                String email = scLine.next();
                String password = scLine.next();
                String DOB = scLine.next();
                boolean isAdmin = scLine.nextBoolean();
                if (!isAdmin) {// regular student record - next field is grade
                    int grade = scLine.nextInt();
                    userArr[count] = new Student(name, surname, email, password, DOB, grade, isAdmin);
                    count++;
                }
                else if(isAdmin){
                    // teacher record - skip placeholder grade field, read subject instead
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
    
    /**
     * Checks a supplied email and password against all loaded users to
     * authenticate a login attempt. Loads user data first if it hasn't
     * been loaded yet for this school.
     *
     * @param inEmail the email address entered by the user attempting to log in
     * @param inPassword the password entered by the user attempting to log in
     * @param inSchoolCode the school code used to load the correct set of users
     * @return the matching Student (or Teacher) object if credentials are correct, otherwise null
     */
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
    
    /**
     * Sets the given user as the currently logged-in user for this session.
     *
     * @param inUser the Student (or Teacher) to mark as currently logged in
     */
    public static void setCurrentUser(Student inUser){//used to keep info of instance
        currentUser = inUser;
    }
    
    /**
     * Retrieves whichever user is currently logged in. note this can also be changed to manipulate results for admins
     *
     * @return the currently logged-in Student (or Teacher), or null if no one is logged in
     */
    public static Student getCurrentUser(){
        return currentUser;
    }
    
    /**
     * Writes the entire contents of userArr back out to the Users file on disk,
     * overwriting whatever was there before. Teachers and Students are printed
     * using their respective printTeacher()/printStudent() formatting methods
     * so the file stays in the same "#"-delimited format loadInfo expects.
     * Called automatically after any add/edit/delete operation.
     */
    public static void updateFile(){
        String temp = "";
        for (int i = 0; i < userArr.length; i++) {
            if (userArr[i] instanceof Teacher) {
                temp+= ((Teacher) userArr[i]).printTeacher()+"\n";
            } else{
                temp += userArr[i].printStudent()+"\n";
            }
        }
        try {
            FileWriter outFile = new FileWriter("data/" + TimetableManager.getSchoolCode() + "/Users");
            outFile.write(temp);
            outFile.close();
            System.out.println("Updated Users");
        } catch (IOException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Builds the full file path for a given file name inside the currently
     * active school's data folder.
     *
     * @param fileName the name of the file to build a path for (e.g. "Users")
     * @return the full relative path string, e.g. "data/12345/Users"
     */
    public static String getPath(String fileName) {
        return "data/" + TimetableManager.getSchoolCode() + "/" + fileName;
    }
    
    /**
     * Creates the data folder for the currently active school code on disk,
     * if it does not already exist. Should be called before any file writes
     * for a brand new school.
     */
    public static void createSchoolFolder() {
        new File("data/" + TimetableManager.getSchoolCode()).mkdirs();
    }
    
    /**
     * Adds a new user to userArr by creating a new, larger array, copying
     * all existing users across, and appending the new one at the end.
     * Persists the change to disk immediately afterwards.
     *
     * @param inUser the new Student (or Teacher) to add
     */
    public static void addUser(Student inUser){
        Student[] temp = new Student[userArr.length+1];
        for (int i = 0; i < userArr.length; i++) {
            temp[i] = userArr[i];
        }
        temp[userArr.length] = inUser;
        userArr = temp;
        updateFile();   
    }
    
    /**
     * Finds the existing user with the same email as inUser and replaces
     * their record with the updated version. Persists the change to disk
     * immediately afterwards.
     *
     * @param inUser the updated Student (or Teacher) data, matched by email
     */
    public static void editUser(Student inUser){
        for (int i = 0; i < userArr.length; i++) {
            if (userArr[i].getEmail().equals(inUser.getEmail())) {
                userArr[i] = inUser;
                System.out.println("Updated User");
            }
        }
        updateFile();
    }
    
    /**
     * Removes all users from userArr whose email matches inUser's email,
     * by building a new, smaller array containing only the users that
     * should remain. Persists the change to disk immediately afterwards.
     * since all emails are unique, in theory duplicate users will be deleted, this is not an issue.
     * @param inUser the user whose email identifies which record(s) to delete
     */
    public static void deleteUser(Student inUser){//this method deletes all with matching emails
        int size = 0;
        for (int i = 0; i < userArr.length; i++) {//counting size without deleted
            if (!userArr[i].getEmail().equals(inUser.getEmail())) {
                size++;
            }
        }
        int count = 0;
        Student temp[] = new Student[size];
        for (int i = 0; i < userArr.length; i++) {
            if (!userArr[i].getEmail().equals(inUser.getEmail())) {
                temp[count] = userArr[i];
                count++;
            }
        }
        userArr = temp;
        updateFile();
        System.out.println("User Deleted");
    }
    
    /**
     * Searches userArr for a user with a matching email address.
     *
     * @param inEmail the email address to search for
     * @return the matching Student (or Teacher) object, or null if no match is found
     */
    public static Student getUser(String inEmail){//search for user with email
        for (int i = 0; i < userArr.length; i++) {
            if (userArr[i].getEmail().equals(inEmail)) {
                return userArr[i];
            }
        }
        return null;
    }
    
    public static void sendAllEmail(String inSubject, String inBody){
        for (int i = 0; i < userArr.length; i++) {
            EmailManager.sendEmail(userArr[i].getEmail(), inSubject, inBody);
        }
    }
}
