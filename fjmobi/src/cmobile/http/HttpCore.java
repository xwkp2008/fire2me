package cmobile.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import ocr.OCR;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.derby.iapi.store.raw.data.DataFactory;
import org.apache.log4j.Logger;

import cmobile.common.Constant;
import cmobile.db.DBFactory;

@SuppressWarnings("unchecked")
public class HttpCore {
	protected transient static final Logger log = Logger.getLogger(OCR.class);

	private static HttpCore instance = new HttpCore();
	private HttpClient client = null;
	private int timout=60;

	private HttpCore() {
		timout = 60;
		if (Constant.TIMEOUT != null&&Constant.TIMEOUT.length()>0) {
			timout = Integer.valueOf(Constant.TIMEOUT);
		}
		client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				timout * 1000);
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
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			log.info(new String(responseBody, "gb2312"));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			log.error("Please check your provided http address!");
		} catch (IOException e) {
			// 发生网络异常
			log.error(e.getMessage(), e);
		} finally {
			// 释放连接
			getMethod.releaseConnection();
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
		GetMethod getMethod = new GetMethod(url);
		  //设置 get 请求超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,timout*1000);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 执行getMethod
		int statusCode = client.executeMethod(getMethod);
		if (statusCode != HttpStatus.SC_OK) {
			throw new IOException(Constant.CONN_FAIL);
		}
		// 读取内容
		byte[] responseBody = getMethod.getResponseBody();
		// 执行回调
		if (callback != null)
			callback.excute(client, responseBody);
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
		PostMethod postMethod = new PostMethod(url);
		// 执行回调--执行附加操作
		if (beforeCallback != null)
			beforeCallback.excute(client, map);
		// 填入各个表单域的值
		if (map != null) {
			NameValuePair[] data = new NameValuePair[map.keySet().size()];
			int i = 0;
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				data[i++] = new NameValuePair(key, map.get(key).toString());
			}
			postMethod.setRequestBody(data);
		}

		// 执行postMethod
		int statusCode;

		statusCode = client.executeMethod(postMethod);

		log.info("返回：" + statusCode);
		// 读取内容
		byte[] responseBody = postMethod.getResponseBody();
		if (endCallback != null) {
			endCallback.excute(client, responseBody);
		}
		// 处理内容
		StringBuffer buf = new StringBuffer();
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发301或者302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
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
		if (responseBody != null)
			buf.append(new String(responseBody, "GBK"));
		return buf.toString();

	}

	/**
	 * Post获取 SongWei
	 */
	public void doPost() throws IOException {
		String url = "https://www.google.com/accounts/log.infoinAuth";
		// url="http://www.newsmth.net/bbslog.infoin2.php";
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("Email", ""),
				new NameValuePair("Passwd", "") };
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		int statusCode = client.executeMethod(postMethod);
		log.info("返回值" + statusCode);
		// 读取内容
		byte[] responseBody = postMethod.getResponseBody();
		// 处理内容
		log.info(new String(responseBody, "GBK"));
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发301或者302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
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
			GetMethod get = new GetMethod(
					"http://images.sohu.com/uiue/sohu_log.infoo/beijing2008/2008sohu.gif");
			client.executeMethod(get);
			String temp = System.getProperty("java.io.tmpdir");
			File storeFile = new File(temp + File.pathSeparator + "yzm.jpeg");
			FileOutputStream output = new FileOutputStream(storeFile);
			output.write(get.getResponseBody());
			output.close();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * Delete获取 SongWei
	 */
	public void doDelete() {
		String url = "http://localhost:8080/FrameProject/ReturnMessage";
		// 创建GET方法的实例
		DeleteMethod deleteMethod = new DeleteMethod(url);
		// 使用系统提供的默认的恢复策略
		deleteMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = client.executeMethod(deleteMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ deleteMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = deleteMethod.getResponseBody();
			// 处理内容
			log.info(new String(responseBody, "UTF-8"));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			log.info("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			deleteMethod.releaseConnection();
		}
	}

}