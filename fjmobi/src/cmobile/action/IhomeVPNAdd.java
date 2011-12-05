package cmobile.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

import cmobile.common.Constant;
import cmobile.common.ParseFactory;
import cmobile.http.Callback;
import cmobile.http.HttpCore;

public class IhomeVPNAdd extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		// 获取用户信息
		HttpCore.getInstance().doGet(
				Constant.IHOME_URL + "/json?action=GET_FAMILY_BY_SESSION",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "GBK");
							String rs = ParseFactory.parse("ihomeuserinfo",
									html);
							key.append(rs);
						} catch (UnsupportedEncodingException e) {
						}
					}
				});
		if (Constant.SET_FAIL.equals(key.toString())) {
			throw new HttpException("获取用户信息失败！");
		}

		String mobileno = rs.get("mobileno").toString();
		rs.put("action", "ADD_T_FAMILY_VPN_USER_ACTION");
		String family_id = key.toString();
		rs.put("FAMILY_ID", family_id);
		key.delete(0, key.length());

		rs.put("RELATION", "71,哥哥");
		//rs.put("MOBILE", mobileno);
		rs.put("INURE_TYPE", 3);

		HttpCore.getInstance().doPost(Constant.IHOME_URL + "/json", rs, null,
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o)
							throws HttpException, IOException {
						String html = new String((byte[]) o, "GBK");
						String rs = ParseFactory.parse("ihomevpnadd", html);
						key.append(rs);
					}
				});
		if (Constant.SET_FAIL.equals(key.toString())) {
			throw new HttpException(Constant.SET_FAIL);
		}
		return null;
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		return false;
	}

}
