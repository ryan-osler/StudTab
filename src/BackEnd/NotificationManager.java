/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author ryano
 */
public class NotificationManager {
    private static Notification[] nArr;
    private static boolean loaded = false;
    private static String currentSchoolCode;    
    public static int countNotifications(){
        int count = 0;
        try{
            Scanner scFile = new Scanner(new File("data/" + currentSchoolCode + "/Notifications.txt"));
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
    public static boolean loadInfo(){
        int size = countNotifications();
        int count = 0;
        if (size <= 0) {
            System.out.println("Size <= 0 // no notifications");
            return false;
            
        }
        nArr = new Notification[size];
        try{
            Scanner scFile = new Scanner(new File("data/" + currentSchoolCode + "/Notifications.txt"));
            while(scFile.hasNext()){
                Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter("#");
                String sender = scLine.next();
                boolean forAll = scLine.nextBoolean();
                String receiver = scLine.next();
                LocalDate date = LocalDate.parse(scLine.next(),DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String content = scLine.next();
                nArr[count] = new Notification(sender,forAll,receiver,date,content);
                count++;
                scLine.close();
            }
            scFile.close();
        }catch(FileNotFoundException f){
            System.out.println("Couldnt Find Notification Info on LoadInfo Method\n"+f);
            return false;
        }
        loaded = true;
        return true;
    }
    
    public static String[] getReceivers(Notification inNotif){
        if (!loaded) {
            loadInfo();
        }
        Scanner scRec = new Scanner(inNotif.getReceiver()).useDelimiter("#");
        String temp[] = null;
        int count = 0;
        while(scRec.hasNext()){
            temp[count] = scRec.next();
            count++;
        }
        return temp;
    }
    
    public static void setSchoolCode(String inSchoolCode){//just a basic ceaser cypher algorithm found on youtube.
        currentSchoolCode = inSchoolCode;
    }
    public static String encryptMessage(String message){
        int key = Integer.parseInt(currentSchoolCode.substring(3));
        int shift = key%26;
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            
            if (Character.isUpperCase(c)) {
                char shifted = (char) ('A' +(c - 'A' + shift)%26);
                result.append(shifted);
            }else if (Character.isLowerCase(c)) {
                char shifted = (char) ('a' + (c - 'a' + shift) % 26);
                result.append(shifted);
            }else{
                result.append(c);
            }
        }
        return result.toString();
    }
    
    public static String decryptMessage(String message){//just a basic ceaser cypher algorithm https://www.baeldung.com/java-caesar-cipher
        //i adapted this algorithm to suit my need.
        int key = Integer.parseInt(currentSchoolCode.substring(3));
        int shift = key%26;
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            
            if (Character.isUpperCase(c)) {
                char shifted = (char) ('A' + (c - 'A' - shift + 26)% 26);
                result.append(shifted);
            }else if (Character.isLowerCase(c)) {
                char shifted = (char) ('a' + (c - 'a' - shift + 26) % 26);
                result.append(shifted);
            }else{
                result.append(c);
            }
        }
        return result.toString();
    }

    public static int getNumNotificationsForUser(Student inUser){//finda number of notifications for a user
        if (!loaded) {
            loadInfo();
        }
        int size = 0;
        for (int i = 0; i < nArr.length; i++) {
            if (nArr[i].getReceiver().equals(inUser.getEmail()) ||
                    (nArr[i].isForAll() && !UserManager.getCurrentUser().getEmail().equals(nArr[i].getSender()))) {
                size++;
            }
        }
        return size;
    }
    public static String[] getNotificationsForUser(Student inUser){
        
        int count = 0;
        int size = 0;
        size = getNumNotificationsForUser(inUser);
        
        String[] temp = new String[size];
        if (size != 0) {
            for (int i = 0; i < nArr.length; i++) {
                if (nArr[i].getReceiver().equals(inUser.getEmail()) ||
                        (nArr[i].isForAll() && !UserManager.getCurrentUser().getEmail().equals(nArr[i].getSender()))) {
                    temp[count] = nArr[i].getContent();
                    count++;
                }
            }
            return temp;
        }
        return null;
    }

}
