/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fjmobi;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.AutumnSkin;

/**
 *
 * @author linq
 */
public class Fjmobi {

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
                    MainForm mainForm = new MainForm();
                    mainForm.setVisible(true);
                } catch (Exception e) {
                }
            }
        });
    }
}
