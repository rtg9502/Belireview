package believe.review.brw.admin.drama;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import believe.review.brw.common.common.AbstractDAO;

@Repository("adminDramaDAO")
public class AdminDramaDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDramaList(){
		return (List<Map<String, Object>>) selectList("admin.selectDramaList");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDramaList_order(Map<String, Object> map){
		
		return (List<Map<String, Object>>) selectList("admin.selectDramaList_order", map);
	}
}
