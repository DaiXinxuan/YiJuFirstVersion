package differentjavabean;

import java.util.List;
import java.util.Map;

public class Main_data_javabean {
 private String projectname;
 List<Map<String,String>> picturelist;
// List<Map<String,String>> houselist;
 List<String[]> housemessage;
 public String getProjectname(){
	 return projectname;
 }
 public void setProjectname(String projectname){
	 this.projectname=projectname;
 }
 public List<Map<String,String>> getPicturelist(){
	 return picturelist;
 }
 public void setPicturelist(List<Map<String,String>> picturelist){
	 this.picturelist=picturelist;
 }
// public List<Map<String,String>> getHouselist(){
//	 return houselist;
// }
// public void setHouselist(List<Map<String,String>> houselist){
//	 this.houselist=houselist;
// }
 public List<String[]> getHouseMessage(){
	 return housemessage;
 }
 
 public void setHouseMesseag(List<String[]> housemessage){
	 this.housemessage=housemessage;
 }
 
}
