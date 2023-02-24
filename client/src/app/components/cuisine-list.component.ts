import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent implements OnInit {

	// TODO Task 2
	// For View 1

  // param$ !: Subscription
  cuisines !: string[]

  constructor(private fb : FormBuilder, private httpSvc : RestaurantService) {}

  async ngOnInit() {
    console.log("ngOnInit ...");
    this.cuisines = await this.httpSvc.getCuisineList();
    console.log(this.cuisines);
    // for (let entry of this.arr) {
    //   this.cuisines.push(entry)
    // }
  }

}
