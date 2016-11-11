package widget.events;

import java.awt.AWTEvent;

public class ChangedSideEvent extends AWTEvent {

	protected int cursorId;
	
	public ChangedSideEvent(Object source, int id, int cursorId) {
		super(source, id);
		this.cursorId = cursorId;
	}
	
	public int getCursorId() {
		return this.cursorId;
	}

}
