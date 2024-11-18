import { Component } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  successMessage: string = '';
  //showSuccessMessage: boolean = false;
  registerRequest: RegistrationRequest = {firstname: '', lastname: '', email: '', password: ''};
  errorMsg: Array<string> = [];
  selectedImage: string | ArrayBuffer | null = null;

  constructor(private router: Router, private authService: AuthenticationService) {
  }

  register() {
    this.errorMsg = [];
    this.authService.register({
      body: this.registerRequest
    }).subscribe({
      next: () => {
        this.successMessage = 'Registration successful, please check your email to activate your account';
        //this.showSuccessMessage = true;
        this.router.navigate(['activate-account']);
      },
      error: (err) => {
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  login() {
    this.router.navigate(['login']);
  }

}
