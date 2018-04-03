package com.mdiba.activiti.chan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.EngineServices;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

public class TaskPublisher {

	private EngineServices m_es;
	private TaskEntity m_tent;
	
	public String chanPostTaskUrl 	= "http://192.168.56.101:3000/api/tasks";
	public String chanPatchTaskUrl 	= "http://192.168.56.101:3000/api/task_by_taskid";
	
	public void postData(String input) {
		try {
			
			StringRepresentation stringRep = new StringRepresentation(input);
			stringRep.setMediaType(MediaType.APPLICATION_JSON);
			ClientResource resource = new ClientResource(chanPostTaskUrl);
			resource.setMethod(Method.POST);
			//ChallengeResponse cred = new ChallengeResponse(ChallengeScheme.CUSTOM);
			
			resource.post(stringRep).write(System.out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void patchData(String input) {
		//This method should actually use http method 'patch'.
		try {
			StringRepresentation stringRep = new StringRepresentation(input);
			stringRep.setMediaType(MediaType.APPLICATION_JSON);
			ClientResource resource = new ClientResource(chanPatchTaskUrl);
			//resource.setMethod(Method.PATCH);
			resource.setMethod(Method.POST);
			
			//ChallengeResponse cred = new ChallengeResponse(ChallengeScheme.CUSTOM);
			//resource.patch(stringRep).write(System.out);
 			resource.post(stringRep).write(System.out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String createTaskCompleteJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		org.activiti.engine.task.Task task = (org.activiti.engine.task.Task) m_tent;
		
		TaskStructRoot tsr = new TaskStructRoot();
		Task t = new Task();
		tsr.setTask(t);
		t.setTaskid(task.getId());
		t.setProcessed(true);

		String ret_str = "";
		
		try {
			ret_str = mapper.writeValueAsString(tsr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret_str;
	}
	
	public String createTaskCreateJson() {
		ObjectMapper mapper = new ObjectMapper();
		//org.activiti.engine.task.Task task = m_es.getTaskService().createTaskQuery().taskId(m_tent.getId()).singleResult();
		
		org.activiti.engine.task.Task task = (org.activiti.engine.task.Task) m_tent;
		
		TaskStructRoot tsr = new TaskStructRoot();
		Task t = new Task();
		tsr.setTask(t);
		t.setTaskid(task.getId());
		t.setUrl("http://api...");
		t.setAssignee(task.getAssignee());
		t.setName(task.getName());
		t.setDescription(task.getDescription());
		t.setProcessed(false);
		
		TaskFormData tfd = m_es.getFormService().getTaskFormData(task.getId());
		List<FormProperty> fprops = tfd.getFormProperties();
		
		List<Form_Properties_Attributes> l_fpa = new ArrayList<Form_Properties_Attributes>();
		t.setForm_properties_attributes(l_fpa);
				
		Iterator<FormProperty> itr = fprops.iterator();
		while(itr.hasNext()) {
			FormProperty fp = itr.next();
			Form_Properties_Attributes fpa = new Form_Properties_Attributes();
			fpa.setFormpropid(fp.getId());
			fpa.setName(fp.getName());
			fpa.setWriteable(fp.isWritable());
			String fptype = fp.getType().getName();
			fpa.setFormproptype(fptype);
			String formvalue = fp.getValue();
			fpa.setValue(formvalue);
			
			List<Enum_Values_Attributes> l_eva = new ArrayList<Enum_Values_Attributes>();
			fpa.setEnum_values_attributes(l_eva);
			
			//todo: also cater for "datePattern"
			if(fptype == "enum") {
				Map<String, String> enummap = (Map<String, String>) fp.getType().getInformation("values");
				
				Iterator<String> itrEn = enummap.keySet().iterator();
				while(itrEn.hasNext()) {
					Enum_Values_Attributes eva = new Enum_Values_Attributes();
					String enkey = itrEn.next();
					String enval = enummap.get(enkey);
					eva.setEnumvalid(enkey);
					eva.setName(enval);
					l_eva.add(eva);
				}
			}
			l_fpa.add(fpa);		
		}		
			
		String ret_str = "";
		
		try {
			ret_str = mapper.writeValueAsString(tsr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret_str;
	}
	
	public void sendCreationNotification() {
		try {
			org.activiti.engine.task.Task task = (org.activiti.engine.task.Task) m_tent;
			
			if(task.getTaskDefinitionKey().startsWith("m_")) {
				String jsonstr = createTaskCreateJson();
				System.out.println("mdibalog: jsonstr = " + jsonstr);
				postData(jsonstr);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void sendCompletionNotification() {
		try {
			org.activiti.engine.task.Task task = (org.activiti.engine.task.Task) m_tent;
			
			if(task.getTaskDefinitionKey().startsWith("m_")) {
				String jsonstr = createTaskCompleteJson();
				System.out.println("mdibalog: jsonstr = " + jsonstr);
				patchData(jsonstr);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		TaskPublisher tp = new TaskPublisher();
		tp.testPatch();
	}
	
	public void testPatch() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
	
		TaskStructRoot tsr = new TaskStructRoot();
		Task t = new Task();
		tsr.setTask(t);
		t.setTaskid("15008");
		t.setProcessed(true);

		String ret_str = "";
	
		try {
			ret_str = mapper.writeValueAsString(tsr);
			patchData(ret_str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public TaskPublisher() {
		super();
	}
	
	public TaskPublisher(EngineServices es, TaskEntity tent) {
		m_es = es;
		m_tent = tent;
	}
	
	public void setEngineServices(EngineServices es) {
		m_es = es;
	}
	
	public void setTaskEntity(TaskEntity tent) {
		m_tent = tent;
	}
	
	public EngineServices getEngineServices() {
		return m_es;
	}
	
	public TaskEntity getTaskEntity() {
		return m_tent;
	}
	
}
