package org.sid.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Particulier;
import org.sid.forms.FreelancerForm;
import org.sid.forms.ParticulierForm;
import org.sid.services.AuthenticationService;
import org.sid.services.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class AuthenticationController implements WebMvcConfigurer {
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private ResearchService researchService;
	
	@RequestMapping("/inscription")
	public String Inscription(Model model) {
		model.addAttribute("freelancerForm", new FreelancerForm());
		return "redirect:/freelancerinscription";
	}

	@RequestMapping("/freelancerinscription")
	public String FreelancerRegistration(Model model, @Valid FreelancerForm freelancerForm,
			BindingResult bindingResult, HttpSession session) {
		Freelancer free = new Freelancer();
		boolean msg = false;
		if (freelancerForm.getPassword() == null) {
			return "FreelancerForm";
		} else if (researchService.findFreelancerByEmail(freelancerForm.getEmail()) != null) {
			model.addAttribute("message_Mail", true);
			return "FreelancerForm";
		} else if (!freelancerForm.getPassword().equals(freelancerForm.getRepassword())) {
			model.addAttribute("message_Repassword", true);
			return "FreelancerForm";
		} else {
			free.setNom(freelancerForm.getNom());
			free.setPrenom(freelancerForm.getPrenom());
			free.setEmail(freelancerForm.getEmail());
			free.setMobile(freelancerForm.getMobile());
			free.setExperience(freelancerForm.getExperience());
			free.setDiplome(freelancerForm.getDiplome());
			free.setPassword(freelancerForm.getPassword());
			free.setDomaine(freelancerForm.getDomaine());
			free.setPresentation(freelancerForm.getPresentation());
			Localisation lc = new Localisation();
			lc.setVille(freelancerForm.getVille());
			lc.setQuartier(freelancerForm.getQuartier());
			free.setLocalisation(lc);
			Freelancer fr = authenticationService.freelancerRegistration(free);
			model.addAttribute("freelancer", fr);
			model.addAttribute("loginFreelancerDone", true);
			session.setAttribute("toProfile", true);
			return "redirect:/AAloginFreelancer";// "AAProfilFreelancer";
		}
	}

	@RequestMapping("/particulierinscription")
	public String ParticularRegistration(Model model, @Valid ParticulierForm particulierForm,
			BindingResult bindingResult,HttpSession session) {
		Particulier pr = new Particulier();
		boolean msg = false;
		if (particulierForm.getPassword() == null) {
			return "ParticulierForm";
		}else if(researchService.findParticularByEmail(particulierForm.getEmail())!= null) {
			model.addAttribute("message_Mail", true);
			return "ParticulierForm";
		}
		else if (!particulierForm.getPassword().equals(particulierForm.getRepassword())) {
			model.addAttribute("message_Repassword", true);
			return "ParticulierForm";
		} else {
			pr.setNom(particulierForm.getNom());
			pr.setPrenom(particulierForm.getPrenom());
			pr.setEmail(particulierForm.getEmail());
			pr.setMobile(particulierForm.getMobile());
			pr.setPassword(particulierForm.getPassword());
			pr.setAdresse(particulierForm.getAdresse());
			pr.setPresentation(particulierForm.getPresentation());
			Particulier prc = authenticationService.particularRegistration(pr);
			model.addAttribute("particulier", prc);
			model.addAttribute("loginParticulierDone", true);
			session.setAttribute("toProfile", true);
			return "redirect:/BBloginParticulier";
		}
	}

}
