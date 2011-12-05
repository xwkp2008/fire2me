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

public class Yueqianfei extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		final StringBuffer keystr = new StringBuffer();
		String key = null;
		key=HttpCore.getInstance().doGet(Constant.URL + "/service/fee/query/queryCallFee.do?number=q_service.q_zhye&tab=1",new Callback() {
			@Override
			public void excute(HttpClient client, Object o) {
				try {
					String html = new String((byte[]) o, "GBK");
					String res=ParseFactory.parse("yueqianfei", html);
					keystr.append(res);
				} catch (UnsupportedEncodingException e) {
				}
			}
		});
		if(Constant.SET_FAIL.equals(key)){
			throw new HttpException(Constant.SET_FAIL);
		}
		return keystr.toString();
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		return false;
	}

}
