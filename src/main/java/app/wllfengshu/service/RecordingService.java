package app.wllfengshu.service;

import app.wllfengshu.exception.NotAcceptableException;

public interface RecordingService {
	
	public String getRecordings(String sessionId,String user_id) throws NotAcceptableException;

	public String getRecording(String recording_id,String sessionId,String user_id) throws NotAcceptableException;

	public String deleteRecording(String recording_id,String sessionId,String user_id) throws NotAcceptableException;
	
}
