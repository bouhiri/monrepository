
package org.sid.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sid.entities.Freelancer;
import org.sid.forms.Login;
import org.sid.services.EmailService;
import org.sid.services.ServiceAutentification;
import org.sid.services.ServiceRecherche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.bytebuddy.utility.RandomString;

@Controller
public class SignInControllerFreelancer implements WebMvcConfigurer {
	@Autowired
	private EmailService emailService;
	@Autowired
	private ServiceAutentification serviceAutentification;
	@Autowired
	private ServiceRecherche serviceRecherche;

	@RequestMapping(value = "/AAloginFreelancer")
	public String loginFreelancer(Model model) {
		model.addAttribute("message", false);
		model.addAttribute("login", new Login());
		return "loginFreelancer";
	}
/*
	@RequestMapping(value = "/connexionFreelancer")
	public String connexion(@Valid Login login, BindingResult bindingResult, Model model,HttpSession session) {
		String pageAfter = "profilFreelancer";
		boolean message = false;
		if (bindingResult.hasErrors()) {
			pageAfter = "loginFreelancer";
			message = true;
			model.addAttribute("messageValid", message);
		} else {
			Freelancer freelancer = serviceAutentification.AuthentificationFreelancer(login.getMail(),
					login.getPassword());
			if (freelancer == null) {
				message = true;
				model.addAttribute("messageForm", message);
				pageAfter = "loginFreelancer";
			} else {
				session.setAttribute("freelancer", freelancer);
				model.addAttribute("isParticulier", false);
				model.addAttribute("freelancer", freelancer);
			}
		}
		return pageAfter;
	}
*/
	@RequestMapping(value = "/forgotPasswordFreelancerPage")
	public String forgotPasswordFreelancerPage() {
		return "forgotPasswordFreelancer";
	}

	@RequestMapping(value = "/resetPasswordFreelancer")
	public String forgotPasswordFreelancer(Model model, @RequestParam("email") String email, HttpSession session) {
		String pageAfter, validationCode = RandomString.make(5);
		Freelancer freelancer = serviceRecherche.findFreelancerByEmail(email);
		if (freelancer != null) {
			emailService.resetPasswordMail(freelancer, validationCode);
			session.setAttribute("validationCode", validationCode);
			session.setAttribute("freelancer", freelancer);
			pageAfter = "resetPasswordFreelancer";
		} else {
			pageAfter = "forgotPasswordFreelancer";
		}

		return pageAfter;
	}
	
	@RequestMapping(value = "/resetPasswordFreelancerToPrfile")
	public String restPassword(Model model, @RequestParam String password, @RequestParam String password2,
			@RequestParam String validationCode, HttpSession session) {
		String pageAfter = "profilFreelancer", currentPage = "resetPasswordFreelancer";
		String validationInput = (String) session.getAttribute("validationCode");
		Freelancer freelancer = (Freelancer) session.getAttribute("freelancer");
		if (!password.equals(password2)) {
			pageAfter = currentPage;
		} else if (!validationInput.equals(validationCode)) {
			pageAfter = currentPage;
		} else {
			freelancer.setPassword(password);
			serviceRecherche.updateFreelancer(freelancer);
			session.setAttribute("freelancer", freelancer);
			SecurityContextHolder cntx = (SecurityContextHolder) session.getAttribute("SPRING_SECURITY_CONTEXT_HOLDER");
			 
			model.addAttribute("freelancer", freelancer);
		}

		return pageAfter;
	}
}
