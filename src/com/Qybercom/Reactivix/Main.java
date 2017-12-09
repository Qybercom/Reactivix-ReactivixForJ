package com.Qybercom.Reactivix;

import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;
import com.Qybercom.Reactivix.Utils.Action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class Main {
	public static ReactivixThread Thread;

    public static void main1 (String[] args) {
    	System.out.println("Hello world");
    }

    public static void main (String[] args) {
		Log("Init.Main");

		Thread = new ReactivixThread(new ClientThread());

		Thread.Start((IReactivixThread context) -> {
			Log("Init.Thread");
			return null;
		});
		/*
		*/
		/*Thread.Internal(new Action<IReactivixThread>() {
			@Override
			public Void apply (IReactivixThread iReactivixThread) {
				return null;
			}
		});*/
		Thread.Internal((IReactivixThread thread) -> {
			Log("internal");
			return null;
		});

		boolean run = true;
		while (run)
		{
			Thread.Pipe();

			try {
				java.lang.Thread.sleep(10);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void Log (String message) {
    	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(Calendar.getInstance().getTime()) + "] [thread: " + java.lang.Thread.currentThread().getId() + "] " + message);
	}
}

class ClientThread implements IReactivixThread {
	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStart (ReactivixThread context) {
		Main.Log("ClientThread.Start");
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadPipe (ReactivixThread context) {
		Main.Log("ClientThread.Pipe");

		context.External((Object main) -> {
			Main.Log("Main.Stop");

			Main.Thread.Stop();
			return null;
		});
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStop (ReactivixThread context) {
		Main.Log("ClientThread.Stop");
	}
}