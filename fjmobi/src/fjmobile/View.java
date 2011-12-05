package fjmobile;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;

import cmobile.common.Constant;
import cmobile.db.DBFactory;
import fjmobile.action.CopyContactAction;
import fjmobile.action.CutContactAction;
import fjmobile.action.DeleteAction;
import fjmobile.action.NewContactAction;
import fjmobile.action.PasteContactAction;
import fjmobile.editor.Duanxin;
import fjmobile.editor.FunctionEditorInput;
import fjmobile.editor.Hujiaozhuanyi;
import fjmobile.editor.Ihome;
import fjmobile.editor.Jiaofeilishi;
import fjmobile.editor.MoShangWang;
import fjmobile.editor.MobileNoEditor;
import fjmobile.editor.Shishihuafei;
import fjmobile.editor.ShouJiShangWang;
import fjmobile.editor.SystemConfig;
import fjmobile.editor.SystemInit;
import fjmobile.editor.Tingjifuji;
import fjmobile.editor.WLAN;
import fjmobile.editor.Xiugaimima;
import fjmobile.editor.Yuejiezhangdan;
import fjmobile.editor.Yueqianfei;
import fjmobile.model.ContactsManager;

public class View extends ViewPart implements ILogListener {
	public static final String ID = "rcpContactMngr.view";

	private TableViewer viewer;
	private CopyContactAction copyAction;
	private CutContactAction cutAction;
	private PasteContactAction pasteAction;
	private DeleteAction removeAction;
	private NewContactAction newContactAction;
	private IWorkbenchAction copyWAction;
	private IWorkbenchAction cutWAction;
	private IWorkbenchAction pasteWAction;
	private IWorkbenchAction deleteAction;

	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			// if (event.getProperty().equals(
			// PreferenceConstants.CONTACTS_DISPLAY_BY__FIRST_NAME))
			// viewer.refresh();
		}
	};

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	public View() {
	};

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new FunctionProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(ContactsManager.getManager());
		getSite().setSelectionProvider(viewer);
		ISelectionChangedListener listener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				try {
					getViewSite().getWorkbenchWindow().getActivePage()
							.closeAllEditors(true);
					Object c = null;
					try {
						c = ((StructuredSelection) event.getSelection())
								.getFirstElement();
					} catch (Exception e) {
						e.printStackTrace();
						c = null;
					}
					IEditorInput input = new FunctionEditorInput(c);
					if (c.equals(Constant.MOBILENO))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, MobileNoEditor.ID);
					else if (c.equals(Constant.SYSTEMCONFIG))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, SystemConfig.ID);
					else if (c.equals(Constant.TINGJIFUJI))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Tingjifuji.ID);
					else if (c.equals(Constant.YUEQIANFEI))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Yueqianfei.ID);
					else if (c.equals(Constant.SHISHIHUAFEI))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Shishihuafei.ID);
					else if (c.equals(Constant.YUEJIEZHANGDAN))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Yuejiezhangdan.ID);
					else if (c.equals(Constant.HUJIAOZHANYI))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Hujiaozhuanyi.ID);
					else if (c.equals(Constant.JIAOFEILISHI))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Jiaofeilishi.ID);
					else if (c.equals(Constant.XIUGAIMIMA))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Xiugaimima.ID);
					else if (c.equals(Constant.DUANXIN))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Duanxin.ID);
					else if (c.equals(Constant.SHOUJIWULIANWANG))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, ShouJiShangWang.ID);
					else if (c.equals(Constant.MOSHANGWANG))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, MoShangWang.ID);
					else if (c.equals(Constant.WLAN))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, WLAN.ID);
					else if (c.equals(Constant.IHOME))
						getViewSite().getWorkbenchWindow().getActivePage()
								.openEditor(input, Ihome.ID);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		};
		viewer.addSelectionChangedListener(listener);

		IEditorInput input = new FunctionEditorInput("号码管理");
		;
		String ID = MobileNoEditor.ID;
		if (!Constant.SYSTEM_REG) {
			input = new FunctionEditorInput("系统验证");
			ID = SystemInit.ID;
			viewer.removeSelectionChangedListener(listener);
		}
		try {
			getViewSite().getWorkbenchWindow().getActivePage().openEditor(
					input, ID);
			Constant.TIMEOUT = DBFactory.getProp(Constant.SYSTEM_TIMEOUT);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		createActions();
		hookGlobalActions();

		Activator.getDefault().getPluginPreferences()
				.addPropertyChangeListener(propertyChangeListener);
		initializeToolBar();

	}

	/**
	 * Create the actions for the view
	 */
	private void createActions() {

		copyAction = new CopyContactAction(this, "Copy");
		removeAction = new DeleteAction(getSite().getWorkbenchWindow(),
				"Delete");
		cutAction = new CutContactAction(copyAction, removeAction, "Cut");

		pasteAction = new PasteContactAction(this, "Paste");
		copyWAction = ActionFactory.COPY.create(getSite().getWorkbenchWindow());
		cutWAction = ActionFactory.CUT.create(getSite().getWorkbenchWindow());
		pasteWAction = ActionFactory.PASTE.create(getSite()
				.getWorkbenchWindow());
		deleteAction = ActionFactory.DELETE.create(getSite()
				.getWorkbenchWindow());

	}

	/**
	 * hook the global cut, copy etc
	 */
	protected void hookGlobalActions() {
		/*
		 * getViewSite().getActionBars().setGlobalActionHandler(
		 * ActionFactory.DELETE.getId(), removeAction);
		 * getViewSite().getActionBars().setGlobalActionHandler(
		 * ActionFactory.COPY.getId(),copyAction);
		 * getViewSite().getActionBars().setGlobalActionHandler(
		 * ActionFactory.CUT.getId(),cutAction);
		 * getViewSite().getActionBars().setGlobalActionHandler(
		 * ActionFactory.PASTE.getId(),pasteAction);
		 */

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private void initializeToolBar() {
		IToolBarManager toolBarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	@Override
	public void logging(IStatus status, String plugin) {

	}
}