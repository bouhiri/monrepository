package org.sid.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.sid.dao.OffreRepository;
import org.sid.entities.Freelancer;
import org.sid.entities.Offre;
import org.sid.services.ServiceEvaluation;
import org.sid.services.ServiceRecherche;
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
	private ServiceRecherche serviceRecherche;
	@Autowired
	private ServiceEvaluation serviceEvaluation;

	@RequestMapping(value = "/freelancerPage")
	public String freelancerPage() {

		return "freelancers";
	}

	@RequestMapping(value = "/freelancerPageSerach")
	public String freelancerPageSearch(Model model, @RequestParam("ville") String ville,
			@RequestParam("competance") String competance, HttpSession session) {
		Set<Freelancer> freelancers;
		if (ville.equals("tout") && competance.equals("tout")) {
			freelancers = serviceRecherche.listAllFreelancers();
		} else if (ville.equals("tout")) {
			freelancers = serviceRecherche.chercherParCompetences(competance);

		} else if (competance.equals("tout")) {
			freelancers = serviceRecherche.chercherParLocalisation(ville);

		} else {
			freelancers = serviceRecherche.chercherParLocalisationCompetance(ville, competance);

		}
		List<Freelancer> f = new ArrayList<Freelancer>();
		List<Double> notes = new ArrayList<Double>();
		List<Object[]> object = new ArrayList<Object[]>();
		freelancers = freelancers == null ? new HashSet<Freelancer>() : freelancers;
		for (Freelancer freelancer : freelancers) {
			f.add(freelancer);
			//notes.add(10.0);
			Object[] oo = new Object[2];
			oo[0] = freelancer;
			oo[1] = 10;
			object.add(oo);
			notes.add(serviceEvaluation.RecalculerMoyenne(freelancer));
		}

		session.setAttribute("freelancers", object);
		// model.addAttribute("freelancers", f);
		session.setAttribute("notes", notes);

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
		List<Offre> offres = serviceRecherche.listAllOffre();
		model.addAttribute("offres", offres);
		session.setAttribute("offres", offres);
		int[] pages = new int[offres.size() / pageSizeOffre];
		model.addAttribute("pages", pages);
		return "redirect:/getPage";// "ListOffre";
	}

	@RequestMapping("/offremotclé")
	public String ChercherParMotcle(Model model, @RequestParam("mot") String mot, HttpSession session) {
		List<Offre> offresParMotCle = new ArrayList<Offre>(), offres;
		offres = serviceRecherche.listAllOffre();
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
