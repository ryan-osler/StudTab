/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import BackEnd.Student;
import BackEnd.Teacher;
import BackEnd.TimetableManager;
import BackEnd.UserManager;
import java.awt.Color;
import java.util.List;
import java.io.File;
import java.awt.datatransfer.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DnDConstants;

/**
 *
 * @author ryano
 */
public class AddUserScreen extends javax.swing.JFrame {

    /**
     * Creates new form AddUserScreen
     */
    private MainMenuScreen mainMenu;
    public AddUserScreen(MainMenuScreen mm) {
        initComponents();
        setLocationRelativeTo(null);
        dragDropSetup();
        TimetableManager.setSchoolCode("SJC001");
        btnSetup();
        this.mainMenu = mm;
        lblError.setText("");
        dragDropSetup();
        checkBoxSetup();
    }
    
    public AddUserScreen(){
        initComponents();
        setLocationRelativeTo(null);
        dragDropSetup();
        TimetableManager.setSchoolCode("SJC001");
        btnSetup();
        lblError.setText("");
        dragDropSetup();
        checkBoxSetup();
    }
    
    private void checkBoxSetup(){
        cbxIsTeacher.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (cbxIsTeacher.isSelected()) {
                    lblGrSub.setText("Subject:");
                }else{
                    lblGrSub.setText("Grade:");
                }
                
            }
        });
    }
    private void btnSetup(){//hovver effect for buttons. this shiz took so long
        Color norm = new Color(47,56,120);
        btnAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnAddUser.setForeground(Color.WHITE);
                btnAddUser.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnAddUser.setForeground(norm);
                btnAddUser.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAddUser.setForeground(norm);
                btnAddUser.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnAddUser.contains(evt.getPoint())) {
                    btnAddUser.setForeground(Color.WHITE);
                    btnAddUser.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnAddUser.setForeground(norm);
                    btnAddUser.setBorder(new LineBorder(norm));
                }
                
            }
        });
        btnMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnMainMenu.setForeground(Color.WHITE);
                btnMainMenu.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnMainMenu.setForeground(norm);
                btnMainMenu.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnMainMenu.setForeground(norm);
                btnMainMenu.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnMainMenu.contains(evt.getPoint())) {
                    btnMainMenu.setForeground(Color.WHITE);
                    btnMainMenu.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnMainMenu.setForeground(norm);
                    btnMainMenu.setBorder(new LineBorder(norm));
                }
                
            }
        });
    }
    
    private boolean validateInfo(){
         String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (txfName.getText().isEmpty() || txfSurname.getText().isEmpty() || txfEmail.getText().isEmpty() 
                || txfGrSub.getText().isEmpty() || txfPassword.getText().isEmpty() || dpDOB.getDate() == null) {
            lblError.setText("Missing Information");
            lblError.setForeground(Color.red);
            return false;
        }else if (!txfEmail.getText().matches(emailRegex)) {
            lblError.setText("Incorrect Email Format");
            return false;
        } else if ((txfName.getText() + txfSurname.getText() + txfPassword.getText() + txfEmail.getText() + txfGrSub.getText()).contains("#")) {
            lblError.setText("Cannot Contain a #");
            lblError.setForeground(Color.red);
            return false;
        }
        return true;
    }
    
    
    private void dragDropSetup(){
        final javax.swing.border.Border normalBorder = pnlFile.getBorder();
        final javax.swing.border.Border hoverBorder = javax.swing.BorderFactory.createLineBorder(Color.GREEN, 3);
        
        new DropTarget(pnlFile, new DropTargetAdapter(){
            
            @Override
            public void dragEnter(DropTargetDragEvent evt){
                pnlFile.setBorder(hoverBorder);
            }
            
            @Override
            public void dragExit(DropTargetEvent evt){
                pnlFile.setBorder(normalBorder);
            }
            
            @Override
            public void drop(DropTargetDropEvent evt){
                pnlFile.setBorder(normalBorder); // reset border regardless of outcome
                
                try{
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    
                    List<File> files = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    
                    if (files.isEmpty()) {
                        return;
                    }
                    File droppedFile = files.get(0);
                    
                    if (!droppedFile.getName().toLowerCase().endsWith(".png")) {
                        lblError.setText("Please drop a PNG file.");
                        lblError.setForeground(Color.red);
                        return;
                    }
                    
                    BufferedImage img = ImageIO.read(droppedFile);
                    
                    if (img == null) {
                        lblError.setText("Couldn't read PNG file.");
                        lblError.setForeground(Color.red);
                        return;
                    }
                    
                    if (img.getWidth() != 170 || img.getHeight() != 120) {
                       lblError.setText("Image must be exactly 170x120 pixels.");
                        lblError.setForeground(Color.red);
                        return;
                    }
                    
                    lblPicture.setIcon(new ImageIcon(img));
                    lblPicture.setText(""); // clear the "Drop Profile Picture Here" placeholder text
                    lblError.setText("");
                    
                }catch(Exception ex){
                    lblError.setText("Couldn't read file:\n" + ex.getMessage());
                    lblError.setForeground(Color.red);
                }
            }
        });
    }
    
    private void saveProfilePicture(String filename) {
        Icon icon = lblPicture.getIcon();
        if (icon instanceof ImageIcon) {
            BufferedImage img = (BufferedImage) ((ImageIcon) icon).getImage();
            
            File saveDir = new File("data/"+TimetableManager.getSchoolCode() + "/Pictures");
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            
            try {
                File savedFile = new File(saveDir, filename + ".png");
                ImageIO.write(img, "png", savedFile);
            } catch (IOException ex) {
                lblError.setText("Couldn't save picture:\n" + ex.getMessage());
                lblError.setForeground(Color.red);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblSurname = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblDOB = new javax.swing.JLabel();
        lblGrSub = new javax.swing.JLabel();
        cbxIsTeacher = new javax.swing.JCheckBox();
        txfName = new javax.swing.JTextField();
        txfSurname = new javax.swing.JTextField();
        txfEmail = new javax.swing.JTextField();
        txfPassword = new javax.swing.JTextField();
        txfGrSub = new javax.swing.JTextField();
        pnlFile = new javax.swing.JPanel();
        lblPicture = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        btnMainMenu = new javax.swing.JButton();
        dpDOB = new com.github.lgooddatepicker.components.DatePicker();
        lblError = new javax.swing.JLabel();
        txfBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(47, 56, 120));
        lblTitle.setText("Add User");
        lblTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        lblName.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(47, 56, 120));
        lblName.setText(" Name:");
        jPanel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        lblSurname.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblSurname.setForeground(new java.awt.Color(47, 56, 120));
        lblSurname.setText(" Surname:");
        jPanel1.add(lblSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        lblEmail.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(47, 56, 120));
        lblEmail.setText(" Email:");
        jPanel1.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));

        lblPassword.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(47, 56, 120));
        lblPassword.setText("Password:");
        jPanel1.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        lblDOB.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblDOB.setForeground(new java.awt.Color(47, 56, 120));
        lblDOB.setText("Date Of Birth:");
        jPanel1.add(lblDOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        lblGrSub.setText("Grade:");
        lblGrSub.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblGrSub.setForeground(new java.awt.Color(47, 56, 120));
        jPanel1.add(lblGrSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));

        cbxIsTeacher.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        cbxIsTeacher.setForeground(new java.awt.Color(47, 56, 120));
        cbxIsTeacher.setText("Teacher");
        jPanel1.add(cbxIsTeacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, -1, -1));

        txfName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfNameActionPerformed(evt);
            }
        });
        jPanel1.add(txfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, -1));
        jPanel1.add(txfSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 120, -1));
        jPanel1.add(txfEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 180, -1));

        txfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 120, -1));
        jPanel1.add(txfGrSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 100, -1));

        pnlFile.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(47, 56, 120), 3, true));
        pnlFile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPicture.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 12)); // NOI18N
        lblPicture.setForeground(new java.awt.Color(47, 56, 120));
        lblPicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPicture.setText("Drop Profile Picture Here");
        pnlFile.add(lblPicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 40, 170, 120));

        jPanel1.add(pnlFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 220, 210));

        btnAddUser.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(47, 56, 120));
        btnAddUser.setText("Add User");
        btnAddUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnAddUser.setContentAreaFilled(false);
        btnAddUser.setFocusPainted(false);
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 450, 100, -1));

        btnMainMenu.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnMainMenu.setForeground(new java.awt.Color(47, 56, 120));
        btnMainMenu.setText("Main Menu");
        btnMainMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnMainMenu.setContentAreaFilled(false);
        btnMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainMenuActionPerformed(evt);
            }
        });
        jPanel1.add(btnMainMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 100, -1));
        jPanel1.add(dpDOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, -1, -1));

        lblError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblError.setText("jLabel1");
        lblError.setForeground(new java.awt.Color(204, 0, 0));
        jPanel1.add(lblError, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 490, 290, -1));

        txfBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loginBackground.png"))); // NOI18N
        txfBackground.setText("Background Icon");
        jPanel1.add(txfBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 550));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfNameActionPerformed

    private void txfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfPasswordActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        if (validateInfo()) {
            Student temp;
            if (cbxIsTeacher.isSelected()) {
                temp = new Teacher(txfName.getText(), txfSurname.getText(), txfEmail.getText(), txfPassword.getText(),
                        dpDOB.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        -1, true, txfGrSub.getText());
            }else{
                temp = new Student(txfName.getText(), txfSurname.getText(), txfEmail.getText(),
                        txfPassword.getText(), dpDOB.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                         Integer.parseInt(txfGrSub.getText()), false);
            }
            if (lblPicture.getIcon() != null) {
                saveProfilePicture(txfName.getText()+txfSurname.getText());
            }
            UserManager.addUser(temp);
            lblError.setForeground(Color.GREEN);
            lblError.setText("UserAdded");
            
        }
        
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainMenuActionPerformed
        mainMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMainMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddUserScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddUserScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddUserScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddUserScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddUserScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnMainMenu;
    private javax.swing.JCheckBox cbxIsTeacher;
    private com.github.lgooddatepicker.components.DatePicker dpDOB;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblGrSub;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPicture;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlFile;
    private javax.swing.JLabel txfBackground;
    private javax.swing.JTextField txfEmail;
    private javax.swing.JTextField txfGrSub;
    private javax.swing.JTextField txfName;
    private javax.swing.JTextField txfPassword;
    private javax.swing.JTextField txfSurname;
    // End of variables declaration//GEN-END:variables
}
