/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package cmobile.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {

    private static Context context;

    private Context() {
        PROGRESS_MAP = new ConcurrentHashMap<String, String>();
        TASK_LIST = new ArrayList<Task>();
        SYSTEM = new ConcurrentHashMap<String, String>();
        PHONE_LIST = new ArrayList<IContact>();
    }

    public static Context getContext() {
        if (context == null) {
            context = new Context();
        }
        return context;
    }

    public String printALL() {
        StringBuffer sb = new StringBuffer();
        for (IContact contact : PHONE_LIST) {
            sb.append(contact.getMobileno());
            sb.append(" ");
            sb.append(contact.getPassword());
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    class Task {

        public Task(String name, String time, String status, String method) {
            this.name = name;
            this.time = time;
            this.status = status;
            this.method = method;
        }
        String name;
        String time;
        String status;
        String method;
    }
    public Map<String, String> PROGRESS_MAP;
    public List<Task> TASK_LIST;
    public List<IContact> PHONE_LIST;
    public Map<String, String> SYSTEM;

    public void setProp(String name, String value) {
        SYSTEM.put(name, value);
    }

    public void getProp(String name) {
        SYSTEM.get(name);
    }

    public void addTask(final String name, final String time, final String status, final String method) {
        TASK_LIST.add(new Task(name, time, status, method));
    }

    public List<IContact> getPHONE_LIST() {
        return PHONE_LIST;
    }

    public void setPHONE_LIST(List<IContact> PHONE_LIST) {
        this.PHONE_LIST = PHONE_LIST;
    }

    public Map<String, String> getPROGRESS_MAP() {
        return PROGRESS_MAP;
    }

    public void setPROGRESS_MAP(Map<String, String> PROGRESS_MAP) {
        this.PROGRESS_MAP = PROGRESS_MAP;
    }

    public Map<String, String> getSYSTEM() {
        return SYSTEM;
    }

    public void setSYSTEM(Map<String, String> SYSTEM) {
        this.SYSTEM = SYSTEM;
    }

    public List<Task> getTASK_LIST() {
        return TASK_LIST;
    }

    public void setTASK_LIST(List<Task> TASK_LIST) {
        this.TASK_LIST = TASK_LIST;
    }
}
