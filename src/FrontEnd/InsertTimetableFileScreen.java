/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import BackEnd.Student;
import BackEnd.Timetable;
import BackEnd.TimetableManager;
import java.awt.Color;
import java.util.Scanner;
import javax.swing.border.LineBorder;

/**
 *
 * @author ryano
 */

public class InsertTimetableFileScreen extends javax.swing.JFrame {

    /**
     * Creates new form InsertTimetableFileScreen
     */
    private MainMenuScreen mainMenu;
    public InsertTimetableFileScreen() {
        initComponents();
        setLocationRelativeTo(null);
        lblError.setText("");
        lblError.setForeground(Color.red);
        setupDragDrop();
        txaFilePreview.setVisible(false);
        btnSetup();
    }
    
    public InsertTimetableFileScreen(MainMenuScreen mm){
        initComponents();
        this.mainMenu = mm;
        setLocationRelativeTo(null);
        lblError.setText("");
        lblError.setForeground(Color.red);
        setupDragDrop();
        txaFilePreview.setVisible(false);
        btnSetup();
    }
    
    private void btnSetup(){//hovver effect for buttons. this shiz took so long
        Color norm = new Color(47,56,120);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnSave.setForeground(Color.WHITE);
                btnSave.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnSave.setForeground(norm);
                btnSave.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSave.setForeground(norm);
                btnSave.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnSave.contains(evt.getPoint())) {
                    btnSave.setForeground(Color.WHITE);
                    btnSave.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnSave.setForeground(norm);
                    btnSave.setBorder(new LineBorder(norm));
                }
                
            }
        });
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnClose.setForeground(Color.WHITE);
                btnClose.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnClose.setForeground(norm);
                btnClose.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnClose.setForeground(norm);
                btnClose.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnClose.contains(evt.getPoint())) {
                    btnClose.setForeground(Color.WHITE);
                    btnClose.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnClose.setForeground(norm);
                    btnClose.setBorder(new LineBorder(norm));
                }
                
            }
        });
    }
    private boolean isValidTimetableFormat(String content) {
        
        if (content == null || content.trim().isEmpty()) {
            return false;
        }
        
        // Regex breakdown: lowkey, ai made this regex
        // ^(\d+)                -> group 1: id (row index)
        // #                     -> literal separator
        // ([\w.+-]+@[\w-]+\.[a-zA-Z]{2,})  -> group 2: email address
        // #                     -> literal separator
        // (\d+)                 -> group 3: day number
        // #                     -> literal separator
        // ([\w]+(%[\w]+)*)$     -> group 4: periods separated by %
        String lineRegex = "^(\\d+)#([\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,})#(\\d+)#([\\w]+(%[\\w]+)*)$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(lineRegex);
        
        String[] lines = content.split("\\r?\\n");

        String expectedEmail = null;
        int expectedPeriodCount = -1;
        int expectedId = 1; // ids should increment 1,2,3... per row
        
        for (String line : lines) {
            
            line = line.trim();

            if (line.isEmpty()) {
            continue;
            }
            
            java.util.regex.Matcher matcher = pattern.matcher(line);
            
            if (!matcher.matches()) {
                return false;
            }
            
            int id = Integer.parseInt(matcher.group(1));
            String email = matcher.group(2);
            int day = Integer.parseInt(matcher.group(3));
            String periodsRaw = matcher.group(4);
            
            // id should increment by 1 each row (row 1, row 2, row 3...)
            if (id != expectedId) {
                return false;
            }
            expectedId++;
            
            // every row should reference the same student email
            if (expectedEmail == null) {
                expectedEmail = email;
            } else if (!expectedEmail.equals(email)) {
                return false;
            }
            
            // every row should have the same number of periods (e.g. 8 for period0-period7)
            String[] periods = periodsRaw.split("%");
            if (expectedPeriodCount == -1) {
                expectedPeriodCount = periods.length;
            } else if (periods.length != expectedPeriodCount) {
                return false;
            }
            
            // day should be a valid day number (adjust range if your school week differs)
            if (day < 1 || day > 5) {
                return false;
            }
        }
        
        return expectedEmail != null;
    }
    
    private void setupDragDrop(){//This helper method sets up my drag drop file system.
        
        final javax.swing.border.Border normalBorder = pnlDropFile.getBorder();
        final javax.swing.border.Border hoverBorder = javax.swing.BorderFactory.createLineBorder(Color.GREEN, 3);
        new java.awt.dnd.DropTarget(pnlDropFile, new java.awt.dnd.DropTargetAdapter(){
            
            @Override
            public void dragEnter(java.awt.dnd.DropTargetDragEvent evt){
                pnlDropFile.setBorder(hoverBorder);
            }
            
            @Override
            public void dragExit(java.awt.dnd.DropTargetEvent evt){
                pnlDropFile.setBorder(normalBorder);
            }
            
            
            @Override
            public void drop(java.awt.dnd.DropTargetDropEvent evt){
                pnlDropFile.setBorder(normalBorder); // reset border regardless of outcome
                
                try{
                    evt.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);
                    
                    java.util.List<java.io.File> files = (java.util.List<java.io.File>)
                            evt.getTransferable().getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
                    
                    if (files.isEmpty()) {//litteraly checks if its empty
                        return;
                    }
                    java.io.File droppedFile = files.get(0);
                    
                    if (!droppedFile.getName().toLowerCase().endsWith(".txt")) {//checkes if its a txt file
                        lblError.setText("Please drop a txt file. =/)/");
                        lblError.setForeground(Color.red);
                        return;
                    }
                    
                    String content = new String(java.nio.file.Files.readAllBytes(droppedFile.toPath()));
                    txaFilePreview.setText(content);
                    lblError.setText("");
                    txaFilePreview.setVisible(true);
                    lblDropFileHere.setVisible(false);
                }catch(Exception ex){
                    lblError.setText("Couldn't read file:\n"+ ex.getMessage());
                }
            }
        });
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
        lblError = new javax.swing.JLabel();
        pnlDropFile = new javax.swing.JPanel();
        lblDropFileHere = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaFilePreview = new javax.swing.JTextPane();
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JToggleButton();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblError.setText("lblError");
        jPanel1.add(lblError, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 300, 380, -1));

        pnlDropFile.setBackground(new java.awt.Color(255, 255, 255));
        pnlDropFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120), 2));
        pnlDropFile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDropFileHere.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N
        lblDropFileHere.setForeground(new java.awt.Color(47, 56, 120));
        lblDropFileHere.setText("Drop File Here");
        pnlDropFile.add(lblDropFileHere, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 100, -1, -1));

        txaFilePreview.setBackground(new java.awt.Color(255, 255, 255));
        txaFilePreview.setBorder(null);
        txaFilePreview.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(txaFilePreview);

        pnlDropFile.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 210));

        jPanel1.add(pnlDropFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 70, 380, 230));

        btnSave.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(47, 56, 120));
        btnSave.setText("Save");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnSave.setContentAreaFilled(false);
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 70, -1));

        btnClose.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnClose.setForeground(new java.awt.Color(47, 56, 120));
        btnClose.setText("Close");
        btnClose.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnClose.setContentAreaFilled(false);
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, 70, -1));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loginBackground.png"))); // NOI18N
        lblBackground.setText("jLabel1");
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 688, 413));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        mainMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        String inTimetables = txaFilePreview.getText();
        int lineCount = inTimetables.split("\n", -1).length;//obtains number of field entries
        Timetable temp[] = new Timetable[lineCount];//used to temorarily store timetable fields
        int count = 0;
        
        if (isValidTimetableFormat(inTimetables)) {
            Scanner scFile = new Scanner(inTimetables);
            boolean allUnique = true;
            while(scFile.hasNext()){
                Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter("#");
                int ID = scLine.nextInt();
                String email = scLine.next();
                int day = scLine.nextInt();
                String lessons = scLine.next();
                temp[count] = new Timetable(ID, email, day, lessons);
                scLine.close();
                count++;
                if (TimetableManager.getKeys().contains(email+day)) {//checking to prevent multiple entries
                    lblError.setText("Email Day combination is taken in day: "+ID);
                    lblError.setForeground(Color.RED);
                    allUnique = false;
                }
            }
            scFile.close();
            
            if (allUnique) {//finally adding in timetable entries
                for (int i = 0; i < count; i++) {
                    TimetableManager.addTimetable(temp[i]);
                    lblError.setText("Timetable Loaded");
                    lblError.setForeground(Color.GREEN);
                }
            }
            
        }else{
            lblError.setForeground(Color.RED);
            lblError.setText("Invalid Timetable format.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(InsertTimetableFileScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsertTimetableFileScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsertTimetableFileScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsertTimetableFileScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InsertTimetableFileScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnClose;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblDropFileHere;
    private javax.swing.JLabel lblError;
    private javax.swing.JPanel pnlDropFile;
    private javax.swing.JTextPane txaFilePreview;
    // End of variables declaration//GEN-END:variables
}
