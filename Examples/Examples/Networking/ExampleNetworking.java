package Examples.Networking;

import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;

import Examples.ExampleBehavior;

/**
 * Class ExampleNetworking
 */
public class ExampleNetworking extends ExampleBehavior {
	public static ReactivixThread Thread;

	/**
	 * @param args Terminal args
	 */
	public static void main (String[] args) {
		Log("ExampleNetworking.Init");

		Thread = new ReactivixThread(new ClientThread());
		Thread.Start();
		Thread.Internal((IReactivixThread thread) -> {
			Log("ExampleNetworking.Internal");

			return null;
		});

		while (run) {
			Thread.Pipe();
			_sleep();
		}
	}
}