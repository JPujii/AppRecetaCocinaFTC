import { Component, OnInit } from '@angular/core';
import { ServiciosService } from 'src/app/servicios/servicios.service';
import { Router } from '@angular/router';
import { IRecipes } from 'src/app/modelos/recipes.interface';
import { IUsuarios } from 'src/app/modelos/usuarios.interface';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {

  sideNavStatus: boolean = false;

  listaUsuarios: IUsuarios[] | undefined;

  constructor(private api: ServiciosService, private router: Router) { }

  ngOnInit(): void {
    this.api.getAllUsers().subscribe(data => {
      this.listaUsuarios = data;
    })
  }

  editarUsuario(id: any) {
    this.router.navigate(['Editar_Usuario', id])
  }

  nuevoUsuario() {
    this.router.navigate(['Nuevo_Usuario'])
  }

}
