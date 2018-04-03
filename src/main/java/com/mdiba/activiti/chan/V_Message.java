package com.mdiba.activiti.chan;

public class V_Message {
	String message_id;
	String to_addr;
	String to_addr_type = "msisdn";
	String content;
	String transport_type = "sms";
	String direction = "fromAct";
	
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getTo_addr() {
		return to_addr;
	}
	public void setTo_addr(String to_addr) {
		this.to_addr = to_addr;
	}
	public String getTo_addr_type() {
		return to_addr_type;
	}
	public void setTo_addr_type(String to_addr_type) {
		this.to_addr_type = to_addr_type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTransport_type() {
		return transport_type;
	}
	public void setTransport_type(String transport_type) {
		this.transport_type = transport_type;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
}
