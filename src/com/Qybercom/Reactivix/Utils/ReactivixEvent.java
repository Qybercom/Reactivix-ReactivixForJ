package com.Qybercom.Reactivix.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ReactivixEvent
 *
 * https://stackoverflow.com/questions/9145019/list-of-objects-with-functions-how-to-call-those-functions
 * https://stackoverflow.com/questions/30274124/working-with-an-arraylist-of-functions-in-java-8
 * http://qaru.site/questions/2291/operator-overloading-in-java
 * https://habrahabr.ru/post/237043/
 * https://stackoverflow.com/questions/4579683/implement-dictionary-using-java
 * https://stackoverflow.com/questions/13543457/how-do-you-create-a-dictionary-in-java
 * https://github.com/Fylipp/easy-events
 * https://stackoverflow.com/questions/6270132/create-a-custom-event-in-java
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