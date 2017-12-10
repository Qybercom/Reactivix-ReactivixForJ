package Examples.Quark.Basic;

import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;
import com.Qybercom.Reactivix.Network.Transports.ReactivixNetworkTransportTCP;
import com.Qybercom.Reactivix.Quark.Events.*;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Quark.QuarkNetworkPacket;

import Examples.Quark.Basic.DTO.*;

/**
 * Class ClientThread
 */
class ClientThread implements IReactivixThread {
	public QuarkNetworkClient Client;

	/**
	 * Default constructor of ClientThread
	 */
	public ClientThread () {
		Client = new QuarkNetworkClient(new ReactivixNetworkTransportTCP(), "127.0.0.1", 8500);
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStart (ReactivixThread context) {
		ExampleQuarkBasic.Log("ClientThread.Start");

		Client.EventConnect().On((QuarkNetworkClientEventConnect e) -> {
			context.External((Object main) -> {
				ExampleQuarkBasic.Log("Main.Client.Connect");

				return null;
			});

			return null;
		});

		Client.EventClose().On((QuarkNetworkClientEventClose e) -> {
			context.External((Object main) -> {
				ExampleQuarkBasic.Log("Main.Client.Close");

				return null;
			});

			return null;
		});

		Client.EventError().On((QuarkNetworkClientEventError e) -> {
			context.External((Object main) -> {
				ExampleQuarkBasic.Log("Main.Client.Error: " + e.Exception().getMessage());

				return null;
			});

			return null;
		});

		Client.Response(new MessageWelcomeDTO(), "/", (QuarkNetworkPacket packet) -> {
			MessageWelcomeDTO dto = (MessageWelcomeDTO)packet.data;

			context.External((Object main) -> {
				ExampleQuarkBasic.Log("Main.Client.Response./: " + dto.message);

				return null;
			});

			Client.Service("/test", new MessageWelcomeDTO("Hello from ReactivixForJ"));

			return null;
		});

		Client.Response(new TestResponseDTO(), "/test", (QuarkNetworkPacket packet) -> {
			TestResponseDTO dto = (TestResponseDTO)packet.data;

			context.External((Object main) -> {
				ExampleQuarkBasic.Log("Main.Client.Response./test: " + dto.status);

				return null;
			});

			return null;
		});

		Client.Event(new TestEventDTO(), "/test", (QuarkNetworkPacket packet) -> {
			context.External((Object main) -> {
				TestEventDTO dto = (TestEventDTO)packet.data;

				ExampleQuarkBasic.Log("Main.Client.Event./test: " + dto.message);

				return null;
			});

			return null;
		});

		Client.Connect();
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadPipe (ReactivixThread context) {
		Client.Pipe();
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStop (ReactivixThread context) {
		ExampleQuarkBasic.Log("ClientThread.Stop");

		//Main.run = false;
		Client.Close();
	}
}
