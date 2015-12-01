package connect;

import httpConnect.ConnectionHandleInteface;


public class ServerHandle {
	private String result;
	ConnectionHandleInteface ch;
 
	public ServerHandle(String result,
			ConnectionHandleInteface ch) throws Exception {
	this.result=result;
	this.ch=ch;
	
}
   public Object Response_Hand() throws Exception{
	   return ch.handResponse(result);
  }
}

