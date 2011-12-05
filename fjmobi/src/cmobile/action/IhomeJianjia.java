package cmobile.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

import cmobile.common.Constant;
import cmobile.common.ParseFactory;
import cmobile.http.Callback;
import cmobile.http.HttpCore;

public class IhomeJianjia extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		String mobileno = rs.get("mobileno").toString();
		Map<String, String> paramsMap=new HashMap<String, String>();
		paramsMap.put("action", "ADD_T_FAMILY_ACTION");
		paramsMap.put("MOBILE", mobileno);

		paramsMap.put("ADDRESS", "fuqing");
		paramsMap.put("NICKNAME", mobileno.substring(3));
		paramsMap.put("ZIP", "362001");
		paramsMap.put("PHONE", mobileno);
		paramsMap.put("access", "1");
		
		paramsMap.put("OPTION_DEAL_ID", rs.get("opid").toString());
		rs.put("INURE_TYPE", rs.get("type").toString());
		
		HttpCore
				.getInstance()
				.doPost(
						Constant.IHOME_URL
								+ "/member/jianjiaB.html",
						rs, null, new Callback() {
							@Override
							public void excute(HttpClient client, Object o)
									throws HttpException, IOException {
								String html = new String((byte[]) o, "utf-8");
								String rs = ParseFactory.parse("ihomevpnadd",
										html);
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
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(Constant.IHOME_URL + "/room/room.html",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "utf-8");
							String rs = ParseFactory
									.parse("ihomejianjia", html);
							key.append(rs);
						} catch (UnsupportedEncodingException e) {
						}
					}
				});
		if (Constant.SET_SUCC.equals(key.toString())) {
			return true;
		}
		return false;
	}

}
