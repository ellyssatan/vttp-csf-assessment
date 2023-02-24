import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { CuisineListComponent } from './components/cuisine-list.component';
import { RestaurantCuisineComponent } from './components/restaurant-cuisine.component';
import { RestaurantDetailsComponent } from './components/restaurant-details.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { RestaurantService } from './restaurant-service';

const appRoutes : Routes = [
	{ path: '', component: CuisineListComponent },
	{ path: ':cuisine/restaurants', component: RestaurantCuisineComponent },
	{ path: 'restaurants/:restaurant', component: RestaurantCuisineComponent },
	{ path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    CuisineListComponent,
    RestaurantCuisineComponent,
    RestaurantDetailsComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, RouterModule.forRoot(appRoutes), HttpClientModule
  ],
  providers: [ RestaurantService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
