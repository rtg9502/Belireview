package believe.review.brw.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import believe.review.brw.user.UserDAO;
import believe.review.brw.common.util.FileUtils;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource(name="userDAO")
	private UserDAO userDAO;
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Override
	public void ModifyMember(Map<String, Object> map, HttpServletRequest request) throws Exception {
		userDAO.ModifyMember(map);
	}
	
	@Override
	public Map<String, Object> userGo(Map<String, Object> map) throws Exception {
		return userDAO.selectId(map);
	}
	
	//프로필사진수정
	@Override
	public void UserProfile(Map<String, Object> map, HttpServletRequest request) throws Exception {
		Map<String,Object> list = fileUtils.parseInsertFileInfo(map, request);
			userDAO.UserProfile(list);
	}
	
	/*@Override
	public List<Map<String, Object>> UserMovieByRecent(Map<String, Object> map) throws Exception {
		return userDAO.UserMovieByRecent(map);
	}*/

	@Override
	public List<Map<String, Object>> UserMovieAll(Map<String, Object> map) throws Exception {
		return userDAO.UserMovieAll(map);
	}
	@Override
	public List<Map<String, Object>> UserDramaAll(Map<String, Object> map) throws Exception {
		return userDAO.UserDramaAll(map);
	}
	@Override
	public List<Map<String, Object>> UserAdAll(Map<String, Object> map) throws Exception {
		return userDAO.UserAdAll(map);
	}
	/*보고싶어요*/
	@Override
	public Map<String, Object> userWishList(Map<String, Object> map) throws Exception {
		return userDAO.userWishList(map);
	}
	@Override
	public void insertWishList(Map<String, Object> map) throws Exception {
		userDAO.insertWishList(map);
	}
	@Override
	public void updateWishList(Map<String, Object> map) throws Exception {
		userDAO.updateWishList(map);
	}
	/*보고싶어요*/

	@Override
	public List<Map<String, Object>> userDramaList(Map<String, Object> map) throws Exception {
		return userDAO.userDramaList(map);
	}
	
	@Override
	public List<Map<String, Object>> userMovieList(Map<String, Object> map) throws Exception {
		return userDAO.userMovieList(map);
	}
	@Transactional
	@Override
	public void deleteUserOne(Map<String, Object> map) throws Exception {
		userDAO.deleteMyPage(map);
		userDAO.deleteAdLike(map);
		userDAO.deleteAdComment(map);
		userDAO.deleteDramaLike(map);
		userDAO.deleteDramaComment(map);
		userDAO.deleteMovieLike(map);
		userDAO.deleteMovieComment(map);
		userDAO.deleteUserOne(map);
	}

	@Override
	public int totalMyMovie(Map<String, Object> map) throws Exception {
		return userDAO.totalMyMovie(map);
	}

	@Override
	public int totalMyDrama(Map<String, Object> map) throws Exception {
		return userDAO.totalMyDrama(map);
	}

	@Override
	public int totalMyAd(Map<String, Object> map) throws Exception {
		return userDAO.totalMyAd(map);
	}

	@Override
	public String totalMyMovieLike(Map<String, Object> map) throws Exception {
		return userDAO.totalMyMovieLike(map);
	}

	@Override
	public String totalMyDramaLike(Map<String, Object> map) throws Exception {
		return userDAO.totalMyDramaLike(map);
	}
	
	
	

}






















