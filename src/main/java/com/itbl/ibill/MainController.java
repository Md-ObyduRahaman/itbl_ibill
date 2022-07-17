package com.itbl.ibill;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

	@Resource
	MainDao mainDao;

	@GetMapping("/")
	public String Login(Model model) {
		LoginModel loginModel = new LoginModel();
		model.addAttribute("loginModel", loginModel);
		return "login";
	}

	@PostMapping("/")
	public String Login1(HttpServletResponse response, Model model, LoginModel user) {
		LoginModel user1 = mainDao.getLogin(user.username, user.password);

//		System.out.println(user);
		if (user1 != null) {
			if (user1.username.equals(user.username) && user1.password.equals(user.password)) {
				Cookie cookie1 = new Cookie("user_name", user1.username);
				Cookie cookie2 = new Cookie("ROLE_ID", user1.role);
				response.addCookie(cookie1);
				response.addCookie(cookie2);
				return "redirect:/mrs_entry";
			} else {
				return "redirect:/";
			}

		} else {

			LoginModel login = new LoginModel();
			model.addAttribute("login", login);

			String msg = "User Id Password Missmatch! Login Failed";
			model.addAttribute("msg", msg);
			return "login";
		}
	}

	@GetMapping("/logout")
	public String LogOut(HttpServletResponse response, Model model) {
		LoginModel login = new LoginModel();
		model.addAttribute("login", login);
		Cookie cookie = new Cookie("user_name", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		Cookie cookie1 = new Cookie("ROLE_ID", null);
		cookie1.setMaxAge(0);
		response.addCookie(cookie1);
		return "redirect:/";
	}

	@GetMapping("/mrs_entry")
	public String mrs(Model model, @CookieValue(value = "user_name", defaultValue = "") String user_name) {

		if (user_name.equals("")) {
			return "redirect:/";
		}

		ArrayList<WalkSeqModel> walkSeqModels = new ArrayList<WalkSeqModel>();
		walkSeqModels.add(new WalkSeqModel("=", "="));
		walkSeqModels.add(new WalkSeqModel(">=", ">="));
		walkSeqModels.add(new WalkSeqModel("<=", "<="));
		model.addAttribute("walkSeqModels", walkSeqModels);

		ArrayList<BillCycleModel> billCycleModels = new ArrayList<BillCycleModel>();
		billCycleModels = mainDao.billCycle(user_name);
		model.addAttribute("billCycleModels", billCycleModels);
		ArrayList<LocationModel> locationModels = new ArrayList<LocationModel>();
		locationModels = mainDao.location(user_name);
		model.addAttribute("locationModels", locationModels);
		ArrayList<BillGroupModel> billGroupModels = new ArrayList<BillGroupModel>();
		billGroupModels = mainDao.billgroup(user_name);
		model.addAttribute("billGroupModels", billGroupModels);
		MRSEntryFormModel entryFormModel = new MRSEntryFormModel();
		model.addAttribute("entryFormModel", entryFormModel);
		model.addAttribute("flag", "0");
		model.addAttribute("saveFlag", "0");
		return "mrs_entry";
	}

	@PostMapping("/mrs_entry")
	public String mrs1(HttpServletResponse response, Model model, MRSEntryFormModel entryFormModel,
			@CookieValue(value = "user_name", defaultValue = "") String user_name) {
		ArrayList<WalkSeqModel> walkSeqModels = new ArrayList<WalkSeqModel>();
		walkSeqModels.add(new WalkSeqModel("=", "="));
		walkSeqModels.add(new WalkSeqModel(">=", ">="));
		walkSeqModels.add(new WalkSeqModel("<=", "<="));
		model.addAttribute("walkSeqModels", walkSeqModels);
		ArrayList<BillCycleModel> billCycleModels = new ArrayList<BillCycleModel>();
		billCycleModels = mainDao.billCycle(user_name);
		model.addAttribute("billCycleModels", billCycleModels);
		ArrayList<LocationModel> locationModels = new ArrayList<LocationModel>();
		locationModels = mainDao.location(user_name);
		model.addAttribute("locationModels", locationModels);
		ArrayList<BillGroupModel> billGroupModels = new ArrayList<BillGroupModel>();
		billGroupModels = mainDao.billgroup(user_name);
		model.addAttribute("billGroupModels", billGroupModels);
		model.addAttribute("entryFormModel", entryFormModel);
		ArrayList<MRSEntryModel> mrsEntryModels = new ArrayList<MRSEntryModel>();
		mrsEntryModels = mainDao.funMrsEntry(entryFormModel.billcycle, entryFormModel.location, entryFormModel.billgrp,
				entryFormModel.book, entryFormModel.walkseq, user_name, entryFormModel.wstype);
		model.addAttribute("mrsEntryModels", mrsEntryModels);
		model.addAttribute("boook", entryFormModel.book);
		model.addAttribute("flag", "1");
		model.addAttribute("saveFlag", "0");
		Cookie cookie = new Cookie("billcycle", entryFormModel.billcycle);
		Cookie cookie1 = new Cookie("location", entryFormModel.location);
		Cookie cookie2 = new Cookie("billgrp", entryFormModel.billgrp);
		Cookie cookie3 = new Cookie("book", entryFormModel.book);
		Cookie cookie4 = new Cookie("walkseq", String.valueOf(entryFormModel.walkseq));
		Cookie cookie5 = new Cookie("wstype", entryFormModel.wstype);
		response.addCookie(cookie);
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		response.addCookie(cookie3);
		response.addCookie(cookie4);
		response.addCookie(cookie5);
		return "mrs_entry";
	}

	@PostMapping("/updatemrs")
	public String TableData(MRSEntryModel mrsEntryModel, RedirectAttributes redirectAttributes,
			@CookieValue(value = "user_name", defaultValue = "") String user_name) {
		ArrayList<String> PRE_READING, ADV, PRE_OFPK_RDNG, OFPK_ADV, PRE_PK_RDNG, PK_ADV, MTR_COND;
		ArrayList<String> CUST_ID = new ArrayList<String>(Arrays.asList(mrsEntryModel.CUST_ID.split(",")));
		ArrayList<String> BILL_CYCLE_CODE = new ArrayList<String>(
				Arrays.asList(mrsEntryModel.BILL_CYCLE_CODE.split(",")));
		PRE_READING = new ArrayList<String>(Arrays.asList(mrsEntryModel.PRE_READING.split(",")));
		ADV = new ArrayList<String>(Arrays.asList(mrsEntryModel.ADV.split(",")));
		PRE_OFPK_RDNG = new ArrayList<String>(Arrays.asList(mrsEntryModel.PRE_OFPK_RDNG.split(",")));
		OFPK_ADV = new ArrayList<String>(Arrays.asList(mrsEntryModel.OFPK_ADV.split(",")));
		PRE_PK_RDNG = new ArrayList<String>(Arrays.asList(mrsEntryModel.PRE_PK_RDNG.split(",")));
		PK_ADV = new ArrayList<String>(Arrays.asList(mrsEntryModel.PK_ADV.split(",")));
		MTR_COND = new ArrayList<String>(Arrays.asList(mrsEntryModel.MTR_COND.split(",")));
		for (int i = 0; i < CUST_ID.size(); i++) {
			if (PRE_READING.isEmpty()) {
				PRE_READING.add(null);
			} else if (i == PRE_READING.size()) {
				PRE_READING.add(null);
			}
			if (ADV.isEmpty()) {
				ADV.add(null);
			} else if (i == ADV.size()) {
				ADV.add(null);
			}
			if (PRE_OFPK_RDNG.isEmpty()) {
				PRE_OFPK_RDNG.add(null);
			} else if (i == PRE_OFPK_RDNG.size()) {
				PRE_OFPK_RDNG.add(null);
			}
			if (OFPK_ADV.isEmpty()) {
				OFPK_ADV.add(null);
			} else if (i == OFPK_ADV.size()) {
				OFPK_ADV.add(null);
			}
			if (PRE_PK_RDNG.isEmpty()) {
				PRE_PK_RDNG.add(null);
			} else if (i == PRE_PK_RDNG.size()) {
				PRE_PK_RDNG.add(null);
			}
			if (PK_ADV.isEmpty()) {
				PK_ADV.add(null);
			} else if (i == PK_ADV.size()) {
				PK_ADV.add(null);
			}
			if (MTR_COND.isEmpty()) {
				MTR_COND.add(null);
			} else if (i == MTR_COND.size()) {
				MTR_COND.add(null);
			}
		}
		int OutPut = 0;
		OutPut = mainDao.updateMrsData(CUST_ID, BILL_CYCLE_CODE, PRE_READING, ADV, PRE_OFPK_RDNG, OFPK_ADV, PRE_PK_RDNG,
				PK_ADV, MTR_COND, "");
		if (OutPut == 1) {
			redirectAttributes.addFlashAttribute("CUST_ID", CUST_ID);
			redirectAttributes.addFlashAttribute("BILL_CYCLE_CODE", BILL_CYCLE_CODE);
			redirectAttributes.addFlashAttribute("PRE_READING", PRE_READING);
			redirectAttributes.addFlashAttribute("ADV", ADV);
			redirectAttributes.addFlashAttribute("PRE_OFPK_RDNG", PRE_OFPK_RDNG);
			redirectAttributes.addFlashAttribute("OFPK_ADV", OFPK_ADV);
			redirectAttributes.addFlashAttribute("PRE_PK_RDNG", PRE_PK_RDNG);
			redirectAttributes.addFlashAttribute("PK_ADV", PK_ADV);
			redirectAttributes.addFlashAttribute("MTR_COND", MTR_COND);
			String msg = "Succesfully Saved";
			redirectAttributes.addAttribute("msg", msg);
			return "redirect:/mrs_entry1";
		} else {
			redirectAttributes.addFlashAttribute("CUST_ID", CUST_ID);
			redirectAttributes.addFlashAttribute("BILL_CYCLE_CODE", BILL_CYCLE_CODE);
			redirectAttributes.addFlashAttribute("PRE_READING", PRE_READING);
			redirectAttributes.addFlashAttribute("ADV", ADV);
			redirectAttributes.addFlashAttribute("PRE_OFPK_RDNG", PRE_OFPK_RDNG);
			redirectAttributes.addFlashAttribute("OFPK_ADV", OFPK_ADV);
			redirectAttributes.addFlashAttribute("PRE_PK_RDNG", PRE_PK_RDNG);
			redirectAttributes.addFlashAttribute("PK_ADV", PK_ADV);
			redirectAttributes.addFlashAttribute("MTR_COND", MTR_COND);
			String msg = "Save Failed";
			redirectAttributes.addAttribute("msg", msg);
			return "redirect:/mrs_entry1";
		}

	}

	@GetMapping("/mrs_entry1")
	public String mrs1(Model model, @ModelAttribute("CUST_ID") ArrayList<String> CUST_ID,
			@ModelAttribute("BILL_CYCLE_CODE") ArrayList<String> BILL_CYCLE_CODE,
			@ModelAttribute("PRE_READING") ArrayList<String> PRE_READING, @ModelAttribute("ADV") ArrayList<String> ADV,
			@ModelAttribute("PRE_OFPK_RDNG") ArrayList<String> PRE_OFPK_RDNG,
			@ModelAttribute("OFPK_ADV") ArrayList<String> OFPK_ADV,
			@ModelAttribute("PRE_PK_RDNG") ArrayList<String> PRE_PK_RDNG,
			@ModelAttribute("PK_ADV") ArrayList<String> PK_ADV, @ModelAttribute("MTR_COND") ArrayList<String> MTR_COND,
			@ModelAttribute("msg") String msg, @CookieValue(value = "billcycle", defaultValue = "") String billcycle,
			@CookieValue(value = "location", defaultValue = "") String location,
			@CookieValue(value = "billgrp", defaultValue = "") String billgrp,
			@CookieValue(value = "book", defaultValue = "") String book,
			@CookieValue(value = "walkseq", defaultValue = "") String walkseq,
			@CookieValue(value = "wstype", defaultValue = "") String wstype,
			@CookieValue(value = "user_name", defaultValue = "") String user_name) {
		ArrayList<WalkSeqModel> walkSeqModels = new ArrayList<WalkSeqModel>();
		walkSeqModels.add(new WalkSeqModel("=", "="));
		walkSeqModels.add(new WalkSeqModel(">=", ">="));
		walkSeqModels.add(new WalkSeqModel("<=", "<="));
		model.addAttribute("walkSeqModels", walkSeqModels);
		ArrayList<BillCycleModel> billCycleModels = new ArrayList<BillCycleModel>();
		billCycleModels = mainDao.billCycle(user_name);
		model.addAttribute("billCycleModels", billCycleModels);
		ArrayList<LocationModel> locationModels = new ArrayList<LocationModel>();
		locationModels = mainDao.location(user_name);
		model.addAttribute("locationModels", locationModels);
		ArrayList<BillGroupModel> billGroupModels = new ArrayList<BillGroupModel>();
		billGroupModels = mainDao.billgroup(user_name);
		model.addAttribute("billGroupModels", billGroupModels);
		MRSEntryFormModel entryFormModel = new MRSEntryFormModel();
		entryFormModel.setBillcycle(billcycle);
		entryFormModel.setLocation(location);
		entryFormModel.setBillgrp(billgrp);
		entryFormModel.setBook(book);
		entryFormModel.setWalkseq(walkseq);
		entryFormModel.setWstype(wstype);
		model.addAttribute("entryFormModel", entryFormModel);
		model.addAttribute("boook", book);
		ArrayList<MRSEntryModel> mrsEntryModels = mainDao.funMrsEntry1(CUST_ID, BILL_CYCLE_CODE, PRE_READING, ADV,
				PRE_OFPK_RDNG, OFPK_ADV, PRE_PK_RDNG, PK_ADV, MTR_COND, billcycle, location, billgrp, book, walkseq,
				user_name, wstype);

		model.addAttribute("mrsEntryModels", mrsEntryModels);
		model.addAttribute("msg", msg);
		model.addAttribute("flag", "1");
		model.addAttribute("saveFlag", "1");
		return "mrs_entry";
	}

}
