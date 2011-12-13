package fjmobile.model;

import cmobile.common.IContact;

public class IhomeMobile implements IContact {
	private String ID;
	private String mobileno;
	private String password;
	private String vpns;

	public IhomeMobile() {
	}

	public IhomeMobile(String mobileno, String password) {
		super();
		this.mobileno = mobileno;
		this.password = password;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	public void setVpns(String vpns) {
		this.vpns = vpns;
	}

	public String getVpns() {
		return vpns;
	}

	@Override
	public String getDuanxin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHujiaozhuanyi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIhome() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJiaofeilishi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMoshangwang() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShishihuafei() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShoujishangwang() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTingjifuji() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getYuejiezhangda() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getYueqianfei() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDuanxin(String duanxin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHujiaozhuanyi(String hujiaozhuanyi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIhome(String ihome) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJiaofeilishi(String jiaofeilishi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMoshangwang(String moshangwang) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShishihuafei(String shishihuafei) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShoujishangwang(String shoujishangwang) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTingjifuji(String tingjifuji) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWlan(String wlan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setYuejiezhangda(String yuejiezhangda) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setYueqianfei(String yueqianfei) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
