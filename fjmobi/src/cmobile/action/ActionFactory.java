package cmobile.action;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

import cmobile.common.Constant;
import cmobile.common.OpFile;
import cmobile.common.StringUtil;
import cmobile.db.DBFactory;
import cmobile.http.HttpCore;
import fjmobile.model.ContactsManager;
import fjmobile.model.IhomeManager;
import org.apache.http.HttpException;

@SuppressWarnings("unchecked")
public class ActionFactory {
	protected transient static final Logger log = Logger
			.getLogger(ActionFactory.class);
	private static String actionFlag = null;

	public static Map excute(List mobilenos, Map map, Map act, String doAction,
			IProgressMonitor monitor) {
		Login login = new Login();
		int succ = 0;
		int error = 0;
		int ignor = 0;
		int pwderror = 0;
		int all = 0;

		all = StringUtil.cNum(act.get("all"));
		if (all <= 0)
			all = mobilenos.size();
		succ = StringUtil.cNum(act.get("succ"));
		error = StringUtil.cNum(act.get("error"));
		ignor = StringUtil.cNum(act.get("ignor"));
		pwderror = StringUtil.cNum(act.get("pwderror"));

		boolean haserror = false;
		int i = 0;
		if (act == null)
			act = new HashMap();
		String res = "";
		Queue<String> queue = new LinkedList<String>();
		if (mobilenos != null)
			queue.addAll(mobilenos);
		setCountInfo(all, succ, error, ignor, pwderror, act);
		flag(map, act, doAction, queue);
		while (!queue.isEmpty()) {
			String mobileno = queue.poll();
			if (monitor.isCanceled()) {
				OpFile.conCurrGoing = false;
				return act;

			}
			haserror = false;

			try {
				Map<String, String> params = new HashMap<String, String>();
				params.put("mobileno", mobileno);
				if (map!=null&&map.containsKey("ihome")) {
					actionFlag = "ihome";
					String password = DBFactory.doGetFiled(mobileno,
							"password", Constant.KEYWORD_IHOME);
					
					params.put("password", password);
					params.put("OPTION_DEAL_ID", map.get("opid").toString());
					params.put("INURE_TYPE", map.get("type").toString());
					boolean key = false;
					IhomeLogin ihomelogin = new IhomeLogin();
					key = ihomelogin.judge(params);
					if (!key)
						throw new HttpException((Constant
								.get(Constant.LOGIN_FAIL)));
					// 可以建家
					IhomeJianjia jianjia=new IhomeJianjia();
					if (jianjia.judge(map)) {
						IhomeJianjia imp = new IhomeJianjia();
						res = imp.action(map);
						updateIhome(res, mobileno, doAction);
						succ++;
					}
					// 建家完成后
					String vpns = DBFactory.doGetFiled(mobileno, "vpns",
							"ihome");
					if (doAction.equals("vpnimp")) {
						String[] vpnarr = StringUtil.split(vpns, ",");
						for (String vpn : vpnarr) {
							IhomeVPNAdd ihomeVPNAdd = new IhomeVPNAdd();
							Map vpnmap = new HashMap<String, String>();
							vpnmap.put("MOBILE", vpn);
							vpnmap
									.put("INURE_TYPE", map.get("type")
											.toString());
							ihomeVPNAdd.doAction(vpnmap);
						}

					}
				} else {
					String password = DBFactory.getFiled(mobileno, "password");
					params.put("password", password);
					boolean key = false;
					key = login.doJudge(params);
					if (!key)
						throw new HttpException((Constant
								.get(Constant.LOGIN_FAIL)));
					if (doAction.equals("yueqianfei")) {
						Yueqianfei yueqianfei = new Yueqianfei();
						res = yueqianfei.doAction(null);
						update(res, mobileno, doAction);
						succ++;
					} else {// 欠费不能操作
						if ("qianfei".equals(login.doAction(null)))
							throw new Exception("此号码已欠费");
						if (doAction.equals("fuji")) {
							Fuji fuji = new Fuji();
							if (fuji.doJudge(null)) {
								fuji.doAction(null);
								update("开机", mobileno, "tingjifuji");
								succ++;
							} else {
								ignor++;
							}
						} else if (doAction.equals("istingji")) {
							Tingji tingji = new Tingji();
							String rs = tingji.doJudge(null) ? "开机" : "停机保号";
							update(rs, mobileno, "tingjifuji");
							succ++;
						} else if (doAction.equals("tingji")) {
							Tingji tingji = new Tingji();
							if (tingji.doJudge(null)) {
								tingji.doAction(null);
								update("停机保号", mobileno, "tingjifuji");
								succ++;
							} else {
								ignor++;
							}
						} else {// 需要开机才能操作
							// 是否开机
							boolean isdo = false;
							Fuji fuji = new Fuji();
							if (fuji.doJudge(null)) {
								fuji.doAction(null);
								isdo = true;
							}
							if (doAction.equals("yuejiezhangdan")) {
								Yuejiezhangdan zhangdan = new Yuejiezhangdan();
								res = zhangdan.doAction(map);
								update(res, mobileno, doAction);
								succ++;
							} else if (doAction.equals("jiaofeilishi")) {
								Jiaofeilishi jiaofei = new Jiaofeilishi();
								res = jiaofei.doAction(map);
								update(res, mobileno, doAction);
								succ++;
							} else if (doAction.equals("shishihuafei")) {
								Shishihuafei shishihuafei = new Shishihuafei();
								res = shishihuafei.doAction(null);
								update(res, mobileno, doAction);
								succ++;
							} else if (doAction.equals("duanxin")) {
								Duanxin duanxin = new Duanxin();
								res = duanxin.doJudge(null) ? "开通" : "未开通";
								update(res, mobileno, doAction);
								succ++;
							} else if (doAction.equals("xiugaimima")) {
								map.put("oldpwd", password);
								Xiugaimima mima = new Xiugaimima();
								res = mima.doAction(map);
								if (Constant.SET_SUCC.equals(res)) {
									update(res, mobileno, doAction);
									update(map.get("password").toString(),
											mobileno, "password");
									succ++;
								} else {
									update("设置失败？密码不能过于简单", mobileno, doAction);
									ignor++;
								}
							} else if (doAction.equals("hujiaozhuanyi")) {
								Hujiaozhuanyi hujiao = new Hujiaozhuanyi();
								res = hujiao.doAction(map);
								update(res, mobileno, doAction);
								succ++;
							} else if (doAction.equals("hujiaozhuanyi_query")) {
								Hujiaozhuanyi hujiao = new Hujiaozhuanyi();
								String rs = hujiao.doJudge(null) ? "已开通"
										: "未开通";
								update(rs, mobileno, "hujiaozhuanyi");
								succ++;
							} else if (doAction.equals("shoujishangwang")) {
								ShouJiShangWang sjsw = new ShouJiShangWang();
								res = sjsw.doAction(map);
								update(res, mobileno, "shoujishangwang");
								succ++;
							} else if (doAction.equals("shoujishangwang_query")) {
								ShouJiShangWang sjsw = new ShouJiShangWang();
								String rs = sjsw.doJudge(null) ? "已开通" : "未开通";
								update(rs, mobileno, "shoujishangwang");
								succ++;
							} else if (doAction.equals("moshangwang")) {
								MoShangWang sjsw = new MoShangWang();
								res = sjsw.doAction(map);
								update(res, mobileno, "moshangwang");
								succ++;
							} else if (doAction.equals("moshangwang_query")) {
								MoShangWang sjsw = new MoShangWang();
								String rs = sjsw.doJudge(null) ? "已开通" : "未开通";
								update(rs, mobileno, "moshangwang");
								succ++;
							} else if (doAction.equals("wlan")) {
								WLAN sjsw = new WLAN();
								res = sjsw.doAction(map);
								update(res, mobileno, "wlan");
								succ++;
							} else if (doAction.equals("wlan_query")) {
								WLAN sjsw = new WLAN();
								String rs = sjsw.doJudge(null) ? "已开通" : "未开通";
								update(rs, mobileno, "wlan");
								succ++;
							}

							// 开机要记得关机
							if (isdo) {
								Tingji tingji = new Tingji();
								if (tingji.doJudge(null)) {
									tingji.doAction(null);
								}
							}
						}
					}
				}
			} catch (HttpException e) {
				if (e.getMessage() != null
						&& Constant.PASSWORD_ERROR.equals(e.getMessage())) {
					pwderror++;
					update(Constant.get(Constant.PASSWORD_ERROR), mobileno,
							doAction);
					error++;
				} else {
					// 发生致命的异常，可能是协议不对或者返回的内容有问题
					res = Constant.get(Constant.CONN_FAIL);
					update(res, mobileno, doAction);
					haserror = true;
					error++;
				}
			} catch (IOException e) {
				if (e instanceof UnknownHostException) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				// 发生网络异常
				res = Constant.get(Constant.CONN_FAIL);
				update(res, mobileno, doAction);
				haserror = true;
				error++;
			} catch (Exception e) {
				String temp = Constant.get2(e.getMessage());
				if (temp == null)
					temp = e.getMessage();
				update(temp, mobileno, doAction);
				error++;
			} finally {
				// 保留执行信息
				if (haserror) {
					queue.add(mobileno);
				} else {
					monitor.worked(1);
					i++;
				}
				monitor
						.setTaskName(mobileno + " " + i + "/"
								+ mobilenos.size());
				flag(map, act, doAction, queue);
				HttpCore.clear();
			}
			setCountInfo(all, succ, error, ignor, pwderror, act);
		}
		// 去除执行信息
		OpFile.delExec();
		return act;
	}

	private static void flag(Map map, Map act, String doAction,
			Queue<String> queue) {
		OpFile.flag(map, act, doAction, queue);
	}

	private static void setCountInfo(int all, int succ, int error, int ignor,
			int pwderror, Map act) {
		act.put("all", "" + all);
		act.put("succ", "" + succ);
		act.put("error", "" + error);
		act.put("ignor", "" + ignor);
		act.put("pwderror", "" + pwderror);
	}

	private static void update(String res, String mobileno, String type) {
		if (actionFlag==null) {
			if (type.equals("tingji") || type.equals("fuji")
					|| type.equals("istingji")) {
				type = "tingjifuji";
			}
			DBFactory.updateMobile(type, mobileno, res);
			ContactsManager.getManager().changeContact(mobileno, type);
		} else if (actionFlag.equals("ihome")) {
			updateIhome(res, mobileno, type);
		}
	}

	private static void updateIhome(String res, String mobileno, String type) {
		DBFactory.doUpdateMobiile(type, mobileno, res, "ihome");
		IhomeManager.getManager().changeContact(mobileno, type);
	}
}
