package com.Qybercom.Reactivix.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ReactivixEvent
 */
public class ReactivixEvent<TEventArg extends IReactivixEvent> {
	private List<Action<TEventArg>> _listeners;

	public ReactivixEvent () {
		_listeners = new ArrayList<>();
	}

	public void On (Action<TEventArg> callback) {
		_listeners.add(callback);
	}

	public void Off (Action<TEventArg> callback) {
		_listeners.remove(callback);
	}

	public void Trigger (TEventArg arg) {
		_listeners.forEach((Action<TEventArg> callback) -> callback.apply(arg));
	}
}