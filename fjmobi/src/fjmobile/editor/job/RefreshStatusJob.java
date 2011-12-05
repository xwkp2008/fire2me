package fjmobile.editor.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import cmobile.action.ActionFactory;
import cmobile.db.DBFactory;

public class RefreshStatusJob extends Job {
	IProgressMonitor monitor;
	private Table table = null;
	private String name = "";

	public RefreshStatusJob(String name, Table table) {
		super(name);
		this.table = table;
		this.name = name;
		setUser(true);
		setPriority(Job.LONG);

	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
		} catch (Exception e) {

		}
		return org.eclipse.core.runtime.Status.OK_STATUS;
	}

}
