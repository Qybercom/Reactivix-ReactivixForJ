package com.Qybercom.Reactivix.Network.Events;

import com.Qybercom.Reactivix.Utils.IReactivixEvent;

import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;

/**
 * Class ReactivixNetworkSocketEventClose
 */
public class ReactivixNetworkSocketEventClose implements IReactivixEvent {
	private ReactivixNetworkSocket _socket;

	/**
	 * @param socket Transmitted ReactivixNetworkSocket actor
	 */
	public ReactivixNetworkSocketEventClose (ReactivixNetworkSocket socket) {
		_socket = socket;
	}

	/**
	 * @return ReactivixNetworkSocket
	 */
	public ReactivixNetworkSocket Socket () {
		return _socket;
	}
}