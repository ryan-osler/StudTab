/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
/**
 *
 * @author ryano
 */
/**got inspiration and help from https://www.youtube.com/watch?v=Ug_8d12LNc8
*This thing took so long to diagnose but eventually i realised i had the incorrect activation file
* in the library
*/
public class EmailManager {
    private static final String FROM_EMAIL = "studtab.notifications@gmail.com";
    private static final String FROM_PASSWORD = "cgmu cdix luwn ikzj";
    public static boolean sendEmail(String toEmail, String subject, String body){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new Authenticator() {
            
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            
            Transport.send(message);
            System.out.println("Email sent to: " +toEmail);
            return true;
            
        }catch (MessagingException ex) {
            System.out.println("Failed to send email: " + ex.getMessage());
            return false;
        }
    }
}
