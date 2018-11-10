package com.polystar.app.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.polystar.app.exception.CustomException;

public class AppUtil {

	public static void startRead(String fileName, int port) {

		try {
			ServerSocket serverSocket;

			serverSocket = new ServerSocket(port);

			String filePath = getFilePathToLoad(fileName);

			while (true) {

				System.out.println("Server starts at port: "+port);
				Socket socket = serverSocket.accept();

				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

				String message = (String) ois.readObject();

				if (message.equalsIgnoreCase(AppConstants.READ_FILE)) {

					FileInputStream fileInputStream = new FileInputStream(filePath);
					Scanner scanner = new Scanner(fileInputStream, AppConstants.DEFAULT_CHARSET);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						oos.writeObject(line);
					}
					scanner.close();
				}
				socket.close();
				serverSocket.close();
			}
		} catch (IOException e) {
			new CustomException("Server", "Error in connecting to file");
		} catch (ClassNotFoundException e) {
			new CustomException("Server", "Error Class not found Exception");
		} catch (Exception e) {
			new CustomException("Server", "General Error");
		}
	}

	private static String getFilePathToLoad(String fileName) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String filePath = classloader.getResource(fileName).getPath();
		return filePath;
	}

}
