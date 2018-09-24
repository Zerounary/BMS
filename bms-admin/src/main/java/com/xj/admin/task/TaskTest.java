package com.xj.admin.task;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("taskTest")
public class TaskTest {

	private Logger logger = LogManager.getLogger(TaskTest.class.getName());

	public void run() {
		for (int i = 0; i < 1; i++) {
			logger.debug(i + " run......................................" + (new Date()));
		}

	}

}
