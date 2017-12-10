package com.Qybercom.Reactivix.Quark.Events;

import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Utils.IReactivixEvent;

/**
 * Class QuarkNetworkClientEventData
 */
public class QuarkNetworkClientEventData implements IReactivixEvent {
	private QuarkNetworkClient _socket;
	private String _data;

	/**
	 * @param socket Transmitted QuarkNetworkClient actor
	 * @param data Data received from socket
	 */
	public QuarkNetworkClientEventData (QuarkNetworkClient socket, String data) {
		_socket = socket;
		_data = data;
	}

	/**
	 * @return QuarkNetworkClient
	 */
	public QuarkNetworkClient Socket () {
		return _socket;
	}

	/**
	 * @return String
	 */
	public String Data () {
		return _data;
	}
}