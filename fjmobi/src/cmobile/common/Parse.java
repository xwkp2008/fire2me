package cmobile.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cmobile.http.ParseCallback;

public class Parse implements ParseHtml {
	String keyword="";
	String content="";
	
	public Parse(String keyword,String content) {
		this.keyword=keyword;
		this.content=content;
	}
	
	public String parse() throws Exception{
		if(keyword!=null&&content!=null){
			Pattern p=Pattern.compile(keyword,Pattern.DOTALL);
			Matcher m = p.matcher(content.trim());
			if(m.find()){
				return m.group(0);
			}
		}
		return null;
	}
	
	public String parse(ParseCallback callback) throws Exception{
		if(keyword!=null&&content!=null){
			Pattern p=Pattern.compile(keyword,Pattern.DOTALL);
			Matcher m = p.matcher(content.trim());
			if(m.find()){
				String rs = m.group(0);
				String temp=callback.excute(rs);
				return temp;
			}
		}
		return null;
	}
	
}
