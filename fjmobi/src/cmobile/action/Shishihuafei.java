package cmobile.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

import cmobile.common.Constant;
import cmobile.common.ParseFactory;
import cmobile.http.Callback;
import cmobile.http.HttpCore;

public class Shishihuafei extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(Constant.URL + "/service/fee/query/queryCallFee.do?tab=1",new Callback() {
			@Override
			public void excute(HttpClient client, Object o) {
				try {
					String html = new String((byte[]) o, "GBK");
					String rs=ParseFactory.parse("shishihuafei", html);
					key.append(rs);
				} catch (UnsupportedEncodingException e) {
				}
			}
		});
		if(Constant.SET_FAIL.equals(key)){
			throw new HttpException(Constant.SET_FAIL);
		}
		return key.toString();
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		return false;
	}

}
