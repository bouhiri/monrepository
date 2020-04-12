package org.sid.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.sid.entities.Competence;
import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.services.RatingService;
import org.sid.services.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class FreelancerProfileController implements WebMvcConfigurer {
	@Autowired
	private RatingService ratingService;
	@Autowired
	private ResearchService researchService;

	@RequestMapping("/AApageUpdateProfileFreelancer")
	public String pageUpdateProfileFreelancer(Model model, HttpSession sesssion) {
		model.addAttribute("freelancer", sesssion.getAttribute("freelancer"));
		return "UpdateProfileFreelancer";
	}

	@RequestMapping("/AAmyFreelancerProfilePage")
	public String myFreelancerProfilePage(Model model, HttpSession sesssion) {
		model.addAttribute("isParticulier", false);
		Freelancer freelancer = (Freelancer) sesssion.getAttribute("freelancer");
		int note = ratingService.recalculateAverage(freelancer).intValue();
		model.addAttribute("note", note);
		model.addAttribute("freelancer", freelancer);
		return "profilFreelancer";
	}

	@RequestMapping("/AAupdateProfileFreelancerSecurity")
	public String updateProfileFreelancerSecurity(Model model, @RequestParam("expassword") String exPassword,
			@RequestParam("password") String newPassword, @RequestParam("repassword") String newPasswordVerify,
			HttpSession sesssion) {
		String pageAfter = "UpdateProfileFreelancer", currentPage = "UpdateProfileFreelancer";
		Freelancer freelancer = (Freelancer) sesssion.getAttribute("freelancer");
		if (!freelancer.getPassword().equals(exPassword)) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);
		} else if (!newPassword.equals(newPasswordVerify)) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);

		} else if (newPassword.length() < 8) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);

		} else {
			model.addAttribute("message_Update", true);
			freelancer.setPassword(newPassword);
			researchService.updateFreelancer(freelancer);
		}
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return pageAfter;
	}

	@RequestMapping("/AAupdateProfileFreelancerProfessional")
	public String updateProfileFreelancerProfessionalInformatio(Model model,
			@RequestParam("experience") String experience, @RequestParam("diplome") String diplome,
			@RequestParam("domaine") String domaine, @RequestParam("presentation") String presentation,
			HttpSession sesssion) {
		String pageAfter = "UpdateProfileFreelancer", currentPage = "UpdateProfileFreelancer";
		Freelancer freelancer = (Freelancer) sesssion.getAttribute("freelancer");
		if (experience.length() == 0) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);
		} else if (diplome.length() == 0) {
			model.addAttribute("message_Update_false", true);
			pageAfter = currentPage;
		} else {

			model.addAttribute("message_Update", true);
			freelancer.setExperience(experience);
			freelancer.setDiplome(diplome);
			freelancer.setPresentation(presentation);
			freelancer.setDomaine(domaine);
			researchService.updateFreelancer(freelancer);
		}
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return pageAfter;
	}

	@RequestMapping("/AAupdateProfileFreelancerPersonnal")
	public String updateProfileFreelancerPersonnal(Model model, @RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom, @RequestParam("ville") String ville,
			@RequestParam("mobile") Long mobile, @RequestParam("quartier") String quartier,
			@RequestParam("email") String email, HttpSession sesssion) {
		String pageAfter = "UpdateProfileFreelancer", currentPage = "UpdateProfileFreelancer";
		Freelancer freelancer = (Freelancer) sesssion.getAttribute("freelancer");
		if (!researchService.findFreelancerByEmail(email).getIdFreelancer().equals(freelancer.getIdFreelancer())
				&& researchService.findFreelancerByEmail(email) != null) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);

		} else if (mobile.toString().length() != 10) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);

		} else {
			model.addAttribute("message_Update", true);
			freelancer.setNom(nom);
			freelancer.setEmail(email);
			freelancer.setMobile(mobile);
			freelancer.setPrenom(prenom);
			Localisation loc = freelancer.getLocalisation();
			loc.setVille(ville);
			loc.setQuartier(quartier);
			freelancer.setLocalisation(loc);
			researchService.updateLocation(loc);
			researchService.updateFreelancer(freelancer);
		}
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return pageAfter;
	}

	@RequestMapping("/AAaddCompetance")
	public String addCompetance(Model model, @RequestParam("id") Integer id, @RequestParam("domaine") String domaine,
			HttpSession sesssion) {
		Competence competence = researchService.findSkillsByDomain(domaine);
		Freelancer freelancer = researchService.findFreelancerById(id);
		if (competence == null) {
			competence = new Competence(domaine);
			researchService.addSkill(competence, freelancer);
		} else {
			Set<Competence> c = freelancer.getCompetences();
			boolean compExist = false;
			for (Competence competence2 : c) {
				if (competence2.getDomaine().equals(domaine)) {
					compExist = true;
					break;
				}
			}
			if (!compExist) {
				//competence = new Competence(domaine);
				researchService.addSkill(competence, freelancer);

			}
		}
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return "profilFreelancer";
	}

	@RequestMapping("/AAdeleteCompetance")
	public String deleteCompetance(Model model, @RequestParam("idCompetence") Integer id,
			@RequestParam("idFreelancer") Integer idFreelancer, HttpSession sesssion) {
		Competence competence = researchService.findSkillsById(id);
		Freelancer freelancer = researchService.findFreelancerById(idFreelancer);
		researchService.deleteSkills(competence, freelancer);
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return "profilFreelancer";
	}

	@RequestMapping("/seeProfileParticulier")
	public String seeProfileParticulier(Model model, @RequestParam("id") Integer id) {
		model.addAttribute("isParticulier", true);
		model.addAttribute("particulier", researchService.findParticulierById(id));
		return "profilParticulier";
	}

}
