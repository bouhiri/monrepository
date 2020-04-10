package org.sid.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sid.entities.Freelancer;
import org.sid.services.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
	@Autowired
	private ResearchService researchService;

	@RequestMapping(value = "/freelancerPage")
	public String freelancerPage() {

		return "freelancers";}
 @RequestMapping("/offres")
 public String SearchOffer(Model model) {
 	model.addAttribute("offres",researchService.OffersList());
 	return "Liste des offres";}
 
@RequestMapping("/offremotclé")
public String ChercherParMotcle(Model model,@RequestParam("word")String word) {
	model.addAttribute("offres",researchService.OfferslistByKeyword(word));
	
	return "Liste des offres";
}
 
 

	@RequestMapping(value = "/freelancerPageSerach")
	public String freelancerPageSearch(Model model, @RequestParam("ville") String ville,
			@RequestParam("competance") String competance) {
		Set<Freelancer> freelancers;
		if (ville.equals("tout") && competance.equals("tout")) {
			freelancers = researchService.listAllFreelancers();
		} else if (ville.equals("tout")) {
			freelancers = researchService.SearchBySkills(competance);
					

		} else if (competance.equals("tout")) {
			freelancers = researchService.SearchByLocation(ville);

		} else {
			freelancers = researchService.SearchBySkillsAndLocation(ville, competance);

		}
		List<Freelancer> f = new ArrayList<Freelancer>();
		List<Double> notes = new ArrayList<Double>();
		List<Object[]> object = new ArrayList<Object[]>();
		freelancers = freelancers == null ? new HashSet<Freelancer>() : freelancers;
		for (Freelancer freelancer : freelancers) {
			f.add(freelancer);
			notes.add(10.0);
			Object[] oo = new Object[2];
			oo[0] = freelancer;
			oo[1] = 10;
			object.add(oo);
			// notes.add(ServiceEvaluation.RecalculerMoyenne(freelancer));
		}
		model.addAttribute("all", object);
		model.addAttribute("freelancers", f);
		model.addAttribute("notes", notes);

		return "freelancers";
	}

	
	}
	
	

