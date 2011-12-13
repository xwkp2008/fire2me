/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cmobile.ver2.action;

import cmobile.common.Constant;
import cmobile.common.Parse;
import cmobile.http.ParseCallback;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpException;
import org.htmlparser.util.ParserUtils;

public class YuEQianFeiAction extends BaseAction {

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
            parse = new Parse(Constant.KEYWORD_YUEQIANFEI, params);
            String res = "";
            if ((res = parse.parse(new ParseCallback() {

                @Override
                public String excute(String rs) {
                    rs = ParserUtils.trimAllTags(rs, false).trim();
                    rs = rs.replaceAll("[\\t|(&nbsp;)]", "");
                    rs = rs.replaceAll("[(\\r\\n)]", " ");
                    rs = rs.replaceAll("\\s+", "\t");
                    rs = rs.replaceAll("元", "  ");
                    System.out.println(rs);
                    return rs;
                }
            })) != null) {
                return res;
            }
        } catch (Exception ex) {
            Logger.getLogger(YuEQianFeiAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Constant.SET_FAIL;
    }
}
