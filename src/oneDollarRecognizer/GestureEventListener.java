package oneDollarRecognizer;

import java.util.EventListener;

public interface GestureEventListener extends EventListener {
	public void gesturePerformed(GestureEvent e);
}
