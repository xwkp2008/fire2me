package cmobile.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import ocr.OCR;

import org.apache.log4j.Logger;

import cmobile.common.Constant;
import cmobile.ver2.action.Action;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("unchecked")
public class HttpCore {

    protected transient static final Logger log = Logger.getLogger(OCR.class);
    private static HttpCore instance = new HttpCore();
    private HttpClient client = null;
    private int timout = 60;

    private HttpCore() {
        timout = 60;
        if (Constant.TIMEOUT != null && Constant.TIMEOUT.length() > 0) {
            timout = Integer.valueOf(Constant.TIMEOUT);
        }
        client = new DefaultHttpClient();
        //client.getConnectionManager().getSchemeRegistry()et.setConnectionTimeout(
        //		timout * 1000);
        client.getParams().setParameter(
                ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    }

    public static HttpCore getInstance() {
        return instance;
    }

    public static void clear() {
        instance = new HttpCore();
    }

    public void main(String args[]) throws IOException {
        // HttpCore.doGet();
        HttpCore.getInstance().doPost();
    }

    /**
	 * Get获取 SongWei
	 */
    public void doGet() {
        String url = "http://www.google.cn";
        // 构造HttpClient的实例
        // 创建GET方法的实例
        HttpHost targetHost = new HttpHost("www.google.cn");
        HttpGet getMethod = new HttpGet("/");
        // 使用系统提供的默认的恢复策略
        try {
            HttpResponse resp = client.execute(targetHost, getMethod);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                        + getMethod.getMethod());
            }
            // 读取内容
            byte[] responseBody = EntityUtils.toByteArray(resp.getEntity());
            // 处理内容
            log.info(new String(responseBody, "gb2312"));
        } catch (IOException e) {
            // 发生网络异常
            log.error(e.getMessage(), e);
        } finally {
        }
    }

    /**
	 * Get获取 SongWei
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
    public String doGet(String url, Callback callback) throws HttpException,
            IOException {
        // 创建GET方法的实例
        HttpGet getMethod = new HttpGet(url);
        //设置 get 请求超时
        getMethod.getParams().setIntParameter("http.socket.timeout", 3000);
        // 执行getMethod
        HttpResponse resp = client.execute(getMethod);
        if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException(Constant.CONN_FAIL);
        }
        // 读取内容
        byte[] responseBody = EntityUtils.toByteArray(resp.getEntity());
        // 执行回调
        if (callback != null) {
            callback.excute(client, responseBody);
        }
        // 处理内容
        // if(responseBody!=null) log.info(new String(responseBody,
        // "gb2312"));

        return Constant.NORMAL;
    }

    /**
	 * 可执行回掉的POST提交
	 * 
	 * @param url
	 * @param map
	 * @param callback
	 * @throws IOException
	 * @throws HttpException
	 * @throws IOException
	 */
    public String doPost(String url, Map map, Callback beforeCallback,
            Callback endCallback) throws HttpException, IOException {
        HttpPost postMethod = new HttpPost(url);
        // 执行回调--执行附加操作
        if (beforeCallback != null) {
            beforeCallback.excute(client, map);
        }
        // 填入各个表单域的值
        if (map != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator it = map.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                nvps.add(new BasicNameValuePair(key, map.get(key).toString()));
            }
            postMethod.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        }

        // 执行postMethod
        int statusCode;

        HttpResponse response = client.execute(postMethod);

        log.info("返回：" + response.getStatusLine().getStatusCode());
        // 读取内容
        byte[] responseBody = EntityUtils.toByteArray(response.getEntity());
        if (endCallback != null) {
            endCallback.excute(client, responseBody);
        }
        // 处理内容
        StringBuffer buf = new StringBuffer();
        // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发301或者302
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY
                || response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
            // 从头中取出转向的地址
            Header locationHeader = postMethod.getFirstHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                log.info("The page was redirected to:" + location);
            } else {
                System.err.println("Location field value is null.");
            }
            return locationHeader.getValue();
        } else {
            log.info("other");
        }
        if (responseBody != null) {
            buf.append(new String(responseBody, "GBK"));
        }
        return buf.toString();

    }

    /**
	 * Post获取 SongWei
	 */
    public void doPost() throws IOException {
        String url = "https://www.google.com/accounts/log.infoinAuth";
        // url="http://www.newsmth.net/bbslog.infoin2.php";
        HttpPost postMethod = new HttpPost(url);
        // 填入各个表单域的值
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("Email", ""));
        nvps.add(new BasicNameValuePair("Passwd", ""));
        postMethod.setEntity(
                new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        // 将表单的值放入postMethod中

        postMethod.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        // 执行postMethod
        HttpResponse response = client.execute(postMethod);

        log.info(
                "返回值" + response.getStatusLine().getStatusCode());
        // 读取内容
        byte[] responseBody = EntityUtils.toByteArray(response.getEntity());
        // 处理内容

        log.info(
                new String(responseBody, "GBK"));
        // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发301或者302

        if (response.getStatusLine().getStatusCode()
                == HttpStatus.SC_MOVED_PERMANENTLY
                || response.getStatusLine().getStatusCode()
                == HttpStatus.SC_MOVED_TEMPORARILY) {
            // 从头中取出转向的地址
            Header locationHeader = postMethod.getFirstHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                log.info("The page was redirected to:" + location);
            } else {
                System.err.println("Location field value is null.");
            }
            return;
        } else {
            log.info("other");
        }
    }

    /**
	 * 远程下载图片
	 * 
	 * @param imageUrl
	 * @throws HttpException
	 * @throws IOException
	 */
    public void doDownImage(String imageUrl) throws HttpException, IOException {
        try {
            HttpGet get = new HttpGet(
                    "http://images.sohu.com/uiue/sohu_log.infoo/beijing2008/2008sohu.gif");
            HttpResponse response = client.execute(get);
            String temp = System.getProperty("java.io.tmpdir");
            File storeFile = new File(temp + File.pathSeparator + "yzm.jpeg");
            FileOutputStream output = new FileOutputStream(storeFile);
            output.write(EntityUtils.toByteArray(response.getEntity()));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
