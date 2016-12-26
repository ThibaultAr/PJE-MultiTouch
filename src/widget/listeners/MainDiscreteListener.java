package widget.listeners;

import widget.MTComponent;
import widget.events.DiscreteEvent;
import widget.events.DiscreteEventListener;

public class MainDiscreteListener implements DiscreteEventListener {

	@Override
	public void gesturePerformed(DiscreteEvent ev) {
		((MTComponent) ev.getSource()).click();
	}
}
