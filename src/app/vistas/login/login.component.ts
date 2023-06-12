import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertasService } from 'src/app/servicios/alertas.service';
import { ServiciosService } from 'src/app/servicios/servicios.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  })


  constructor(private api: ServiciosService, private router: Router, private alerta: AlertasService) { }

  ngOnInit(): void {
  }

  onLogin(form: any) {
    this.api.login(form).subscribe(data => {
      if (data) {
        this.router.navigate(['Recetas'])
      } else {
        this.alerta.showError("Credenciales incorrectas", "Error")
      }
    })
  }
}
