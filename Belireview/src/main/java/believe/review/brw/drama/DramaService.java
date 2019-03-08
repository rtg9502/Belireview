package believe.review.brw.drama;

import java.util.List;
import java.util.Map;

public interface DramaService {
	
	List<Map<String,Object>> selectBoardList(Map<String,Integer> map) throws Exception;
	
	Map<String,Object> dramaDetail(Map<String,Object> map) throws Exception;
	
	List<Map<String,Object>> dramaActor(Map<String,Object> map) throws Exception;
	
	List<Map<String,Object>> dramaCommentByRecent(Map<String, Object> map) throws Exception;
	
	List<Map<String,Object>> dramaCommentByLike(Map<String, Object> map) throws Exception;
	
	int totalDramaCount(Map<String,Object> map) throws Exception;
}
