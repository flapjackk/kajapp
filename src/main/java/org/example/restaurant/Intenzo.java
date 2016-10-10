package org.example.restaurant;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
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
	}
}
