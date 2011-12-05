package fjmobile;

import java.util.ArrayList;
import java.util.List;

import key.Crypt;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cmobile.common.Constant;
import cmobile.db.DBFactory;

public class FunctionProvider implements IStructuredContentProvider {

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		// 验证1、注册码是否有？有是否正确。2、数据库是否可以访问
		if (DBFactory.getConn() != null) {
			System.out.println(DBFactory.getProp("system_regcode") + "="
					+ Crypt.getMD5(Crypt.getMac()));
			String hasreg = DBFactory.getProp("system_regcode");
			if (Crypt.getMD5(Crypt.getMac() + 0).equals(hasreg)) {
				Constant.IS_SIMPLE = false;
				Constant.SYSTEM_REG = true;
			} else if (Crypt.getMD5(Crypt.getMac() + 1).equals(hasreg)) {
				Constant.IS_SIMPLE = true;
				Constant.SYSTEM_REG = true;
			}
		}

		List list = new ArrayList();
		list.add(Constant.MOBILENO);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.SYSTEMCONFIG);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.TINGJIFUJI);
		list.add(Constant.YUEQIANFEI);
		list.add(Constant.SHISHIHUAFEI);
		list.add(Constant.YUEJIEZHANGDAN);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.XIUGAIMIMA);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.HUJIAOZHANYI);
		list.add(Constant.JIAOFEILISHI);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.DUANXIN);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.SHOUJIWULIANWANG);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.MOSHANGWANG);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.WLAN);
		if (!Constant.IS_SIMPLE)
			list.add(Constant.IHOME);
		return list.toArray();
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}
