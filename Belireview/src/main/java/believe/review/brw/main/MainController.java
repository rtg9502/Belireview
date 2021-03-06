package believe.review.brw.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import believe.review.brw.common.common.CommandMap;
import believe.review.brw.common.util.Paging;
import believe.review.brw.rank.RankService;
import believe.review.brw.user.UserService;

@Controller
public class MainController {
	private int currentPage = 1;	 
	private int mainTotal;
	private int movieTotal;
	private int dramaTotal;
	private int adTotal;
	private int blockCount = 10;	 
	private int blockPage = 5; 	 
	private Paging mainPage;
	private Paging moviePage;
	private Paging dramaPage;
	private Paging adPage;
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	
	@Resource(name="rankService")
	private RankService rankService;
	
	@Resource(name="mainService")
	private MainService mainService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(value = "/main.br", method = RequestMethod.GET)
	public String home(Model model,HttpSession session) throws Exception{

		List<Map<String, Object>> drama_list = mainService.dramaListTop8();
		List<Map<String, Object>> movie_list = mainService.movieListTop8();
		List<Map<String, Object>> ad_list = mainService.adListTop8();
		
		if(session.getAttribute("ID")!=null) {//로그인했을때
			Map tmp = new HashMap();
			tmp.put("ID", session.getAttribute("ID"));
			tmp.put("NAME", session.getAttribute("NAME"));
			Map<String,Object> tmp2 = userService.userWishList(tmp);
			if(tmp2!=null) {
				if(tmp2.get("MYPAGE_DRAMA")!=null) {//보고싶어요
					model.addAttribute("wishD",tmp2.get("MYPAGE_DRAMA"));
				}
				if(tmp2.get("MYPAGE_MOVIE")!=null) {//보고싶어요
					model.addAttribute("wishM",tmp2.get("MYPAGE_MOVIE"));
				}
			}
		}
		
		model.addAttribute("drama_list", drama_list);
		model.addAttribute("movie_list", movie_list);
		model.addAttribute("ad_list", ad_list);
		
		return "main";
	}
	@RequestMapping(value = "/like.br", method = RequestMethod.POST)
	@ResponseBody
	public Map home_post(Model model,HttpSession session,CommandMap commandMap) throws Exception{
		
		Map<String, Object> mv = commandMap.getMap();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map = userService.userWishList(mv);
		if(map != null) {
			if(mv.get("DRAMA_NO")!=null) {
				if(map.get("MYPAGE_DRAMA") != null) {
					String[] str = map.get("MYPAGE_DRAMA").toString().split(",");
					boolean exist = false;
					String drama_no = "";
					for(String s: str) {
						if(mv.get("DRAMA_NO").equals(s)) {
							exist = true;
						}else {
							drama_no += s+",";
						}
					}
					if(!exist) {
						drama_no += mv.get("DRAMA_NO");
						mv.put("add", "add");
					}else {
						mv.put("sub", "sub");
					}
					mv.put("DRAMA_NO"	,drama_no);
					System.out.println(mv.get("DRAMA_NO"));
					userService.updateWishList(mv);
				}
			}else {
				mv.put("add", "add");
				userService.updateWishList(mv);
			}
			if(mv.get("MOVIE_NO")!=null) {
				if(map.get("MYPAGE_MOVIE")!=null) {
					String[] str = map.get("MYPAGE_MOVIE").toString().split(",");
					boolean exist = false;
					String movie_no = "";
					for(String s: str) {
						if(mv.get("MOVIE_NO").equals(s)) {
							exist = true;
						}else {
							movie_no += s+",";
						}
					}
					if(!exist) {
						movie_no += mv.get("MOVIE_NO");
						mv.put("add", "add");
					}else {
						mv.put("sub", "sub");
					}
					mv.put("MOVIE_NO"	,movie_no);
					userService.updateWishList(mv);
				}
			}else {
				mv.put("add", "add");
				userService.updateWishList(mv);
			}
		}else {
			mv.put("add", "add");
			userService.insertWishList(mv);
		}
		return mv;
	}
	
	
	@RequestMapping(value = "mainSearch.br")
	public ModelAndView mainSearch(CommandMap commandMap,HttpServletRequest request) throws Exception {
		String table = null;
		
		if(rankService.selectCountSearchText(commandMap.getMap()) > 0) {
			rankService.updateSearchText(commandMap.getMap());
		}else {
			if(rankService.selectDramaSearch(commandMap.getMap()) > 0) {
				table = "drama";
			}else if(rankService.selectMovieSearch(commandMap.getMap()) > 0) {
				table = "movie";
			}else if(rankService.selectActorSearch(commandMap.getMap()) > 0) {
				table = "actor";
			}
			
			commandMap.put("table_value", table);
			
			if(table != null) {
				rankService.insertSearchText(commandMap.getMap());
			}
		}
		
		String p = request.getParameter("currentPage");
		System.out.println(p);
		
		if(p == null || p.trim().isEmpty() || p.equals("0")) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
		ModelAndView mv = new ModelAndView("mainSearch");
		
		List<Map<String,Object>> searchMain = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> searchMovie = mainService.movieSerach(commandMap.getMap());
		List<Map<String,Object>> searchDrama = mainService.dramaSerach(commandMap.getMap());
		List<Map<String,Object>> searchAd = mainService.adSerach(commandMap.getMap());
		
		for(int i=0 ;i<searchMovie.size();i++) {
			Map<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("NO", searchMovie.get(i).get("MOVIE_NO"));
			tmp.put("NAME", searchMovie.get(i).get("MOVIE_NAME"));
			tmp.put("DATE", searchMovie.get(i).get("MOVIE_DATE"));
			tmp.put("GENRE", searchMovie.get(i).get("MOVIE_GENRE"));
			tmp.put("IMAGE", "/brw/resources/images/movie/poster/"+searchMovie.get(i).get("MOVIE_POSTER_IMAGE"));
			tmp.put("TYPE", "영화");
			searchMain.add(i,tmp);
		}
		for(int i=searchMain.size(), j=0;j<searchDrama.size();j++,i++) {
			Map<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("NO", searchDrama.get(j).get("DRAMA_NO"));
			tmp.put("NAME", searchDrama.get(j).get("DRAMA_NAME"));
			tmp.put("DATE", searchDrama.get(j).get("DRAMA_DATE"));
			tmp.put("GENRE", searchDrama.get(j).get("DRAMA_GENRE"));
			tmp.put("IMAGE", "/brw/resources/images/drama/poster/"+searchDrama.get(j).get("DRAMA_POSTER_IMAGE"));
			tmp.put("TYPE", "TV");
			searchMain.add(i,tmp);
		}
		for(int i=searchMain.size(),j=0;j<searchAd.size();j++,i++) {
			Map<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("NO", searchAd.get(j).get("AD_NO"));
			tmp.put("NAME", searchAd.get(j).get("AD_NAME"));
			tmp.put("DATE", searchAd.get(j).get("AD_READCOUNT"));
			tmp.put("GENRE", searchAd.get(j).get("AD_COMPANY"));
			tmp.put("IMAGE", searchAd.get(j).get("AD_POSTER_IMAGE"));
			tmp.put("TYPE", "광고");
			searchMain.add(i,tmp);
		}
		/*메인페이징*/
		mainTotal = searchMain.size();
		mv.addObject("mainTotal",mainTotal);
		
		mainPage = new Paging(currentPage, mainTotal, blockCount, blockPage, "/brw/main/mainSearch");
		
		int lastCount = mainTotal;
		
		if(mainPage.getEndCount() < mainTotal)
			lastCount = mainPage.getEndCount() + 1;
		
		searchMain = searchMain.subList(mainPage.getStartCount(), lastCount);
		/*무비페이징*/
		movieTotal = searchMovie.size();
		mv.addObject("movieTotal",movieTotal);
		
		moviePage = new Paging(currentPage, movieTotal, blockCount, blockPage, "/brw/movie/movieSearch");
		
		lastCount = movieTotal;
		
		if(moviePage.getEndCount() < movieTotal)
			lastCount = moviePage.getEndCount() + 1;
		
		searchMovie = searchMovie.subList(moviePage.getStartCount(), lastCount);
		/*드라마페이징*/
		dramaTotal = searchDrama.size();
		mv.addObject("dramaTotal",dramaTotal);
		
		dramaPage = new Paging(currentPage, dramaTotal, blockCount, blockPage, "/brw/drama/dramaSearch");
		
		lastCount = dramaTotal;
		
		if(dramaPage.getEndCount() < dramaTotal)
			lastCount = dramaPage.getEndCount() + 1;
		
		searchDrama = searchDrama.subList(dramaPage.getStartCount(), lastCount);
		/*광고페이징*/
		adTotal = searchAd.size();
		mv.addObject("adTotal",adTotal);
		
		adPage = new Paging(currentPage, adTotal, blockCount, blockPage, "/brw/ad/adSearch");
		
		lastCount = adTotal;
		
		if(adPage.getEndCount() < adTotal)
			lastCount = adPage.getEndCount() + 1;
		
		searchAd = searchAd.subList(adPage.getStartCount(), lastCount);
	
		if(request.getParameter("searchText") != null) {
			mv.addObject("request",request.getParameter("searchText"));
		}
		else {
			mv.addObject("request",request.getParameter("GENRE"));
			mv.addObject("genre","genre");
		}
		mv.addObject("currentPage",currentPage);
		mv.addObject("searchMain",searchMain);
		mv.addObject("searchMovie",searchMovie);
		mv.addObject("searchDrama",searchDrama);
		mv.addObject("searchAd",searchAd);
		
		return mv;
		}	
		@RequestMapping(value = "mainSearch2.br")
		@ResponseBody
		public Map<String,Object> mainSearch2(CommandMap commandMap,HttpServletRequest request) throws Exception {
			
			String p = request.getParameter("currentPage");
			System.out.println(p);
			
			if(p == null || p.trim().isEmpty() || p.equals("0")) {
	            currentPage = 1;
	        } else {
	            currentPage = Integer.parseInt(request.getParameter("currentPage"));
	        }
			Map<String,Object> mv = new HashMap<String,Object>();
			
			List<Map<String,Object>> searchMain = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> searchMovie = mainService.movieSerach(commandMap.getMap());
			List<Map<String,Object>> searchDrama = mainService.dramaSerach(commandMap.getMap());
			List<Map<String,Object>> searchAd = mainService.adSerach(commandMap.getMap());
			
			for(int i=0 ;i<searchMovie.size();i++) {
				Map<String,Object> tmp = new HashMap<String,Object>();
				tmp.put("NO", searchMovie.get(i).get("MOVIE_NO"));
				tmp.put("NAME", searchMovie.get(i).get("MOVIE_NAME"));
				tmp.put("DATE", searchMovie.get(i).get("MOVIE_DATE"));
				tmp.put("GENRE", searchMovie.get(i).get("MOVIE_GENRE"));
				tmp.put("IMAGE", "/brw/resources/images/movie/poster/"+searchMovie.get(i).get("MOVIE_POSTER_IMAGE"));
				tmp.put("TYPE", "영화");
				searchMain.add(i,tmp);
			}
			for(int i=searchMain.size(), j=0;j<searchDrama.size();j++,i++) {
				Map<String,Object> tmp = new HashMap<String,Object>();
				tmp.put("NO", searchDrama.get(j).get("DRAMA_NO"));
				tmp.put("NAME", searchDrama.get(j).get("DRAMA_NAME"));
				tmp.put("DATE", searchDrama.get(j).get("DRAMA_DATE"));
				tmp.put("GENRE", searchDrama.get(j).get("DRAMA_GENRE"));
				tmp.put("IMAGE", "/brw/resources/images/drama/poster/"+searchDrama.get(j).get("DRAMA_POSTER_IMAGE"));
				tmp.put("TYPE", "TV");
				searchMain.add(i,tmp);
			}
			for(int i=searchMain.size(),j=0;j<searchAd.size();j++,i++) {
				Map<String,Object> tmp = new HashMap<String,Object>();
				tmp.put("NO", searchAd.get(j).get("AD_NO"));
				tmp.put("NAME", searchAd.get(j).get("AD_NAME"));
				tmp.put("DATE", searchAd.get(j).get("AD_READCOUNT"));
				tmp.put("GENRE", searchAd.get(j).get("AD_COMPANY"));
				tmp.put("IMAGE", searchAd.get(j).get("AD_POSTER_IMAGE"));
				tmp.put("TYPE", "광고");
				searchMain.add(i,tmp);
			}
			mainTotal = searchMain.size();
			
			mainPage = new Paging(currentPage, mainTotal, blockCount, blockPage, "/brw/main/mainSearch");
			
			int lastCount = mainTotal;
			if(mainPage.getEndCount() < mainTotal)
				lastCount = mainPage.getEndCount() + 1;
			
			searchMain = searchMain.subList(mainPage.getStartCount(), lastCount);
			
			StringBuffer sb = new StringBuffer();
			
			for(Map m : searchMain) {
				sb.append("<li class=\"ContentListItemWithPoster__Self-swai1z-0 hKRvvO\">");
				
				if(m.get("TYPE").toString().equals("영화")) {
					sb.append("<a href=\"/brw/movie/movieDetail.br?MOVIE_NO=").append(m.get("NO")).append("\">");
				}else if(m.get("TYPE").toString().equals("TV")) {
					sb.append("<a href=\"/brw/drama/dramaDetail.br?DRAMA_NO=").append(m.get("NO")).append("\">");
				}else {
					sb.append("<a href=\"/brw/ad/adDetail.br?AD_NO=").append(m.get("NO")).append("\">");
				}
				sb.append("<div class=\"ContentListItemWithPoster__ContentPosterBlock-swai1z-1 kxDIaJ\">")
				.append("<div class=\"LazyLoadingImg__Self-s1jb87ps-0 csJkbb\">")
				.append("<img class=\"LazyLoadingImg__Self-s1jb87ps-0 csJkbb\" data-image-id=\"20\" src=\"").append(m.get("IMAGE")).append("\"></div></div>")
				.append("<div class=\"ContentListItemWithPoster__ContentInfo-swai1z-2 kVeCGy\">")
				.append("<div class=\"SearchResultsSection__TopResultItemTitle-s1qazrkm-1 kBOijn\">").append(m.get("NAME")).append("</div>")
				.append("<div class=\"SearchResultsSection__TopResultItemExtraInfo-s1qazrkm-2 dGUMNT\">").append(m.get("DATE")).append(" ・ ").append(m.get("GENRE")).append("</div>")
				.append("<div class=\"SearchResultsSection__TopResultContentType-s1qazrkm-3 eeOgLY\">").append(m.get("TYPE")).append("</div>")
				.append("</div></a></li>");
			}		
			mv.put("request",request.getParameter("searchText"));
			mv.put("searchMain",sb.toString());
	
			return mv;
		}	 
		
		/*메인 검색영화,드라마,광고*/
		@RequestMapping(value = "mdaSearch.br")
		@ResponseBody
		public Map<String,Object>mdaSearch(CommandMap commandMap,HttpServletRequest request) throws Exception {
			
			String p = request.getParameter("currentPage");
			
			if(p == null || p.trim().isEmpty() || p.equals("0")) {
				
	            currentPage = 1;
	        } else {
	            currentPage = Integer.parseInt(request.getParameter("currentPage"));
	        }

			Map<String,Object> mv = new HashMap<String,Object>();
			
			List<Map<String,Object>> mdaSearch = new ArrayList();
			String TYPE = commandMap.getMap().get("type").toString();
			
			if(TYPE.equals("1")){
				 mdaSearch = mainService.movieSerach(commandMap.getMap());
			}else if(TYPE.equals("2")) {
				mdaSearch = mainService.dramaSerach(commandMap.getMap());
			}else {
				mdaSearch = mainService.adSerach(commandMap.getMap());
			}
			
			mainTotal = mdaSearch.size();
			
			mainPage = new Paging(currentPage, mainTotal, blockCount, blockPage, "/brw/main/mainSearch");
			
			int lastCount = mainTotal;
			
			if(mainPage.getEndCount() < mainTotal)
				lastCount = mainPage.getEndCount() + 1;
			
			mdaSearch = mdaSearch.subList(mainPage.getStartCount(), lastCount);
			
			StringBuffer sb = new StringBuffer();
			
			for(Map mda : mdaSearch) {
				String name,date,genre,image;
				
				sb.append("<li class=\"StackableListItem-s18nuw36-0 cIJjio\">");
				if(TYPE.equals("1")){
					sb.append("<a lng=\"ko-KR\" class=\"InnerPartOfListWithImage__LinkSelf-s11a1hqv-1 gmbtJD\" title=\"${request}\" href=\"/brw/movie/movieDetail.br?MOVIE_NO=").append(mda.get("MOVIE_NO")).append("\">");
					image = "/brw/resources/images/movie/poster/"+mda.get("MOVIE_POSTER_IMAGE").toString();
					name = mda.get("MOVIE_NAME").toString();
					date = mda.get("MOVIE_DATE").toString();
					genre = mda.get("MOVIE_GENRE").toString();
				}else if(TYPE.equals("2")) {
					sb.append("<a lng=\"ko-KR\" class=\"InnerPartOfListWithImage__LinkSelf-s11a1hqv-1 gmbtJD\" title=\"${request}\" href=\"/brw/drama/dramaDetail.br?DRAMA_NO=").append(mda.get("DRAMA_NO")).append("\">");
					image = "/brw/resources/images/drama/poster/"+mda.get("DRAMA_POSTER_IMAGE").toString();
					name = mda.get("DRAMA_NAME").toString();
					date = mda.get("DRAMA_DATE").toString();
					genre = mda.get("DRAMA_GENRE").toString();
				}else {
					sb.append("<a lng=\"ko-KR\" class=\"InnerPartOfListWithImage__LinkSelf-s11a1hqv-1 gmbtJD\" title=\"${request}\" href=\"/brw/ad/adDetail.br?AD_NO=").append(mda.get("AD_NO")).append("\">");
					image = mda.get("AD_POSTER_IMAGE").toString();
					name = mda.get("AD_NAME").toString();
					date = mda.get("AD_READCOUNT").toString();
					genre = mda.get("AD_COMPANY").toString();
				}
				sb.append("<div class=\"InnerPartOfListWithImage__ImageBlock-s11a1hqv-3 kXgAWr\">");
				sb.append("<div class=\"LazyLoadingBackground-cgbyi4-0 cioRyq LazyLoadingBackgroundw__Self-s1stfhov-0 jXCeuY\" alt=\"${request}\">");
				sb.append("<span class=\"LazyLoadingBackgroundw__BackgroundImage-s1stfhov-1 mPWPS\" data-background-image-id=\"38\">");
				sb.append("<img class=\"LazyLoadingBackground__StylingMerged-cgbyi4-2 kDLFDU LazyLoadingBackground__Self-cgbyi4-0 dxPvni\" src=\"").append(image).append("\"></span>");
				sb.append("</div></div>");
				sb.append("<div class=\"InnerPartOfListWithImage__Info-s11a1hqv-5 hufKbr\">");
				sb.append("<div class=\"InnerPartOfListWithImage__Titles-s11a1hqv-4 jtpmaI\">");
				sb.append("<div class=\"SearchResultItemForContent__ResultTitle-s1phcxqf-1 gGTmOM\">").append(name).append("</div>");
				sb.append("<div class=\"SearchResultItemForContent__ResultExtraInfo-s1phcxqf-0 crwUoZ\">").append(date).append(" ・ ").append(genre).append("</div>");
				sb.append("</div></div></div>");
				sb.append("</div></a></li>");
			}		
					
			mv.put("request",request.getParameter("searchText"));
			mv.put("mdaSearch",sb.toString());
	
			return mv;
		}	
		
}

