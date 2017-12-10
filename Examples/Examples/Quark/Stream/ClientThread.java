package Examples.Quark.Stream;

import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;
import com.Qybercom.Reactivix.Network.Transports.ReactivixNetworkTransportTCP;
import com.Qybercom.Reactivix.Quark.Events.*;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;

import Examples.Quark.Stream.Services.IndexService;
import Examples.Quark.Stream.Services.TestService;

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
		ExampleQuarkStream.Log("ClientThread.Start");

		Client.EventConnect().On((QuarkNetworkClientEventConnect e) -> {
			context.External((Object main) -> {
				ExampleQuarkStream.Log("Main.Client.Connect");

				return null;
			});

			return null;
		});

		Client.EventClose().On((QuarkNetworkClientEventClose e) -> {
			context.External((Object main) -> {
				ExampleQuarkStream.Log("Main.Client.Close");

				return null;
			});

			return null;
		});

		Client.EventError().On((QuarkNetworkClientEventError e) -> {
			context.External((Object main) -> {
				ExampleQuarkStream.Log("Main.Client.Error: " + e.Exception().getMessage());

				return null;
			});

			return null;
		});

		Client.Stream(new IndexService());
		Client.Stream(new TestService());

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
		ExampleQuarkStream.Log("ClientThread.Stop");

		//Main.run = false;
		Client.Close();
	}
}