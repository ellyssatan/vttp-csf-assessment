import { Component } from '@angular/core';
import { Restaurant } from '../models';

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css']
})
export class RestaurantCuisineComponent {
	
	// TODO Task 3
	// For View 2

  cuisine !: string

  restaurants : Restaurant[] = []

  
}
