/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
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
            nArr = new Notification[0];
            loaded = true;
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
    
    public static String[] getReceivers(Notification inNotif){//this needs fixing
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
    public static void updateFile(){//all these methods are like a repeat of the usermanager and timetablemanager classes
        String temp = "";
        for (int i = 0; i < nArr.length; i++) {
            temp += nArr[i].printNotification()+"\n";
        }
        try {
            FileWriter outFile = new FileWriter("data/" + currentSchoolCode + "/Notifications.txt");
            outFile.write(temp);
            outFile.close();
            System.out.println("Updated Notifications");
        } catch (IOException ex) {
            System.out.println("Error. Couldnt find notifications file when updating file\n" +ex + "\n\n");
        }
    }
    
    public static void addNotification(Notification inNotif){
        Notification[] temp = new Notification[nArr.length+1];
        for (int i = 0; i < nArr.length; i++) {
            temp[i] = nArr[i];
        }
        temp[nArr.length] = inNotif;
        nArr = temp;
        updateFile();
    }

    public static void editNotification(Notification inNotif){
        for (int i = 0; i < nArr.length; i++) {
            if (nArr[i].getSender().equals(inNotif.getSender())
                    && nArr[i].getReceiver().equals(inNotif.getReceiver())
                    && nArr[i].getUploadDate().equals(inNotif.getUploadDate())) {
                nArr[i] = inNotif;
                System.out.println("Updated Notification");
            }
        }
        updateFile();
    }

    public static void deleteNotification(Notification inNotif){
        int size = 0;
        for (int i = 0; i < nArr.length; i++) {
            if (!(nArr[i].getSender().equals(inNotif.getSender())
                    && nArr[i].getReceiver().equals(inNotif.getReceiver())
                    && nArr[i].getUploadDate().equals(inNotif.getUploadDate()))) {
                size++;
            }
        }
        int count = 0;
        Notification temp[] = new Notification[size];
        for (int i = 0; i < nArr.length; i++) {
            if (!(nArr[i].getSender().equals(inNotif.getSender())
                    && nArr[i].getReceiver().equals(inNotif.getReceiver())
                    && nArr[i].getUploadDate().equals(inNotif.getUploadDate()))) {
                temp[count] = nArr[i];
                count++;
            }
        }
        nArr = temp;
        updateFile();
        System.out.println("Notification Deleted");
    }
    
    public static Notification getNotification(String inSender, String inReceiver, java.time.LocalDate inDate){
        for (int i = 0; i < nArr.length; i++) {
            if (nArr[i].getSender().equals(inSender)
                    && nArr[i].getReceiver().equals(inReceiver)
                    && nArr[i].getUploadDate().equals(inDate)) {
                return nArr[i];
            }
        }
        return null;
    }
}
