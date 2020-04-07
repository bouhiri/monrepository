package org.sid.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.sid.services.ServiceRecherche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
	@Autowired
	private ServiceRecherche serviceRecherche;

	@RequestMapping("/AAconnexionFreelancer")
	public String connexionFreelancer() {
		return "redirect:/AAloginFreelancer";
	}

	@RequestMapping("/AAconnexionSuccessFreelancer")
	public String connexionSuccessFreelancer(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		SecurityContextHolder cntx = (SecurityContextHolder) session.getAttribute("SPRING_SECURITY_CONTEXT_HOLDER");
		String username = cntx.getContext().getAuthentication().getName();
		Freelancer freelancer = serviceRecherche.findFreelancerByEmail(username);
		session.setAttribute("freelancer", freelancer);
		return "redirect:/AAmyFreelancerProfilePage";
	}

	@RequestMapping("/AAloginErrorFreelancer")
	public String loginErrorFreelancer(Model model) {
		model.addAttribute("messageForm", true);
		return "loginFreelancer";
	}

	/****************************************************************/
	@RequestMapping("/BBconnexionParticulier")
	public String connexionParticulier() {
		return "redirect:/BBloginParticulier";
	}

	@RequestMapping("/BBconnexionSuccessParticulier")
	public String connexionSuccessParticulier(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		SecurityContextHolder cntx = (SecurityContextHolder) session.getAttribute("SPRING_SECURITY_CONTEXT_HOLDER");
		String username = cntx.getContext().getAuthentication().getName();
		Particulier particulier = serviceRecherche.findParticulierByEmail(username);
		session.setAttribute("particulier", particulier);
		return "redirect:/BBmyParticulierProfilePage";
	}

	@RequestMapping("/BBloginErrorParticulier")
	public String loginErrorParticulier(Model model) {
		model.addAttribute("messageForm", true);
		return "loginParticulier";
	}

	/****************************************************************/
	@RequestMapping("/403")
	public String methode4(Model model) {
		return "403";
	}
}
