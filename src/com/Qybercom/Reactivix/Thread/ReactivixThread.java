package com.Qybercom.Reactivix.Thread;

import com.Qybercom.Reactivix.Utils.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ReactivixThread
 */
public class ReactivixThread implements Runnable {
	public static final int TICK = 10; // Milliseconds

	public int _tick = TICK;
	public int getTick () { return _tick; }
	public void setTick (int tick) { _tick = tick; }

	private IReactivixThread _context;
	private Thread _thread;
	private List<Action<IReactivixThread>> _tasksInternal;
	private List<Action<Object>> _tasksExternal;

	private Action<IReactivixThread> _startCallback;
	private Action<IReactivixThread> _stopCallback;
	private boolean _stopSignal;

	/**
	 * @param context The internal thread context
	 */
	public ReactivixThread (IReactivixThread context) {
		_context = context;
		_thread = new Thread(this);

		_tasksInternal = new ArrayList<>();
		_tasksExternal = new ArrayList<>();

		_stopSignal = false;
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	public void run () {
		if (_startCallback != null)
			_startCallback.apply(_context);

		_context.ReactivixThreadStart(this);

		boolean run = true;

		while (run) {
			while (_tasksInternal.size() > 0) {
				_tasksInternal.get(0).apply(_context);
				_tasksInternal.remove(0);
			}

			_context.ReactivixThreadPipe(this);

			if (_stopSignal) {
				run = false;

				if (_stopCallback != null)
					_stopCallback.apply(_context);

				_context.ReactivixThreadStop(this);
			}

			try {
				Thread.sleep(_tick);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Overloaded variant of Pipe() without context
	 */
	public void Pipe () {
		Pipe(null);
	}

	/**
	 * @param context The external thread context
	 */
	public void Pipe (Object context) {
		while (_tasksExternal.size() > 0) {
			_tasksExternal.get(0).apply(context);
			_tasksExternal.remove(0);
		}
	}

	/**
	 * Overloaded variant of Start() without callback
	 */
	public void Start () {
		Start(null);
	}

	/**
	 * @param start The callback with internal thread context
	 */
	public void Start (Action<IReactivixThread> start) {
		_stopSignal = false;
		_startCallback = start;
		_thread.start();
	}

	/**
	 * Overloaded variant of Stop() without callback
	 */
	public void Stop () {
		Stop(null);
	}

	/**
	 * @param stop The callback with internal thread context
	 */
	public void Stop (Action<IReactivixThread> stop) {
		_stopSignal = true;
		_stopCallback = stop;
	}

	/**
	 * @param task Callback which will be passed as signal to internal thread context
	 */
	public void Internal (Action<IReactivixThread> task) {
		_tasksInternal.add(task);
	}

	/**
	 * @param task Callback which will be passed as signal to external thread context
	 */
	public void External (Action<Object> task) {
		_tasksExternal.add(task);
	}
}