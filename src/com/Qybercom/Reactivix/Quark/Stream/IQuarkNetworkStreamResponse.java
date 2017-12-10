package com.Qybercom.Reactivix.Quark.Stream;

import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Quark.QuarkNetworkPacket;

/**
 * Interface IQuarkNetworkStreamResponse
 */
public interface IQuarkNetworkStreamResponse extends IQuarkNetworkStreamGeneric {
	public IQuarkNetworkPacketData StreamResponseDTO();
	public void StreamResponse(QuarkNetworkClient client, QuarkNetworkPacket packet);
}