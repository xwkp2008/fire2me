/**
 * 
 */
package fjmobile.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.TreeItem;

/**
 * @author linq
 * 
 */
public class IhomeItem {
	private static Map<IhomeMobile, List<IhomeMobile>> impItems = new HashMap<IhomeMobile, List<IhomeMobile>>();

	public static Map<IhomeMobile, List<IhomeMobile>> getImpItems() {
		return impItems;
	}

	/**
	 * 从列表中读取
	 * 
	 * @param main
	 * @param sub
	 * @param limit
	 */
	public static void setImpItems(List<IhomeMobile> main,
			List<IhomeMobile> sub, int limit) {
		int indx = 0;
		for (IhomeMobile item : main) {
			List<IhomeMobile> mainItems = new ArrayList<IhomeMobile>();
			for (int i = indx; i < sub.size() && indx <= limit; i++) {
				mainItems.add(sub.get(indx));
				indx++;
			}
			impItems.put(item, mainItems);
		}
	}

	/**
	 * 从数结构中读取数据
	 * 
	 * @param tree
	 * @throws Exception 
	 */
	public static void setImpItems(TreeItem tree, int limit) throws Exception {
		TreeItem[] treeItems = tree.getItems();
		for (TreeItem treeItem : treeItems) {
			List<IhomeMobile> mainItems = new ArrayList<IhomeMobile>();
			TreeItem[] subTreeItem = treeItem.getItems();
			for (TreeItem subItem : subTreeItem) {
				if (subTreeItem.length > limit)
					throw new Exception("vpn数量" + limit + "错误！");
				IhomeMobile mobile = new IhomeMobile();
				mobile.setMobileno(subItem.getText());
				mobile.setMobileno(subItem.getData("password").toString());
				mainItems.add(mobile);
			}
			IhomeMobile main = new IhomeMobile();
			main.setMobileno(treeItem.getText());
			main.setPassword(treeItem.getData("password").toString());
			impItems.put(main, mainItems);
		}
	}
}
