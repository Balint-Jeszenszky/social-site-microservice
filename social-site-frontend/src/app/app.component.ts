import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(userService: UserService, router: Router) {
    userService.isLoggedIn().subscribe(loggedin => {
      if (!loggedin) {
        router.navigate(['auth'])
      }
    });
  }
}
