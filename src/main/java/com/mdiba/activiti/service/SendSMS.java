package com.mdiba.activiti.service;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.Expression;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdiba.activiti.chan.V_Message;

public class SendSMS implements JavaDelegate {
	
	private Expression msisdn;
	private Expression content;
	
	public String chanPostVMsgUrl = "http://192.168.56.101:3000/api/v_messages";
	
	public void execute(DelegateExecution execution) {
		
		String msgid	= execution.getProcessInstanceId() + ":" + execution.getCurrentActivityId();
		String cellno	= (String) msisdn.getValue(execution);
		String message 	= (String) content.getValue(execution);
		
		String payload = createJsonPayload(msgid, cellno, message);
		
		postData(payload);
		
		execution.setVariable("sentsmsdatetime", new Date());
		System.out.println(content.getValue(execution).toString() + execution.getVariable("sentsmsdatetime"));
	}
	
	public String createJsonPayload(String msgid, String cellno, String message) {
		String retstr = "";
		ObjectMapper mapper = new ObjectMapper();
		
		V_Message vmsg = new V_Message();
		vmsg.setMessage_id(msgid);
		vmsg.setTo_addr(cellno);
		vmsg.setContent(message);
		
		try {
			retstr = mapper.writeValueAsString(vmsg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retstr;
	}

	public void postData(String input) {
		try {
			
			StringRepresentation stringRep = new StringRepresentation(input);
			stringRep.setMediaType(MediaType.APPLICATION_JSON);
			ClientResource resource = new ClientResource(chanPostVMsgUrl);
			resource.setMethod(Method.POST);
			//ChallengeResponse cred = new ChallengeResponse(ChallengeScheme.CUSTOM);
			
			resource.post(stringRep).write(System.out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
