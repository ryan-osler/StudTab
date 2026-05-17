/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ryano
 */
public class TimetableManager {
    public static Timetable[] tArr;
    public static boolean loaded = false;
    public static Student currentUser;
    public static String schoolCode = null;
    
    private static int countTimetables(){
        if (schoolCode == null) {
            System.out.println("No School Code");
            return -1;
        }
        int count = 0;
        try{
            Scanner scFile = new Scanner(new File("data/" + getSchoolCode() + "/Timetables"));
            while(scFile.hasNext()){
                count++;
                scFile.nextLine();
            }
            scFile.close();
            return count;
        }   catch(FileNotFoundException f){
            System.out.println("Couldn't Find File\n"+f);
        }
        return -1;
    }
    
    public static boolean loadInfo(){
        if (schoolCode==null) {
            System.out.println("No School Code");
            return false;
        }
        int size = countTimetables();
        int count = 0;
        if (size<count) {
            return false;
        }
        tArr = new Timetable[size];
        try{
            Scanner scFile = new Scanner(new File("data/" + getSchoolCode() + "/Timetables"));
            while(scFile.hasNext()){
                Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter("#");
                int ID = scLine.nextInt();
                String email = scLine.next();
                int day = scLine.nextInt();
                String lessons = scLine.next();
                tArr[count] = new Timetable(ID, email,day,lessons);
                count++;
                scLine.close();
            }
            scFile.close();
        } catch(FileNotFoundException f){
            System.out.println("Countn't Find File\n"+f);
        }
        loaded = true;
        return true;
    }
    public static void setCurrentUser(Student inUser){
        currentUser = inUser;
    }
    public static Student getCurrentUser(){
        return currentUser;
    }
    public static void setSchoolCode(String inSchoolCode){
        schoolCode = inSchoolCode;
    }
    public static String getLesson(int day, int inPeriod){
        if (!loaded) {
            loadInfo();
        }
        if (tArr == null) return null;
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i].getEmail().equals(currentUser.getEmail()) && tArr[i].getDay() == day) {
                return tArr[i].getLesson(inPeriod);
            }
        }
        return null;
    }
    public static String getSchoolCode(){
        return schoolCode;
    }
    
}
