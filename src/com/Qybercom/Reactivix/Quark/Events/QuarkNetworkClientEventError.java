package com.Qybercom.Reactivix.Quark.Events;

import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Utils.IReactivixEvent;

/**
 * Class QuarkNetworkClientEventError
 */
public class QuarkNetworkClientEventError implements IReactivixEvent {
	private QuarkNetworkClient _socket;
	private Exception _e;

	/**
	 * @param socket Transmitted QuarkNetworkClient actor
	 * @param e Occurred exception
	 */
	public QuarkNetworkClientEventError (QuarkNetworkClient socket, Exception e) {
		_socket = socket;
		_e = e;
	}

	/**
	 * @return QuarkNetworkClient
	 */
	public QuarkNetworkClient Socket () {
		return _socket;
	}

	/**
	 * @return Exception
	 */
	public Exception Exception () {
		return _e;
	}
}