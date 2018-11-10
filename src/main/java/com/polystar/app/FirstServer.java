package com.polystar.app;

import com.polystar.app.util.AppConstants;
import com.polystar.app.util.AppUtil;

public class FirstServer {
	public static void main(String[] args) {
		AppUtil.startRead(AppConstants.FIRST_FILE_NAME, AppConstants.FIRST_SERVER_PORT);
	}
}
