package fjmobile.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorSite;

import cmobile.common.Function;
import cmobile.common.OpFile;
import cmobile.common.StringUtil;
import cmobile.db.DBFactory;

public class ImpIhomeWizard extends Wizard {
	private ImpIhomeMainWizardPage mainpage;
	private ImpIhomeSubWizardPage subpage;
	private ImpIhomeChooseWizardPage choosepage;
	private IEditorSite editor;

	public ImpIhomeWizard(IEditorSite editor) {
		super();
		this.editor=editor;
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		// super.createPageControls(pageContainer);
	}

	public void addPages() {
		mainpage = new ImpIhomeMainWizardPage();
		addPage(mainpage);
		subpage = new ImpIhomeSubWizardPage();
		addPage(subpage);
		choosepage = new ImpIhomeChooseWizardPage();
		addPage(choosepage);
	}

	public boolean canFinish() {
		if (this.getContainer().getCurrentPage() != this.choosepage) {
			return false;
		}
		return super.canFinish();
	}

	public boolean performFinish() {
		this.getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				Tree tree = subpage.getTree();
				TreeItem[] mainItem = tree.getItems();
				// 循环注册
				for (TreeItem item : mainItem) {
					String mobileno = item.getText();
					String password = item.getData("password").toString();

					Map<String, String> map = new HashMap<String, String>();
					map.put("mobileno", mobileno);
					map.put("password", password);
					map.put("opid", choosepage.getOPTION_DEAL_ID());
					map.put("type", choosepage.getINURE_TYPE());
					
					//标示IHOME
					map.put("ihome","true");
					
					List<String> items = new ArrayList<String>();
					List<String> subitems = new ArrayList<String>();
					items.add(mobileno);
					DBFactory.doInsertMobileno(mobileno, password, "ihome");

					for (TreeItem subitem : item.getItems()) {
						String submobileno = subitem.getText();
						// String subpassword = subitem.getData("password")
						// .toString();
						// VPN成员不需要密码
						subitems.add(submobileno);
					}
					String vpns = StringUtil.combineList(subitems, ",");
					DBFactory.doUpdateMobiile("vpns", mobileno, vpns, "ihome");

					OpFile.corruct(items, map, "ihomeimp", editor, new Function() {
						@Override
						public void exec() {

						}
					});

				}
			}
		});
		return true;
	}

}
