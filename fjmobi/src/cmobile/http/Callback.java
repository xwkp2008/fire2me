package cmobile.http;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

public interface Callback {
	public void excute(HttpClient client,Object o) throws HttpException, IOException;
}
