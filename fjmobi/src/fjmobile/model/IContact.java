package fjmobile.model;

import org.eclipse.core.runtime.IAdaptable;

public interface IContact extends IAdaptable {
	public void setID(String iD);

	public String getID();

	public String getMobileno();

	public void setMobileno(String mobileno);

	public String getPassword();

	public void setPassword(String password);

	public String getTingjifuji();

	public void setTingjifuji(String tingjifuji);

	public String getShishihuafei();

	public void setShishihuafei(String shishihuafei);

	public String getYuejiezhangda();

	public void setYuejiezhangda(String yuejiezhangda);

	public String getYueqianfei();

	public void setYueqianfei(String yueqianfei);

	public String getHujiaozhuanyi();

	public void setHujiaozhuanyi(String hujiaozhuanyi);

	public String getJiaofeilishi();

	public void setJiaofeilishi(String jiaofeilishi);

	public String getDuanxin();

	public void setDuanxin(String duanxin);

	public String getShoujishangwang();

	public void setShoujishangwang(String shoujishangwang);

	public String getMoshangwang();

	public void setMoshangwang(String moshangwang);

	public String getWlan();

	public void setWlan(String wlan);

	public String getIhome();

	public void setIhome(String ihome);

	static IContact[] NONE = new IContact[] {};
}
