/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fjmobi;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *
 * @author linq
 */
public class MainForm extends javax.swing.JFrame {

    private TreeModel funcTreeModel;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        //设置图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.createImage(getClass().getResource("/fjmobi/images/xbmc.png")); //设置您的窗体标题上的图标
        this.setIconImage(image);
        //配置树
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode n;
        root.add(n = new DefaultMutableTreeNode("功能"));
        n.add(new Item("余额欠费查询", "balanceArrears"));
        n.add(new Item("停机复机", "shutdownOpen"));
        n.add(new Item("缴费历史", "arrearsHistory"));
        root.add(n = new DefaultMutableTreeNode("系统"));
        n.add(new Item("验证码", "maskSymb"));
        n.add(new Item("参数", "pramas"));
        funcTreeModel = new DefaultTreeModel(root);
        initComponents();
    }

    private static class Item extends DefaultMutableTreeNode {

        private String label;
        private String clazz;
        private JComponent component;

        public Item(String label, String clazz) {
            this.label = label;
            this.clazz = clazz;
        }

        @Override
        public String toString() {
            return label;
        }

        public JComponent getComponent() {
            if (component == null) {
                try {
                    component = (JComponent) Class.forName(clazz).newInstance();
                } catch (Throwable ex) {
                    component = new JLabel(ex.toString());
                    ex.printStackTrace();
                }
            }
            return component;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fileStatusText = new javax.swing.JLabel();
        numberStatusText = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JEditorPane();

        jFileChooser1.setCurrentDirectory(new java.io.File("D:\\"));
            jFileChooser1.setDialogTitle("选择号码导入");

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("移动自动系统");
            setAlwaysOnTop(true);
            setBackground(java.awt.SystemColor.controlDkShadow);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            setFont(new java.awt.Font("宋体", 1, 14)); // NOI18N
            setForeground(java.awt.Color.red);
            setIconImages(null);
            setResizable(false);

            jToolBar1.setFloatable(false);
            jToolBar1.setRollover(true);
            jToolBar1.setBorderPainted(false);

            jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fjmobi/images/gnome-shutdown.png"))); // NOI18N
            jButton2.setToolTipText("启动所有任务");
            jButton2.setFocusable(false);
            jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
            jToolBar1.add(jButton2);

            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fjmobi/images/media-playlist-shuffle.png"))); // NOI18N
            jButton1.setFocusable(false);
            jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar1.add(jButton1);

            jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fjmobi/images/media-playback-pause.png"))); // NOI18N
            jButton3.setToolTipText("暂停所有任务");
            jButton3.setFocusable(false);
            jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar1.add(jButton3);

            jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fjmobi/images/gnome-app-install-star.png"))); // NOI18N
            jButton4.setToolTipText("帮助");
            jButton4.setFocusable(false);
            jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar1.add(jButton4);

            jSplitPane1.setDividerLocation(160);
            jSplitPane1.setPreferredSize(new java.awt.Dimension(300, 324));

            tree.setModel(funcTreeModel);
            tree.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            tree.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    treeMouseClicked(evt);
                }
            });
            tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
                public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                    treeValueChanged(evt);
                }
            });
            jScrollPane1.setViewportView(tree);

            jSplitPane1.setLeftComponent(jScrollPane1);

            jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

            jTextField1.setText("文件地址");
            jTextField1.setEnabled(false);
            jTextField1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField1ActionPerformed(evt);
                }
            });

            jButton5.setText("选择文件");
            jButton5.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton5ActionPerformed(evt);
                }
            });

            jLabel1.setText("文件读取状态：");

            jLabel2.setText("号码状态：");

            fileStatusText.setText(".....");

            numberStatusText.setText("......");

            jScrollPane2.setViewportView(logArea);
            logArea.getAccessibleContext().setAccessibleName("logArea");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(fileStatusText))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(numberStatusText))
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(fileStatusText))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(numberStatusText))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addContainerGap())
            );

            fileStatusText.getAccessibleContext().setAccessibleName("fileStatus");
            numberStatusText.getAccessibleContext().setAccessibleName("numberStatus");

            jTabbedPane1.addTab("号码管理", jPanel1);

            jSplitPane1.setRightComponent(jTabbedPane1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jFileChooser1.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String fileName = f.getName().toUpperCase();
                if (fileName.endsWith("CSV") || fileName.endsWith("TXT")) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "号码文件";
            }
        });
        int result = jFileChooser1.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String fname = jFileChooser1.getName(jFileChooser1.getSelectedFile());
            jTextField1.setText(jFileChooser1.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void treeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeMouseClicked
    }//GEN-LAST:event_treeMouseClicked

    private void treeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeValueChanged
        //JOptionPane.showMessageDialog(this, evt.getNewLeadSelectionPath().getLastPathComponent()); //提示框
        Object o = (Object) evt.getNewLeadSelectionPath().getLastPathComponent();
        if(o instanceof Item){
            Item leaf=(Item)o;
            JOptionPane.showMessageDialog(this,leaf.clazz); //提示框
        }
    }//GEN-LAST:event_treeValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws NoSuchMethodException, InstantiationException {
        //初始化树

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileStatusText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JEditorPane logArea;
    private javax.swing.JLabel numberStatusText;
    private javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables
}
