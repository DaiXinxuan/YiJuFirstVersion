package com.eip.post;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import connect.NetHelper;

import testandmanage.LogUtil;
import testandmanage.MyApplication;

public class post {
	  
	   
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			if(NetHelper.IsHaveInternet(MyApplication.getContext())!=true){
				LogUtil.d("post","网络连接异常");
			};
			URLConnection conn = realUrl.openConnection();
			conn.setReadTimeout(90000);
			conn.setConnectTimeout(90000);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			LogUtil.d("post", "正常7，开始连接");
			out = new PrintWriter(conn.getOutputStream());
			LogUtil.d("post", "正常8");
			out.print(param);
			out.flush();
			
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			LogUtil.d("post", "正常9,获取输入成功");
			while ((line = in.readLine()) != null) {
				result += line;
			}
			LogUtil.d("post", "正常10");
		} catch (Exception e) {
			System.out.println("出现异常！" + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
