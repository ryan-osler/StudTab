/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.LineBorder;

/**
 *
 * @author ryano
 */
public class CreateSchoolScreen extends javax.swing.JFrame {

    /**
     * Creates new form CreateSchoolScreen
     */
    public CreateSchoolScreen() {
        initComponents();
        btnSetup();
        setLocationRelativeTo(null);
        lblError.setText("");
    }
    
    private void btnSetup(){//hovver effect for buttons. this shiz took so long
        Color norm = new Color(47,56,120);
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnBack.setForeground(Color.WHITE);
                btnBack.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnBack.setForeground(norm);
                btnBack.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnBack.setForeground(norm);
                btnBack.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnBack.contains(evt.getPoint())) {
                    btnBack.setForeground(Color.WHITE);
                    btnBack.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnBack.setForeground(norm);
                    btnBack.setBorder(new LineBorder(norm));
                }
                
            }
        });
        btnCreateSchool.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnCreateSchool.setForeground(Color.WHITE);
                btnCreateSchool.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnCreateSchool.setForeground(norm);
                btnCreateSchool.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCreateSchool.setForeground(norm);
                btnCreateSchool.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnCreateSchool.contains(evt.getPoint())) {
                    btnCreateSchool.setForeground(Color.WHITE);
                    btnCreateSchool.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnCreateSchool.setForeground(norm);
                    btnCreateSchool.setBorder(new LineBorder(norm));
                }
                
            }
        });
    }
    private boolean validateInfo(){//method needs work.
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        //Statement for presence check
        if (txfName.getText().trim().isEmpty() || txfSurname.getText().trim().isEmpty() || txfEmail.getText().trim().isEmpty() || 
                txfPassword.getText().trim().isEmpty() || txfSchoolName.getText().trim().isEmpty() || 
                txfSchoolCode.getText().trim().isEmpty() || txfSubject.getText().trim().isEmpty() || dpDOB.getDate() == null || 
                dpFoundingDate.getDate() == null) {
            return false;
        }
        else if (!txfEmail.getText().matches(emailRegex)) {//checking email format with regex
            lblError.setText("Incorrect Email Format");
            return false;
        }else if (txfName.getText().contains("#") || txfSurname.getText().contains("#") || txfEmail.getText().contains("#") || 
                txfPassword.getText().contains("#") || txfSchoolName.getText().contains("#") || 
                txfSchoolCode.getText().contains("#") || txfSubject.getText().contains("#")) {
            return false;//preventing syntax injection.
        }else if (!txfSchoolCode.getText().trim().substring(0,3).matches("^[A-Za-z]+$")
                || !txfSchoolCode.getText().trim().substring(3).matches("^[0-9]+$")) {
            lblError.setText("Incorrect Code Format \"AAA000\"");
            return false;//checking format for 
        }
        return true;
    }
    private boolean createTimttableFile(String code){//method creates timetables txf file
        try {
            FileWriter outFile = new FileWriter("data/"+code+"/Timetables");//data is not added now
            outFile.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CreateSchoolScreen.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Couldnt create timetabled file");
            lblError.setText("Couldnt create timetabled file");
        }
        return false;
    }
    private String createUserFile(){
        String temp = "";
        
        LocalDate DOB = dpDOB.getDate();
        
        temp += txfName.getText().trim()+"#"+txfSurname.getText().trim()+"#"+txfEmail.getText().trim()+"#"+txfPassword.getText().trim()+"#"+
                DOB.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+"#true#-1#"+txfSubject.getText();
        return temp;
    }
    private boolean createSchoolInfoFile(String name, String code, LocalDate date, boolean isHigh){
        try {
            FileWriter outFile = new FileWriter("data/"+code+"/SchoolInfo");
            String temp = name+"\n"+code+"\n"+date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+"\n"+isHigh;
            outFile.write(temp);
            outFile.close();
        } catch (IOException ex) {
            Logger.getLogger(CreateSchoolScreen.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error While Creating SchoolInfo file.");
            lblError.setText("Couldnt Create School File");
            return false;
        }
        
        return true;/*this is a helper method to create the school info file.
        this is a boolean method for future use with validation ext.
        */
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
        lblSchoolName = new javax.swing.JLabel();
        lblSchoolCode = new javax.swing.JLabel();
        lblFoundingDate = new javax.swing.JLabel();
        txfSchoolName = new javax.swing.JTextField();
        txfSchoolCode = new javax.swing.JTextField();
        lblSchoolInfo = new javax.swing.JLabel();
        cbxHighSchool = new javax.swing.JCheckBox();
        lblAdminInfo = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblSurname = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblDOB = new javax.swing.JLabel();
        lblSubject = new javax.swing.JLabel();
        txfName = new javax.swing.JTextField();
        txfSurname = new javax.swing.JTextField();
        txfEmail = new javax.swing.JTextField();
        txfPassword = new javax.swing.JTextField();
        txfSubject = new javax.swing.JTextField();
        dpDOB = new com.github.lgooddatepicker.components.DatePicker();
        dpFoundingDate = new com.github.lgooddatepicker.components.DatePicker();
        btnCreateSchool = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 580));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(47, 56, 120));
        lblTitle.setText("Create School");
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        lblSchoolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSchoolName.setText("School Name:");
        lblSchoolName.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblSchoolName.setForeground(new java.awt.Color(47, 56, 120));
        lblSchoolName.setToolTipText("");
        jPanel1.add(lblSchoolName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        lblSchoolCode.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblSchoolCode.setForeground(new java.awt.Color(47, 56, 120));
        lblSchoolCode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSchoolCode.setText("School Code:");
        lblSchoolCode.setToolTipText("");
        jPanel1.add(lblSchoolCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, -1, -1));

        lblFoundingDate.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblFoundingDate.setForeground(new java.awt.Color(47, 56, 120));
        lblFoundingDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoundingDate.setText("Founding Date:");
        lblFoundingDate.setToolTipText("");
        jPanel1.add(lblFoundingDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, -1, -1));

        txfSchoolName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfSchoolNameActionPerformed(evt);
            }
        });
        jPanel1.add(txfSchoolName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 160, -1));
        jPanel1.add(txfSchoolCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 80, -1));

        lblSchoolInfo.setText("School Info");
        lblSchoolInfo.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N
        lblSchoolInfo.setForeground(new java.awt.Color(47, 56, 120));
        jPanel1.add(lblSchoolInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, -1, -1));

        cbxHighSchool.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 14)); // NOI18N
        cbxHighSchool.setForeground(new java.awt.Color(47, 56, 120));
        cbxHighSchool.setText("HighSchool");
        jPanel1.add(cbxHighSchool, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, -1, -1));

        lblAdminInfo.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N
        lblAdminInfo.setForeground(new java.awt.Color(47, 56, 120));
        lblAdminInfo.setText("Admin Info");
        jPanel1.add(lblAdminInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, -1, -1));

        lblName.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(47, 56, 120));
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblName.setText("Name:");
        jPanel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, -1, -1));

        lblSurname.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblSurname.setForeground(new java.awt.Color(47, 56, 120));
        lblSurname.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSurname.setText("Surname:");
        jPanel1.add(lblSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, -1, -1));

        lblEmail.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(47, 56, 120));
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEmail.setText("Email:");
        jPanel1.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, -1, -1));

        lblPassword.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(47, 56, 120));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPassword.setText("Password:");
        jPanel1.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, -1, -1));

        lblDOB.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblDOB.setForeground(new java.awt.Color(47, 56, 120));
        lblDOB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDOB.setText("Date of Birth:");
        jPanel1.add(lblDOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, -1, -1));

        lblSubject.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        lblSubject.setForeground(new java.awt.Color(47, 56, 120));
        lblSubject.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSubject.setText("Subject:");
        jPanel1.add(lblSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, -1, -1));
        jPanel1.add(txfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 170, 110, -1));
        jPanel1.add(txfSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 110, -1));
        jPanel1.add(txfEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 250, 210, -1));
        jPanel1.add(txfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, 160, -1));
        jPanel1.add(txfSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 370, 110, -1));
        jPanel1.add(dpDOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 330, -1, -1));
        jPanel1.add(dpFoundingDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, -1));

        btnCreateSchool.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnCreateSchool.setForeground(new java.awt.Color(47, 56, 120));
        btnCreateSchool.setText("Create School");
        btnCreateSchool.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnCreateSchool.setContentAreaFilled(false);
        btnCreateSchool.setFocusPainted(false);
        btnCreateSchool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateSchoolActionPerformed(evt);
            }
        });
        jPanel1.add(btnCreateSchool, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 480, 100, -1));

        btnBack.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnBack.setForeground(new java.awt.Color(47, 56, 120));
        btnBack.setText("Back");
        btnBack.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        jPanel1.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 480, 100, -1));

        lblError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblError.setText("lblError");
        lblError.setForeground(new java.awt.Color(204, 0, 0));
        jPanel1.add(lblError, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 430, 290, -1));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loginBackground.png"))); // NOI18N
        lblBackground.setText("jLabel1");
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 580));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1024, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfSchoolNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfSchoolNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfSchoolNameActionPerformed

    private void btnCreateSchoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateSchoolActionPerformed
        if (validateInfo()) {
            String schoolName = txfSchoolName.getText().trim();
            String schoolCode = txfSchoolCode.getText().trim();
            LocalDate foundingDate = dpFoundingDate.getDate();
            boolean isHigh = cbxHighSchool.isSelected();
            
            File folder = new File("data/"+txfSchoolCode.getText());
            try{//creates new file directory for the school
                if (folder.mkdir()) {
                    System.out.println("Created Directory");//creates directory
                    FileWriter outFile = new FileWriter("data/" + txfSchoolCode.getText() + "/Users");
                    outFile.write(createUserFile());
                    
                    File picFolder = new File(folder.getPath()+"/Pictures");//creates pictures directory inside of school folder
                    picFolder.mkdir();
                    
                    createSchoolInfoFile(schoolName, schoolCode, foundingDate, isHigh);
                    outFile.close();
                
                    createTimttableFile(schoolCode);//creates timetable file without any content
                    outFile.close();
                
                    LoginScreen ls = new LoginScreen();
                    ls.setVisible(true);
                    this.dispose();
                }else{
                    System.out.println("School Code In Use");
                    lblError.setText("SchoolCode in use");
                }
            
            
            }catch(IOException f){
               f.printStackTrace();
                System.out.println("Error With File Creation");
            }
        }
        
    }//GEN-LAST:event_btnCreateSchoolActionPerformed

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
            java.util.logging.Logger.getLogger(CreateSchoolScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateSchoolScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateSchoolScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateSchoolScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateSchoolScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreateSchool;
    private javax.swing.JCheckBox cbxHighSchool;
    private com.github.lgooddatepicker.components.DatePicker dpDOB;
    private com.github.lgooddatepicker.components.DatePicker dpFoundingDate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAdminInfo;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblFoundingDate;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSchoolCode;
    private javax.swing.JLabel lblSchoolInfo;
    private javax.swing.JLabel lblSchoolName;
    private javax.swing.JLabel lblSubject;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txfEmail;
    private javax.swing.JTextField txfName;
    private javax.swing.JTextField txfPassword;
    private javax.swing.JTextField txfSchoolCode;
    private javax.swing.JTextField txfSchoolName;
    private javax.swing.JTextField txfSubject;
    private javax.swing.JTextField txfSurname;
    // End of variables declaration//GEN-END:variables
}
