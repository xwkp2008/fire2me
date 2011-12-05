package fjmobile;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

public class Perspective implements IPerspectiveFactory {
	private static final String ID_PROGRESSVIEW = "org.eclipse.ui.views.ProgressView";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		ConsoleFactory cf = new ConsoleFactory();
		cf.openConsole();

		layout.setEditorAreaVisible(true);
		boolean showTitle = false;
		layout.addStandaloneView(View.ID, showTitle, IPageLayout.LEFT, 0.15f,
				editorArea);
		layout.addFastView(ID_PROGRESSVIEW);
		IFolderLayout up = layout.createFolder("up", IPageLayout.BOTTOM, 0.7f,
				editorArea);
		up.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		up.addView(IConsoleConstants.ID_CONSOLE_VIEW);
	}

}
