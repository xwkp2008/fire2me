package cmobile.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ocr.OCR;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;

import cmobile.common.Constant;
import cmobile.common.JsDes;
import cmobile.common.ParseFactory;
import cmobile.http.Callback;
import cmobile.http.HttpCore;

/**
 * @author linq
 * 
 */
public class Login extends AbstractAction {

	protected transient static final Logger log = Logger.getLogger(Login.class);

	@Override
	public String action(Map rs) throws HttpException, IOException {
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(
				Constant.URL + "/service/info/ywbl_gxtx_mmxg.jsp?tab=5",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o)
							throws UnsupportedEncodingException {
						String html = new String((byte[]) o, "GBK");
						String rs = ParseFactory.parse("qianfeitingji", html);
						key.append(rs);
					}
				});
		if (Constant.SET_SUCC.equals(key.toString())) {
			return "qianfei";
		}
		return "";
	}

	private String getYZM(final String yzmfile) throws HttpException,
			IOException {
		HttpCore.getInstance().doGet(Constant.URL + "/service/validimage.jsp",
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						File storeFile = new File(yzmfile);
						FileOutputStream output;
						try {
							output = new FileOutputStream(storeFile);
							// 得到网络资源的字节数组,并写入文件
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
				validateCode=validateCode.replaceAll(" ", "");
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
		map.put("loginmode", "01");
		map.put("cityCode", "first");
		map.put("sso", "0");

		JsDes des = new JsDes();
		String key1 = "YHXWWLKJYXGS";
		String key2 = "ZFCHHYXFL10C";
		String key3 = "DES";

		map.put("mobileno", des.strEnc(params.get("mobileno").toString(), key1,
				key2, key3));
		map.put("password1", des.strEnc(params.get("password").toString(),
				key1, key2, key3));
		map.put("password", des.strEnc(params.get("password").toString(), key1,
				key2, key3));
		// map.put("smscode", "13459499657");
		final String yzmfile = "tmp/yzm.jpeg";
		File tmpfile = new File("tmp");
		if (!tmpfile.exists())
			tmpfile.mkdirs();
		final StringBuffer key = new StringBuffer();
		HttpCore.getInstance().doGet(Constant.URL, null);

		HttpCore.getInstance().doPost(
				Constant.URL + "/service/user/mobilenoLogin.do", map,
				new Callback() {
					@Override
					public void excute(HttpClient client, Object o)
							throws HttpException, IOException {
						String validateCode = getYZM(yzmfile);
						map.put("validateCode", validateCode);
					}
				}, new Callback() {// endCallback
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "GBK");
							String rs = ParseFactory.parse("isloginfail", html);
							key.append(rs);
						} catch (UnsupportedEncodingException e) {
							log.error(e.getMessage(), e);
							// key.append(Constant.get(Constant.ERROR));
						}
					}
				});
		if (Constant.PASSWORD_ERROR.equals(key.toString())) {
			throw new HttpException(Constant.PASSWORD_ERROR);
		}
		key.delete(0, key.length());

		HttpCore.getInstance().doGet(
				Constant.URL + "/service/user/isLogined.do", new Callback() {
					@Override
					public void excute(HttpClient client, Object o) {
						try {
							String html = new String((byte[]) o, "GBK");
							String rs = ParseFactory.parse("islogin", html);
							key.append(rs);
						} catch (UnsupportedEncodingException e) {
							log.error(e.getMessage(), e);
						}
					}
				});
		if (Constant.LOGIN_SUCC.equals(key.toString())) {
			return true;
		}
		return false;
	}

}
