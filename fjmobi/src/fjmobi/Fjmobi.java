/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fjmobi;

import java.awt.Font;
import java.util.Enumeration;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.AutumnSkin;

/**
 *
 * @author linq
 */
public class Fjmobi {

    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InstantiationException, NoSuchMethodException {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    SubstanceLookAndFeel.setSkin(new AutumnSkin());

                    InitGlobalFont(new Font("Dialog", Font.PLAIN, 14));
                    MainForm mainForm = new MainForm();
                    mainForm.setVisible(true);
                } catch (Exception e) {
                }
            }
        });
    }
}
