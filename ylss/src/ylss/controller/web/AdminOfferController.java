package ylss.controller.web;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ylss.model.table.Offer;
import ylss.model.table.User;
import ylss.service.web.AdminOfferService;

@Controller
@RequestMapping("admin")
public class AdminOfferController {

	@Autowired
	@Resource
	AdminOfferService adminOfferService;

	User aUser;

	Offer aOffer;
	HashMap<String, Object> result;

	// @RequestMapping("/addOffer.do")
	// public Object addOffer(Offer aOffer, ModelMap model) {
	//
	// HashMap<String, Object> addOffer = adminOfferService.addOffer(aOffer);
	// model.addAttribute("code", addOffer.get("code"));
	// model.addAttribute("msg", addOffer.get("msg"));
	//
	// return "offerRegistration";
	// }
	@ResponseBody
	@RequestMapping("/addOffer.do")
	public HashMap<String, Object> addOffer(Offer aOffer) {
		HashMap<String, Object> addOffer = adminOfferService.addOffer(aOffer);
		return addOffer;
	}

	@RequestMapping("/listApplyOffer.do")
	public Object listApplyOffer(@RequestParam int pageNo,
			@RequestParam int pageSize, ModelMap model) {

		model.addAttribute("applyOfferList",
				adminOfferService.listApplyOffer(pageNo, pageSize));
		model.addAttribute("totalNo", adminOfferService.getApplyOfferTotalNo());
		return "listApplyOffer";
	}
}
