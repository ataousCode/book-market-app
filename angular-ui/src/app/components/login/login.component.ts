import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {register} from "../../services/fn/authentication/register";
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/services';
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMessage: Array<string> = [];
  submitted = false;

  constructor(private router: Router, private authService: AuthenticationService,
              private tokenService: TokenService) {}

  login() {
    this.errorMessage = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (response) => {
        //todo: save the token
        this.tokenService.token = response.token as string;
        this.router.navigate(['books']);
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMessage = err.error.validationErrors;
        } else {
          this.errorMessage.push(err.error);
        }
      }
    });
  }

  register() {
    this.router.navigate(['register']);
  }
}
