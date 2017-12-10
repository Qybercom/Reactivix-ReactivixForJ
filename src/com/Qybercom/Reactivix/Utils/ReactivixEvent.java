package com.Qybercom.Reactivix.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ReactivixEvent
 */
public class ReactivixEvent<TEventArg extends IReactivixEvent> {
	private List<Action<TEventArg>> _listeners;

	/**
	 * Default constructor of ReactivixEvent
	 */
	public ReactivixEvent () {
		_listeners = new ArrayList<>();
	}

	/**
	 * @param callback Event listener
	 */
	public void On (Action<TEventArg> callback) {
		_listeners.add(callback);
	}

	/**
	 * @param callback Event Listener
	 */
	public void Off (Action<TEventArg> callback) {
		_listeners.remove(callback);
	}

	/**
	 * @param arg Event data
	 */
	public void Trigger (TEventArg arg) {
		_listeners.forEach((Action<TEventArg> callback) -> callback.apply(arg));
	}
}