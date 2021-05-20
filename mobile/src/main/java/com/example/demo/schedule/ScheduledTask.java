package com.example.demo.schedule;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.demo.model.BusTableModel;
import com.example.demo.model.RealBusModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.service.WatchService;
import com.example.demo.util.FcmUtil;

@Component
public class ScheduledTask {
	private String serviceKey = "n8%2FD0zEruryexLDsaOSUdl2o93qJBNr1zI13UEPXD8BLUVQ4qeUXNh%2F8SmmaqyPtIBIXqtb%2BiEX8GqpIyUqVjw%3D%3D";
	private double measures = 0.001;	// about 100 meter
	
	@Autowired
	FcmUtil fcmUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	WatchService watchService;
	
	private int limit_time = 7200;
	
    @Scheduled(fixedDelay = 5000)
    public void runEvery5secs(){
    	System.out.println("checked.");
        watchService.deleteExpired(limit_time);
        
        List<BusTableModel> bustablemodels = watchService.getBusId();
        for(BusTableModel btm : bustablemodels) {
        	getLocation(btm);
        }
        List<UserModel> users = watchService.findActUser();
        
        for(UserModel user : users) {
        	double lati = user.getLatitude();
        	double longi = user.getLongitude();
        	
        	List<RealBusModel> realDist = watchService.getRealDist();
        	// busTableModel에 routeno, vehicleno, dist 추가.
        	
        	RealBusModel bm = realDist.get(0);
        	for(RealBusModel btm : realDist) {
        		if(Math.abs(bm.getLatitude() - lati) +Math.abs(bm.getLongitude() - longi) > 
        		Math.abs(btm.getLatitude() - lati) +Math.abs(btm.getLongitude() - longi)) {
        			bm = btm;
        		}
        	}
        	if(Math.abs(bm.getLatitude() - lati) +Math.abs(bm.getLongitude() - longi) <= measures) {
        		watchService.hit(bm.getRouteno());	// after, need to modify
        	}
        	
        	List<UserModel> targetUser = watchService.getOverHitUser();
        	if(targetUser!=null) {
        		UserModel us = targetUser.get(0);
        		//	Send FCM to targetUser. 
        		String tokenId=us.getToken();
        		String title="하차 알림 제목입니다.";
        		String content="하차 알림 내용입니다";
        		
        		fcmUtil.send_FCM(tokenId, title, content);
        	}
        }
    }
    
    private void getLocation(BusTableModel btm) {
    	String routeno = btm.getRouteno();
    	String citycode = Integer.toString(btm.getCitycode());
    	
    	String url = "http://openapi.tago.go.kr/openapi/service/BusLcInfoInqireService/getRouteAcctoBusLcList?serviceKey="+ serviceKey + 
    			"&cityCode=" + citycode + "&routeId=" + routeno;
    	
    	DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try {
            dBuilder = dbFactoty.newDocumentBuilder();
            doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        NodeList nList = doc.getElementsByTagName("item");
        System.out.println("파싱할 리스트 수 : "+ nList.getLength());

        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;
                String arg1,arg2,arg3,arg4;
                arg1 = getTagValue("vehicleno", eElement);
                arg2 = getTagValue("routenm", eElement);
                arg3 = getTagValue("gpslati", eElement);
                arg4 = getTagValue("gpslong", eElement);
                
                System.out.println("######################");
                //System.out.println(eElement.getTextContent());
                System.out.println("latitude  : " + getTagValue("gpslati", eElement));
                System.out.println("longitude  : " + getTagValue("gpslong", eElement));
                System.out.println("routenm  : " + getTagValue("routenm", eElement));
                System.out.println("vehicleno  : " + getTagValue("vehicleno", eElement));

                watchService.insertBusLocation(arg1,arg2,arg3,arg4);
        }	// if end
    	
    }
    
    
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }
}