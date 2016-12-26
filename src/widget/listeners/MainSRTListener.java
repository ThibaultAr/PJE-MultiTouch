package widget.listeners;

import widget.MTComponent;
import widget.events.SRTEvent;
import widget.events.SRTEventListener;

public class MainSRTListener implements SRTEventListener {

	@Override
	public void gesturePerformed(SRTEvent ev) {
		((MTComponent) ev.getSource()).updatePosition(ev.getTranslation(), ev.getAngle(), ev.getScale());
	}
}
