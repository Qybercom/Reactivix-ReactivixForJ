package com.Qybercom.Reactivix.Network.Events;

import com.Qybercom.Reactivix.Utils.IReactivixEvent;

import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;

/**
 * Class ReactivixNetworkSocketEventConnect
 */
public class ReactivixNetworkSocketEventConnect implements IReactivixEvent {
	private ReactivixNetworkSocket _socket;

	/**
	 * @param socket Transmitted ReactivixNetworkSocket actor
	 */
	public ReactivixNetworkSocketEventConnect (ReactivixNetworkSocket socket) {
		_socket = socket;
	}

	/**
	 * @return ReactivixNetworkSocket
	 */
	public ReactivixNetworkSocket Socket () {
		return _socket;
	}
}