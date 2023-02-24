import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core'
import { firstValueFrom } from 'rxjs';
import { Restaurant, Comment } from './models'

@Injectable()
export class RestaurantService {

	constructor(private http : HttpClient) {}
	BACKEND_API_URL = "http://localhost:8080";

	// TODO Task 2 
	// Use the following method to get a list of cuisines
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public getCuisineList() : Promise<string[]> {
		// Implememntation in here

		console.log('>>>> requesting for cuisine list')
		return firstValueFrom(this.http.get<string[]>(`${this.BACKEND_API_URL}/api/cuisines`));
	}

	// TODO Task 3 
	// Use the following method to get a list of restaurants by cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public getRestaurantsByCuisine(cuisine : string) : Promise<any> {
		// Implememntation in here
		console.log('>>>> svc requesting for restaurant list')
		return firstValueFrom(this.http.get<any>(`${this.BACKEND_API_URL}/api/${cuisine}/restaurants`));
	}
	
	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	public getRestaurant(cood : number[]): Promise<Restaurant> {
		// Implememntation in here

		const params = new HttpParams()
		.set("lat", cood[1])
		.set("lng", cood[0]);
		
		return firstValueFrom(
			this.http.post<Restaurant>(`${this.BACKEND_API_URL}/map`, { params : params })
		)
		
	}

// 	// TODO Task 5
// 	// Use this method to submit a comment
// 	// DO NOT CHANGE THE METHOD'S NAME OR SIGNATURE
	public postComment(comment: Comment): Promise<any> {
		// Implememntation in here

		return firstValueFrom(
			this.http.post<Restaurant>(`${this.BACKEND_API_URL}/maps`, comment)
		)
	}
}
