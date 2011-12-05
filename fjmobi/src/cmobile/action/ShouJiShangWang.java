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

public class ShouJiShangWang extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		boolean open = true;
		if (rs != null) {
			String operate = rs.get("sjswoperate").toString();
			if (!"open".equals(operate)) {
				open = false;
			}
		}
		String operateStr = "/service/operate/other/modifyService.do?action=100202&status=1&number=o_service.o_sjhlwkg&again=0";
		if (!open)
			operateStr = "/service/operate/other/modifyService.do?action=100202&status=0&number=o_service.o_sjhlwkg&again=0";
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
								+ "/service/operate/other/queryService.do?action=100202&number=o_service.o_sjhlwkg&tab=2",
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
