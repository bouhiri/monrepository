package org.sid.controller;

import javax.validation.Valid;

import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Particulier;
import org.sid.forms.FreelancerForm;
import org.sid.forms.ParticulierForm;
import org.sid.services.ServiceAutentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class AuthentificationController implements WebMvcConfigurer {
	@Autowired
	private ServiceAutentification serviceAutentification;

	@RequestMapping("/AAfreelancerinscription")
	public String inscriptiondufreelancer(Model model, @Valid FreelancerForm freelancerForm,
			BindingResult bindingResult) {
		Freelancer free = new Freelancer();
		boolean msg = false;
		if (bindingResult.hasErrors()) {
			msg = true;
			model.addAttribute("message1", msg);
			return "FreelancerForm";
		} else if (!freelancerForm.getPassword().equals(freelancerForm.getRepassword())) {
			msg = true;
			model.addAttribute("message2", msg);
			return "FreelancerForm";
		} else {
			free.setNom(freelancerForm.getNom());
			free.setPrenom(freelancerForm.getPrenom());
			free.setEmail(freelancerForm.getEmail());
			free.setMobile(freelancerForm.getMobile());
			free.setExperience(freelancerForm.getExperience());
			free.setDiplome(freelancerForm.getDiplome());
			free.setPassword(freelancerForm.getPassword());
			Localisation lc = new Localisation();
			lc.setVille(freelancerForm.getVille());
			lc.setQuartier(freelancerForm.getQuartier());
			free.setLocalisation(lc);

			Freelancer fr = serviceAutentification.inscriptiondufreelancer(free);

			model.addAttribute("freelancer", fr);
			return "AAProfilFreelancer";
		}
	}

	@RequestMapping("/BBparticulierinscription")
	public String inscriptionduparticulier(Model model, @Valid ParticulierForm particulierForm,
			BindingResult bindingResult) {
		Particulier pr = new Particulier();
		boolean msg = false;
		if (bindingResult.hasErrors()) {
			msg = true;
			model.addAttribute("messagevl", msg);
			return "ParticulierForm";

		} else if (!particulierForm.getPassword().equals(particulierForm.getRepassword())) {
			msg = true;
			model.addAttribute("messagecn", msg);
			return "BBParticulierForm";
		} else {
			pr.setNom(particulierForm.getNom());
			pr.setPrenom(particulierForm.getPrenom());
			pr.setEmail(particulierForm.getEmail());
			pr.setMobile(particulierForm.getMobile());
			pr.setPassword(particulierForm.getPassword());
			pr.setAdresse(particulierForm.getAdresse());

			Particulier prc = serviceAutentification.inscriptionduparticulier(pr);

			model.addAttribute("particulier", prc);
			return "BBProfilParticulier";

		}
	}
}
