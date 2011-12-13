package cmobile.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ocr.OCR;

import org.apache.log4j.Logger;

import cmobile.common.Constant;
import cmobile.common.ParseFactory;
import cmobile.http.Callback;
import cmobile.http.HttpCore;
import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;

/**
 * @author linq
 * 
 */
public class IhomeLogin extends AbstractAction {

	protected transient static final Logger log = Logger
			.getLogger(IhomeLogin.class);

	@Override
	public String action(Map rs) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(Constant.IHOME_URL + "/room/room.html",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o)
							throws UnsupportedEncodingException {
						String html = new String((byte[]) o, "GBK");
						String rs = ParseFactory.parse("ihomelogin", html);
						key.append(rs);
					}
				});
		return key.toString();
	}

	private String getYZM(final String yzmfile) throws HttpException,
			IOException {
		HttpCore.getInstance().doGet(
				"http://211.143.149.155:8008/sso2/common/image.jsp",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						File storeFile = new File(yzmfile);
						FileOutputStream output;
						try {
							output = new FileOutputStream(storeFile);
							output.write((byte[]) o);
							output.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		File file = new File(yzmfile);
		String validateCode = "";
		if (file.exists()) {
			OCR ocr = new OCR();
			try {
				validateCode = ocr.recognizeText(file, "jpeg");
				validateCode = validateCode.replaceAll(" ", "");
				System.out.println(validateCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return validateCode;
	}

	@Override
	public boolean judge(Map params) throws HttpException, IOException {
		final Map map = new HashMap();
		map.put("type", "B");
		map.put("spid", "8aaec0111d2485fc011d669f363e16d0");
		map.put("sid", "[object Object]");
		map
				.put(
						"backurl",
						"http://ihome.fj.chinamobile.com/ssoAssert.jsp?CALLBACK_URL=http://ihome.fj.chinamobile.com/room/room.html");

		// JsDes des = new JsDes();
		// String key1 = "YHXWWLKJYXGS";
		// String key2 = "ZFCHHYXFL10C";
		// String key3 = "DES";

		map.put("mobileno", params.get("mobileno").toString());
		map.put("password2", params.get("password").toString());
		map.put("password", params.get("password").toString());
		// map.put("smscode", "13459499657");
		final String yzmfile = "tmp/yzm.jpeg";
		File tmpfile = new File("tmp");
		if (!tmpfile.exists())
			tmpfile.mkdirs();
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(Constant.IHOME_URL, null);

		HttpCore.getInstance().doPost(
				"http://211.143.149.155:8008/sso2/idp/IDPLogin", map,
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o)
							throws HttpException, IOException {
						String validateCode = getYZM(yzmfile);
						map.put("valid", validateCode);
					}
				}, new Callback() {// endCallback
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "GBK");
							String rs = ParseFactory.parse("ihomelogin", html);
							key.append(rs);
						} catch (UnsupportedEncodingException e) {
							log.error(e.getMessage(), e);
						}
					}
				});
		key.delete(0, key.length());
		HttpCore
				.getInstance()
				.doPost(
						"http://211.143.149.155:8008/sso2/login/servicePassValidate.do",
						map, new Callback() {
							@Override
							public void excute(HttpClient client, Object o)
									throws HttpException, IOException {
								// String validateCode = getYZM(yzmfile);
								// map.put("validateCode", validateCode);
							}
						}, new Callback() {// endCallback
							@Override
							public void excute(HttpClient client, Object o) {
								try {
									String html = new String((byte[]) o, "GBK");
								} catch (UnsupportedEncodingException e) {
									log.error(e.getMessage(), e);
								}
							}
						});
		HttpCore.getInstance().doGet(Constant.IHOME_URL + "/room/room.html",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o)
							throws UnsupportedEncodingException {
						String html = new String((byte[]) o, "utf-8");
						String rs = ParseFactory.parse("ihomelogin", html);
						key.append(rs);
					}
				});
		if (Constant.LOGIN_SUCC.equals(key.toString())) {
			return true;
		}
		return false;
	}
}
