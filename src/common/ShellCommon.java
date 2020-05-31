package common;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class ShellCommon {
	protected Shell shell;
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void clearShell(Shell shell) {
		for (Control kid : shell.getChildren()) {
	          kid.dispose();
	    }
	}
}
