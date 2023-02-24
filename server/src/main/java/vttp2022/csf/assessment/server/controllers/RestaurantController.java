package vttp2022.csf.assessment.server.controllers;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

@Controller
@RequestMapping(path="/api")
@CrossOrigin(origins = "*")
public class RestaurantController {

    @Autowired
	private RestaurantService restaurantSvc;

    @GetMapping(path="/cuisines")
    @ResponseBody
    public ResponseEntity<String> retrieveCuisines() {
        
        List<String> c = restaurantSvc.getCuisines();

        JsonObject response = Json.createObjectBuilder()
			.add("cuisines", c.toString())
			.build();
        return ResponseEntity.ok(response.toString());
    }

    @GetMapping(path="/{cuisine}/restaurants", consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> retrieveRestaurantsByCuisines(@PathVariable String cuisine) {

        if (cuisine.contains("_")) {
            cuisine.replaceAll("_", "/");
            System.out.println(">>>> " + cuisine);
        }
        
        List<Restaurant> restaurants = restaurantSvc.getRestaurantsByCuisine(cuisine);

        List<String> restaurantList = new LinkedList<>();

        for (int i = 0; i < restaurants.size(); i++) {
            restaurantList.add(restaurants.get(i).getName());
        }

        JsonObject response = Json.createObjectBuilder()
			.add("restaurants", restaurantList.toString())
			.build();

        return ResponseEntity.ok(response.toString());
    }

    @PostMapping(path="/comments", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postComment(@PathVariable String cuisine) {

        if (cuisine.contains("_")) {
            cuisine.replaceAll("_", "/");
            System.out.println(">>>> " + cuisine);
        }
        
        List<Restaurant> restaurants = restaurantSvc.getRestaurantsByCuisine(cuisine);

        List<String> restaurantList = new LinkedList<>();

        for (int i = 0; i < restaurants.size(); i++) {
            restaurantList.add(restaurants.get(i).getName());
        }

        JsonObject response = Json.createObjectBuilder()
			.add("restaurants", restaurantList.toString())
			.build();

        return ResponseEntity.ok(response.toString());
    }

    
}
