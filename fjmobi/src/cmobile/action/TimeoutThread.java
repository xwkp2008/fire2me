package cmobile.action;

/**
 * 本线程设置了一个超时时间 该线程开始运行后，经过指定超时时间， 该线呈会抛出一个未检查异常通知调用该线呈的程序超时
 * 在超时结束前可以调用该类的cancel方法取消计时
 * 
 * @author solonote
 */
public class TimeoutThread extends Thread {

	/**
	 * 计时器超时时间
	 */
	private long timeout;

	/**
	 * 计时是否被取消
	 */
	private boolean isCanceled = false;

	/**
	 * 当计时器超时时抛出的异常
	 */
	private TimeoutException timeoutException;
	
	private boolean isTimeout=false;

	/**
	 * 构造器
	 * 
	 * @param timeout
	 *            指定超时的时间
	 */
	public TimeoutThread(long timeout, TimeoutException timeoutErr) {
		super();
		this.timeout = timeout;
		this.timeoutException = timeoutErr;
		isTimeout=false;
		// 设置本线程为守护线程
		this.setDaemon(true);
	}

	/**
	 * 取消计时
	 */
	public void cancel() {
		isCanceled = true;
	}

	/**
	 * 启动超时计时器
	 */
	public void run() {
		try {
			Thread.sleep(timeout);
			if (!isCanceled){
				isTimeout=true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setTimeout(boolean isTimeout) {
		this.isTimeout = isTimeout;
	}

	public boolean isTimeout() {
		return isTimeout;
	}
}
