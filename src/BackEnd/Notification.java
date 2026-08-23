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
public class Notification {
    private String sender;
    private boolean forAll;
    private String receiver;
    private LocalDate uploadDate;
    private String content;

    public Notification(String sender, boolean forAll, String receiver, LocalDate uploadDate, String content) {
        this.sender = sender;
        this.forAll = forAll;
        this.receiver = receiver;
        this.uploadDate = uploadDate;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public boolean isForAll() {
        return forAll;
    }

    public String getReceiver() {
        return receiver;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public String getContent() {
        return content;
    }
    
}
