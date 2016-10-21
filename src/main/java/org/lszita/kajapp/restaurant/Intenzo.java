package org.lszita.kajapp.restaurant;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Intenzo {
	
	public JsonObject getMenu(){
		
		Document doc;
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		
		try {
			doc = Jsoup.connect("http://www.cafeintenzo.hu").timeout(7000).get();
			Elements menu = doc.select("#hetimenu > div > div > div");
			for(int i = 1; i < menu.size(); i++){
				jsonArrayBuilder.add(menu.get(i).text());
			}
			jsonBuilder.add("menu", jsonArrayBuilder);
		} catch (IOException e) {
			jsonBuilder.add("error", e.getMessage());
		}
		return jsonBuilder.build();
	}
	
	// old site got removed RIP
	/* 
	public JsonObject getMenu(){
		
		Document doc;
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		
		try {
			
			doc = Jsoup.connect("http://www.cafeintenzo.hu/heti_menu").get();
			Elements menu = doc.select("#tartalom > table > tbody > tr:nth-child(2) > td");
			Elements from = doc.select("#tartalom > table > tbody > tr:nth-child(1) > th:nth-child(1)");
			Elements to = doc.select("#tartalom > table > tbody > tr:nth-child(1) > th:nth-child(5)");
			menu.forEach(e -> jsonArrayBuilder.add(e.text()));
			
			jsonBuilder.add("from", from.get(0).text());
			jsonBuilder.add("to", to.get(0).text());
			jsonBuilder.add("menu", jsonArrayBuilder);
			
		} catch (IOException e) {
			jsonBuilder.add("error", e.getMessage());
		}
		
		return jsonBuilder.build();
	}*/
	
	
}
