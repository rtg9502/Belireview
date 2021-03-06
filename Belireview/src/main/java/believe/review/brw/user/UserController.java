package believe.review.brw.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import believe.review.brw.common.common.CommandMap;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private int totalCountM,totalCountD,totalCountA;
	private String totalMyMovieLike, totalMyDramaLike;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping(value = "/user.br") // 마이페이지
	public ModelAndView user(CommandMap commandMap,HttpServletRequest request)throws Exception {
		HttpSession session = request.getSession();
		commandMap.put("ID", session.getAttribute("ID"));
		ModelAndView mv = new ModelAndView();
		totalCountM = userService.totalMyMovie(commandMap.getMap());
		totalCountD = userService.totalMyDrama(commandMap.getMap());
		totalCountA = userService.totalMyAd(commandMap.getMap());
		totalMyMovieLike = userService.totalMyMovieLike(commandMap.getMap());
		totalMyDramaLike = userService.totalMyDramaLike(commandMap.getMap());
		mv.setViewName("user");
		mv.addObject("totalCountM",totalCountM);
		mv.addObject("totalCountD",totalCountD);
		mv.addObject("totalCountA",totalCountA);
		
		if (totalMyMovieLike != null) {
				String[] str = totalMyMovieLike.toString().split(",");
		mv.addObject("totalMyMovieLike",str.length);
		}
		if (totalMyDramaLike != null) {
			String[] str = totalMyDramaLike.toString().split(",");
			mv.addObject("totalMyDramaLike",str.length);
	}
		return mv;
	}

	@RequestMapping(value = "/userModify_Pro", method = RequestMethod.POST) // 프로필사진변경
	public ModelAndView userProfile(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> Pmap = new HashMap<String, Object>();
		Pmap = commandMap.getMap();
		HttpSession session = request.getSession();

		userService.UserProfile(Pmap, request);

		// 세션 이미지 변경
		Map<String, Object> usermem = userService.userGo(commandMap.getMap());
		session.setAttribute("PROFILE_IMAGE", usermem.get("PROFILE_IMAGE"));

		mv.setViewName("user");
		return mv;
	}

	@RequestMapping(value = "/userModifyPass") // 수정폼으로 이동전 비밀번호 확인
	public ModelAndView userModifyPass() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pwdCheck");
		return mv;
	}

	@RequestMapping(value = "/userModify", method = RequestMethod.GET) // 정보수정폼
	public ModelAndView userModify() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modify");
		return mv;
	}

	@RequestMapping(value = "/userModify", method = RequestMethod.POST) // 폼값 받아 회원정보수정
	public ModelAndView userModify(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap = commandMap.getMap();

		userMap.put("email_marketing", request.getParameter("termsEmail"));
		userMap.put("sms_marketing", request.getParameter("termsLocation"));
		
		if(userMap.get("password") != ""){
		String encodedPassword = passwordEncoder.encode((String)userMap.get("password"));
		userMap.put("password", encodedPassword);
		}
		
		System.out.println(userMap.get("password"));
		userService.ModifyMember(userMap, request);

		Map<String, Object> usermem = userService.userGo(commandMap.getMap());

		// 업데이트된 정보 세션에 다시저장
		session.setAttribute("NAME", usermem.get("NAME"));
		session.setAttribute("TEL", usermem.get("TEL"));
		session.setAttribute("EMAIL", usermem.get("EMAIL"));
		session.setAttribute("PASSWORD", usermem.get("PASSWORD"));
		session.setAttribute("EMAIL_MARKETING", usermem.get("EMAIL_MARKETING"));
		session.setAttribute("SMS_MARKETING", usermem.get("SMS_MARKETING"));

		mv.setViewName("user");
		return mv;
	}

	@RequestMapping(value = "/userMovie")
	public ModelAndView userMovie(CommandMap commandMap, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		ModelAndView mv = new ModelAndView("userMovie");
		List<Map<String, Object>> userMovieAll = userService.UserMovieAll(commandMap.getMap());
		mv.addObject("userMovieAll", userMovieAll);
		Map<String, Object> userWishList = userService.userWishList(commandMap.getMap());

		if (userWishList != null) {
			if(userWishList.get("MYPAGE_MOVIE")!=null) {
				String[] str = userWishList.get("MYPAGE_MOVIE").toString().split(",");
				commandMap.put("a", str);
	
				List<Map<String, Object>> userMovieList = userService.userMovieList(commandMap.getMap());
	
				mv.addObject("userWishList", userWishList);
				mv.addObject("userMovieList", userMovieList);
			}
		}

		return mv;
	}
	
	@RequestMapping(value = "/userDrama")
	public ModelAndView userdrama(CommandMap commandMap, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		ModelAndView mv = new ModelAndView("userDrama");
		List<Map<String, Object>> userdramaAll = userService.UserDramaAll(commandMap.getMap());
		mv.addObject("userdramaAll", userdramaAll);
		Map<String, Object> userWishList = userService.userWishList(commandMap.getMap());

		if (userWishList != null) {
			if(userWishList.get("MYPAGE_DRAMA")!=null) {
				String[] str = userWishList.get("MYPAGE_DRAMA").toString().split(",");
				commandMap.put("a", str);
	
				List<Map<String, Object>> userDramaList = userService.userDramaList(commandMap.getMap());
	
				mv.addObject("userWishList", userWishList);
				mv.addObject("userDramaList", userDramaList);
			}
		}

		return mv;
	}
	
	@RequestMapping(value = "/userAD")
	public ModelAndView userad(CommandMap commandMap, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		ModelAndView mv = new ModelAndView("userAD");
		List<Map<String, Object>> useradAll = userService.UserAdAll(commandMap.getMap());
		mv.addObject("useradAll", useradAll);
	

		return mv;
	}

	@RequestMapping(value = "/userDeleteForm")
	public ModelAndView userDeleteForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("userDeleteForm");
		return mv;
	}

	@RequestMapping(value = "/userDeletechk")
	public ModelAndView userDeletechk() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("userDeletechk");
		return mv;
	}

	@RequestMapping(value = "/userDelete")
	public ModelAndView userDelete(CommandMap commandMap, HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();

		System.out.println("id" + commandMap.get("id"));
		userService.deleteUserOne(userService.userGo(commandMap.getMap()));
		session.invalidate();

		mv.setViewName("main");
		return mv;

	}

}
