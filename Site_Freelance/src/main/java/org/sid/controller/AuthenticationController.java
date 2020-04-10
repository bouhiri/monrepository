package org.sid.controller;

import javax.validation.Valid;

import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Particulier;
import org.sid.forms.FreelancerForm;
import org.sid.forms.ParticulierForm;
import org.sid.services.AuthenticationService;

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

	@RequestMapping("/inscription")
	public String Inscription(Model model) {
		model.addAttribute("freelancerForm", new FreelancerForm());
		return "redirect:/freelancerinscription";
	}

	@RequestMapping("/freelancerinscription")
	public String FreelancerRegistration(Model model, @Valid FreelancerForm freelancerForm,
			BindingResult bindingResult) {
		Freelancer free = new Freelancer();
		boolean msg = false;
		/*if (bindingResult.hasErrors()) {
			msg = true;
			model.addAttribute("message1", msg);

			return "FreelancerForm";
		}*/
		if (freelancerForm.getPassword() == null)
			return "FreelancerForm";
		else if (!freelancerForm.getPassword().equals(freelancerForm.getRepassword())) {
			msg = true;
			model.addAttribute("message2", msg);

			return "FreelancerForm";
		}

		else {
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

			Freelancer fr = authenticationService.FreelancerRegistration(free);
					

			model.addAttribute("freelancer", fr);

			

			return "redirect:/AAloginFreelancer";//"AAProfilFreelancer";

		}
			
	}

	@RequestMapping("/particulierinscription")

	public String ParticularRegistration(Model model, @Valid ParticulierForm particulierForm,
			BindingResult bindingResult) {
		Particulier pr = new Particulier();
		boolean msg = false;
		/*
		 * if (bindingResult.hasErrors()) { msg = true; model.addAttribute("messagevl",
		 * msg); return "ParticulierForm";
		 */

		if (particulierForm.getPassword() == null)
			return "ParticulierForm";

		else if (!particulierForm.getPassword().equals(particulierForm.getRepassword())) {
			msg = true;
			model.addAttribute("messagecn", msg);
			return "ParticulierForm";
		} else {
			pr.setNom(particulierForm.getNom());
			pr.setPrenom(particulierForm.getPrenom());
			pr.setEmail(particulierForm.getEmail());
			pr.setMobile(particulierForm.getMobile());
			pr.setPassword(particulierForm.getPassword());
			pr.setAdresse(particulierForm.getAdresse());
			pr.setPresentation(particulierForm.getPresentation());

			Particulier prc = authenticationService.ParticularRegistration(pr);
					
			model.addAttribute("particulier", prc);

		


			return"redirect:/BBloginParticulier";//"BBProfilParticulier";


		

		}
	}
		
	}
