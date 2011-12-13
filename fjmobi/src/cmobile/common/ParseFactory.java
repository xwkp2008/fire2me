package cmobile.common;

import java.io.IOException;

import ocr.OCR;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserUtils;

import cmobile.http.ParseCallback;
import org.apache.http.HttpException;

public class ParseFactory {
	protected transient static final Logger log = Logger.getLogger(OCR.class);

	public static String parse(String type, String content) {
		Parse parse = null;
		try {
			if ("islogin".equals(type)) {
				parse = new Parse(Constant.KEYWORD_LOGINED, content);
				if (parse.parse() != null) {
					return Constant.LOGIN_SUCC;
				}
				return Constant.LOGIN_FAIL;
			} else if ("isloginfail".equals(type)) {
				parse = new Parse(Constant.KEYWORD_PASSWORDERROR, content);
				if (parse.parse() != null) {
					return Constant.PASSWORD_ERROR;
				}
				return Constant.LOGIN_SUCC;
			} else if ("isfuji".equals(type)) {
				parse = new Parse(Constant.KEYWORD_TINGJI, content);
				if (parse.parse() != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("fuji".equals(type)) {
				parse = new Parse(Constant.KEYWORD_FUJI, content);
				if (parse.parse() != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("istingji".equals(type)) {
				parse = new Parse(Constant.KEYWORD_FUJI, content);
				if (parse.parse() != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("yueqianfei".equals(type)) {
				parse = new Parse(Constant.KEYWORD_YUEQIANFEI, content);
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
				return Constant.SET_FAIL;
			} else if ("shishihuafei".equals(type)) {
				String res = "";
				parse = new Parse(Constant.KEYWORD_SHISHIHUAFEI, content);
				if ((res = parse.parse(new ParseCallback() {
					@Override
					public String excute(String rs) {
						try {
							rs = ParserUtils.trimTags(rs,
									new String[] { "strong" }, false, true);
						} catch (Exception e) {
						}
						rs = ParserUtils.trimAllTags(rs, false).trim();
						rs = rs.replaceAll("[\\t|(&nbsp;)]", "");
						rs = rs.replaceAll("[(\\r\\n)]", " ");
						rs = rs.replaceAll("\\s+", "\t");
						rs = rs.replaceAll("费用名称.*由他人付费（元）", "");
						rs = rs.replaceAll("尊敬的.*", "").trim();
						System.out.println(rs);
						return rs;
					}

				})) != null) {
					return res;
				}
				return Constant.SET_FAIL;
			} else if ("yuejiezhangdan".equals(type)) {
				Parse parse2 = new Parse(Constant.KEYWORD_YUEJIEZHANGDAN2,
						content);
				String pinpai = parse2.parse(new ParseCallback() {
					@Override
					public String excute(String rs) throws HttpException,
							IOException {
						try {
							rs = rs.replaceAll("客户名称： <", "*<");
							rs = ParserUtils.trimTags(rs, new String[] { "p" },
									true, false);
						} catch (Exception e) {
						}
						rs = ParserUtils.trimAllTags(rs, false).trim();
						rs = rs.replaceAll("[(\\r\\n)]", "");
						rs = rs.replaceAll("账单月份：", "\t").trim();
						rs = rs.replaceAll("客户名称：", "\t").trim();
						rs = rs.replaceAll("客户品牌：", "\t").trim();
						return rs;
					}
				});
				// System.out.println(pinpai);
				parse = new Parse(Constant.KEYWORD_YUEJIEZHANGDAN, content);
				String rs = "";
				if ((rs = parse.parse(new ParseCallback() {
					@Override
					public String excute(String rs) {
						try {
							rs = ParserUtils.trimTags(rs,
									new String[] { "strong" }, true, false);
						} catch (Exception e) {
						}
						rs = ParserUtils.trimAllTags(rs, false).trim();
						rs = rs.replaceAll("[\\t|(&nbsp;)]", "");
						rs = rs.replaceAll("[(\\r\\n)]", " ");
						rs = rs.replaceAll("\\s+", "\t");
						rs = rs.replaceAll("（元）", "");
						rs = rs.replaceAll("费用名称.*由他人付费元", "").trim();
						return rs;
					}
				})) != null) {
					return rs + "\t" + pinpai;
				}
				return Constant.SET_FAIL;
			} else if ("xiugaimima".equals(type)) {
				parse = new Parse(Constant.KEYWORD_XIUGAIMIMA, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("hujiaozhuanyi".equals(type)) {
				parse = new Parse(Constant.KEYWORD_HUJIAOZHUANYI, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("ishujiaozhuanyi".equals(type)) {
				parse = new Parse(Constant.KEYWORD_ISHUJIAOZHUANYI, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("ishujiaozhuanyi2".equals(type)) {
				parse = new Parse(Constant.KEYWORD_ISHUJIAOZHUANYI2, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("openhujiaozhuanyi".equals(type)) {
				parse = new Parse(Constant.KEYWORD_OPENHUJIAOZHUANYI, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("jiaofeilishi".equals(type)) {
				parse = new Parse(Constant.KEYWORD_JIAOFEILISHI, content);
				String rs = "";
				if ((rs = parse.parse(new ParseCallback() {
					@Override
					public String excute(String rs) {
						// rs = rs.replaceAll("缴费[^(\\s)]{2}", "");
						rs = ParserUtils.trimAllTags(rs, false).trim();
						rs = rs.replaceAll("[\\t|(&nbsp;)]", "");
						rs = rs.replaceAll("[(\\r\\n)]", " ");
						// rs = rs.replaceAll("\\s+", "\t");
						return rs;
					}
				})) != null) {
					return rs;
				}
				return Constant.SET_FAIL;
			} else if ("duanxin".equals(type)) {
				parse = new Parse(Constant.KEYWORD_DUANXIN, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("qianfeitingji".equals(type)) {
				parse = new Parse(Constant.KEYWORD_QIANFEITINGJI, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("shoujishangwang".equals(type)) {
				parse = new Parse(Constant.KEYWORD_SHOUJISHANGWANG, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("ihomelogin".equals(type)) {
				parse = new Parse(Constant.KEYWORD_IHOMELOGIN, content);
				if ((parse.parse()) != null) {
					return Constant.LOGIN_SUCC;
				}
				return Constant.LOGIN_FAIL;
			} else if ("ihomejianjia".equals(type)) {
				parse = new Parse(Constant.KEYWORD_IHOME_JIANJIA, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("ihomevpnadd".equals(type)) {
				parse = new Parse(Constant.KEYWORD_IHOME_VPN, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("ihomevpnsucc".equals(type)) {
				parse = new Parse(Constant.KEYWORD_IHOME_SUCCESS, content);
				if ((parse.parse()) != null) {
					return Constant.SET_SUCC;
				}
				return Constant.SET_FAIL;
			} else if ("ihomeuserinfo".equals(type)) {
				parse = new Parse(Constant.KEYWORD_IHOME_USERINFO, content);
				String rs = "";
				if ((rs = parse.parse(new ParseCallback() {
					@Override
					public String excute(String rs) throws HttpException,
							IOException {
						rs = rs.substring(12);
						return rs;
					}
				})) != null)
					return rs;
				return Constant.SET_FAIL;
			} else {
				throw new Exception("解析错误");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
