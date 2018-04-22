package app.wllfengshu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.Recording;

/**
 * @title 录音记录管理，不提供添加和更新操作
 */
@Repository
public interface RecordingDao {
	public List<Recording> getRecordings(@Param("user_id")String user_id);

	public Recording getRecording(@Param("id")String id);

	public void deleteRecording( @Param("id")String id);
	
}
