import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Restaurant } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent implements OnInit {
	
	// TODO Task 4 and Task 5
	// For View 3

  // initialise form
  form !: FormGroup

  restaurant !: Restaurant

  constructor(private fb : FormBuilder, private httpSvc : RestaurantService, private router : Router) {}

  ngOnInit(): void {
    console.log("ngOnInit ...");
    this.form = this.createForm()
  }

  // initialise form fields
  createForm() : FormGroup {
    return this.fb.group({
        name: this.fb.control('', [ Validators.required, Validators.minLength(4) ]),
        rating: this.fb.control('', [ Validators.required, Validators.min(1), Validators.max(5) ]),
        text: this.fb.control('', [ Validators.required ]),
      })
  }

  // process form data
  submitComment() {
    const data = this.form.value as Comment

    // this.httpSvc.
    // this.router.navigate(['/'])   // reroute

    console.info('>>> post: ', data)
  } 

}
