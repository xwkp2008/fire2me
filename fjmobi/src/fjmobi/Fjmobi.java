/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fjmobi;

import ch.randelshofer.quaqua.QuaquaManager;
import ch.randelshofer.quaqua.util.Methods;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author linq
 */
public class Fjmobi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InstantiationException, NoSuchMethodException {
        try {
            if (System.getProperty("os.name").toLowerCase().indexOf("mac") == -1) {
                System.setProperty("Quaqua.Debug.crossPlatform", "true");
                System.setProperty("swing.aatext", "true");
            }
            Methods.invokeStatic(JFrame.class, "setDefaultLookAndFeelDecorated", Boolean.TYPE, Boolean.TRUE);
            Methods.invokeStatic(JDialog.class, "setDefaultLookAndFeelDecorated", Boolean.TYPE, Boolean.TRUE);
            System.setProperty("Quaqua.TabbedPane.design", "jaguar");
            String lafClassName = QuaquaManager.getLookAndFeelClassName();
            UIManager.setLookAndFeel(lafClassName);
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //IManager.setLookAndFeel("ch.randelshofer.quaqua.snow_leopard.Quaqua16SnowLeopardLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Fjmobi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Fjmobi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Fjmobi.class.getName()).log(Level.SEVERE, null, ex);
        }
        MainForm mainForm = new MainForm();
        mainForm.setVisible(true);
    }
}
