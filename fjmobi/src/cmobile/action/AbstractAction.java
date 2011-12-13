package cmobile.action;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import cmobile.common.Constant;
import org.apache.http.HttpException;

public abstract class AbstractAction implements Action {

    public String doAction(final Map rs) throws HttpException, IOException {
        final StringBuffer key = new StringBuffer();
        TimeoutException timeout = new TimeoutException("超时");
        long timout = getTimeout();
        TimeoutThread t = new TimeoutThread(timout, timeout);
        t.start();
        key.append(action(rs));
        t.cancel();
        if (t.isTimeout()) {
            stopThread(t);
            throw new UnknownHostException("执行超时");
        }
        stopThread(t);
        return key.toString();
    }

    private long getTimeout() {
        int timout = 60;
        if (Constant.TIMEOUT != null && Constant.TIMEOUT.length() > 0) {
            timout = Integer.valueOf(Constant.TIMEOUT);
        }
        return timout * 1000;
    }

    public boolean doJudge(final Map params) throws HttpException, IOException {
        final StringBuffer key = new StringBuffer();
        TimeoutException timeout = new TimeoutException("超时");
        long timout = getTimeout();
        TimeoutThread t = new TimeoutThread(timout, timeout);
        t.start();
        key.append(judge(params));
        t.cancel();
        if (t.isTimeout()) {
            stopThread(t);
            throw new UnknownHostException("执行超时");
        }
        stopThread(t);
        return Boolean.valueOf(key.toString());
    }

    private void stopThread(TimeoutThread t) {
        t = null;
    }
}
