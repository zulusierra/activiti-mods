package com.mdiba.activiti.test;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

public class JavaBpmnTest {
	
	@Rule 
	public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg-mem.xml");
	
	private ProcessInstance startProcessInstance() {
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("initphone", "+2782xxxxxxx");
		return runtimeService.startProcessInstanceByKey("guineaPig1", variableMap);
	}
	
	@Test
	@Deployment(resources={"com/mdiba/activiti/guineapig.bpmn"})
	public void executeJavaService() {
		ProcessInstance processInstance = startProcessInstance();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Date sentsmsdatetime = (Date) runtimeService.getVariable(processInstance.getId(), "sentsmsdatetime");
		assertNotNull(sentsmsdatetime);
		System.out.println("sentsmsdatetime is " + sentsmsdatetime);
	}

}
