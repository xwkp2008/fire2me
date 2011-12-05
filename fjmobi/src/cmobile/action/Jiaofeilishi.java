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

public class Jiaofeilishi extends AbstractAction{

	@Override
	public String action(Map rs) throws HttpException, IOException {
		//rs包含了月份信息
		final StringBuffer key = new StringBuffer();
		String rts="";
		rts=HttpCore
				.getInstance()
				.doPost(
						Constant.URL
								+ "/service/fee/query/queryMoneyRecord.do?tab=1",
						rs, null, new Callback() {
							@Override
							public void excute(HttpClient client, Object o) {
								try {
									String html = new String((byte[]) o, "GBK");
									String rs = ParseFactory.parse(
											"jiaofeilishi", html);
									key.append(rs);
								} catch (UnsupportedEncodingException e) {
								}
							}
						});
		if (Constant.SET_FAIL.equals(rts)) {
			throw new HttpException(Constant.SET_FAIL);
		}
		if(rs.isEmpty()){
			throw new HttpException(Constant.SET_FAIL);
		}
		return key.toString();
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		return false;
	}

}
