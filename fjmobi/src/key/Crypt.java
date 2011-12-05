package key;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {
	public static String getMac() {
		String mac = "";
		try {
			mac = MacReaderUtil.INSTANCE.getEncMac();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mac;
	}

	// 用来将字节转换成 16 进制表示的字符
	static char hexDigits[] = { '0', 'a', '2', 'b', '4', 'c', '6', 'd', '8',
			'e', '1', 'f', '3', 'i', '5', 'j' };

	/** */
	/**
	 * 对一段String生成MD5加密信息
	 * 
	 * @param message
	 *            要加密的String
	 * @return 生成的MD5信息
	 */
	public static String getMD5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(message.getBytes());
			return byteToHexString(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}

	public static void main(String[] args) {
		System.out.println(Crypt.getMac());
		System.out.println(Crypt.getMD5(Crypt.getMac()));
	}
}
