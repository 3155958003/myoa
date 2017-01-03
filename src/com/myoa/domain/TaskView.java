package com.myoa.domain;

import org.jbpm.api.task.Task;

/**
 * 任务视图
 * @author Administrator
 *
 */
public class TaskView {
	private Task task;
	private Application application;

	public TaskView(Task task, Application application) {
		this.task = task;
		this.application = application;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
