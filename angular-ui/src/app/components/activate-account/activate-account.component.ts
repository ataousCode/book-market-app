import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {
  message = '';
  isOkay = false;
  submitted = false;

  constructor(private router: Router, private authenticationService: AuthenticationService) {}

  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(token: string) {
    this.authenticationService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message = 'Your account has been activated.\nNow you can proceed to login';
        this.submitted = true;
        this.isOkay = true;
        },
      error: () => {
        this.message = 'Ops! token has been expired or invalid.';
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }












}
