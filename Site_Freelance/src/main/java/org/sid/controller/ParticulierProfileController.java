package org.sid.controller;

import javax.servlet.http.HttpSession;

import org.sid.entities.Avis;
import org.sid.entities.Freelancer;
import org.sid.entities.Offre;
import org.sid.entities.Particulier;
import org.sid.services.RatingService;
import org.sid.services.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class ParticulierProfileController implements WebMvcConfigurer {
	@Autowired
	private RatingService ratingService;
	@Autowired
	private ResearchService researchService;

	@RequestMapping("/BBseeProfile")
	public String seeProfile(Model model, @RequestParam(name = "id", defaultValue = "-1") Integer id,
			HttpSession session) {
		model.addAttribute("isParticulier", true);
		return "profilFreelancer";
	}

	@RequestMapping(value = "/BBaddAvis")
	public String addOpinion(Model model, @RequestParam("avis") String avis, @RequestParam("id") String id,
			HttpSession sesssion) {
		Freelancer freelancer = researchService.findFreelancerById(Integer.parseInt(id));
		Avis aviss = new Avis(avis);
		Particulier particulier = (Particulier) sesssion.getAttribute("particulier");
		ratingService.addOpinion(aviss, freelancer, particulier);
		model.addAttribute("isParticulier", true);
		model.addAttribute("freelancer", freelancer);
		return "profilFreelancer";
	}

	@RequestMapping("/BBdeleteOffre")
	public String deleteOffre(Model model, @RequestParam("idOffre") Integer id,
			@RequestParam("idParticulier") Integer idParticulier) {
		researchService.deleteOfferById(id);
		model.addAttribute("isParticulier", true);
		model.addAttribute("particulier", researchService.findParticulierById(idParticulier));
		return "profilParticulier";
	}

	@RequestMapping("/BBaddOffre")
	public String addOffre(Model model, @RequestParam("offre") String description, @RequestParam("id") Integer id) {
		Offre offre = new Offre(description);
		Particulier particulier = researchService.findParticulierById(id);
		researchService.addOffer(offre, particulier);
		model.addAttribute("isParticulier", true);
		model.addAttribute("particulier", particulier);
		return "profilParticulier";
	}

	@RequestMapping(value = "/BBaddNote")
	public String addNote(Model model, @RequestParam("note") Integer note, @RequestParam("id") String id,
			@RequestParam("idParticulier") String idParticulier, HttpSession session) {
		Freelancer freelancer = researchService.findFreelancerById(Integer.parseInt(id));
		Particulier particulier = researchService.findParticulierById(Integer.parseInt(idParticulier));
		if (note <= 10) {
			ratingService.giveScore(freelancer, note.byteValue(), particulier);
		}
		model.addAttribute("isParticulier", true);
		session.setAttribute("freelancer", freelancer);
		return "redirect:/BBseeProfile";
	}

	@RequestMapping("/BBmyParticulierProfilePage")
	public String myParticulierProfilePage(Model model, HttpSession sesssion) {
		model.addAttribute("particulier", sesssion.getAttribute("particulier"));
		return "profilParticulier";
	}

	@RequestMapping("/BBpageUpdateProfileParticulier")
	public String pageUpdateProfileParticulier(Model model, HttpSession sesssion) {
		model.addAttribute("particulier", sesssion.getAttribute("particulier"));
		return "UpdateProfileParticulier";
	}

	@RequestMapping("/BBupdateProfileParticulierPersonnal")
	public String updateProfileParticulierPersonnal(Model model, @RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom, @RequestParam("mobile") Long mobile,
			@RequestParam("adresse") String adresse, @RequestParam("email") String email,
			 @RequestParam("presentation") String presentation, HttpSession sesssion) {
		String pageAfter = "UpdateProfileParticulier", currentPage = "UpdateProfileParticulier";
		Particulier particulier = (Particulier) sesssion.getAttribute("particulier");
		if (!researchService.findParticularByEmail(email).getIdParticulier().equals(particulier.getIdParticulier())
				&& researchService.findFreelancerByEmail(email) != null) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);

		} else if (mobile.toString().length() != 10) {
			pageAfter = currentPage;
			model.addAttribute("message_Update_false", true);

		} else {
			model.addAttribute("message_Update", true);

			particulier.setNom(nom);
			particulier.setEmail(email);
			particulier.setMobile(mobile);
			particulier.setPrenom(prenom);
			particulier.setAdresse(adresse);
			particulier.setPresentation(presentation);
			researchService.updateParticular(particulier);
		}
		sesssion.setAttribute("particulier", particulier);
		model.addAttribute("particulier", particulier);
		model.addAttribute("isParticulier", true);
		return pageAfter;
	}

	@RequestMapping("/BBupdateProfileParticulierSecurity")
	public String updateProfileParticulierSecurity(Model model, @RequestParam("expassword") String exPassword,
			@RequestParam("password") String newPassword, @RequestParam("repassword") String newPasswordVerify,
			HttpSession sesssion) {
		String pageAfter = "UpdateProfileParticulier", currentPage = "UpdateProfileParticulier";
		Particulier particulier = (Particulier) sesssion.getAttribute("particulier");
		if (!particulier.getPassword().equals(exPassword)) {
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
			particulier.setPassword(newPassword);
			researchService.updateParticular(particulier);
		}
		sesssion.setAttribute("particulier", particulier);
		model.addAttribute("particulier", particulier);
		model.addAttribute("isParticulier", true);
		return pageAfter;
	}

}
