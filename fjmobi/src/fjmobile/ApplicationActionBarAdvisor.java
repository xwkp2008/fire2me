package fjmobile;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import cmobile.common.OpFile;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	private IWorkbenchAction exitAction;
	private Action saveAction;
	private Action clearAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml
		// file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		/*
		 * exitAction = ActionFactory.QUIT.create(window); register(exitAction);
		 * aboutAction= ActionFactory.ABOUT.create(window);
		 * register(aboutAction); prefAction =
		 * ActionFactory.PREFERENCES.create(window); register(prefAction);
		 * saveAction = ActionFactory.SAVE.create(window); register(saveAction);
		 * cutAction = ActionFactory.CUT.create(window); register(cutAction);
		 * copyAction = ActionFactory.COPY.create(window); register(copyAction);
		 * pasteAction = ActionFactory.PASTE.create(window);
		 * register(pasteAction); deleteAction =
		 * ActionFactory.DELETE.create(window); register(deleteAction);
		 * selectAllAction = ActionFactory.SELECT_ALL.create(window);
		 * register(selectAllAction); newAction = new
		 * NewContactAction(window,"New Contact..."); register(newAction);
		 */
		ImageDescriptor quitActionImage = Activator
				.getImageDescriptor("icons/quit.png");
		ImageDescriptor wizardActionImage = Activator
				.getImageDescriptor("icons/bug-report.png");
		ImageDescriptor rapWebSiteActionImage = Activator
				.getImageDescriptor("icons/manage-configuration.png");
		try{
		exitAction = ActionFactory.QUIT.create(window);
		exitAction.setImageDescriptor(quitActionImage);
		exitAction.setToolTipText("退出自动机");
		register(exitAction);

		saveAction = new Action("save") {
			@Override
			public void run() {
				OpFile.saveExec();
				MessageDialog.openInformation(window.getShell(), "信息", "保存成功");
			}
		};
		saveAction.setId("saveAction");
		saveAction.setImageDescriptor(rapWebSiteActionImage);
		saveAction.setToolTipText("保存当前任务");
		register(saveAction);

		clearAction = new Action("clear") {
			@Override
			public void run() {
				OpFile.delExec();
				MessageDialog.openInformation(window.getShell(), "信息", "清除成功");
			}
		};
		clearAction.setId("clearAction");
		clearAction.setToolTipText("清除已存任务");
		clearAction.setImageDescriptor(wizardActionImage);
		register(clearAction);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	protected void fillMenuBar(IMenuManager menuBar) {
		/*
		 * MenuManager fileMenu = new MenuManager("&File",
		 * IWorkbenchActionConstants.M_FILE); menuBar.add(fileMenu);
		 * 
		 * MenuManager editMenu = new MenuManager("&Edit",
		 * IWorkbenchActionConstants.M_EDIT); menuBar.add(editMenu);
		 * 
		 * MenuManager helpMenu = new MenuManager("&Help",
		 * IWorkbenchActionConstants.M_HELP); menuBar.add(helpMenu);
		 * 
		 * fileMenu.add(newAction); fileMenu.add(saveAction); fileMenu.add(new
		 * Separator()); fileMenu.add(prefAction); fileMenu.add(new
		 * Separator()); fileMenu.add(exitAction); editMenu.add(cutAction);
		 * editMenu.add(copyAction); editMenu.add(pasteAction); editMenu.add(new
		 * Separator()); editMenu.add(deleteAction); editMenu.add(new
		 * Separator()); editMenu.add(selectAllAction);
		 * helpMenu.add(aboutAction);
		 */

	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));
		toolbar.add(saveAction);
		toolbar.add(clearAction);
		toolbar.add(exitAction);
	}

}
