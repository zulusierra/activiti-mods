package com.mdiba.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import com.mdiba.activiti.chan.TaskPublisher;

public class MyEventListener implements ActivitiEventListener {

	  public void onEvent(ActivitiEvent event) {
	    switch (event.getType()) {
	    case TASK_CREATED:
	    	Object createdEnt = ((ActivitiEntityEvent) event).getEntity();
	    	if (createdEnt instanceof TaskEntity) {
	    		System.out.println("mdibalog: Explorer - TASK_CREATED event fired.");
	    		TaskEntity tent = (TaskEntity) createdEnt;

	    		try {
	    			TaskPublisher tp = new TaskPublisher(event.getEngineServices(), tent);
	    			tp.sendCreationNotification();	
	    		} catch (Exception e) {
	    			System.out.println(e.getMessage());
	    		}
	    	}
	    	break;
	    case TASK_COMPLETED:
	    	Object completedEnt = ((ActivitiEntityEvent) event).getEntity();
	    	if (completedEnt instanceof TaskEntity) {
	    		System.out.println("mdibalog: Explorer - TASK_COMPLETED event fired.");
	    		TaskEntity tent = (TaskEntity) completedEnt;

	    		try {
	    			TaskPublisher tp = new TaskPublisher(event.getEngineServices(), tent);
	    			tp.sendCompletionNotification();	
	    		} catch (Exception e) {
	    			System.out.println(e.getMessage());
	    		}
	    	}
	    	break;
	      default:
	    }
	  }

	  public boolean isFailOnException() {
	    // The logic in the onEvent method of this listener is not critical, exceptions
	    // can be ignored if logging fails...
	    return false;
	  }
}
