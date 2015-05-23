package com.ralitski.art.core.event;

public interface Listener {
	/**
	 * If this Listener should be passed events.
	 * @return If the Listener is active and should receive events.
	 */
	boolean isActive();
}
