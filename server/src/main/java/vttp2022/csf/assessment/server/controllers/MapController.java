package vttp2022.csf.assessment.server.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;


import javax.imageio.ImageIO;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

@RestController
@RequestMapping(path="/map")
@CrossOrigin(origins = "*")
public class MapController {

    @Autowired
	private RestaurantService restaurantSvc;

    @GetMapping(consumes=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<String> retrieveRestaurant(@RequestParam Float lat, @RequestParam Float lng) {

        
        String url = UriComponentsBuilder
				.fromUriString("http://map.chuklee.com/map")
				.queryParam("lat", lat)
				.queryParam("lng", lng)
				.toUriString();

        RequestEntity req = RequestEntity.get(url).build();
        // Make the call to url
        RestTemplate template = new RestTemplate();
        ResponseEntity<byte[]> resp = template.exchange(req, byte[].class);
		byte[] bytes = resp.getBody();

        restaurantSvc.getMap(bytes);

        return ResponseEntity.ok(resp.toString());
    }

}