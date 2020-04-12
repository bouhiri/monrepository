package org.sid.controller;

import javax.servlet.http.HttpSession;

import org.sid.entities.Particulier;
import org.sid.forms.Login;
import org.sid.services.EmailService;
import org.sid.services.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.bytebuddy.utility.RandomString;

@Controller
public class SignInControllerParticulier implements WebMvcConfigurer {
	@Autowired
	private EmailService emailService;
	@Autowired
	private  ResearchService researchService;

	@RequestMapping(value = "/")
	public String toHome(Model model) {
		return "home";
	}

	@RequestMapping(value = "/BBloginParticulier")
	public String loginParticulier(Model model) {
		model.addAttribute("message", false);
		model.addAttribute("login", new Login());
		return "loginParticulier";
	}

	@RequestMapping(value = "/forgotPasswordParticulierPage")
	public String forgot() {
		return "forgotPasswordParticulier";
	}

	@RequestMapping(value = "/resetPasswordParticulier")
	public String forgotPasswordParticulier(Model model, @RequestParam("email") String email, HttpSession session) {
		String pageAfter, validationCode = RandomString.make(5);
		Particulier particulier = researchService.findParticularByEmail(email);
		if (particulier != null) {
			emailService.resetPasswordMail(particulier, validationCode);
			session.setAttribute("validationCode", validationCode);
			session.setAttribute("particulier", particulier);
			pageAfter = "resetPasswordParticulier";
		} else {
			model.addAttribute("message_Mail", true);
			pageAfter = "forgotPasswordParticulier";
		}

		return pageAfter;
	}

	@RequestMapping(value = "/resetPasswordParticulierToProfile")
	public String restPassword(Model model, @RequestParam String password, @RequestParam String password2,
			@RequestParam String validationCode, HttpSession session) {
		String pageAfter = "redirect:/BBloginParticulier", currentPage = "resetPasswordParticulier";
		String validationInput = (String) session.getAttribute("validationCode");
		Particulier particulier = (Particulier) session.getAttribute("particulier");
		if (!password.equals(password2)) {
			pageAfter = currentPage;
			model.addAttribute("message_Reset", true);
		} else if (!validationInput.equals(validationCode)) {
			pageAfter = currentPage;
			model.addAttribute("message_ValidationCode", true);
		} else {
			particulier.setPassword(password);
			researchService.updateParticular(particulier);
			model.addAttribute("isParticulier", true);
			session.setAttribute("particulier", particulier);
			model.addAttribute("particulier", particulier);
			session.setAttribute("toProfile", true);
		}
		return pageAfter;
	}
}
