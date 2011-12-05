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

public class Tingji extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		String key = null;
		key = HttpCore
				.getInstance()
				.doGet(
						Constant.URL
						// +
								// "/service/operate/other/stopPhoneCall.do?number=o_service.o_tjfj&status=tj&tab=2",
								+ "/service/operate/other/stopPhoneCall.do?number=o_service.o_tj&status=tj&tab=2",
						null);
		if (!Constant.NORMAL.equals(key)) {
			throw new HttpException(Constant.SET_FAIL);
		}
		return null;
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(
				Constant.URL
						+ "/service/operate/other/queryPhoneStatus.do?tab=2",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "GBK");
							String rs = ParseFactory.parse("isfuji", html);
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
