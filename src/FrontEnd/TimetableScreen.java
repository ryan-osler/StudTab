/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import BackEnd.Student;
import BackEnd.TimetableManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ryano
 */
public class TimetableScreen extends javax.swing.JFrame {

    /**
     * Creates new form TimetableScreen
     */
    private MainMenuScreen mainMenu;
    public TimetableScreen(MainMenuScreen mm) {
        
        initComponents();
        setLocationRelativeTo(null);
        setTblTimetable();
        this.mainMenu = mm;
        loadTimetable();
        tblTimetable.setDefaultEditor(Object.class, null);
        btnSetup();
    }
    private Student targetStudent;
    public TimetableScreen(MainMenuScreen mm, Student inUser) {
        
        initComponents();
        targetStudent = inUser;
        setLocationRelativeTo(null);
        setTblTimetable();
        this.mainMenu = mm;
        loadTargetTimetable();
        btnSetup();
        
    }
    
    public TimetableScreen(){
        initComponents();
        setLocationRelativeTo(null);
        setTblTimetable();
        loadTimetable();
        tblTimetable.setDefaultEditor(Object.class, null);
        btnSetup();
    }
    
    private void btnSetup(){//hovver effect for buttons. this shiz took so long
        Color norm = new Color(47,56,120);
        btnReturnToMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnReturnToMainMenu.setForeground(Color.WHITE);
                btnReturnToMainMenu.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnReturnToMainMenu.setForeground(norm);
                btnReturnToMainMenu.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnReturnToMainMenu.setForeground(norm);
                btnReturnToMainMenu.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnReturnToMainMenu.contains(evt.getPoint())) {
                    btnReturnToMainMenu.setForeground(Color.WHITE);
                    btnReturnToMainMenu.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnReturnToMainMenu.setForeground(norm);
                    btnReturnToMainMenu.setBorder(new LineBorder(norm));
                }
                
            }
        });
        btnInsertTimetableFile.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnInsertTimetableFile.setForeground(Color.WHITE);
                btnInsertTimetableFile.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnInsertTimetableFile.setForeground(norm);
                btnInsertTimetableFile.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnInsertTimetableFile.setForeground(norm);
                btnInsertTimetableFile.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnInsertTimetableFile.contains(evt.getPoint())) {
                    btnInsertTimetableFile.setForeground(Color.WHITE);
                    btnInsertTimetableFile.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnInsertTimetableFile.setForeground(norm);
                    btnInsertTimetableFile.setBorder(new LineBorder(norm));
                }
                
            }
        });
    }
    public void loadTimetable(){
        DefaultTableModel model = (DefaultTableModel) tblTimetable.getModel();
        model.setRowCount(0);
        for (int i = 0; i < 7; i++) {
            model.addRow(new Object[] {"P"+i, 
                TimetableManager.getLesson(1, i), 
                TimetableManager.getLesson(2, i),
                TimetableManager.getLesson(3, i),
                TimetableManager.getLesson(4, i),
                TimetableManager.getLesson(5, i)});
        }
    }
    public void loadTargetTimetable(){//loads table for a target
        Student temp = TimetableManager.getCurrentUser();
        TimetableManager.setCurrentUser(targetStudent);
        DefaultTableModel model = (DefaultTableModel) tblTimetable.getModel();
        model.setRowCount(0);
        for (int i = 0; i < 7; i++) {
            model.addRow(new Object[] {"P"+i, 
                TimetableManager.getLesson(1, i), 
                TimetableManager.getLesson(2, i),
                TimetableManager.getLesson(3, i),
                TimetableManager.getLesson(4, i),
                TimetableManager.getLesson(5, i)});
        }
        TimetableManager.setCurrentUser(temp);
    }
    
    private void setTblTimetable(){
        tblTimetable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer(){//force render of background to white
            @Override
            public  java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column){
                
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
        
        tblTimetable.getTableHeader().setDefaultRenderer( // fixes rendering of heading and sets to white.
            new javax.swing.table.DefaultTableCellRenderer(){
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column){
                
                    java.awt.Component c = super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    
                    c.setBackground(Color.WHITE);
                    setHorizontalAlignment(CENTER);
                    c.setForeground(Color.BLACK);
                
                    return c;
                }
            }
        );
        tblTimetable.getTableHeader().setReorderingAllowed(false); // prevent user from moving table
        tblTimetable.getTableHeader().setResizingAllowed(false); // prevents user from resizing
        
        tblTimetable.getColumnModel().getColumn(0).setMinWidth(30);//resizing 1st column
        tblTimetable.getColumnModel().getColumn(0).setMaxWidth(30);
        
        tblTimetable.getTableHeader().setDefaultRenderer(//setting borders for headers
            new javax.swing.table.DefaultTableCellRenderer(){
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column){
                    javax.swing.JLabel c = (javax.swing.JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                    c.setHorizontalAlignment(CENTER);
                    c.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
                    return c;
                }
            }
        );
        
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
        btnInsertTimetableFile = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTimetable = new javax.swing.JTable();
        btnReturnToMainMenu = new javax.swing.JButton();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnInsertTimetableFile.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnInsertTimetableFile.setForeground(new java.awt.Color(47, 56, 120));
        btnInsertTimetableFile.setText("Insert Timetable File");
        btnInsertTimetableFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnInsertTimetableFile.setContentAreaFilled(false);
        btnInsertTimetableFile.setFocusPainted(false);
        jPanel1.add(btnInsertTimetableFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 450, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblTimetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "P", "Monday", "Tuesday", "Wednsday", "Thursday", "Friday"
            }
        ));
        tblTimetable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblTimetable.setGridColor(new java.awt.Color(0, 0, 0));
        tblTimetable.setRowHeight(50);
        tblTimetable.setRowMargin(1);
        tblTimetable.setRowSelectionAllowed(false);
        tblTimetable.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblTimetable.setShowGrid(true);
        jScrollPane1.setViewportView(tblTimetable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 380));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 610, 380));

        btnReturnToMainMenu.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnReturnToMainMenu.setForeground(new java.awt.Color(47, 56, 120));
        btnReturnToMainMenu.setText("MainMenu");
        btnReturnToMainMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnReturnToMainMenu.setContentAreaFilled(false);
        btnReturnToMainMenu.setFocusPainted(false);
        btnReturnToMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnToMainMenuActionPerformed(evt);
            }
        });
        jPanel1.add(btnReturnToMainMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loginBackground.png"))); // NOI18N
        lblBackground.setText("jLabel1");
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 490));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnToMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnToMainMenuActionPerformed
        mainMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnReturnToMainMenuActionPerformed

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
            java.util.logging.Logger.getLogger(TimetableScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimetableScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimetableScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimetableScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimetableScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsertTimetableFile;
    private javax.swing.JButton btnReturnToMainMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JTable tblTimetable;
    // End of variables declaration//GEN-END:variables
}
