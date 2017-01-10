package ylss.controller.filter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.autoBackup.util.BackupSql;
import cn.autoBackup.util.DeleteLog;
import cn.autoBackup.util.MysqlBean;

public class SystemSelfFilter implements ServletContextListener {
	private MyThread myThread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (myThread != null && myThread.isInterrupted()) {
			myThread.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String str = null;
		if (str == null && myThread == null) {
			myThread = new MyThread();
			myThread.start();
		}
	}

	class MyThread extends Thread {
		private Date date = new Date();
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		private String Name = sdf.format(date);

		// private String host = "127.0.0.1";
		private String host = "123.56.115.198";
		private String port = "3306";
		private String database = "ylss";
		private String savePathLinux = "/root/ylss/mysql_back_date/";
		private String savePathWindows = "D:\\ylss\\";
		// 文件名，必须以此文件名生成规则，否则无法自动删除
		private String fileName = "ylss_" + Name + ".sql";
		private String username = "root";
		private String password = "HhylA7215208BliangyuqiaoZ0413";

		private String mailHost = "smtp-mail.outlook.com";
		private String mailPort = "587";
		private String mailName = "luojianzhong1997@outlook.com";
		private String mailpw = "adminljz123.";
		private String toAddress = "2946034248@qq.com";
		private String mSubString = "医来伸手数据库备份";
		private String content = "医来伸手数据库备份,请妥善保管以防丢失";

		@Override
		public void run() {
			while (!this.isInterrupted()) {
				try {
					MysqlBean info = new MysqlBean(host, port, database,
							savePathLinux, savePathWindows, fileName, username,
							password);
					BackupSql.backupSql(info);
					// 只用于outlook邮箱，其他邮箱暂未测试,不适用于qq邮箱
					BackupSql.mailInfo(mailHost, mailPort, mailName, mailpw,
							mailName, toAddress, mSubString, content);
					boolean b = BackupSql.backup();
					if (b) {
						System.out.println("备份成功");
					} else {
						System.out.println("备份失败，请检查");
					}
					File filedir = new File(savePathLinux);
					String dir = "";
					if (filedir.exists()) {
						dir = savePathLinux;
					} else {
						dir = savePathWindows;
					}
					// 自动删除超过一定天数制定文件夹下的sql数据备份，文件名必须是ylss_yyyy_MM_dd.sql为规则的文件
					DeleteLog.delLogs(7, dir);
					Thread.sleep((long)1000 * 60 * 60 * 24);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
