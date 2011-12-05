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

public class WLAN extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		boolean open = true;

		if (rs != null) {
			String operate = rs.get("sjswoperate").toString();
			if (!"open".equals(operate)) {
				open = false;
			}
		}
		String operateStr = "/service/operate/basic/isBizproc.do?tab=2&serviceid=o_service_wlankg&status=01&biz_type=2&orig_domain=BOSS&misc=WLAN&info_code=1&info_value=10";
		if (!open)
			operateStr = "/service/operate/basic/isBizproc.do?tab=2&serviceid=o_service_wlankg&status=02&biz_type=2&orig_domain=BOSS&misc=WLAN&info_code=1&info_value=11";
		String key = null;
		key = HttpCore.getInstance().doGet(Constant.URL + operateStr, null);
		if (!Constant.NORMAL.equals(key)) {
			throw new HttpException(Constant.SET_FAIL);
		}
		return open ? "开通完毕" : "已关闭";
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		HttpCore
				.getInstance()
				.doGet(
						Constant.URL
								+ "/service/operate/basic/queryIsBizproc.do?tab=2",
						new Callback() {
							@Override
							public void excute(HttpClient client, Object o) {
								try {
									String html = new String((byte[]) o, "GBK");
									String rs = ParseFactory.parse(
											"shoujishangwang", html);
									key.append(rs);
								} catch (UnsupportedEncodingException e) {
									// key.append(Constant.get(Constant.ERROR));
								}
							}
						});
		if (Constant.SET_SUCC.equals(key.toString())) {
			return true;
		}
		return false;
	}

}
