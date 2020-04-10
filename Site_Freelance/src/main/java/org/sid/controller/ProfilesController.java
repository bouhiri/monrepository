package org.sid.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.sid.entities.Avis;
import org.sid.entities.Competence;
import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Offre;
import org.sid.entities.Particulier;
import org.sid.services.ServiceEvaluation;
import org.sid.services.ServiceRecherche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class ProfilesController implements WebMvcConfigurer {
	@Autowired
	private ServiceEvaluation serviceEvaluation;
	@Autowired
	private ServiceRecherche serviceRecherche;

	@RequestMapping("/BBseeProfile")
	public String seeProfile(Model model, @RequestParam(name = "id", defaultValue = "-1" ) Integer id,HttpSession session) {
		model.addAttribute("isParticulier", true);
		Freelancer freelancer;
		if(id == -1) {
			freelancer = (Freelancer) session.getAttribute("freelancer");
		} else { 
			freelancer = serviceRecherche.findFreelancerById(id);
		}
		int note =  serviceEvaluation.RecalculerMoyenne(freelancer).intValue();
		Particulier particulier = (Particulier) session.getAttribute("particulier");
		model.addAttribute("particulier", particulier);
		model.addAttribute("note", note);
		model.addAttribute("freelancer", freelancer);
		return "profilFreelancer";
	}

	@RequestMapping(value = "/BBaddAvis")
	public String addAvis(Model model, @RequestParam("avis") String avis, @RequestParam("id") String id,HttpSession sesssion) {
		Freelancer freelancer = serviceRecherche.findFreelancerById(Integer.parseInt(id));
		Avis aviss = new Avis(avis);
		Particulier particulier = (Particulier) sesssion.getAttribute("particulier");
		serviceEvaluation.AjouterAvis(aviss, freelancer, particulier);
		model.addAttribute("isParticulier", true);
		model.addAttribute("freelancer", freelancer);
		return "profilFreelancer";
	}

	@RequestMapping("/seeProfileParticulier")
	public String seeProfileParticulier(Model model, @RequestParam("id") Integer id) {
		model.addAttribute("isParticulier", true);
		model.addAttribute("particulier", serviceRecherche.findParticulierById(id));
		return "profilParticulier";
	}

	@RequestMapping("/BBdeleteOffre")
	public String deleteOffre(Model model, @RequestParam("idOffre") Integer id,
			@RequestParam("idParticulier") Integer idParticulier) {
		serviceRecherche.deleteOffreById(id);
		model.addAttribute("isParticulier", true);
		model.addAttribute("particulier", serviceRecherche.findParticulierById(idParticulier));
		return "profilParticulier";
	}

	@RequestMapping("/BBaddOffre")
	public String addOffre(Model model, @RequestParam("offre") String description, @RequestParam("id") Integer id) {
		Offre offre = new Offre(description);
		Particulier particulier = serviceRecherche.findParticulierById(id);
		serviceRecherche.addOffre(offre, particulier);
		model.addAttribute("isParticulier", true);
		model.addAttribute("particulier", particulier);
		return "profilParticulier";
	}

	@RequestMapping("/AApageUpdateProfileFreelancer")
	public String pageUpdateProfileFreelancer(Model model, /*@RequestParam("id") Integer id,*/ HttpSession sesssion) {
		//sesssion.setAttribute("freelancer", serviceRecherche.findFreelancerById(id));
		model.addAttribute("freelancer", sesssion.getAttribute("freelancer"));
		return "UpdateProfileFreelancer";
	}
	
	@RequestMapping("/AAmyFreelancerProfilePage")
	public String myFreelancerProfilePage(Model model, /*@RequestParam("id") Integer id,*/ HttpSession sesssion) {
		model.addAttribute("isParticulier", false);
		Freelancer freelancer = (Freelancer) sesssion.getAttribute("freelancer");
		int note =  serviceEvaluation.RecalculerMoyenne(freelancer).intValue();
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
		} else if (!newPassword.equals(newPasswordVerify)) {
			pageAfter = currentPage;
		} else if (newPassword.length() < 8) {
			pageAfter = currentPage;
		} else {
			freelancer.setPassword(newPassword);
			serviceRecherche.updateFreelancer(freelancer);
		}
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return pageAfter;
	}

	@RequestMapping("/AAupdateProfileFreelancerProfessional")
	public String updateProfileFreelancerProfessionalInformatio(Model model,
			@RequestParam("experience") String experience, @RequestParam("diplome") String diplome,
			HttpSession sesssion) {
		String pageAfter = "UpdateProfileFreelancer", currentPage = "UpdateProfileFreelancer";
		Freelancer freelancer = (Freelancer) sesssion.getAttribute("freelancer");
		if (experience.length() == 0) {
			pageAfter = currentPage;
		} else if (diplome.length() == 0) {
			pageAfter = currentPage;
		} else {
			freelancer.setExperience(experience);
			freelancer.setDiplome(diplome);
			serviceRecherche.updateFreelancer(freelancer);
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
		if (!serviceRecherche.findFreelancerByEmail(email).getIdFreelancer().equals(freelancer.getIdFreelancer())
				&& serviceRecherche.findFreelancerByEmail(email) != null) {
			pageAfter = currentPage;
			// mail existante pour autre user
		} else if (mobile.toString().length() != 10) {
			pageAfter = currentPage;

		} else {
			freelancer.setNom(nom);
			freelancer.setEmail(email);
			freelancer.setMobile(mobile);
			freelancer.setPrenom(prenom);
			Localisation loc = freelancer.getLocalisation();
			loc.setVille(ville);
			loc.setQuartier(quartier);
			freelancer.setLocalisation(loc);
			serviceRecherche.updateLocalisation(loc);
			serviceRecherche.updateFreelancer(freelancer);

		}
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return pageAfter;
	}

	@RequestMapping("/AAaddCompetance")
	public String addCompetance(Model model, @RequestParam("id") Integer id, @RequestParam("domaine") String domaine
			,HttpSession sesssion) {
		Competence competence = serviceRecherche.findCompetanceByDomaine(domaine);
		Freelancer freelancer = serviceRecherche.findFreelancerById(id);
		if (competence == null) {
			competence = new Competence(domaine);
			serviceRecherche.addCompetance(competence, freelancer);
		} else {
			Set<Competence> c = freelancer.getCompetences();
			boolean compExist = false;
			for (Competence competence2 : c) {
				if( competence2.getDomaine().equals(domaine) ) {
					compExist = true ;
					break;
				}
			}
			if (!compExist) {
				competence = new Competence(domaine);
				serviceRecherche.addCompetance(competence, freelancer);
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
		Competence competence = serviceRecherche.findCompetanceById(id);
		Freelancer freelancer = serviceRecherche.findFreelancerById(idFreelancer);
		serviceRecherche.deleteCompetance(competence, freelancer);
		sesssion.setAttribute("freelancer", freelancer);
		model.addAttribute("freelancer", freelancer);
		model.addAttribute("isParticulier", false);
		return "profilFreelancer";
	}
	
	@RequestMapping(value = "/BBaddNote")
	public String addNote(Model model, @RequestParam("note") Integer note, @RequestParam("id") String id,
			@RequestParam("idParticulier") String idParticulier, HttpSession session) {
		Freelancer freelancer = serviceRecherche.findFreelancerById(Integer.parseInt(id));
		Particulier particulier = serviceRecherche.findParticulierById(Integer.parseInt(idParticulier));
		if(note <= 10) {
			serviceEvaluation.DonnerNote(freelancer, note.byteValue(),particulier);
		}
		model.addAttribute("isParticulier", true);
		session.setAttribute("freelancer", freelancer);
		return "redirect:/BBseeProfile";
	}
	
	/***************************************************************************/
	@RequestMapping("/BBmyParticulierProfilePage")
	public String myParticulierProfilePage(Model model, /*@RequestParam("id") Integer id,*/ HttpSession sesssion) {
		model.addAttribute("particulier", sesssion.getAttribute("particulier"));
		return "profilParticulier";
	}
	
	@RequestMapping("/BBpageUpdateProfileParticulier")
	public String pageUpdateProfileParticulier(Model model/*, @RequestParam("id") Integer id*/, HttpSession sesssion) {
		//sesssion.setAttribute("particulier", serviceRecherche.findParticulierById(id));
		model.addAttribute("particulier", sesssion.getAttribute("particulier"));
		return "UpdateProfileParticulier";
	}
	
	@RequestMapping("/BBupdateProfileParticulierPersonnal")
	public String updateProfileParticulierPersonnal(Model model, @RequestParam("nom") String nom,
			@RequestParam("prenom") String prenom,
			@RequestParam("mobile") Long mobile, @RequestParam("adresse") String adresse,
			@RequestParam("email") String email, HttpSession sesssion) {
		String pageAfter = "UpdateProfileParticulier", currentPage = "UpdateProfileParticulier";
		Particulier particulier = (Particulier) sesssion.getAttribute("particulier");
		if (!serviceRecherche.findParticulierByEmail(email).getIdParticulier().equals(particulier.getIdParticulier())
				&& serviceRecherche.findFreelancerByEmail(email) != null) {
			pageAfter = currentPage;
			// mail existante pour autre user
		} else if (mobile.toString().length() != 10) {
			pageAfter = currentPage;

		} else {
			particulier.setNom(nom);
			particulier.setEmail(email);
			particulier.setMobile(mobile);
			particulier.setPrenom(prenom);
			particulier.setAdresse(adresse);
			serviceRecherche.updateParticulier(particulier);

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
		} else if (!newPassword.equals(newPasswordVerify)) {
			pageAfter = currentPage;
		} else if (newPassword.length() < 8) {
			pageAfter = currentPage;
		} else {
			particulier.setPassword(newPassword);
			serviceRecherche.updateParticulier(particulier);
		}
		sesssion.setAttribute("particulier", particulier);
		model.addAttribute("particulier", particulier);
		model.addAttribute("isParticulier", true);
		return pageAfter;
	}
	
}
