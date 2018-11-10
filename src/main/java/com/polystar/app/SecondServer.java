package com.polystar.app;

import com.polystar.app.util.AppConstants;
import com.polystar.app.util.AppUtil;

public class SecondServer {
	public static void main(String[] args) {
		AppUtil.startRead(AppConstants.SECOND_FILE_NAME,AppConstants.SECOND_SERVER_PORT );
	}
}
