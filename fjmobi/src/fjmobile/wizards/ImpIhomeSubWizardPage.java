package fjmobile.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import cmobile.common.OpFile;
import cmobile.common.IContact;

public class ImpIhomeSubWizardPage extends WizardPage {
	private Tree tree;
	
	public Tree getTree() {
		return tree;
	}

	public ImpIhomeSubWizardPage() {
		super("subwizard");
		setTitle("显示导入号码");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		container.setLayout(new GridLayout(3, false));

		tree = new Tree(container, SWT.BORDER);

		GridData gd_tree = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_tree.heightHint = 268;
		tree.setLayoutData(gd_tree);

		String mainPath = ((ImpIhomeMainWizardPage) this.getPreviousPage())
				.getMainFile().getText();
		String subPath = ((ImpIhomeMainWizardPage) this.getPreviousPage())
				.getSubFile().getText();

		// 读取文件
		IContact[] main = new IContact[] {};
		IContact[] sub = new IContact[] {};
		try {
			main = OpFile.imp(mainPath);
			sub = OpFile.imp(subPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int indx = 0;
		int limit = 8;
		for (int i = 0; i < main.length; i++) {
			IContact mobile = main[i];
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText(mobile.getMobileno());
			item.setData("password", mobile.getPassword());
			for (int j = indx; (j < sub.length) && (j - indx < limit); j++) {
				IContact submobile = sub[j];
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				subItem.setText(submobile.getMobileno());
				subItem.setData("password", mobile.getPassword());
			}
			indx += limit;
		}

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

		final DragSource source = new DragSource(tree, operations);
		source.setTransfer(types);
		final TreeItem[] dragSourceItem = new TreeItem[1];
		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				if (selection.length > 0 && selection[0].getItemCount() == 0) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			};

			public void dragSetData(DragSourceEvent event) {
				event.data = dragSourceItem[0].getText();
			}

			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE)
					dragSourceItem[0].dispose();
				dragSourceItem[0] = null;
			}
		});

		DropTarget target = new DropTarget(tree, operations);
		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				if (event.item != null) {
					TreeItem item = (TreeItem) event.item;
					Point pt = event.display.map(null, tree, event.x, event.y);
					Rectangle bounds = item.getBounds();
					if (pt.y < bounds.y + bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_BEFORE;
					} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_AFTER;
					} else {
						event.feedback |= DND.FEEDBACK_SELECT;
					}
				}
			}

			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				String text = (String) event.data;
				if (event.item == null) {
					TreeItem item = new TreeItem(tree, SWT.NONE);
					item.setText(text);
				} else {
					TreeItem item = (TreeItem) event.item;
					Point pt = event.display.map(null, tree, event.x, event.y);
					Rectangle bounds = item.getBounds();
					TreeItem parent = item.getParentItem();
					if (parent != null) {
						TreeItem[] items = parent.getItems();
						int index = 0;
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
						if (pt.y < bounds.y + bounds.height / 3) {
							TreeItem newItem = new TreeItem(parent, SWT.NONE,
									index);
							newItem.setText(text);
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							TreeItem newItem = new TreeItem(parent, SWT.NONE,
									index + 1);
							newItem.setText(text);
						} else {
							TreeItem newItem = new TreeItem(item, SWT.NONE);
							newItem.setText(text);
						}

					} else {
						TreeItem[] items = tree.getItems();
						int index = 0;
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
						if (pt.y < bounds.y + bounds.height / 3) {
							TreeItem newItem = new TreeItem(tree, SWT.NONE,
									index);
							newItem.setText(text);
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							TreeItem newItem = new TreeItem(tree, SWT.NONE,
									index + 1);
							newItem.setText(text);
						} else {
							TreeItem newItem = new TreeItem(item, SWT.NONE);
							newItem.setText(text);
						}
					}

				}
			}
		});

	}

	public void createContact() {
	}
}
