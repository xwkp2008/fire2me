package cmobile.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cmobile.action.ActionFactory;
import fjmobile.model.Contact;

public class OpFile {

    public static boolean conCurrGoing = false;// 是否有任务运行
    public static String conCurrEditor = "";// 当前请求的Editor
    public static String nowEditor = "";// 当前请求的Editor
    public static String actionStr = null;
    public static String queue = "0";
    public static Map res = null;
    public static Map act = null;
    protected transient static final Logger log = Logger.getLogger(ActionFactory.class);

    public static List<IContact> imp(String filepath) throws Exception {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new Exception("文件不存在！");
        }
        try {
            FileReader reader = new FileReader(filepath);
            BufferedReader buf = new BufferedReader(reader);
            String line = "";
            List<IContact> list = new ArrayList<IContact>();
            while ((line = buf.readLine()) != null) {
                String[] rs = line.split(",");
                if (rs != null) {
                    Contact mobile = null;
                    if (rs.length == 2) {
                        if (!rs[0].matches("\\d+")) {
                            throw new Exception("文件读取出错！");
                        }
                        mobile = new Contact(rs[0], rs[1]);
                    } else if (rs.length == 1) {
                        if (!rs[0].matches("\\d+")) {
                            throw new Exception("文件读取出错！");
                        }
                        mobile = new Contact(rs[0]);
                    }
                    list.add((IContact) mobile);
                }
            }
            return list;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("文件读取错误");
        }
    }
//	public static void exp(String filepath, Table table) throws Exception {
//		try {
//			FileWriter writer = new FileWriter(filepath);
//			BufferedWriter buf = new BufferedWriter(writer);
//			TableItem[] items = table.getItems();
//			for (TableItem item : items) {
//				String mobileno = item.getText(0);
//				String password = DBFactory.getFiled(mobileno, "password");
//				if (!item.getText(1).equals(password)) {
//					buf.write(item.getText(0) + "\t" + password + "\t"
//							+ item.getText(1));
//				} else {
//					buf.write(item.getText(0) + "\t" + item.getText(1));
//				}
//
//				buf.write(System.getProperty("line.separator"));
//			}
//			buf.close();
//			writer.close();
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			throw new Exception("文件导出失败");
//		}
//	}
//	public static void ExportFile(final SelectionEvent e, Table table) {
////		FileDialog fileDialog = new FileDialog(e.display.getActiveShell(),
////				SWT.SAVE);
////		String filepath = fileDialog.open();
////		if (filepath != null)
////			try {
////				OpFile.exp(filepath, table);
////				File file = new File(filepath);
////				if (!file.exists())
////					throw new Exception("生成文件失败");
////			} catch (Exception e1) {
////				MessageDialog.openInformation(e.display.getActiveShell(), "错误",
////						e1.getMessage());
////			}
//	}
//
//	public static void run(final List mobileList, IProgressMonitor monitor,
//			String type, Function func) {
//		int size = mobileList.size();
//		monitor.beginTask("刷新手机状态", size);
//		int i = 0;
//		for (Iterator it = mobileList.iterator(); it.hasNext();) {
//			monitor.worked(i);
//			if (monitor.isCanceled())
//				return;
//			String mobileno = it.next().toString();
//			i++;
//			monitor.subTask("当前执行" + mobileno + " " + i + "/" + size);
//			Timer timer = new Timer(true);
//			timer.schedule(new java.util.TimerTask() {
//				public void run() {
//				}
//			}, 0, 5 * 60 * 1000);
//			String password = DBFactory.getFiled(mobileno, "password");
//			// String status = ActionFactory
//			// .excute(mobileno, password, null, type);
//			// DBFactory.updateMobile(type, mobileno, status);
//		}
//		monitor.done();
//	}
//
//	public static void corruct(final List mobiles, final Map map,
//			final String action, final IEditorSite editor, final Function fuc) {
//		final StringBuffer restr = new StringBuffer();
//		String time = DBFactory.getProp(Constant.SYSTEM_TIMEOUT);
//		Job webJob = new Job("执行操作") {
//			@SuppressWarnings("restriction")
//			@Override
//			protected IStatus run(IProgressMonitor monitor) {
//				try {
//					if (conCurrGoing)
//						throw new NonRepeatableRequestException("另外一个请求正在运行");
//					Map res = new HashMap();
//					monitor.beginTask("执行操作", mobiles.size());
//					conCurrGoing = true;
//					conCurrEditor = editor.getId();
//
//					String actstr = DBFactory.getProp(action + "_act");
//					Map act = StringUtil.toMap(actstr);
//
//					res = ActionFactory.excute(mobiles, map, act, action,
//							monitor);
//					monitor.done();
//					restr.append("总共" + res.get("all"));
//					restr.append(" 成功" + res.get("succ"));
//					restr.append(" 失败" + res.get("error"));
//					restr.append(" 其中密码错误" + res.get("pwderror"));
//					restr.append(" 其他错误" + res.get("ignor"));
//					Display.getDefault().asyncExec(new Runnable() {
//						public void run() {
//							showMessageOnStatusbar2(restr.toString(), editor);
//							ConsoleFactory.print(editor.getRegisteredName()
//									+ ":[" + StringUtil.toDateStr(new Date())
//									+ "]" + restr.toString());
//							if (fuc != null)
//								fuc.exec();
//						}
//					});
//				} catch (final NonRepeatableRequestException e) {
//					Display.getDefault().asyncExec(new Runnable() {
//						public void run() {
//							MessageDialog.openError(editor.getShell(), "错误", e
//									.getMessage());
//						}
//					});
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					conCurrGoing = false;
//					conCurrEditor = "";
//				}
//
//				return Status.OK_STATUS;
//			}
//		};
//		webJob.setUser(false); // 是否需要弹出进度窗口
//		webJob.schedule();
//	}
//
//	public static void refresh(Map map, List mobileList, final String type,
//			IEditorSite editor, Function func) {
//		try {
//			if (mobileList == null || mobileList.size() == 0)
//				throw new Exception("没有选择操作的手机号！");
//			OpFile.corruct(mobileList, map, type, editor, func);
//		} catch (Exception e1) {
//			MessageDialog.openInformation(editor.getShell(), "错误", e1
//					.getMessage());
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public static void retain(List mobileList, IEditorSite editor) {
//		OpFile.actionStr = StringUtil.combineList(mobileList);
//		Map map = DBFactory.getPropMap("_retain");
//		nowEditor = editor.getId();
//		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
//			String action = (String) it.next();
//			String type = action.replaceAll("_retain", "");
//			String retain = (String) map.get(action);
//			if (retain != null && retain.length() > 0
//					&& mobileList.contains(action)) {
//				List mobilenos = new ArrayList();
//				if (retain != null) {
//					String[] strlist;
//					strlist = retain.split(",");
//					mobilenos = Arrays.asList(strlist);
//				}
//
//				if (mobilenos != null && mobilenos.size() > 0) {
//					String paramstr = DBFactory.getProp(type + "_map");
//					Map params = StringUtil.toMap(paramstr);
//
//					// if (!conCurrEditor.equals(nowEditor)) {
//					boolean anw = MessageDialog.openQuestion(editor.getShell(),
//							"是否继续?", "是否继续上次未完成操作" + mobilenos.size() + "["
//									+ mobilenos.get(0) + ".....]");
//					if (anw)
//						OpFile.refresh(params, mobilenos, type, editor,
//								new Function() {
//									@Override
//									public void exec() {
//										// setTable();
//									}
//								});
//					else
//						delExec();
//					// }
//				}
//			}
//		}
//	}
//
//	public static void saveExec() {
//		if (OpFile.actionStr == null || queue == null)
//			return;
//		String type = actionStr.replaceAll("_retain", "");
//		DBFactory.Sysprop(type + "_retain", queue);
//		DBFactory.Sysprop(type + "_map", StringUtil.toStr(res));
//		DBFactory.Sysprop(type + "_act", StringUtil.toStr(act));
//	}
//
//	public static void flag(Map map, Map actres, String action,
//			Queue<String> queue) {
//		OpFile.actionStr = action;
//		OpFile.queue = StringUtil.combineArray(queue.toArray(new String[queue
//				.size()]), ",");
//		OpFile.res = map;
//		OpFile.act = actres;
//	}
//
//	public static void delExec() {
//		List list = new ArrayList();
//		if (actionStr.indexOf(",") > 0) {
//			String[] temp = actionStr.split(",");
//			for (String str : temp) {
//				list.add(str);
//			}
//		} else {
//			list.add(actionStr);
//		}
//		for (Object o : list) {
//			String action = String.valueOf(o);
//			String type = action.replaceAll("_retain", "");
//			DBFactory.delProp(type + "_retain");
//			DBFactory.delProp(type + "_map");
//			DBFactory.delProp(type + "_act");
//		}
//	}
//
//	public static void showMessageOnStatusbar(String message, IEditorSite editor) {
//		// IStatusLineManager statusline = editor.getActionBars()
//		// .getStatusLineManager();
//		// statusline.setErrorMessage(editor.getShell().getImage(), message);
//	}
//
//	public static void showMessageOnStatusbar2(String message,
//			IEditorSite editor) {
//		WorkbenchWindow workbenchWindow = (WorkbenchWindow) Activator
//				.getDefault().getWorkbench().getActiveWorkbenchWindow();
//		IStatusLineManager statusline = workbenchWindow.getStatusLineManager();
//		statusline.setErrorMessage(editor.getShell().getImage(), message);
//	}
//
//	public static void corruct() {
//		Job webJob = new Job("获取信息") {
//			@Override
//			protected IStatus run(IProgressMonitor monitor) {
//				// TODO Auto-generated method stub
//				try {
//					monitor.beginTask("", 100);
//					for (int i = 0; i < 10; i++) {
//						Thread.sleep(5000);
//						monitor.worked(10);
//					}
//					monitor.done();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				return Status.OK_STATUS;
//			}
//		};
//		Job webJob2 = new Job("获取信息") {
//			@Override
//			protected IStatus run(IProgressMonitor monitor) {
//				// TODO Auto-generated method stub
//				try {
//					monitor.beginTask("", 100);
//					for (int i = 0; i < 10; i++) {
//						Thread.sleep(5000);
//						monitor.worked(10);
//					}
//					monitor.done();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				return Status.OK_STATUS;
//			}
//		};
//		webJob.setUser(true); // 是否需要弹出进度窗口
//		webJob.schedule();
//		// webJob2.setUser(true); // 是否需要弹出进度窗口
//		// webJob2.schedule();
//	}
}
