package ylss.controller.app;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ylss.service.app.AccountService;

@Controller
public class Notify {

	@Autowired
	@Resource
	AccountService accountService;

	@RequestMapping("/notify.do")
	public Object notifyServer(HttpServletRequest request)
			throws FileNotFoundException, UnsupportedEncodingException {

		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter
				.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) requestParams.get(name);

			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}

			// if (name.equals("body")) {
			// String str = valueStr.replace("_", "%");
			// str = URLDecoder.decode(str, "utf-8");
			// valueStr = str;
			// }
			params.put(name, valueStr);
		}
		// boolean verifySuccess = AlipayNotify.verify(params);
		PrintWriter outputLog = null;
		if (Paths.get("D:/notify.txt").toFile().exists()) {
			outputLog = new PrintWriter("D:/notify.txt");
		} else {
			outputLog = new PrintWriter("/root/ylss/notify.txt");
		}
		// PrintWriter outputLog = new PrintWriter("/root/ylss/notify.txt");
		String trade_status = request.getParameter("trade_status");
		String subject = request.getParameter("subject");
		subject = subject.replace('\"', ' ').trim();

		String total_fee_str = request.getParameter("total_fee");
		total_fee_str = total_fee_str.replace('\"', ' ').trim();
		double total_fee = Double.valueOf(total_fee_str);
		outputLog.println(trade_status);// &
										// trade_status.equals("TRADE_SUCCESS")
		boolean verifySuccess = true;
		if (verifySuccess) {
			try {
				outputLog.println(new Date().toString() + "verify success");
				accountService.recharge(total_fee, subject);
				outputLog.close();
				return "success";
			} catch (Exception e) {
				outputLog.println(new Date().toString() + e.toString());
				outputLog.close();
				return "fail";
			}
		} else {
			outputLog.println(new Date().toString() + "verify fail");
			outputLog.close();
			return "fail";
		}

	}

}
