package cmobile.action;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

public interface Action {
	public boolean judge(Map params) throws HttpException, IOException;
	public String action(Map rs) throws HttpException, IOException;
}
