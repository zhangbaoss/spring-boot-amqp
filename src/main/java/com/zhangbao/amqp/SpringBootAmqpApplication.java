package com.zhangbao.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SpringBootAmqpApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringBootAmqpApplication.class, args);
		String str = "2;5;6;7;8;9;13;14;15;16;20";
		int size = getNumberStrSize(str);
		System.out.println("size : " + size);
	}

	private static int getNumberStrSize(String str) {
		String[] arr = str.split(";");
		ArrayList<String> list = new ArrayList<>();
		list.add(arr[0]);
		ArrayList<String> nowList = new ArrayList<>();
		for (int i = 1; i < arr.length; i++) {
			if (Integer.valueOf(arr[i - 1]) - Integer.valueOf(arr[i]) != -1) {
				if (list.size() > nowList.size()) {
					nowList = list;
				}
				list = new ArrayList<>();
			}
			list.add(arr[i]);
		}
		return nowList.size();
	}

}
