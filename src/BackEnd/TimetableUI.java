/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.time.LocalDate;

/**
 *
 * @author ryano
 */
public class TimetableUI {
    public static void main(String[] args) {
        
        TimetableManager.setSchoolCode("SJC001");
        Student r = new Student("Ryan", "Osler", "26344@stjohnscollege.co.za", "sjC648627", "18-11-2007", 12, true);
        TimetableManager.setCurrentUser(r);
        for (int i = 0; i < 6; i++) {
            System.out.println(
                TimetableManager.getLesson(1, i)+"\t"+
                TimetableManager.getLesson(2, i)+"\t"+
                TimetableManager.getLesson(3, i)+"\t"+
                TimetableManager.getLesson(4, i)+"\t"+
                TimetableManager.getLesson(5, i));
        }   
    }
}
