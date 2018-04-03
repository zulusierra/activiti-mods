package com.mdiba.activiti.chan;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FiddleAround {

	public static void main(String[] args) {
		FiddleAround fa = new FiddleAround();
		fa.generateJson();
		String s_json = fa.generateJson();
		System.out.println(s_json);
	}
	
	public String generateJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		
		TaskStructRoot tsr = new TaskStructRoot();
		Task t = new Task();
		tsr.setTask(t);
		t.setTaskid("A8");
		t.setProcessed(false);

		String ret_str = "";
		
		try {
			ret_str = mapper.writeValueAsString(tsr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret_str;
	}
	
}
