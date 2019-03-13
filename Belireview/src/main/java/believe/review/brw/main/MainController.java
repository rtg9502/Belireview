package believe.review.brw.main;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import believe.review.brw.common.common.CommandMap;

@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Resource(name="mainService")
	private MainService mainService;
	
	@RequestMapping(value = "/main.br", method = RequestMethod.GET)
	public String home(Model model) throws Exception{

		List<Map<String, Object>> drama_list = mainService.dramaListTop8();
		
		model.addAttribute("drama_list", drama_list);
		
		return "main";
	}
	
	@RequestMapping(value = "/admin/main.br", method = RequestMethod.GET)
	public String adminhome(Model model) throws Exception{

		List<Map<String, Object>> drama_list = mainService.dramaListTop8();
		List<Map<String, Object>> movie_list = mainService.movieListTop8();
		List<Map<String, Object>> ad_list = mainService.adListTop8();
		
		model.addAttribute("drama_list", drama_list);
		model.addAttribute("movie_list", movie_list);
		model.addAttribute("ad_list", ad_list);
		
		return "adminMain";
	}

	@RequestMapping(value = "mainSearch.br")

	public ModelAndView mainSearch(CommandMap commandMap) throws Exception {

		ModelAndView mv = new ModelAndView("mainSearch");

		return mv;
	}
}
