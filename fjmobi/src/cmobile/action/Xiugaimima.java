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

public class Xiugaimima extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		// rs包含修改密码和原始密码
		final StringBuffer key = new StringBuffer();
		HttpCore
				.getInstance()
				.doPost(
						Constant.URL
								+ "/service/info/modifyPassword.do?number=o_service.o_mmxg&tab=5",
						rs, null, new Callback() {
							@Override
							public void excute(HttpClient client, Object o) {
								try {
									String html = new String((byte[]) o, "GBK");
									String rs = ParseFactory.parse(
											"xiugaimima", html);
									key.append(rs);
								} catch (UnsupportedEncodingException e) {
								}
							}
						});
		if (Constant.SET_FAIL.equals(key.toString())) {
			return Constant.SET_FAIL;
		}
		return Constant.SET_SUCC;
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		return false;
	}

}
