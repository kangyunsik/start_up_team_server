package com.example.demo.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.FacilityModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.FacilityService;
import com.example.demo.service.UserService;

@Component
public class Calculator {
	@Autowired
	FacilityService facilityService;
	
	@Autowired
	UserService userService;
	
	public List<FacilityModel> getPoint() {
		List<UserModel> users = userService.printUser();
		List<FacilityModel> m_facility = facilityService.printBestFacility();
		
		String http[] = makeHttp2(users,m_facility);
		
		for(int k=0;k<http.length;k++) {
			String httpres = httpRequest(http[k]);
			System.out.println("httpres: "+httpres);
			
			try {
				JSONParser jsonParser = new JSONParser();
				
	            JSONObject jsonObject = (JSONObject) jsonParser.parse(httpres);
	            
	            JSONArray rowsArray = (JSONArray) jsonObject.get("rows");
	            
	            for(int i=0;i<rowsArray.size();i++){
	            	   int timesum = 0;
	            	   JSONObject rowsObject = (JSONObject) rowsArray.get(i);
	            	   JSONArray elementsArray = (JSONArray) rowsObject.get("elements");
	            	   
	            	   for(int j = 0; j < elementsArray.size(); j++) {
	            		   JSONObject elementsObject = (JSONObject) elementsArray.get(j);
	            		   if(!((String)elementsObject.get("status")).equals("ZERO_RESULTS")) {
	            			   JSONObject durationObject = (JSONObject) elementsObject.get("duration");

		            		   int time = timeToInt((String) durationObject.get("text"));
		            		   timesum += time;
		            		   System.out.println("time : " + time);
	            		   }
	            		   else 
		            		   timesum += 9999;
	            	   }
	            
	            	   System.out.println(m_facility.get(i+k*100/users.size()).getFacility_name()+": "+timesum);
	            	   m_facility.get(i+k*100/users.size()).setTime(timesum);
	            }
	        } catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
		}
		
		Collections.sort(m_facility, new Comparator<FacilityModel>() {
	        @Override
	        public int compare(FacilityModel f1, FacilityModel f2) {
	        	if(f1.getTime() > f2.getTime())
	        		return 1;
	        	else if (f1.getTime() < f2.getTime())
	        		return -1;
	        	else
	        		return 0;
	        }
		});
		
		//최소 시간 우선 순위 정렬
		/*Collections.sort(m_facility, new Comparator<FacilityModel>() {
	        @Override
	        public int compare(FacilityModel f1, FacilityModel f2) {
	        	int time1 = 0;
	        	int time2 = 0;
	        	
	        	System.out.println("a");
	        	double lati[] = new double[2];
	        	double longi[] = new double[2];
	        	
	        	String httpres = "";
	        	
	        	lati[0] = f1.getLatitude();
	        	longi[0] = f1.getLongitude();
	        	
	        	for(int i = 0;i<users.size();i++) {
	        		lati[1] = users.get(i).getLatitude();
	        		longi[1] = users.get(i).getLongitude();
	        		
	        		httpres = httpRequest(lati,longi);
	        		time1 += timeToInt(jsonTimeParsing(httpres));
	        	}
	        	
	        	lati[0] = f2.getLatitude();
	        	longi[0] = f2.getLongitude();
	        	
	        	for(int i = 0;i<users.size();i++) {
	        		lati[1] = users.get(i).getLatitude();
	        		longi[1] = users.get(i).getLongitude();
	        		
	        		httpres = httpRequest(lati,longi);
	        		time2 += timeToInt(jsonTimeParsing(httpres));
	        	}
	        	
	        	if(time1 > time2)
	        		return 1;
	        	else if (time1 < time2)
	        		return -1;
	        	else
	        		return 0;
	        }
	    });*/

		return m_facility;
	}
	
	//중간 지점 찾기
	/*public void midPoint(List<UserModel> users){
		int size = users.size();
		double midPointLatitude = 0;
		double midPointLongitude = 0;
		
		for(int i = 0;i<size;i++) {
			midPointLatitude = midPointLatitude + users.get(i).getLongitude();
			midPointLongitude = midPointLongitude + users.get(i).getLatitude();
		}
		
		midPointLatitude = midPointLatitude/size;
		midPointLongitude = midPointLongitude/size;
		
		Latitude = midPointLatitude;
		Longitude = midPointLongitude;
	}*/
	
	//주소 생성
	public String makeHttp(double[] latitude, double[] longitude) {
		String s = "";
		String API_Key = "AIzaSyCfoe52Ldl1_LXZfUlwN17Htm1lzkW4MtY";
		s = "https://maps.googleapis.com/maps/api/directions/json?origin="+ Double.toString(latitude[0])+','+Double.toString(longitude[0])+"&destination="
		+Double.toString(latitude[1])+','+Double.toString(longitude[1])+"&mode=transit&language=ko&departure_time=now&key="+ API_Key;
		
		return s;
	}
	
	public String[] makeHttp2(List<UserModel> users, List<FacilityModel> m_facility) {
		  int size = (int) Math.ceil((double)users.size() * (double)m_facility.size()/100);
		  int block = 100/users.size();
		  int rest = m_facility.size()%block;
		  String[] sArray = new String[size];
		
		  
		  System.out.println("usersize: "+users.size()+", m_facilitysize: "+m_facility.size()+", size: "+size+", block: "+block);
		  
	      String s = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
	      String API_Key = "AIzaSyCfoe52Ldl1_LXZfUlwN17Htm1lzkW4MtY";
	      
	      for(int j = 0;j<size;j++) {
	    	  sArray[j] = s;
	    	  sArray[j] += m_facility.get(block*j).getLatitude() + "," + m_facility.get(block*j).getLongitude();
	      
	    	  for(int i = 1;i<((j==size-1&&rest!=0)?rest:block);i++) {
	    		  sArray[j] += "|" + m_facility.get(i+block*j).getLatitude() + "," + m_facility.get(i+block*j).getLongitude();   
	    	  }
	      
	    	  sArray[j] += "&destinations=" + users.get(0).getLatitude() + "," + users.get(0).getLongitude();
	      
	    	  for(int i = 1;i<users.size();i++) {
	    		  sArray[j] += "|" + users.get(i).getLatitude() + "," + users.get(i).getLongitude();   
	    	  }
	      
	    	  sArray[j] += "&mode=transit&language=ko&departure_time=now&key=" + API_Key;
	    	  		
	    	  System.out.println("http"+j+": "+ sArray[j]);
	      }
	      return sArray;
	   }

	
	//제이슨 받아오기
	@SuppressWarnings("static-access")
	public String httpRequest(String s) {
		String receiveMsg = "";
		String str;
		URL url = null;
		try {
			url = new URL(s);
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			if(conn.getResponseCode() == conn.HTTP_OK) {
				InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
				BufferedReader reader = new BufferedReader(tmp);
				StringBuffer buffer = new StringBuffer();
				while((str = reader.readLine()) != null) {
					buffer.append(str);
				}
				receiveMsg = buffer.toString();
				
				reader.close();
			}
			else {
				//Log.i("통신 결과",conn.getResponseCode()+"에러");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return receiveMsg;
	}
	
	//시간 형변환
	public int timeToInt(String s) {
		int result = 0;
		String[] split = s.split("일 |시간 |분");
		
		if(split.length == 1) {
			//try {
			result = Integer.parseInt(split[0]);
			//}catch(Exception e){
			//	return 0;
			//}
		}
		else if(split.length == 2) {
			result = Integer.parseInt(split[0])*60 + Integer.parseInt(split[1]);
		}
		else {
			result = Integer.parseInt(split[0])*1440 + Integer.parseInt(split[1])*60 + Integer.parseInt(split[2]);
		}
		
		return result;
	}
	
	//시간 계산
	public String jsonTimeParsing(String s){
		String result = "";
		try {
			JSONParser jsonParser = new JSONParser();
			
            JSONObject jsonObject = (JSONObject) jsonParser.parse(s);
            
            JSONArray routesArray = (JSONArray) jsonObject.get("routes");
            
            for(int i=0;i<routesArray.size();i++){
            	   JSONObject legsObject = (JSONObject) routesArray.get(i);
            	   JSONArray legsArray = (JSONArray) legsObject.get("legs");
            	   
            	   for(int j = 0; j < legsArray.size(); j++) {
            		   JSONObject durationObject = (JSONObject) legsArray.get(j);
            		   JSONObject textObject = (JSONObject) durationObject.get("duration");

            		   result = (String) textObject.get("text");
            		   System.out.println("시간: "+result);
            	   }
            }
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//대중교통 횟수 계산
	public int jsonTransitParsing(String s) {
		int count = 0;
		
		try {
			JSONParser jsonParser = new JSONParser();
			
            JSONObject jsonObject = (JSONObject) jsonParser.parse(s);
            
            JSONArray routesArray = (JSONArray) jsonObject.get("routes");
            
            for(int i=0;i<routesArray.size();i++){
            	   JSONObject legsObject = (JSONObject) routesArray.get(i);
            	   JSONArray legsArray = (JSONArray) legsObject.get("legs");
            	     	   
            	   for(int j = 0; j < legsArray.size(); j++) {
            		   JSONObject durationObject = (JSONObject) legsArray.get(j);
            		   JSONArray stepsArray = (JSONArray) durationObject.get("steps");
            		   
            		   for(int k = 0; k < stepsArray.size(); k++) {
            			   JSONObject stepsObject = (JSONObject) stepsArray.get(k);            			  
 
            			   if(((String)stepsObject.get("travel_mode")).equals("TRANSIT"))
            				   count++;
            		   }
            	   }
            }
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}			
		return count;
	}
	
	//길찾기
	public void jsonParsing(String s){
		try {
			JSONParser jsonParser = new JSONParser();
			
            JSONObject jsonObject = (JSONObject) jsonParser.parse(s);
            
            JSONArray routesArray = (JSONArray) jsonObject.get("routes");
 
            for(int i=0;i<routesArray.size();i++){
            	   JSONObject legsObject = (JSONObject) routesArray.get(i);
            	   JSONArray legsArray = (JSONArray) legsObject.get("legs");
            	   
            	   for(int j = 0; j < legsArray.size(); j++) {
            		   JSONObject durationObject = (JSONObject) legsArray.get(j);
            		   JSONArray stepsArray = (JSONArray) durationObject.get("steps");
            		   
            		   for(int k = 0; k < stepsArray.size(); k++) {
            			   JSONObject stepsObject = (JSONObject) stepsArray.get(k);
            			   
            			   JSONObject distanceTextObject = (JSONObject)stepsObject.get("distance");
            			   System.out.println("거리: "+distanceTextObject.get("text"));
            			   
            			   JSONObject durationTextObject = (JSONObject)stepsObject.get("duration");
            			   System.out.println("시간: "+durationTextObject.get("text"));
            			   
            			   JSONObject end_locationTextObject = (JSONObject)stepsObject.get("end_location");
            			   System.out.println("도착지: "+end_locationTextObject.get("lat")+" "+ end_locationTextObject.get("lng"));
            			   
            			   System.out.println("방법: "+stepsObject.get("html_instructions"));
            			   
            			   System.out.println();
            		   }
            	   }
            }
        } catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	}
}
