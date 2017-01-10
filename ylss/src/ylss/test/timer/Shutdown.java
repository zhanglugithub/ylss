package ylss.test.timer;

import java.util.Timer;
import java.util.TimerTask;

public class Shutdown extends TimerTask {
	Timer timer = null;

	public Shutdown() {
		
	}

	public Shutdown(Timer mytimer) {

		timer = mytimer;
	}

	public void run() {
		System.out.println("结束");
		timer.cancel();// 使用这个方法退出任务
	}

}
