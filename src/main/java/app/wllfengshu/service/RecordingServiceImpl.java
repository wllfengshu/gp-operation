package app.wllfengshu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.RecordingDao;
import app.wllfengshu.entity.Recording;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.AuthUtil;

@Service
public class RecordingServiceImpl implements RecordingService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private RecordingDao recordingDao;
	
	@Override
	public String getRecordings(String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		List<Recording> recordings = recordingDao.getRecordings(user_id);
		responseMap.put("data", recordings);
		responseMap.put("count", recordings.size());
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String getRecording(String recording_id,String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		Recording recording = recordingDao.getRecording(recording_id);
		responseMap.put("data", recording);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String deleteRecording(String recording_id,String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		recordingDao.deleteRecording(recording_id);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}

}
