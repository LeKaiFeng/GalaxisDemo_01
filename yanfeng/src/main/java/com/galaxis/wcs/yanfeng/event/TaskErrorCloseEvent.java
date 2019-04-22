package com.galaxis.wcs.yanfeng.event;

import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TaskErrorCloseEvent extends ApplicationEvent {

    private TaskEvent taskEvent;

    public TaskErrorCloseEvent(Object source, TaskEvent taskEvent) {
        super(source);
        this.taskEvent = taskEvent;
    }

	public TaskEvent getTaskEvent() {
		return taskEvent;
	}

	public void setTaskEvent(TaskEvent taskEvent) {
		this.taskEvent = taskEvent;
	}

	
}
