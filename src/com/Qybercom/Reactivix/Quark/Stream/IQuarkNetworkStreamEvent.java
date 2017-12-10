package com.Qybercom.Reactivix.Quark.Stream;

import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Quark.QuarkNetworkPacket;

/**
 * Interface IQuarkNetworkStreamEvent
 */
public interface IQuarkNetworkStreamEvent extends IQuarkNetworkStreamGeneric {
	/**
	 * @return IQuarkNetworkPacketData
	 */
	IQuarkNetworkPacketData StreamEventDTO();

	/**
	 * @param client Associated QuarkNetworkClient
	 * @param packet Ready for using DTO
	 */
	void StreamEvent(QuarkNetworkClient client, QuarkNetworkPacket packet);
}