/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cmobile.ver2.action;

import cmobile.common.Constant;
import cmobile.common.Parse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpException;

public class LoginedAction extends BaseAction {

    @Override
    public boolean prefix(Map params) throws HttpException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String action(Map rs) throws HttpException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean subfix(Map params) throws HttpException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String parse(String params) {
        try {
            Parse parse = null;
            parse = new Parse(Constant.KEYWORD_PASSWORDERROR, params);
            if (parse.parse() != null) {
                return Constant.PASSWORD_ERROR;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Constant.LOGIN_SUCC;
    }
}
