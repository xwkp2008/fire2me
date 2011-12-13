/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cmobile.ver2.action;

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpException;

/**
 *
 * @author linq
 */
public interface Action {
        public boolean prefix(Map params) throws HttpException, IOException;
	public String action(Map rs) throws HttpException, IOException;
        public boolean subfix(Map params) throws HttpException, IOException;
        public String parse(java.lang.String params);
}

