package Examples.Quark.Stream.Services;

import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Quark.QuarkNetworkPacket;
import com.Qybercom.Reactivix.Quark.Stream.IQuarkNetworkStream;

import Examples.Quark.Stream.ExampleQuarkStream;

/**
 * Class TestService
 */
public class TestService implements IQuarkNetworkStream {
	/**
	 * @return String
	 */
	public String URL () {
		return "/test";
	}

	/**
	 * @return IQuarkNetworkPacketData
	 */
	public IQuarkNetworkPacketData StreamResponseDTO () {
		return new TestResponseDTO();
	}

	/**
	 * @param client Associated QuarkNetworkClient
	 * @param packet Ready for using DTO
	 */
	public void StreamResponse (QuarkNetworkClient client, QuarkNetworkPacket packet) {
		TestResponseDTO dto = (TestResponseDTO)packet.data;

		ExampleQuarkStream.Log("Service.Response./test: " + dto.status);

		ExampleQuarkStream.Thread.External((Object) -> {
			ExampleQuarkStream.Log("[Main] Service.Response./test: " + dto.status);

			return null;
		});
	}

	/**
	 * @return IQuarkNetworkPacketData
	 */
	public IQuarkNetworkPacketData StreamEventDTO () {
		return new TestEventDTO();
	}

	/**
	 * @param client Associated QuarkNetworkClient
	 * @param packet Ready for using DTO
	 */
	public void StreamEvent (QuarkNetworkClient client, QuarkNetworkPacket packet) {
		TestEventDTO dto = (TestEventDTO)packet.data;

		ExampleQuarkStream.Log("Service.Event./test: " + dto.message);

		ExampleQuarkStream.Thread.External((Object) -> {
			ExampleQuarkStream.Log("[Main] Service.Event./test: " + dto.message);

			return null;
		});
	}
}

/**
 * Class TestResponseDTO
 */
class TestResponseDTO implements IQuarkNetworkPacketData {
	public int status;
}

/**
 * Class TestEventDTO
 */
class TestEventDTO implements IQuarkNetworkPacketData {
	public String message;
}