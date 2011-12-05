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

public class Hujiaozhuanyi extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		boolean open = true;

		if (rs != null) {
			String operate = rs.get("action").toString();
			if ("0".equals(operate)) {
				open = false;
			}
		}
		String operateStr = "/service/operate/usual/setPhoneCall.do?status=kt&number=o_service.o_hjzy&type=4&set=1&name=关机和不在服务区转移&tab=2";
		if (!open)
			operateStr = "/service/operate/usual/setPhoneCall.do?status=qx&number=o_service.o_hjzy&type=4&set=0&name=关机和不在服务区转移";

		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doPost(Constant.URL + operateStr, rs, null,
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "GBK");
							String rs = ParseFactory.parse("hujiaozhuanyi",
									html);
							key.append(rs);
						} catch (UnsupportedEncodingException e) {
						}
					}
				});
		return open ? "开通完毕" : "已关闭";
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		final StringBuffer key2 = new StringBuffer();
		HttpCore
				.getInstance()
				.doGet(
						Constant.URL
								+ "/service/operate/usual/queryPhoneCall.do?number=o_service.o_hjzy&tab=2",
						new Callback() {
							@Override
							public void excute(HttpClient client, Object o) {
								try {
									String html = new String((byte[]) o, "GBK");
									String rs = ParseFactory.parse(
											"ishujiaozhuanyi", html);
									key.append(rs);
									String rs2 = ParseFactory.parse(
											"ishujiaozhuanyi2", html);
									key2.append(rs2);
								} catch (UnsupportedEncodingException e) {
									// key.append(Constant.get(Constant.ERROR));
								}
							}
						});
		if (!Constant.SET_SUCC.equals(key.toString())) {
			key.delete(0, key.length());
			HttpCore
					.getInstance()
					.doGet(
							Constant.URL
									+ "/service/operate/usual/modifyPhoneCall.do?action=1&number=o_service.o_hjzykg&tab=2",
							new Callback() {
								@Override
								public void excute(HttpClient client, Object o) {
									try {
										String html = new String((byte[]) o,
												"GBK");
										String rs = ParseFactory.parse(
												"openhujiaozhuanyi", html);
										key.append(rs);
									} catch (UnsupportedEncodingException e) {
										// key.append(Constant.get(Constant.ERROR));
									}
								}
							});
			if (Constant.SET_SUCC.equals(key.toString())) {
				return true;
			}
		}
		if (Constant.SET_SUCC.equals(key2.toString())) {
			return true;
		}
		return false;
	}

}
