package different_jsonparse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import testandmanage.LogUtil;
import testandmanage.MyApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

public class Parsenew {
	String address=null;
	public  static String  base_url_picture="http://192.168.0.17:8080/eip";
	
 public Parsenew(String address) {
	this.address=address;
}
 
 public Bitmap parse_picture(){
	URL url = null;
	Bitmap bm=null;
	Bitmap bit=null;
	try {
		url = new URL(base_url_picture+address);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		/*Toast.makeText(MyApplication.getContext(), url+"", Toast.LENGTH_LONG).show();*/
		conn.setDoInput(true);
		conn.setConnectTimeout(6000);//设置超时 
		conn.setRequestMethod("GET");
		conn.setUseCaches(true);//不缓存 
		InputStream in=conn.getInputStream();
	    int len=0;
        byte buf[]=new byte[1024];
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        while((len=in.read(buf))!=-1){
            out.write(buf,0,len);  //把数据写入内存
        }
        out.close();  //关闭内存输出流
        byte[] data2=out.toByteArray(); //把内存输出流转换成byte数组
        bit=BitmapFactory.decodeByteArray(data2, 0, data2.length);
		in.close();
		conn.disconnect();
			}
      catch (Exception e) {
    	  e.printStackTrace();
    	 /* Toast.makeText(MyApplication.getContext(), "big wrong", Toast.LENGTH_LONG).show();*/
		}
	return bit;
	 
 }
 
 public String parse_txt(){
	URL url = null;
	String back_string_data=null;
	try {
		url = new URL(address);
		LogUtil.d("sss",""+url);
		HttpURLConnection connection=(HttpURLConnection) url.openConnection();
		/*connection.connect();*/
		connection.setConnectTimeout(7000);
		connection.setRequestMethod("GET");        //设置请求方法为GET
        /*connection.setDoInput(true); */    
		InputStream is=connection.getInputStream();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=is.read(buffer))!=-1){
			out.write(buffer,0,len);
		}
		LogUtil.d("back_buffer_byte", ""+buffer);
        back_string_data=new String(buffer);
        back_string_data=String.copyValueOf(back_string_data.toCharArray(),
        		0,buffer.length);
		is.close();
		out.close();
			}
      catch (Exception e) {
			e.printStackTrace();
		}
	LogUtil.d("back_string", ""+back_string_data);
	return back_string_data;
	 
 }
}
