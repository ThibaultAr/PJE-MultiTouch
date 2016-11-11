package widget.events;

import java.util.EventListener;

public interface ChangedSideListener extends EventListener {
	
	public void changedSidePerformed(ChangedSideEvent e);
}
