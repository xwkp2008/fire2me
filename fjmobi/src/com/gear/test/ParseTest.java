package com.gear.test;

import java.io.UnsupportedEncodingException;

import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
import org.junit.Test;

import cmobile.common.Constant;
import cmobile.common.Parse;
import cmobile.common.ParseFactory;

public class ParseTest {

	@Test
	public void testParse() {
		Parse parse = new Parse(Constant.KEYWORD_LOGINED,
				"<div class=\"login_xu\"><p>欢迎您：神州行客户</p><p>手机号：13459**9657</p></div>");
		try {
			System.out.println(parse.parse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String test="<strong>费用名称</strong>sdf";
		try {
			test = ParserUtils.trimTags(test, new TagNameFilter(""), false, true);
			System.out.println(test);
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//System.out.println(ParseFactory.parse("istingji", test));
		//System.out.println(ParseFactory.parse("yueqianfei", test));
		//System.out.println(ParseFactory.parse("shishihuafei", test));
		//System.out.println(ParseFactory.parse("yuejiezhangdan", test));
		//System.out.println(ParseFactory.parse("hujiaozhuanyi", test));
		//System.out.println(ParseFactory.parse("xiugaimima", test));
		//System.out.println(ParseFactory.parse("jiaofeilishi", test));
		//System.out.println(ParseFactory.parse("duanxin", test));
	}
}
