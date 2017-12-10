package com.Qybercom.Reactivix.Network.Events;

import com.Qybercom.Reactivix.Utils.IReactivixEvent;

import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;

/**
 * Class ReactivixNetworkSocketEventData
 */
public class ReactivixNetworkSocketEventData implements IReactivixEvent {
	private ReactivixNetworkSocket _socket;
	private String _data;

	/**
	 * @param socket Transmitted ReactivixNetworkSocket actor
	 * @param data Data received from socket
	 */
	public ReactivixNetworkSocketEventData (ReactivixNetworkSocket socket, String data) {
		_socket = socket;
		_data = data;
	}

	/**
	 * @return ReactivixNetworkSocket
	 */
	public ReactivixNetworkSocket Socket () {
		return _socket;
	}

	/**
	 * @return String
	 */
	public String Data () {
		return _data;
	}
}