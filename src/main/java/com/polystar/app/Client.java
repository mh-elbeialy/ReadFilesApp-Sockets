package com.polystar.app;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import com.polystar.app.exception.CustomException;
import com.polystar.app.util.AppConstants;

public class Client {
	public static void main(String[] args) throws InterruptedException {

		Map<String, Integer> wordsMap = new HashMap<String, Integer>();
		CountDownLatch countDownLatch = new CountDownLatch(2);

		new Thread(() -> {
			processOutput(wordsMap, AppConstants.FIRST_SERVER_PORT);
			countDownLatch.countDown();
		}).start();
		new Thread(() -> {
			processOutput(wordsMap, AppConstants.SECOND_SERVER_PORT);
			countDownLatch.countDown();
		}).start();

		countDownLatch.await();

		System.out.println(findMostFrequencyWord(wordsMap));
	}

	public static void processOutput(Map<String, Integer> wordsMap, int port) {

		InetAddress host = null;
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			new CustomException("Client", e1.getMessage());
		}

		try (Socket socket = new Socket(host.getHostName(), port);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

			oos.writeObject(AppConstants.READ_FILE);
			while (true) {
				String line = (String) ois.readObject();
				wordsMap = generateWordsMap(wordsMap, line);
			}

		} catch (EOFException e) {
			return;
		} catch (Exception e) {
			new CustomException("Client", e.getMessage());
		}
	}

	private static synchronized Map<String, Integer> generateWordsMap(Map<String, Integer> wordsMap, String line) {
		String[] words = line.split(" ");
		for (int i = 0; i < words.length; i++) {
			String s = words[i];
			if (wordsMap.keySet().contains(s)) {
				Integer count = wordsMap.get(s) + 1;
				wordsMap.put(s, count);
			} else
				wordsMap.put(s, 1);
		}
		return wordsMap;
	}

	public static String findMostFrequencyWord(Map<String, Integer> words) {
		Map<String, Integer> sorted = words.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		StringBuffer buffer = new StringBuffer();
		int max = 5;
		int count = 0;
		for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
			if (count >= max)
				break;

			buffer.append(" " + entry.getKey() + "  : " + entry.getValue());
			buffer.append(System.lineSeparator());
			count++;
		}
		return buffer.toString();
	}

}
