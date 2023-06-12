import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ServiciosService } from 'src/app/servicios/servicios.service';
import { IUsuarios } from 'src/app/modelos/usuarios.interface';
import { AlertasService } from 'src/app/servicios/alertas.service';

@Component({
  selector: 'app-editar-usuario',
  templateUrl: './editar-usuario.component.html',
  styleUrls: ['./editar-usuario.component.css']
})
export class EditarUsuarioComponent implements OnInit {

  sideNavStatus: boolean = false;

  usuarioForm!: FormGroup;
  usuario!: IUsuarios;

  constructor(private router: Router, private api: ServiciosService, private formBuilder: FormBuilder, private route: ActivatedRoute, private alerta: AlertasService) {
    this.usuarioForm = new FormGroup({
      id: new FormControl(),
      nombre: new FormControl(),
      apellidos: new FormControl(),
      email: new FormControl(),
      password: new FormControl(),
      fecha_nacimiento: new FormControl(),
    });
  }

  ngOnInit(): void {
    const usuarioId = this.route.snapshot.paramMap.get('id');
    this.api.getUserUnico(usuarioId).subscribe((data: IUsuarios) => {
      this.usuario = data;
      this.usuarioForm = this.formBuilder.group({
        id: [this.usuario.email, Validators.required],
        nombre: [this.usuario.nombre, Validators.required],
        apellidos: [this.usuario.apellidos, Validators.required],
        email: [this.usuario.email, Validators.required],
        password: [this.usuario.password, Validators.required],
        fecha_nacimiento: [this.usuario.fecha_nacimiento, Validators.required],
      });

    });
  }

  submitForm() {
    this.api.updateUser(this.usuarioForm.value).subscribe(data => {
      this.alerta.showSuccess('Usuario editado con exito', 'Hecho')
    });

  }

  eliminar() {
    this.api.deleteUser(this.usuarioForm.value.id)
      .subscribe(resultado => {
        this.alerta.showSuccess('Usuario eliminado correctamente', 'Hecho');
        this.router.navigate(['Usuarios'])
      });
  }

  salir() {
    this.router.navigate(['Usuarios'])
  }
}
