package key;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 钟春林 2006-5-28
 */
public class MacReaderUtil {
	public static MacReaderUtil INSTANCE = new MacReaderUtil();
	private boolean blDebug = false;
	/**
	 * 
	 */
	private MacReaderUtil() {
		super();
		// TODO 自动生成构造函数存根
	}

	public String getEncMac() throws Exception {
		String mac = "";
		try {
			mac = this.getMac();
			mac = this.getMD5Digest(mac);
			return mac;
		} catch (Exception ex) {
			throw new Exception("取系统信息失败.");
		}
	}

	public Vector getAllEncMac() throws Exception {
		try {
			Vector v = getAllMac();
			int nSize = v.size();
			if (nSize <= 0)
				throw new Exception("查找对应系统信息失败.");
			Vector vEnc = new Vector();
			for (int i = 0; i < nSize; i++) {
				String str = (String) v.elementAt(i);
				str = this.getMD5Digest(str);
				vEnc.add(str);
			}
			return vEnc;
		} catch (Exception ex) {
			throw new Exception("取系统信息失败.");
		}
	}

	private Vector getAllMac() {
		String mac = "";
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows"))
			return getAllWindows();
		else if (os.startsWith("Linux"))
			return getAllLinux();
		return null;
	}

	public String getMac() {
		String mac = "";
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows"))
			return getWindows();
		else if (os.startsWith("Linux"))
			return getLinux();
		return mac;
	}

	private String getWindows() {
		return (String) getAllWindows().elementAt(0);
	}

	private Vector getAllWindows() {
		Vector vMac = new Vector();
		String[] cmd = new String[]{"ipconfig","-all"};
		String mac = null;
		try {
			String stdoutString = this.execCmd(cmd);
			java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(
					stdoutString, "\n");
			Vector vIps = this.getLocalHost();
			while (tokenizer.hasMoreTokens()) {
				String str = tokenizer.nextToken().trim();
				int macAddressPosition = str.indexOf(":");
				if (macAddressPosition <= 0)
					continue;

				String macAddressCandidate = str.substring(
						macAddressPosition + 1).trim();
				if (this.isMacAddress(macAddressCandidate, 1)) {
					vMac.add(macAddressCandidate);
					continue;
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return vMac;
	}

	private String getLinux() {
		return (String) getAllLinux().elementAt(0);
	}

	private Vector getAllLinux() {
		Vector vMac = new Vector();
		ClassLoader.getSystemClassLoader();
		String[] cmd = { "/bin/sh", "-c", "ifconfig|grep -o '\\<[0-9a-f]\\{2\\}:[0-9a-f]\\{2\\}:[0-9a-f]\\{2\\}:[0-9a-f]\\{2\\}:[0-9a-f]\\{2\\}:[0-9a-f]\\{2\\}'" };
		try {
			String stdoutString = this.execCmd(cmd);
			Vector vIps = this.getLocalHost();
			java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(
					stdoutString, "\n");

			while (tokenizer.hasMoreTokens()) {
				String macAddressCandidate=tokenizer.nextToken().trim();
				if (this.isMacAddress(macAddressCandidate, 2)) {
					vMac.add(macAddressCandidate);
					continue;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return vMac;
	}

	private String execCmd(String[] command) throws IOException {
		Process p = Runtime.getRuntime().exec(command);
		InputStreamReader stdoutStream = new InputStreamReader(p
				.getInputStream());
		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		return outputText;
	}

	private Vector getLocalHost() throws ParseException {
		Vector vIps = new Vector();
		try {
			InetAddress host = InetAddress.getLocalHost();
			InetAddress[] allIps = InetAddress.getAllByName(host.getHostName());
			int nLen = allIps.length;
			for (int i = 0; i < nLen; i++) {
				String ip = allIps[i].getHostAddress();
				vIps.add(ip);
			}
			if (vIps.size() <= 0) {
				String ip = java.net.InetAddress.getLocalHost()
						.getHostAddress();
				vIps.add(ip);
			}
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		}
		return vIps;
	}

	public boolean isMacAddress(String macAddressCandidate, int type) {
		String pattern = "";
		String pattern1 = "[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}";// win
		String pattern2 = "[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}";// linux
		if (type == 2)
			pattern = pattern2;
		else
			pattern = pattern1;
		Pattern macPattern = Pattern.compile(pattern);
		Matcher m = macPattern.matcher(macAddressCandidate);
		return m.matches();
	}

	public Vector getMacAddress(String macAddressCandidate, int type) {
		Vector addrs = new Vector();
		String pattern = "";
		String pattern1 = "[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}";// win
		String pattern2 = "[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}";// linux
		if (type == 2)
			pattern = pattern2;
		else
			pattern = pattern1;
		Pattern macPattern = Pattern.compile(pattern);
		Matcher m = macPattern.matcher(macAddressCandidate);
		for (int i = 0; i < m.groupCount(); i++) {
			addrs.add(m.group(i));
		}
		return addrs;
	}

	private String getMD5Digest(String msg) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(msg.getBytes());
			byte[] val = md5.digest();

			String s = toPlusHex(val);
			return s;
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	private String toPlusHex(byte[] val) {
		BigInteger bi = new BigInteger(val);
		bi = bi.abs();
		// Format to hexadecimal
		String s = bi.toString(16);
		if (s.length() % 2 != 0) {
			// Pad with 0
			s = "0" + s;
		}
		return s;
	}

	public static void main(String[] arg) {
		MacReaderUtil util = new MacReaderUtil();
		try {
			Vector v = util.getAllEncMac();
			for (int i = 0; i < v.size(); i++) {
				System.out.println("mac():" + v.elementAt(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// boolean bl = util.isMacAddress("00:13:20:96:BD:FA", 2);
		// System.out.println(bl);
		// System.out.println(util.getEncMac());
	}
}