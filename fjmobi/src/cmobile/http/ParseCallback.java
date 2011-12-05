package cmobile.http;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

public interface ParseCallback {
	public String excute(String rs) throws HttpException, IOException;
}
