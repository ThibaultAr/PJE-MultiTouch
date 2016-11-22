package widget.events;

import java.util.EventListener;

public interface DiscreteEventListener extends EventListener {
	public void gesturePerformed(DiscreteEvent ev);
}
