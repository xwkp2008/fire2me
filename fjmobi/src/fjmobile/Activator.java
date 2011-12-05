package fjmobile;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class Activator extends AbstractUIPlugin {

	//The shared instance.
	private static Activator plugin;
	
	private static final String LOG_PROPERTIES_FILE = "logger.properties";

	//private PluginLogManager logManager;
	
	/**
	 * The constructor.
	 */
	public Activator() {
		plugin = this;
	}
	
	/*public static PluginLogManager getLogManager() {
		return getDefault().logManager;
	}
*/
	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		configure();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}
	
	/**
	 * Returns the shared instance.
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private void configure() {

		/*try {
			File file=new File(LOG_PROPERTIES_FILE);
			System.out.println(file.getAbsolutePath());
			//URL url = getBundle().getEntry("/" + LOG_PROPERTIES_FILE);
			InputStream propertiesInputStream = new FileInputStream(file);
			if (propertiesInputStream != null) {
				Properties props = new Properties();
				props.load(propertiesInputStream);
				propertiesInputStream.close();
				System.out.println(Logger.getRootLogger().getName());
				this.logManager = new PluginLogManager(this, props);
			}
		} catch (Exception e) {
			String message = "Error while initializing log properties."
					+ e.getMessage();
			IStatus status = new Status(IStatus.ERROR, getDefault().getBundle()
					.getSymbolicName(), IStatus.ERROR, message, e);
			getLog().log(status);
			throw new RuntimeException(
					"Error while initializing log properties.", e);
		}*/
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("fjmobile", path);
	}
}
