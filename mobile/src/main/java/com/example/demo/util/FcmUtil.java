package com.example.demo.util;

import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.FacilityModel;
import com.example.demo.service.UserService;
import com.example.demo.service.WatchService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class FcmUtil {
	// YS LAPTOP SETTING.
	// private String json_path =
	// "C:/Users/강윤식/git/start_up_team_server/mobile/src/main/webapp/resoureces/fcm/buspush-28fed-firebase-adminsdk-i4i4h-0b4d9a46ee.json";
	// GCP SETTING
	private String json_path = "/home/yunsik/git/start_up_team_server/mobile/src/main/webapp/resoureces/fcm/buspush-28fed-firebase-adminsdk-i4i4h-0b4d9a46ee.json";

	private String firebase_url = "https://BusPush.firebaseio.com";

	@Autowired
	WatchService watchService;
	
	@Autowired
	UserService userService;

	public void send_FCM(String tokenId, String title, String busstation, String laststation) {
		try {
			// 본인의 json 파일 경로 입력
			FileInputStream refreshToken = new FileInputStream(json_path);
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken)).setDatabaseUrl(firebase_url).build();

			// Firebase 처음 호출시에만 initializing 처리
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}

			// String registrationToken = 안드로이드 토큰 입력;
			String registrationToken = tokenId;

			Message message = Message.builder()
					.putData("title", title)
					.putData("busstation", busstation)
					.putData("bus_latlng", get_latlng(busstation))
					.putData("laststation", laststation)
					.putData("last_latlng", get_latlng(laststation))
					.putData("id", userService.getUserByToken(tokenId).get(0).getId())
					.setToken(registrationToken).build();

			String response = FirebaseMessaging.getInstance().send(message);

			// 결과 출력
			System.out.println("Successfully sent message: " + response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String get_latlng(String string) {
		FacilityModel fm = watchService.getLocationByName(string).get(0);
		return fm.getLatitude() + ":" + fm.getLongitude();
	}
}
