package fjmobile;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/** */
/**
 * 描述:样式显示控制台视图
 * */
public class ConsoleFactory implements IConsoleFactory {

	private static MessageConsole console = new MessageConsole("运行日志", null);

	/** */
	/**
	 * 描述:打开控制台
	 * */
	public void openConsole() {
		showConsole();
	}

	/** */
	/**
	 * 描述:显示控制台
	 * */
	public static void showConsole() {
		try {
			if (console != null) {
				// 得到默认控制台管理器
				IConsoleManager manager = ConsolePlugin.getDefault()
						.getConsoleManager();
				// 得到所有的控制台实例
				IConsole[] existing = manager.getConsoles();
				boolean exists = false;
				// 新创建的MessageConsole实例不存在就加入到控制台管理器，并显示出来
				for (int i = 0; i < existing.length; i++) {
					if (console == existing[i])
						exists = true;
				}
				if (!exists) {
					manager.addConsoles(new IConsole[] { console });
				}
				manager.showConsoleView(console);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * 描述:关闭控制台
	 * */
	public static void closeConsole() {
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		if (console != null) {
			manager.removeConsoles(new IConsole[] { console });
		}
	}

	public static MessageConsole getConsole() {
		return console;
	}

	public static void print(String message) {
		MessageConsoleStream stream = console.newMessageStream();
		stream.println(message);
	}

}
