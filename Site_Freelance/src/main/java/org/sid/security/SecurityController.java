package org.sid.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.sid.services.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
	@Autowired
	private ResearchService researchService;

	@RequestMapping("/AAconnexionFreelancer")
	public String connexionFreelancer() {
		return "redirect:/AAloginFreelancer";
	}

	@RequestMapping("/AAconnexionSuccessFreelancer")
	public String connexionSuccessFreelancer(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		SecurityContextHolder cntx = (SecurityContextHolder) session.getAttribute("SPRING_SECURITY_CONTEXT_HOLDER");
		String username = cntx.getContext().getAuthentication().getName();
		Freelancer freelancer = researchService.findFreelancerByEmail(username);
		session.setAttribute("freelancer", freelancer);
		session.setAttribute("freelancerConnected", true);
		return "redirect:/AAmyFreelancerProfilePage";
	}

	@RequestMapping("/AAloginErrorFreelancer")
	public String loginErrorFreelancer(Model model, HttpSession session) {
		model.addAttribute("messageForm", true);
		return "loginFreelancer";
	}

	@RequestMapping("/BBconnexionParticulier")
	public String connexionParticulier() {
		return "redirect:/BBloginParticulier";
	}

	@RequestMapping("/logoutFreelancer")
	public String logoutFreelancer(HttpSession session) {
		session.setAttribute("freelancerConnected", false);
		session.setAttribute("toProfile", false);
		return "redirect:/";
	}

	@RequestMapping("/logoutParticulier")
	public String logoutParticulier(HttpSession session) {
		session.setAttribute("particulierConnected", false);
		session.setAttribute("toProfile", false);
		return "redirect:/";
	}

	@RequestMapping("/BBconnexionSuccessParticulier")
	public String connexionSuccessParticulier(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		SecurityContextHolder cntx = (SecurityContextHolder) session.getAttribute("SPRING_SECURITY_CONTEXT_HOLDER");
		String username = cntx.getContext().getAuthentication().getName();
		Particulier particulier = researchService.findParticularByEmail(username);
		session.setAttribute("particulier", particulier);
		session.setAttribute("particulierConnected", true);
		return "redirect:/BBmyParticulierProfilePage";
	}

	@RequestMapping("/BBloginErrorParticulier")
	public String loginErrorParticulier(Model model, HttpSession session) {
		model.addAttribute("messageForm", true);
		return "loginParticulier";
	}

	@RequestMapping("/403")
	public String methode4(Model model) {
		return "403";
	}
}
