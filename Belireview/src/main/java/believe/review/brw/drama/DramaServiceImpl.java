package believe.review.brw.drama;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("dramaService")

public class DramaServiceImpl  implements DramaService{

	@Resource(name="dramaDAO")
	private DramaDAO dramaDAO;
	
	
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String,Integer> map) throws Exception {
		return dramaDAO.selectBoardList(map);
	}
	@Override
	public Map<String, Object> dramaDetail(Map<String, Object> map) throws Exception {
		return dramaDAO.dramaDetail(map);
	}
	@Override
	public List<Map<String,Object>> dramaActor(Map<String, Object> map) throws Exception {
		return dramaDAO.dramaActor(map);
	}
	@Override
	public List<Map<String, Object>> dramaCommentByRecent(Map<String, Object> map) throws Exception {
		return dramaDAO.dramaCommentByRecent(map);
	}
	@Override
	public List<Map<String, Object>> dramaCommentByLike(Map<String, Object> map) throws Exception {
		return dramaDAO.dramaCommentByLike(map);
	}
	@Override
	public int totalDramaCount(Map<String, Object> map) throws Exception {
		return dramaDAO.totalDramaCount(map);
	}
}
