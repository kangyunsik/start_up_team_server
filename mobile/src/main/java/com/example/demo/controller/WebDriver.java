package com.example.demo.controller;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

class Movetype {
	String time = "";
	char type;

	Movetype(String time, char type) {
		this.time = time;
		this.type = type;
	}
}

class Businfo {
	String length;
	String leftlength;
	ArrayList<Movetype> move;
	ArrayList<String> busnum;
	ArrayList<String> sname;

	Businfo(String length, String leftlength) {
		this.length = length;
		this.leftlength = length;
	}
}

public class WebDriver {
	private FirefoxDriver driver;
	public static String WEB_DRIVER_ID = "webdriver.gecko.driver";


	// GCP Setting
	public static String WEB_DRIVER_PATH = "/home/yunsik/Downloads/geckodriver";

	// YS desktop Setting
	//public static String WEB_DRIVER_PATH = "/Users/강윤식/Desktop/geckodriver.exe";

	// YS desktop Setting
	//public static String WEB_DRIVER_PATH = "C:/Users/강윤식/Desktop/geckodriver.exe";
	
	//final static double start_x = 37.47708963, start_y = 126.9635058;
	//final static double end_x = 37.62383376, end_y = 127.0616906;

	public Businfo[] info;
	StringBuilder sb = new StringBuilder();
	String[] post;

	private static String base_url;

	public WebDriver() {
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		firefox();

	}

	private void firefox() {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		driver = new FirefoxDriver();
	}

	public void crawl(String start_x,String start_y,String end_x,String end_y) {
		// Crawl
		info = new Businfo[10];
		for (int i = 0; i < 10; i++) {
			info[i] = new Businfo("0", "0");
			info[i].move = new ArrayList<Movetype>();
			info[i].busnum = new ArrayList<String>();
			info[i].sname = new ArrayList<String>();
		}
		try {
			base_url = "https://m.map.naver.com/directions/#/publicTransit/list/," + start_x + "," + start_y + "," + start_x
					+ "," + start_y + ",false,/" + "," + end_x + "," + end_y + "," + end_x + "," + end_y + ",false,/2";
			System.out.println("url : [" + base_url+"]");
			
			driver.get(base_url);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driver.get(base_url);
			String result = driver.getPageSource();
			Document doc = Jsoup.parse(result);
			Elements[] temp = new Elements[5];

			Elements p = doc.select("a");
			for (int num = 0; num < 5; num++) {
				temp[num] = doc.select("xxxxxxxxxx");
				for (int i = 0; i < p.size(); i++) {
					if (p.get(i).hasAttr("data-route_id")
							&& p.get(i).attr("data-route_id").equals(Integer.toString(num))) {
						temp[num].add(p.get(i));
					}
				}
			}
			/*
			 * for(int i=0;i<5;i++) { for(int j=0;j<temp[i].size();j++)
			 * System.out.println(temp[i].get(j).html());
			 * System.out.println("mmmmmmmmmmmmmmmmmmm"); }
			 */
			Elements busnumber = null;
			Elements etcinfo = null;
			Elements stationinfo = null;
			Element worb = null;
			for (int num = 0; num < 5; num++) {
				for (int i = 0; i < temp[num].size(); i++) {
					busnumber = temp[num].get(i).select("strong");
					for (int j = 0; j < busnumber.size(); j++) {
						if (busnumber.get(j).html().contains("em")) {
							worb = busnumber.get(j);
							info[num].length = worb.select("em").html().replace("\n", "");
						} else
							info[num].busnum.add(busnumber.get(j).html());
					}

					etcinfo = temp[num].select("span");
					for (int w = 0; w < etcinfo.size(); w++) {
						if (etcinfo.get(w).attr("class").contains("busArrival")) {
							worb = etcinfo.get(w);
							String adding = worb.html();
							adding = adding.replace("<span>", "");
							adding = adding.replace("</span>", "");
							adding = adding.replaceAll(" ", "");
							adding = adding.replace(",", "-");
							info[num].leftlength = adding;
						}
					}
					stationinfo = etcinfo.select("em");
					for (int k = 0; k < stationinfo.size(); k++) {
						if (stationinfo.get(k).attr("class").contains("ells")) {
							worb = stationinfo.get(k);
							String adding = worb.html().replace("[0-9]", "");
							//adding = adding.replaceAll("[a-zA-Z]", "");
							adding = adding.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
							//adding = adding.replaceAll("spanclasstxttransspan", "");
							adding = adding.replaceAll(" ", "");
							info[num].sname.add(adding);
							//System.out.println(adding);
						}
					}
					for (int q = 0; q < etcinfo.size(); q++) {
						worb = null;
						if (etcinfo.get(q).attr("class").equals("time_bar_walk")) {
							worb = etcinfo.get(q);
							info[num].move.add(new Movetype(worb.select("em").html(), 'w'));
						} else if (etcinfo.get(q).attr("class").contains("time_bar_bus")) {
							worb = etcinfo.get(q);
							info[num].move.add(new Movetype(worb.select("em").html(), 'b'));
						}
					}

				}
			}
			// System.out.println(info[0].sname.size());
			for (int i = 0; i < 10; i++) {
				// System.out.println("----"+(i+1)+"?? ??");
				// System.out.println("?? ???? ???? : "+info[i].leftlength);
				// System.out.println("? ???? : "+info[i].length);
				for (int j = 0; j < info[i].move.size(); j++) {
					// System.out.println(info[i].move.get(j).time+"?? :
					// "+info[i].move.get(j).type);
				}

				for (int k = 0; k < info[i].busnum.size(); k++) {
					// System.out.printf("busnum : ");
					// System.out.printf(info[i].busnum.get(k));
					// System.out.println(" station name : "+info[i].sname.get(k));
					if (k == info[i].busnum.size() - 1) {
						// System.out.println("Last station name : "+info[i].sname.get(k+1));
					}
				}
			}
			String[] all = new String[10];
			for (int i = 0; i < 10; i++) {
				all[i] = "";
			}
			int check;
			for (int i = 0; i < 10; i++) {
				check = 0;
				if (!info[i].length.equals("0")) {
					all[i] = all[i] + info[i].length + "<total> ";
					all[i] = all[i] + info[i].leftlength + "<left> ";
					for (int j = 0; j < info[i].move.size(); j++) {
						if (info[i].move.get(j).type == 'w') {
							all[i] = all[i] + info[i].move.get(j).time + "<time> " + "0<busnum> " + "0<station> ";
						} else if (info[i].move.get(j).type == 'b') {
							all[i] = all[i] + info[i].move.get(j).time + "<time> " + info[i].busnum.get(check)
									+ "<busnum> " + info[i].sname.get(check) + "<station> ";
							++check;
						}
						if (j == info[i].move.size() - 1) {
							all[i] = all[i] + info[i].sname.get(check) + "<last>";
						}

					}
				}
			}

			for (int i = 0; i < 10; i++) {
				sb.append(all[i] + " ");
			}
			System.out.println(sb.toString());
			post = sb.toString().split(" ");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			driver.close();
		}
	}
}
