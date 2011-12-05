package fjmobile.action;

import org.eclipse.jface.action.Action;

public class CutContactAction extends Action
{
	private CopyContactAction copyAction;
	private DeleteAction removeAction;
	
	public CutContactAction(
		CopyContactAction copyAction,
		DeleteAction removeAction,
		String text) {
		
		super(text);
		this.copyAction = copyAction;
		this.removeAction = removeAction;
	}
	
	public void run(){
		copyAction.run();
		removeAction.run();
		
	}
}
