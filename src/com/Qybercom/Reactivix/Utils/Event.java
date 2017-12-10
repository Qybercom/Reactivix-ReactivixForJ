package com.Qybercom.Reactivix.Utils;

import java.util.*;

/**
 * Class Event
 */
public class Event {
	/**
	 * The register of assigned events
	 */
	private Map<String, List<Action>> _listeners;

	public Event () {
		_listeners = new HashMap<>();
	}

	public void On (String event, Action listener) {
		_listeners.computeIfAbsent(event, k -> new ArrayList<>());
		_listeners.get(event).add(listener);
	}

	public void Off (String event, Action listener) {
		_listeners.computeIfAbsent(event, k -> new ArrayList<>());
		_listeners.get(event).remove(listener);
	}

	public void Trigger (String event, Object args) {
		_listeners.computeIfAbsent(event, k -> new ArrayList<>());
		_listeners.get(event).forEach((Action listener) -> {
			listener.apply(args);
		});
	}
}