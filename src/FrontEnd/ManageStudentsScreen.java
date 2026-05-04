/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import BackEnd.Teacher;
import BackEnd.TimetableManager;
import BackEnd.UserManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ryano
 */
public class ManageStudentsScreen extends javax.swing.JFrame {

    /**
     * Creates new form ManageStudentsScreen
     */
    private MainMenuScreen mainMenu;
    public ManageStudentsScreen(MainMenuScreen mm) {
        initComponents();
        btnSetup();
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, true  // ← last one
        };
        this.mainMenu = mm;
        setTblStudents();
        loadTblStudents();
        setLocationRelativeTo(null);
    }
    public ManageStudentsScreen(){
        initComponents();
        btnSetup();
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, true  // ← last one
        };
        setTblStudents();
        loadTblStudents();
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
        btnAddStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                btnAddStudent.setForeground(Color.WHITE);
                btnAddStudent.setBorder(new LineBorder(Color.WHITE));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                btnAddStudent.setForeground(norm);
                btnAddStudent.setBorder(new LineBorder(norm));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAddStudent.setForeground(norm);
                btnAddStudent.setBorder(new LineBorder(norm));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if (btnAddStudent.contains(evt.getPoint())) {
                    btnAddStudent.setForeground(Color.WHITE);
                    btnAddStudent.setBorder(new LineBorder(Color.WHITE));
                }else{
                    btnAddStudent.setForeground(norm);
                    btnAddStudent.setBorder(new LineBorder(norm));
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
    private void loadTblStudents(){
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);
        for (int i = 0; i < UserManager.countUsers(TimetableManager.getSchoolCode()); i++) {
            if (!(UserManager.userArr[i] instanceof Teacher)) {
                model.addRow(new Object[] {
                    UserManager.userArr[i].getName(),
                    UserManager.userArr[i].getSurname(),
                    UserManager.userArr[i].getEmail(),
                    UserManager.userArr[i].getPassword(),
                    UserManager.userArr[i].getDOB(),
                    UserManager.userArr[i].getGrade(),
                    "edit"
                });
            }
            
        }
    }
    private void loadTblStudents(String inEmail){//Method for searching
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);
        for (int i = 0; i < UserManager.countUsers(TimetableManager.getSchoolCode()); i++) {
            if (!(UserManager.userArr[i] instanceof Teacher)&&
                    (UserManager.userArr[i].getEmail().contains(inEmail)||
                        UserManager.userArr[i].getName().contains(inEmail)||
                        UserManager.userArr[i].getSurname().contains(inEmail))) {
                model.addRow(new Object[] {
                    UserManager.userArr[i].getName(),
                    UserManager.userArr[i].getSurname(),
                    UserManager.userArr[i].getEmail(),
                    UserManager.userArr[i].getPassword(),
                    UserManager.userArr[i].getDOB(),
                    UserManager.userArr[i].getGrade(),
                    "Delete"
                });
            }
            
        }
    }
    private void setTblStudents(){
        tblStudents.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer(){//force render of background to white
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
        
        tblStudents.getTableHeader().setDefaultRenderer( // fixes rendering of heading and sets to white.
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
        tblStudents.getTableHeader().setReorderingAllowed(false); // prevent user from moving table
        tblStudents.getTableHeader().setResizingAllowed(false); // prevents user from resizing
        
        
        tblStudents.getTableHeader().setDefaultRenderer(//setting borders for headers
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
        tblStudents.getColumnModel().getColumn(6).setCellRenderer(new EditRenderer());
        tblStudents.getColumnModel().getColumn(6).setCellEditor(new EditEditor());
    }
    
    private class EditRenderer extends javax.swing.table.DefaultTableCellRenderer{
        private final javax.swing.JButton btn = new javax.swing.JButton("Edit");
        
        EditRenderer(){
            btn.setBackground(new Color(47,56,120));
            btn.setForeground(Color.WHITE);
            btn.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 13));
            btn.setBorderPainted(false);
            btn.setOpaque(true);
        }
        
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            return btn;
        }
    }
    
    private class EditEditor extends javax.swing.DefaultCellEditor{
        private final javax.swing.JButton btn;
        
        EditEditor(){
            super(new javax.swing.JCheckBox());
            btn = new javax.swing.JButton("Edit");
            btn.setBackground(new Color(47,56,120));
            btn.setForeground(Color.WHITE);
            btn.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 13));
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            
            btn.addActionListener(e -> {
                int row = tblStudents.getSelectedRow();
                if (row >=0) {
                    String name = (String) tblStudents.getValueAt(row, 0);
                    String surname = (String) tblStudents.getValueAt(row, 1);
                    String email = (String) tblStudents.getValueAt(row, 2);
                    String password = (String) tblStudents.getValueAt(row, 3);
                    String DOB = (String) tblStudents.getValueAt(row, 4);
                    String grade = String.valueOf(tblStudents.getValueAt(row, 5));
                    
                    //TODO
                    txfName.setText(name);
                    txfSurname.setText(surname);
                    txfEmail.setText(email);
                    txfPassword.setText(password);
                    txfDOB.setText(DOB);
                    txfGrade.setText(grade);
                    System.out.println("Edit Clicked: "+name+" "+surname);
                }
                fireEditingStopped();
            });
        }
        
        @Override
        public java.awt.Component getTableCellEditorComponent(
            javax.swing.JTable table, Object value, boolean isSelected, int row, int column){
            return btn;
        }
        
        @Override public Object getCellEditorValue(){ 
            return "Edit";
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
        jLabel1 = new javax.swing.JLabel();
        txfSearchStudent = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txfName = new javax.swing.JTextField();
        txfSurname = new javax.swing.JTextField();
        txfEmail = new javax.swing.JTextField();
        txfDOB = new javax.swing.JTextField();
        txfPassword = new javax.swing.JTextField();
        txfGrade = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        lblSurname = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblDOB = new javax.swing.JLabel();
        lblGrade = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnAddStudent = new javax.swing.JButton();
        btnMainMenu = new javax.swing.JButton();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(47, 56, 120));
        jLabel1.setText("Search Student:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, -1, -1));

        txfSearchStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfSearchStudentActionPerformed(evt);
            }
        });
        txfSearchStudent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfSearchStudentKeyReleased(evt);
            }
        });
        jPanel1.add(txfSearchStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 180, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Surname", "Email", "Password", "Date Of Birth", "Grade", "Edit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudents.setGridColor(new java.awt.Color(0, 0, 0));
        tblStudents.setRowHeight(50);
        tblStudents.setRowMargin(1);
        tblStudents.setRowSelectionAllowed(false);
        tblStudents.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblStudents.setShowGrid(true);
        jScrollPane1.setViewportView(tblStudents);
        if (tblStudents.getColumnModel().getColumnCount() > 0) {
            tblStudents.getColumnModel().getColumn(0).setResizable(false);
            tblStudents.getColumnModel().getColumn(1).setResizable(false);
            tblStudents.getColumnModel().getColumn(2).setResizable(false);
            tblStudents.getColumnModel().getColumn(3).setResizable(false);
            tblStudents.getColumnModel().getColumn(4).setResizable(false);
            tblStudents.getColumnModel().getColumn(5).setResizable(false);
            tblStudents.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 350));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 920, 350));

        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(47, 56, 120));
        jLabel2.setText("Edit Student:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, -1, -1));
        jPanel1.add(txfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 120, -1));
        jPanel1.add(txfSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 480, 120, -1));

        txfEmail.setEditable(false);
        jPanel1.add(txfEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 210, -1));
        jPanel1.add(txfDOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 480, 90, -1));
        jPanel1.add(txfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 450, 130, -1));
        jPanel1.add(txfGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 450, 50, -1));

        lblName.setText("Name:");
        jPanel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, -1, -1));

        lblSurname.setText("Surname:");
        jPanel1.add(lblSurname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 480, -1, -1));

        lblPassword.setText("Password:");
        jPanel1.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, -1, -1));

        lblDOB.setText("Date of Birth:");
        jPanel1.add(lblDOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 480, -1, -1));

        lblGrade.setText("Grade:");
        jPanel1.add(lblGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 450, -1, -1));

        btnSave.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(47, 56, 120));
        btnSave.setText("Save");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnSave.setContentAreaFilled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, 70, -1));

        btnAddStudent.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 18)); // NOI18N
        btnAddStudent.setForeground(new java.awt.Color(47, 56, 120));
        btnAddStudent.setText("Add Student");
        btnAddStudent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 56, 120)));
        btnAddStudent.setContentAreaFilled(false);
        btnAddStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStudentActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 90, -1));

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
        jPanel1.add(btnMainMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 90, -1));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loginBackground.png"))); // NOI18N
        lblBackground.setText("jLabel1");
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1024, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfSearchStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfSearchStudentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfSearchStudentActionPerformed

    private void txfSearchStudentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfSearchStudentKeyReleased
        if (txfSearchStudent.getText().isEmpty()) {
            loadTblStudents();
        }else{
            loadTblStudents(txfSearchStudent.getText());
        }
    }//GEN-LAST:event_txfSearchStudentKeyReleased

    private void btnAddStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStudentActionPerformed
        AddUserScreen as  = new AddUserScreen(mainMenu);
        as.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAddStudentActionPerformed

    private void btnMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainMenuActionPerformed
        mainMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMainMenuActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
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
            java.util.logging.Logger.getLogger(ManageStudentsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageStudentsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageStudentsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageStudentsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageStudentsScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddStudent;
    private javax.swing.JButton btnMainMenu;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblGrade;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txfDOB;
    private javax.swing.JTextField txfEmail;
    private javax.swing.JTextField txfGrade;
    private javax.swing.JTextField txfName;
    private javax.swing.JTextField txfPassword;
    private javax.swing.JTextField txfSearchStudent;
    private javax.swing.JTextField txfSurname;
    // End of variables declaration//GEN-END:variables
}
