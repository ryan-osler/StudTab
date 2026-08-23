/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.Scanner;

/**
 *
 * @author ryano
 */
public class Timetable {
    private int ID;
    private String email;
    private int day;
    private String lessons;
    
    public Timetable(int inID, String inEmail, int inDay, String inLessons){
        ID = inID;
        email = inEmail;
        day = inDay;
        lessons = inLessons;
    }
    public String getEmail(){
        return email;
    }
    public int getDay(){
        return day;
    }
    public String getLesson(int inPeriod){
        Scanner scLessons = new Scanner(lessons).useDelimiter("%");
        if (inPeriod > 6 || inPeriod < 0) {
            return null;
        }
        for (int i = 0; i < inPeriod; i++) {
            scLessons.next();
        }
        return scLessons.next();
    }
    public String printTimetable(){
        return ID+"#"+email+"#"+day+"#"+lessons;
    }

    public int getID() {
        return ID;
    }
}
