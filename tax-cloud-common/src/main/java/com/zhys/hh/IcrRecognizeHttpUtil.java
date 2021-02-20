package com.zhys.hh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IcrRecognizeHttpUtil {

	public static String sendPostPath(String url, String paramJson) {

		String returninfo = null;
		URL httpUrl;
		String resultOfReport = null;
		try {
			httpUrl = new URL(url);

			HttpURLConnection huc = (HttpURLConnection) httpUrl
					.openConnection();
			huc.setRequestMethod("POST");
			huc.setDoInput(true);
			huc.setDoOutput(true);
			huc.setRequestProperty("Content-Type", "application/json");
			// 链接地址
			huc.connect();
			OutputStreamWriter writer = new OutputStreamWriter(
					huc.getOutputStream());
			// 发送参数
			writer.write(paramJson);
			// 清理当前编辑器的左右缓冲区，并使缓冲区数据写入基础流
			writer.flush();
			int code = huc.getResponseCode();
			//System.out.println(code);

			BufferedReader br = null;
			if (code == 200) {

				br = new BufferedReader(new InputStreamReader(
						huc.getInputStream(), "UTF-8"));
				String line;

				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					returninfo = line;
					sb.append(line);
					System.out.print(returninfo);
				}
				System.out.println("\n");

				resultOfReport = sb.toString();
			}

			huc.disconnect();
			if (br != null) {
				br.close();
			}
			if (writer != null) {
				writer.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return resultOfReport;
	}
}
