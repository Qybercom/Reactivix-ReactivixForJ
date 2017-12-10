package Examples.Quark.Stream.Services;

import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Quark.QuarkNetworkPacket;
import com.Qybercom.Reactivix.Quark.Stream.IQuarkNetworkStream;

import Examples.Quark.Stream.ExampleQuarkStream;
import Examples.Quark.Stream.DTO.MessageWelcomeDTO;

/**
 * Class IndexService
 */
public class IndexService implements IQuarkNetworkStream {
	/**
	 * @return String
	 */
	public String URL () {
		return "/";
	}

	/**
	 * @return IQuarkNetworkPacketData
	 */
	public IQuarkNetworkPacketData StreamResponseDTO () {
		return new IndexResponseDTO();
	}

	/**
	 * @param client Associated QuarkNetworkClient
	 * @param packet Ready for using DTO
	 */
	public void StreamResponse (QuarkNetworkClient client, QuarkNetworkPacket packet) {
		IndexResponseDTO dto = (IndexResponseDTO)packet.data;

		ExampleQuarkStream.Log("Service.Response./: " + dto.message);

		ExampleQuarkStream.Thread.External((Object) -> {
			ExampleQuarkStream.Log("[Main] Service.Response./: " + dto.message);

			return null;
		});

		client.Service("/test", new MessageWelcomeDTO("Hello from ReactivixForJ"));
	}

	/**
	 * @return IQuarkNetworkPacketData
	 */
	public IQuarkNetworkPacketData StreamEventDTO () {
		return null;
	}

	/**
	 * @param client Associated QuarkNetworkClient
	 * @param packet Ready for using DTO
	 */
	public void StreamEvent (QuarkNetworkClient client, QuarkNetworkPacket packet) {
		ExampleQuarkStream.Log("Service.Event./");
	}
}

/**
 * Class IndexResponseDTO
 */
class IndexResponseDTO implements IQuarkNetworkPacketData {
	public String message;
}