package cmobile.db;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cmobile.common.StringUtil;

public class DBFactory {
	static private Logger log = Logger.getLogger(DBFactory.class);
	private static Connection conn = null;

	public static Connection getConn() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver")
						.newInstance();
				try {
					conn = DriverManager
							.getConnection("jdbc:derby:FJMOBILEDB1;");// 连接数据库
				} catch (SQLException e) {
					conn = DriverManager
							.getConnection("jdbc:derby:FJMOBILEDB1;create=true;");// 连接数据库
					initDB(conn);
					log.info("初始化数据库");
				}
				conn.setAutoCommit(true);
			}
			return conn;
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static boolean inertMobileno(String mobileno, String password) {
		return doInsertMobileno(mobileno, password, null);
	}

	public static boolean doInsertMobileno(String mobileno, String password,
			String type) {
		Statement st = null;
		String db = type;
		if (type == null) {
			db = "mobile";
		}
		try {
			st = getConn().createStatement();
			ResultSet rs = st.executeQuery("select * from " + db
					+ " where mobileno='" + mobileno + "'");
			if (!rs.next())
				st.execute("insert into "+db+"(mobileno,password) values('"
						+ mobileno + "','" + password + "')");
			else
				st.execute("update " + db + " set password='" + password
						+ "' where mobileno='" + mobileno + "'");
			return true;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean delMobile(String[] mobiles) {
		return doDelMobile(mobiles, null);
	}

	private static boolean doDelMobile(String[] mobiles, String type) {
		Statement st = null;
		String db = type;
		if (type == null) {
			db = "mobile";
		}
		try {
			st = getConn().createStatement();
			String sql = "delete from " + db + " where mobileno in("
					+ StringUtil.combineArray(mobiles) + ")";
			st.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static boolean delMobile(List mobiles) {
		return delMobile((String[]) mobiles.toArray(new String[mobiles.size()]));
	}

	public static ResultSet queryAllMobile(String reg) {
		return doQueryAllMobile(reg, null);
	}

	public static ResultSet doQueryAllMobile(String reg, String type) {
		Statement st = null;
		String db = type;
		if (type == null) {
			db = "mobile";
		}
		try {
			st = getConn().createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("select * from " + db);
			if (reg != null) {
				sql.append(" where mobileno like '%" + reg + "%'");
			}
			sql.append(" order by ID");
			ResultSet rs = st.executeQuery(sql.toString());
			return rs;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
		}
		return null;
	}

	public static String getFiled(String mobileno, String filed) {
		return doGetFiled(mobileno, filed, null);
	}

	public static String doGetFiled(String mobileno, String filed, String type) {
		Statement st = null;
		String db = type;
		if (type == null) {
			db = "mobile";
		}
		try {
			st = getConn().createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("select * from " + db);
			sql.append(" where mobileno='" + mobileno + "'");
			sql.append(" order by ID");
			ResultSet rs = st.executeQuery(sql.toString());
			if (rs != null && rs.next()) {
				return rs.getString(filed);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
		}
		return null;
	}

	public static boolean clearAll(String filed) {
		Statement st = null;
		try {
			st = getConn().createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("update mobile set ");
			sql.append(filed + "=''");
			st.execute(sql.toString());
			return true;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean updateMobile(String filed, String mobileno,
			String value) {
		return doUpdateMobiile(filed, mobileno, value,null);
	}

	public static boolean doUpdateMobiile(String filed, String mobileno,
			String value, String type) {
		Statement st = null;
		String db = type;
		if (type == null) {
			db = "mobile";
		}
		try {
			st = getConn().createStatement();
			st.execute("update "+db+" set " + filed + "='" + value
					+ "' where mobileno='" + mobileno + "'");
			return true;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean Sysprop(String prop, String value) {
		Statement st = null;
		try {
			st = getConn().createStatement();

			ResultSet rs = st.executeQuery("select * from sysprop where prop='"
					+ prop + "'");
			if (rs == null || !rs.next()) {
				PreparedStatement ps = getConn().prepareStatement(
						"insert into sysprop values('" + prop + "',?)",
						Statement.RETURN_GENERATED_KEYS);
				Reader reader = new StringReader(value);
				ps.setClob(1, reader);
				ps.executeUpdate();
			} else {
				PreparedStatement ps = getConn().prepareStatement(
						"update sysprop set value=? where prop='" + prop + "'",
						Statement.RETURN_GENERATED_KEYS);
				Reader reader = new StringReader(value);
				ps.setClob(1, reader);
				ps.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static String getProp(String prop) {
		Statement st = null;
		try {
			st = getConn().createStatement();
			ResultSet rs = st.executeQuery("select * from sysprop where prop='"
					+ prop + "'");
			if (rs != null && rs.next()) {
				return getClob(rs.getClob("value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String getClob(Clob rs) throws SQLException, IOException {
		if (rs != null) {
			try {
				Reader reader = rs.getCharacterStream();
				int c = 0;
				StringBuffer value = new StringBuffer();
				while (reader != null && (c = reader.read()) > 0) {
					value.append((char) c);
				}
				return value.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static Map getPropMap(String prop) {
		Statement st = null;
		Map map = new HashMap();
		try {
			st = getConn().createStatement();
			ResultSet rs = st
					.executeQuery("select * from sysprop where prop like'%"
							+ prop + "%'");
			while (rs != null && rs.next()) {
				map.put(rs.getString("prop"), getClob(rs.getClob("value")));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static boolean delProp(String prop) {
		Statement st = null;
		try {
			st = getConn().createStatement();
			st.executeUpdate("delete from sysprop where prop='" + prop + "'");
			return true;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void initDB(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		st
				.execute("create table mobile (ID int generated by default as identity,mobileno VARCHAR(12) NOT NULL,password VARCHAR(10) NOT NULL,xiugaimima VARCHAR(255),tingjifuji VARCHAR(20),yueqianfei VARCHAR(255),yuejiezhangdan VARCHAR(2000),shishihuafei VARCHAR(512),jiaofeilishi VARCHAR(2000),hujiaozhuanyi VARCHAR(12),duanxin VARCHAR(64),shoujishangwang VARCHAR(64),moshangwang VARCHAR(64),wlan VARCHAR(64),ihome VARCHAR(64), primary key(ID))");// 建表
		st
				.execute("create table ihome (ID int generated by default as identity,mobileno VARCHAR(12) NOT NULL,password VARCHAR(10) NOT NULL,vpns VARCHAR(255),primary key(ID))");// 属性表
		st
				.execute("create table sysprop (prop VARCHAR(64) NOT NULL,value clob,primary key(prop))");// 属性表
		st.close();
	}
}
