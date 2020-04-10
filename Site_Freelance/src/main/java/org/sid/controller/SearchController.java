package org.sid.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;


import org.sid.entities.Freelancer;

import org.sid.services.ResearchService;

import org.sid.entities.Offre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 
@RequestMapping("/offremotclÃ©")
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


		return "redirect:/getPageFreelancers";
	}

	@RequestMapping("/getPageFreelancers")
	public String getPageFreelancers(Model model, HttpSession session,
			@RequestParam(name = "pg", defaultValue = "0") int pg) {
		List<Object[]> object = (List<Object[]>) session.getAttribute("freelancers");
		List<Double> notes = (List<Double>) session.getAttribute("notes");
		pg = pg + 1;
		int size = pageSizeFreelancers, from = (pg - 1) * size, to;
		from = from < 0 ? 0 : from;

		to = from + size;
		to = to >= object.size() ? object.size() : to;
		System.out.println("from  " + from + "  to  " + to);
		int[] pages = new int[object.size() / pageSizeFreelancers];
		model.addAttribute("all", object.subList(from, to));
		model.addAttribute("pages", pages);
		model.addAttribute("notes", notes.subList(from, to));

		return "freelancers";
	}
	public int pageSizeOffre = 2;
	public int pageSizeFreelancers = 6;

	@RequestMapping("/offres")
	public String ChercherOffre(Model model, @RequestParam(name = "page", defaultValue = "0") int pg,
			HttpSession session) {
		List<Offre> offres = researchService.OffersList();
		model.addAttribute("offres", offres);
		session.setAttribute("offres", offres);
		int[] pages = new int[offres.size() / pageSizeOffre];
		model.addAttribute("pages", pages);
		return "redirect:/getPage";// "ListOffre";
	}

	@RequestMapping("/offremotclé")
	public String ChercherParMotcle(Model model, @RequestParam("mot") String mot, HttpSession session) {
		List<Offre> offresParMotCle = new ArrayList<Offre>(), offres;
		offres = researchService.OffersList();
		for (Offre offre : offres) {
			if (offre.getDescription().contains(mot))
				offresParMotCle.add(offre);
		}
		session.setAttribute("offres", offresParMotCle);
		model.addAttribute("offres", offresParMotCle);
		int[] pages = new int[offresParMotCle.size() / pageSizeOffre];
		model.addAttribute("pages", pages);
		return "redirect:/getPage";// "ListOffre";
	}

	@RequestMapping("/getPage")
	public String getPage(Model model, HttpSession session, @RequestParam(name = "pg", defaultValue = "0") int pg) {
		List<Offre> offres = (List<Offre>) session.getAttribute("offres");
		pg = pg + 1;
		int size = pageSizeOffre, from = (pg - 1) * size, to;
		from = from < 0 ? 0 : from;

		to = from + size;
		to = to >= offres.size() ? offres.size() : to;
		System.out.println("from  " + from + "  to  " + to);
		int[] pages = new int[offres.size() / pageSizeOffre];
		model.addAttribute("offres", offres.subList(from, to));
		model.addAttribute("pages", pages);
		return "ListOffre";
	}

}
