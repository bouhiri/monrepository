package org.sid.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sid.dao.FreelancerRepository;
import org.sid.entities.Freelancer;
import org.sid.forms.Login;
import org.sid.services.EmailService;
import org.sid.services.ServiceAutentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.bytebuddy.utility.RandomString;

@Controller
public class SignINController implements WebMvcConfigurer {
	@Autowired
	private EmailService emailService;
	@Autowired
	private FreelancerRepository freelancerRepository;
	@Autowired
	private ServiceAutentification serviceAutentification;

	@RequestMapping(value = "/loginFreelancer")
	public String loginFreelancer(Model model) {
		model.addAttribute("message", false);
		model.addAttribute("login", new Login());
		return "loginFreelancer";
	}
	

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}

	@RequestMapping(value = "/connexionFreelancer")
	public String connexion(@Valid Login login, BindingResult bindingResult, Model model) {
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
			}
		}
		return pageAfter;
	}

	@RequestMapping(value = "/forgotPasswordFreelancerPage")
	public String forgot() {
		return "forgotPasswordFreelancer";
	}

	@RequestMapping(value = "/resetPasswordFreelancer")
	public String forgotPasswordFreelancer(Model model, @RequestParam("email") String email, HttpSession session) {
		String pageAfter, validationCode = RandomString.make(5);
		Freelancer freelancer;
		Optional<Freelancer> optionnelFreelancer = freelancerRepository.findByEmail(email);
		if (optionnelFreelancer.isPresent()) {
			freelancer = optionnelFreelancer.get();
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
		} else
			freelancer.setPassword(password);
			freelancerRepository.save(freelancer);
		return pageAfter;
	}
}
