package com.gear.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.junit.Test;

import cmobile.db.DBFactory;

public class dbtest {

	@Test
	public void db() {
		Connection conn = DBFactory.getConn();
		try {
			Statement st = conn.createStatement();
			//st.execute("insert into mobile(mobileno,password) values('13459148294','981619')");
			//st.execute("insert into mobile(mobileno,password) values('13459114304','174428')");
			ResultSet rs=st.executeQuery("select * from sysprop");
			//Map rs=DBFactory.getPropMap("_retain");
			while(rs.next()){
				System.out.println(rs.getString(0)+":"+rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
