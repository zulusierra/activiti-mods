package com.mdiba.activiti.chan;

import java.util.List;

public class Task {
	String taskid;
	String url;
	String assignee;
	String name;
	String description;
	Boolean processed;
	List<Form_Properties_Attributes> form_properties_attributes;
	
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	public List<Form_Properties_Attributes> getForm_properties_attributes() {
		return form_properties_attributes;
	}
	public void setForm_properties_attributes(
			List<Form_Properties_Attributes> form_properties_attributes) {
		this.form_properties_attributes = form_properties_attributes;
	}
	
}
