package com.Qybercom.Reactivix.Quark.Stream;

import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Quark.QuarkNetworkPacket;

/**
 * Interface IQuarkNetworkStreamEvent
 */
public interface IQuarkNetworkStreamEvent extends IQuarkNetworkStreamGeneric {
	public IQuarkNetworkPacketData StreamEventDTO();
	public void StreamEvent(QuarkNetworkClient client, QuarkNetworkPacket packet);
}