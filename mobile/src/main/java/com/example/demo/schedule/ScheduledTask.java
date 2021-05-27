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
import com.example.demo.model.MessageQueue;
import com.example.demo.model.RealBusModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.WatchModel;
import com.example.demo.service.MessageQueueService;
import com.example.demo.service.UserService;
import com.example.demo.service.WatchService;
import com.example.demo.util.FcmUtil;

@Component
public class ScheduledTask {
	private String serviceKey = "n8%2FD0zEruryexLDsaOSUdl2o93qJBNr1zI13UEPXD8BLUVQ4qeUXNh%2F8SmmaqyPtIBIXqtb%2BiEX8GqpIyUqVjw%3D%3D";
	private double measures = 0.007; // about 250 meter
	//private double measures = 20000.0; // about 130 meter

	@Autowired
	FcmUtil fcmUtil;

	@Autowired
	UserService userService;

	@Autowired
	WatchService watchService;

	@Autowired
	MessageQueueService messageQueueService;

	private int limit_time = 20000;

	@Scheduled(fixedDelay = 5000)
	public void messageSender() {
		List<MessageQueue> messageQueue = messageQueueService.getMessage();
		UserModel user;
		
		for (MessageQueue msg : messageQueue) {
			String token = msg.getToken();
			
			List<RealBusModel> rbm = messageQueueService.getBusByVehicleNo(msg.getVehicleno());
			RealBusModel rb;
			try {
				rb = rbm.get(0);
			}catch(Exception e) {
				continue;
			}
			double buslati = rb.getLatitude();
			double buslongi = rb.getLongitude();

			System.out.println("msg lati : " + msg.getLatitude() + "/ longi : " + msg.getLongitude());

			double dist = Math.abs(buslati - msg.getLatitude()) + Math.abs(buslongi - msg.getLongitude());
			System.out.println("bus place: " + buslati + " / " + buslongi);
			System.out.println("exit place  : " + msg.getLatitude() + " / " + msg.getLongitude());
			System.out.println("dist : " + dist);
			if (dist <= measures) {
				String title;
				
				if(msg.getBusstation().equals(msg.getLaststation())) {
					title = "Last Get out off soon.";
				}else {
					title = "Get out off soon";
				}
				
				fcmUtil.send_FCM(token, title, msg.getBusstation(),msg.getLaststation());
				messageQueueService.deleteByToken(token);
			}
		}
	}

	@Scheduled(fixedDelay = 5000)
	public void runEvery5secs1() {
		watchService.deleteExpired(limit_time);
		watchService.hit2();
		List<BusTableModel> bustablemodels;
		bustablemodels = watchService.getBusId("31190"); // 31190 == test value. (citycode = youngin)
		for (BusTableModel btm : bustablemodels) {
			getLocation(btm);
		}
		bustablemodels = watchService.getBusId("31010"); // 31010 == test value. (citycode = suwon)
		for (BusTableModel btm : bustablemodels) {
			getLocation(btm);
		}
		bustablemodels = watchService.getBusId("31020"); // 31020 == test value. (citycode = sengnam)
		for (BusTableModel btm : bustablemodels) {
			getLocation(btm);
		}
	}

	@Scheduled(fixedDelay = 5000)
	public void runEvery5secs2() {
		List<UserModel> users = watchService.findActUser();

		for (UserModel user : users) {
			System.out.println("checked2. two");
			double lati = user.getLatitude();
			double longi = user.getLongitude();
			System.out.println("user lati : " + lati + "/ longi : " + longi);
			List<RealBusModel> realDist = watchService.getRealDist(user.getId());
			// busTableModel에 routeno, vehicleno, dist 추가.
			System.out.println("checked2. three");

			RealBusModel bm = realDist.get(0);
			double bmdist, btmdist;

			for (RealBusModel btm : realDist) {

				bmdist = Math.abs(bm.getLatitude() - lati) + Math.abs(bm.getLongitude() - longi);
				if (bm.getLatitude() > bm.getLongitude()) {
					bmdist = Math.abs(bm.getLongitude() - lati) + Math.abs(bm.getLatitude() - longi);
				}

				btmdist = Math.abs(btm.getLatitude() - lati) + Math.abs(btm.getLongitude() - longi);
				if (btm.getLatitude() > btm.getLongitude()) {
					btmdist = Math.abs(btm.getLongitude() - lati) + Math.abs(btm.getLatitude() - longi);
				}

//				System.out.println("bm dist : " + bmdist + " btmdist : " + btmdist);
//				System.out.println("bm lati : " + bm.getLatitude() + " bm long : " + bm.getLongitude());
//				System.out.println("btm lati : " + btm.getLatitude() + " btm long : " + btm.getLongitude());

				bm = bmdist > btmdist ? btm : bm;
			}

//			System.out.println("bm : " + bm.getRouteno());
			if (bm.getLatitude() < bm.getLongitude())
				System.out.println(
						"man dist : " + Math.abs(bm.getLatitude() - lati) + "/" + Math.abs(bm.getLongitude() - longi));
			else
				System.out.println(
						"man dist : " + Math.abs(bm.getLatitude() - longi) + Math.abs(bm.getLongitude() - lati));
				
			System.out.println(bm.getVehicleno());
			if (Math.abs(bm.getLatitude() - lati) + Math.abs(bm.getLongitude() - longi) <= measures) {
				watchService.hit(bm.getVehicleno()); // after, need to modify
			}

			List<UserModel> targetUser = watchService.getOverHitUser();
			if (targetUser != null && targetUser.size() > 0) {
				UserModel us = targetUser.get(0);
				// Send FCM to targetUser.
				WatchModel wm = watchService.getWatchById(us.getId()).get(0);
				
				messageQueueService.insertMessageQueue(us.getToken(), wm.getQuit_latitude(), wm.getQuit_longitude(),
						wm.getVehicleno(),wm.getBusstation(),wm.getLaststation());
				
				
				//watchService.deleteByToken(us.getToken());
				watchService.updateHit(wm.getVehicleno());
			}
		}
	}

	private void getLocation(BusTableModel btm) {
		String routeno = btm.getRouteid();
		String citycode = Integer.toString(btm.getCitycode());

		System.out.println("citycode : " + citycode + " / routeno : " + routeno);
		String url = "http://openapi.tago.go.kr/openapi/service/BusLcInfoInqireService/getRouteAcctoBusLcList?serviceKey="
				+ serviceKey + "&cityCode=" + citycode + "&routeId=" + routeno;

		System.out.println("url : [" + url + "]");
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

		for (int temp = 0; temp < nList.getLength(); temp++) {
			String arg1, arg2, arg3, arg4;
			try {
				Node nNode = nList.item(temp);

				Element eElement = (Element) nNode;
				arg1 = getTagValue("vehicleno", eElement);
				arg2 = getTagValue("routenm", eElement);
				arg3 = getTagValue("gpslati", eElement);
				arg4 = getTagValue("gpslong", eElement);
			} catch (Exception e) {
				continue;
			}

			// System.out.println("######################");
			// System.out.println(eElement.getTextContent());
			// System.out.println("latitude : " + getTagValue("gpslati", eElement));
			// System.out.println("longitude : " + getTagValue("gpslong", eElement));
			// System.out.println("routenm : " + getTagValue("routenm", eElement));
			// System.out.println("vehicleno : " + getTagValue("vehicleno", eElement));

			watchService.insertBusLocation(arg1, arg2, arg3, arg4);
		} // if end

	}

	private static String getTagValue(String tag, Element eElement) throws Exception {
		NodeList nlList = null;
		Node nValue = null;
		nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		nValue = (Node) nlList.item(0);

		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}
}