package fjmobile.model;

import org.eclipse.core.runtime.Preferences;

import fjmobile.Activator;
import fjmobile.preferences.PreferenceConstants;

public class Contact implements IContact {
	private String ID;
	private String mobileno;
	private String password;

	private String xiugaimima;
	private String tingjifuji;

	private String shishihuafei;
	private String yuejiezhangda;
	private String yueqianfei;

	private String hujiaozhuanyi;
	private String jiaofeilishi;
	private String duanxin;
	private String shoujishangwang;
	private String moshangwang;
	private String wlan;

	private String ihome;

	public Contact() {

	}

	public Contact(String mobileno) {
		this.mobileno = mobileno;
	}

	public Contact(String mobileno, String password) {
		this.mobileno = mobileno;
		this.password = password;
	}

	public String toString() {
		String name;
		Preferences prefs = Activator.getDefault().getPluginPreferences();
		String fnameFirst = prefs
				.getString(PreferenceConstants.CONTACTS_DISPLAY_BY__FIRST_NAME);
		if (fnameFirst.equals("0"))
			name = mobileno + "," + password;
		else
			name = password + "," + mobileno;
		return name;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTingjifuji() {
		return tingjifuji;
	}

	public void setTingjifuji(String tingjifuji) {
		this.tingjifuji = tingjifuji;
	}

	public String getShishihuafei() {
		return shishihuafei;
	}

	public void setShishihuafei(String shishihuafei) {
		this.shishihuafei = shishihuafei;
	}

	public String getYuejiezhangda() {
		return yuejiezhangda;
	}

	public void setYuejiezhangda(String yuejiezhangda) {
		this.yuejiezhangda = yuejiezhangda;
	}

	public String getYueqianfei() {
		return yueqianfei;
	}

	public void setYueqianfei(String yueqianfei) {
		this.yueqianfei = yueqianfei;
	}

	public String getHujiaozhuanyi() {
		return hujiaozhuanyi;
	}

	public void setHujiaozhuanyi(String hujiaozhuanyi) {
		this.hujiaozhuanyi = hujiaozhuanyi;
	}

	public String getJiaofeilishi() {
		return jiaofeilishi;
	}

	public void setJiaofeilishi(String jiaofeilishi) {
		this.jiaofeilishi = jiaofeilishi;
	}

	public String getDuanxin() {
		return duanxin;
	}

	public void setDuanxin(String duanxin) {
		this.duanxin = duanxin;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getID() {
		return ID;
	}

	public void setXiugaimima(String xiugaimima) {
		this.xiugaimima = xiugaimima;
	}

	public String getXiugaimima() {
		return xiugaimima;
	}

	public String getShoujishangwang() {
		return shoujishangwang;
	}

	public void setShoujishangwang(String shoujishangwang) {
		this.shoujishangwang = shoujishangwang;
	}

	public String getMoshangwang() {
		return moshangwang;
	}

	public void setMoshangwang(String moshangwang) {
		this.moshangwang = moshangwang;
	}

	public String getWlan() {
		return wlan;
	}

	public void setWlan(String wlan) {
		this.wlan = wlan;
	}

	@Override
	public String getIhome() {
		return ihome;
	}

	@Override
	public void setIhome(String ihome) {
		this.ihome = ihome;
	}

}
