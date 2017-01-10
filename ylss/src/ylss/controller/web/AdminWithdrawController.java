package ylss.controller.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ylss.model.constant.databaseConstant.WithdrawStatus;
import ylss.model.table.User;
import ylss.model.table.Withdraw;
import ylss.service.web.AdminWithdrawService;
import ylss.utils.resultMap;

@Controller
@RequestMapping("admin")
public class AdminWithdrawController {

	@Autowired
	@Resource
	AdminWithdrawService adminWithdrawService;

	User aUser;

	Withdraw aWithdraw;
	HashMap<String, Object> result;

	@RequestMapping("/reviewWithdraw.do")
	public Object reviewWithdraw(ModelMap model, @RequestParam int withdrawId,
			@RequestParam String withdrawStatus) {

		if ((!withdrawStatus.equals(WithdrawStatus.unpassed))
				& (!withdrawStatus.equals(WithdrawStatus.paid))) { // 既非 审核 通过也
																	// 非 不通过
			result = resultMap.createResult(0, "处理的状态不对");
		} else {
			result = adminWithdrawService.reviewDoctorWithDraw(withdrawId,
					withdrawStatus);

		}
		model.addAttribute("result", result);
		model.addAttribute("msg", result.get("msg"));
		
		return "listWithdraw";
	}

	@RequestMapping("/listWithdraw.do")
	public Object listWithdraw(ModelMap model) {
		List<Withdraw> withdrawList = adminWithdrawService.listWithdraw();

		model.addAttribute("withdrawList", withdrawList);

		return "listWithdraw";
	}

	
	@RequestMapping("/alterDoctorAlipay.do")
	public Object alterDoctorAlipay(String phoneNo,String newAlipayNo,ModelMap model){
		
		HashMap<String, Object> result= adminWithdrawService.updateDoctorAlipay(phoneNo, newAlipayNo);
		model.addAttribute("msg", result.get("msg"));
		return "alterDoctorAlipay";
	}
}
