package ylss.test.timer;

import java.util.Timer;

public class TestTimer {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 1000);// 在1秒后执行此任务
		timer.schedule(new Shutdown(timer), 3000);// 在3秒后执行此任务
	}

}
