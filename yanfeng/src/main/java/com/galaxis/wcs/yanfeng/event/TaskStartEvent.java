package com.galaxis.wcs.yanfeng.event;

import com.galaxis.wcs.yanfeng.util.template.TaskEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TaskStartEvent extends ApplicationEvent {

    private TaskEvent taskEvent;

    public TaskStartEvent(Object source, TaskEvent taskEvent) {
        super(source);
        this.taskEvent = taskEvent;
    }
}
