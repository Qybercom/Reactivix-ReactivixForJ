package com.Qybercom.Reactivix.Quark.Events;

import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Utils.IReactivixEvent;

/**
 * Class QuarkNetworkClientEventConnect
 */
public class QuarkNetworkClientEventConnect implements IReactivixEvent {
	private QuarkNetworkClient _socket;

	/**
	 * @param socket Transmitted QuarkNetworkClient actor
	 */
	public QuarkNetworkClientEventConnect (QuarkNetworkClient socket) {
		_socket = socket;
	}

	/**
	 * @return QuarkNetworkClient
	 */
	public QuarkNetworkClient Socket () {
		return _socket;
	}
}