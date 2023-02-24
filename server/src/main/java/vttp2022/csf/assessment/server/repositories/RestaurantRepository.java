package vttp2022.csf.assessment.server.repositories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {

	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  

	@Autowired
	private MongoTemplate mongoTemplate;

	// collection name
	private static String C_RESTAURANTS = "restaurants";

	// db.restaurants.distinct('cuisine')
	public List<String> getCuisines() {
		// Implmementation in here
		List<String> cuisineList = mongoTemplate.findDistinct(new Query(), "cuisine", C_RESTAURANTS, String.class);


		List<String> cuisines = new LinkedList<>();
		cuisines = cuisineList.stream().map(s -> s.replaceAll("/", "_")).collect(Collectors.toList());
			
		System.out.println(">>>> " + cuisines);
		return cuisines;
	}

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method

	// db.restaurants.find({'cuisine':'<cuisine>'})
	public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		Query query = Query.query(
			Criteria.where("cuisine").in(cuisine)
		);
		List<Restaurant> results = mongoTemplate.find(query, Restaurant.class, C_RESTAURANTS);

		System.out.println("?????????????");
		System.out.println(results);

		// return mongoTemplate.find(q, Document.class, C_GAME)
        //     .stream()
        //     .map(d -> Game.create(d))
        //     .toList();
		return results;
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method

	/*
	 * db.restaurants.aggregate([
			{
				$match: { name : "Ajisen Ramen" }
			},
			{
				$project: {
					_id: 0,
					name: 1,
					restaurant_id: 1,
					cuisine: 1,
					address: {
						$concat: [ "$address.building", ", ", "$address.street", ", ", "$address.zipcode", ", ", "$borough" ]
					},
					coordinates: "$address.coord"
				}
			}
		]);
	 */
	public Optional<Restaurant> getRestaurant(String name) {
		// Implmementation in here

		MatchOperation matchRated = Aggregation.match(
					Criteria.where("name").is(name));

		ProjectionOperation projectRestaurant = Aggregation.project("name", "restaurant_id", "cuisine", "address", "coordinates")
					.and("address.coord").as("coordinates")
					.and(StringOperators.Concat.valueOf("address.building").concat(", ").concatValueOf("address.street").concat(", ").concatValueOf("address.zipcode")
									.concat(", ").concatValueOf("borough")).as("address");

		Aggregation pipeline = Aggregation.newAggregation(projectRestaurant);

		AggregationResults<Document> results = mongoTemplate.aggregate(
						pipeline, C_RESTAURANTS, Document.class);

		if (!results.iterator().hasNext())
			return Optional.empty();

		// Get one result only
		Document doc = results.iterator().next();

		Restaurant r = new Restaurant();
		r.setRestaurantId(doc.getString("restaurant_id"));
		r.setName(doc.getString("name"));
		r.setCuisine(doc.getString("cuisine"));
		r.setAddress(doc.getString("address"));
		LatLng c = new LatLng();
		String[] coord = doc.get("coordinates").toString().split(",");
		c.setLatitude(Integer.parseInt(coord[1]));
		c.setLongitude(Integer.parseInt(coord[0]));
		r.setCoordinates(c);
		r.setMapURL(null);
		System.out.println(">>>>>>> results" + r);
		
		return Optional.of(r);
	}
	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public void addComment(Comment comment) {
		// Implmementation in here
		
	}
	
	// You may add other methods to this class

}
