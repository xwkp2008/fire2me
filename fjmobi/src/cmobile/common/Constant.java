package cmobile.common;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	private static Map<String, String> map = new HashMap<String, String>();
	public static final String NORMAL = "0";
	public static final String ERROR = "-1";
	public static final String CONN_SUCC = "2";
	public static final String CONN_FAIL = "-2";
	public static final String PASSWORD_ERROR = "-3";
	public static final String LOGIN_SUCC = "5";
	public static final String LOGIN_FAIL = "-5";
	public static final String SET_SUCC = "6";
	public static final String SET_FAIL = "-6";

	static {
		map.put(ERROR, "错误返回");
		map.put(CONN_FAIL, "无法连接服务");
		map.put("-3", "密码错误");
		map.put("-4", "验证码校验失败");
		map.put(LOGIN_FAIL, "登录失败");
		map.put(SET_FAIL, "设置失败");

		map.put(NORMAL, "正常");

		map.put("1", "连接成功");
		map.put(CONN_SUCC, "获取成功");
		map.put("3", "校验成功");
		map.put(LOGIN_SUCC, "登录成功");
		map.put(SET_SUCC, "设置成功");
	}

	public static String get(String key) {
		if (key != null)
			if (map.containsKey(key)) {
				return map.get(key);
			}
		return map.get(ERROR);
	}
	
	public static String get2(String key) {
		if (key != null)
			if (map.containsKey(key)) {
				return map.get(key);
			}
		return null;
	}

	public static String URL = "http://www.fj.10086.cn";
	
	public static String IHOME_URL = "https://www.fj.10086.cn";

	public static String KEYWORD_LOGINED = "欢迎您：(.*)手机号(.*)$";
	public static String KEYWORD_PASSWORDERROR = "服务密码错误";
	public static String KEYWORD_LOGINFAIL = "错误提示";
	public static String KEYWORD_FUJI = "您即将办理复机业务";
	public static String KEYWORD_TINGJI = "您即将开通停机保号业务";
	public static String KEYWORD_YUEQIANFEI = "<td [^(\\(元\\)]*帐户总余额[^!]*</table>";
	public static String KEYWORD_SHISHIHUAFEI = "费用名称.*实时话费总额为(\\.|[0-9])*元";
	public static String KEYWORD_YUEJIEZHANGDAN = "项目名称.*费用合计：(-|\\.|[0-9])*";
	public static String KEYWORD_YUEJIEZHANGDAN2 = "客户名称.*账单月份：";
	public static String KEYWORD_XIUGAIMIMA = "修改成功";
	public static String KEYWORD_HUJIAOZHUANYI = "成功";
	public static String KEYWORD_JIAOFEILISHI = "您好，您[^问]*</table>";
	public static String KEYWORD_DUANXIN = "<td .*业务状态.*已开通.*</table>";
	public static String KEYWORD_ISHUJIAOZHUANYI = "已开启呼叫转移";
	public static String KEYWORD_ISHUJIAOZHUANYI2 = "关机和不在.*已设置</td>";
	public static String KEYWORD_OPENHUJIAOZHUANYI = "开通了“呼叫转移”";
	public static String KEYWORD_QIANFEITINGJI = "手机状态为(欠费停机|单停)";
	public static String KEYWORD_SHOUJISHANGWANG = "已开通";
	
	public static String KEYWORD_IHOME = "ihome";
	public static String KEYWORD_IHOMELOGIN = "退出登录";
	public static String KEYWORD_IHOME_JIANJIA = "jianjia.jpg";
	public static String KEYWORD_IHOME_VPN = "vpn管理";
	public static String KEYWORD_IHOME_USERINFO = "PK_FAMILY\":\"(\\d)+";
	public static String KEYWORD_IHOME_SUCCESS = "\"success\":true";
        
        //public static String PARAMAS_NUMBERS="";
	
	public static String MOBILENO = "号码管理";
	public static String SYSTEMCONFIG = "系统设置";
	public static String TINGJIFUJI = "停机/复机";
	public static String YUEQIANFEI = "余额欠费";
	public static String SHISHIHUAFEI = "实时话费";
	public static String XIUGAIMIMA = "修改密码";
	public static String YUEJIEZHANGDAN = "月结账单";
	public static String DUANXIN = "短信状态";
	public static String HUJIAOZHANYI = "呼叫转移";
	public static String JIAOFEILISHI = "缴费历史";
	public static String SHOUJIWULIANWANG = "手机互联网";
	public static String MOSHANGWANG= "MO互联网";
	public static String WLAN = "WLAN";
	public static String IHOME = "IHOME";
	
	public static String TIMEOUT=null;
	public static boolean IS_SIMPLE=true;
	public static boolean SYSTEM_REG=false;
        public static boolean SYSTEM_SHUTDOWN=false;
	public static String SYSTEM_PROC="system_proc";
	public static String SYSTEM_TIMEOUT="system_timeout";

}
