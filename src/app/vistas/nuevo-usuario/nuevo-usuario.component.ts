import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IUsuarios } from 'src/app/modelos/usuarios.interface';
import { AlertasService } from 'src/app/servicios/alertas.service';
import { ServiciosService } from 'src/app/servicios/servicios.service';

@Component({
  selector: 'app-nuevo-usuario',
  templateUrl: './nuevo-usuario.component.html',
  styleUrls: ['./nuevo-usuario.component.css']
})
export class NuevoUsuarioComponent implements OnInit {

  sideNavStatus: boolean = false;

  usuarioForm!: FormGroup;

  constructor(private router: Router, private api: ServiciosService, private formBuilder: FormBuilder, private route: ActivatedRoute, private alerta: AlertasService) { }

  ngOnInit(): void {
    this.usuarioForm = this.formBuilder.group({
      id: ['', Validators.required],
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      fecha_nacimiento: ['', Validators.required],
    })
  }

  rellenarIdConGmail() {
    const emailValue = this.usuarioForm.get('email')?.value;
    this.usuarioForm.get('id')?.setValue(emailValue);
  }

  rellenarEmailConId() {
    const idValue = this.usuarioForm.get('id')?.value;
    this.usuarioForm.get('email')?.setValue(idValue);
  }

  submitForm() {
    const body = this.usuarioForm.value;
    this.api.createUser(body).subscribe(response => {
      this.alerta.showSuccess('Usuario creado con exito', 'Hecho')
      this.salir()
    });
  }

  salir() {
    this.router.navigate(['Usuarios'])
  }

}
