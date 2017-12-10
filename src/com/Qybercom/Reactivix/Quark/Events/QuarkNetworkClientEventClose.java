package com.Qybercom.Reactivix.Quark.Events;

import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Utils.IReactivixEvent;

/**
 * Class QuarkNetworkClientEventClose
 */
public class QuarkNetworkClientEventClose implements IReactivixEvent {
	private QuarkNetworkClient _socket;

	/**
	 * @param socket Transmitted QuarkNetworkClient actor
	 */
	public QuarkNetworkClientEventClose (QuarkNetworkClient socket) {
		_socket = socket;
	}

	/**
	 * @return QuarkNetworkClient
	 */
	public QuarkNetworkClient Socket () {
		return _socket;
	}
}