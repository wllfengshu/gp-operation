package app.wllfengshu.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.service.RecordingService;
import app.wllfengshu.util.LogUtils;

@Controller
@Path("/operation/recording")
public class RecordingRest {
	
	@Autowired
	private RecordingService recordingService ;
    
	/**
	 * @title 查询所有录音信息
	 * @param sessionId
	 * @param request
	 * @param response
	 * @return
	 */
    @GET
    public Response getRecordings(@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@HeaderParam(value="tenant_id") String tenant_id,
    		@QueryParam("token") String token,
    		@QueryParam("ani") String ani,@QueryParam("dnis") String dnis,
    		@QueryParam("pageNo") int pageNo,@QueryParam("pageSize") int pageSize,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=recordingService.getRecordings(sessionId,user_id,tenant_id,token,ani,dnis,pageNo,pageSize);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# RecordingRest-getRecordings,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 查询录音详情
     * @param Recording_id 录音ID
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/{recording_id}/")
    public Response getRecording(@PathParam("recording_id")String recording_id,
    		@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=recordingService.getRecording(recording_id,sessionId,user_id);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# RecordingRest-getRecording,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 删除录音信息
     * @param Recording_id 录音ID
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @DELETE
    @Path("/{recording_id}/")
    public Response deleteRecording(@PathParam("recording_id")String recording_id,
    		@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=recordingService.deleteRecording(recording_id,sessionId,user_id);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# RecordingRest-deleteRecording,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
}
