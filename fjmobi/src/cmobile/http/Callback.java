package cmobile.http;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;


public interface Callback {
	public void excute(HttpClient client,Object o) throws HttpException, IOException;
}
