
package org.sid.controller;

import javax.servlet.http.HttpSession;

import org.sid.entities.Freelancer;
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
public class SignInControllerFreelancer implements WebMvcConfigurer {
	@Autowired
	private EmailService emailService;
	@Autowired
	private  ResearchService researchService;

	@RequestMapping(value = "/AAloginFreelancer")
	public String loginFreelancer(Model model) {
		model.addAttribute("message", false);
		model.addAttribute("login", new Login());
		return "loginFreelancer";
	}
	
	@RequestMapping(value = "/forgotPasswordFreelancerPage")
	public String forgotPasswordFreelancerPage() {
		return "forgotPasswordFreelancer";
	}

	@RequestMapping(value = "/resetPasswordFreelancer")
	public String forgotPasswordFreelancer(Model model, @RequestParam("email") String email, HttpSession session) {
		String pageAfter, validationCode = RandomString.make(5);
		Freelancer freelancer = researchService.findFreelancerByEmail(email);
		if (freelancer != null) {
			emailService.resetPasswordMail(freelancer, validationCode);
			session.setAttribute("validationCode", validationCode);
			session.setAttribute("freelancer", freelancer);
			pageAfter = "resetPasswordFreelancer";
		} else {
			model.addAttribute("message_Mail", true);
			pageAfter = "forgotPasswordFreelancer";
		}

		return pageAfter;
	}
	
	@RequestMapping(value = "/resetPasswordFreelancerToPrfile")
	public String restPassword(Model model, @RequestParam String password, @RequestParam String password2,
			@RequestParam String validationCode, HttpSession session) {
		String pageAfter = "redirect:/AAloginFreelancer", currentPage = "resetPasswordFreelancer";
		String validationInput = (String) session.getAttribute("validationCode");
		Freelancer freelancer = (Freelancer) session.getAttribute("freelancer");
		if (!password.equals(password2)) {
			pageAfter = currentPage;
			model.addAttribute("message_Reset", true);
		} else if (!validationInput.equals(validationCode)) {
			pageAfter = currentPage;
			model.addAttribute("message_ValidationCode", true);

		} else {
			freelancer.setPassword(password);
			researchService.updateFreelancer(freelancer);
			session.setAttribute("freelancer", freelancer);			 
			model.addAttribute("freelancer", freelancer);
			session.setAttribute("toProfile", true);
		}

		return pageAfter;
	}
}
