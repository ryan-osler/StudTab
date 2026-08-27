/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import javax.swing.Timer;

/**
 *
 * @author ryano
 */
public class IdleManager {
    private static Timer timer;
    private static final int IDLE_TIME= 30 * 60 * 1000; // 30 min
    
    public static void Start(){
        timer = new Timer(IDLE_TIME, e-> {
            System.out.println("User ddling, CLosing application");
            System.exit(0);
        });
        timer.setRepeats(false);
        timer.start();
        
        //listening for user interaction
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener(){
            @Override
            public void eventDispatched(AWTEvent event){
                timer.restart();
            }
        }, AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
    }
}
