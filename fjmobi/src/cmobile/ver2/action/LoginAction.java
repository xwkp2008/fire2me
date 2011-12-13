/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cmobile.ver2.action;

import cmobile.action.Login;
import cmobile.common.Constant;
import cmobile.common.JsDes;
import cmobile.common.Parse;
import cmobile.http.Callback;
import cmobile.http.HttpCore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import ocr.OCR;
import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;

public class LoginAction extends BaseAction {

    protected transient static final Logger log = Logger.getLogger(Login.class);

    @Override
    public boolean prefix(Map params) throws HttpException, IOException {
        return true;
    }

    @Override
    public String action(Map rs) throws HttpException, IOException {
        final StringBuffer key = new StringBuffer();
        HttpCore.getInstance().doGet(
                Constant.URL + "/service/fee/query/queryCallFee.do?number=q_service.q_zhye",
                new Callback() {

                    public void excute(HttpClient client, Object o)
                            throws UnsupportedEncodingException {
                        String html = new String((byte[]) o, "GBK");
                        String rs = new YuEQianFeiAction().parse(html);
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
    public boolean subfix(Map params) throws HttpException, IOException {
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
        if (!tmpfile.exists()) {
            tmpfile.mkdirs();
        }
        final StringBuffer key = new StringBuffer();
        HttpCore.getInstance().doGet("http://www.fj.10086.cn/service/index.jsp", null);

        HttpCore.getInstance().doPost(
                "https://fj.ac.10086.cn/sso3/Login", map,
                new Callback() {

                    public void excute(HttpClient client, Object o)
                            throws HttpException, IOException {
                        String validateCode = getYZM(yzmfile);
                        map.put("validateCode", validateCode);
                    }
                }, new Callback() {// endCallback

            public void excute(HttpClient client, Object o) {
                try {
                    String html = new String((byte[]) o, "GBK");
                    String rs = parse(html);
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
                    String rs = parse(html);
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

    @Override
    public String parse(java.lang.String params) {
        try {
            Parse parse = null;
            parse = new Parse(Constant.KEYWORD_LOGINED, params);
            if (parse.parse() != null) {
                return Constant.LOGIN_SUCC;
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Constant.LOGIN_FAIL;
    }
}
