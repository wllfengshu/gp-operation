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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class RecordingServiceImpl implements RecordingService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private RecordingDao recordingDao;
	
	@Override
	public String getRecordings(String sessionId,String user_id,String tenant_id,String token,String ani,String dnis,int pageNo,int pageSize) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		JSONObject user = AuthUtil.instance.getUser(sessionId, user_id);
		if (null==user || user.isNullObject()) {
			throw new NotAcceptableException("没有权限");
		}
		JSONArray roles = user.getJSONArray("roles");
		JSONObject role = roles.getJSONObject(0);
		String role_name=role.getString("role_name");
		if (!"qc".equals(role_name)) {
			throw new NotAcceptableException("角色异常");
		}
		List<Recording> recordings =null;
		if (token.equals("crm")) {//质检员对当前租户下所有录音进行质检
			recordings = recordingDao.getRecordings(tenant_id,ani,dnis,(pageNo-1)*pageSize,pageSize);
		}else{//其他token直接返回失败
			throw new NotAcceptableException("凭证异常");
		}
		responseMap.put("data", recordings);
		responseMap.put("count", recordingDao.getCount(tenant_id,ani,dnis));
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
