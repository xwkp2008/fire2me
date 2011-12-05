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

public class Duanxin extends AbstractAction {

	@Override
	public String action(Map rs) throws HttpException, IOException {
		return null;
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(
				Constant.URL + "/service/operate/basic/queryService.do?action=100111&number=o_service.o_gndx&tab=2",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "GBK");
							String rs=ParseFactory.parse("duanxin", html);
							key.append(rs);
						} catch (UnsupportedEncodingException e) {
							//key.append(Constant.get(Constant.ERROR));
						}
					}
				});
		if(Constant.SET_SUCC.equals(key.toString())){
			return true;
		}
		return false;
	}

}
