import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {register} from "../../services/fn/authentication/register";
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMessage: Array<string> = [];

  constructor(private router: Router, private authService: AuthenticationService) {}

  login() {
    this.errorMessage = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (result) => {
        //todo: save the token
        this.router.navigate(['books']);
      },
      error: (err) => {
        console.log(err);
        
      }
    });
  }

  register() {
    this.router.navigate(['register']);
  }
}
