import { Component, OnInit } from '@angular/core';
import { ServiciosService } from 'src/app/servicios/servicios.service';
import { Router } from '@angular/router';
import { IRecipes } from 'src/app/modelos/recipes.interface';


@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  sideNavStatus: boolean = false;

  listaRecetas: IRecipes[] | undefined;

  constructor(private api: ServiciosService, private router: Router) { }

  ngOnInit(): void {
    this.api.getAllRecipes().subscribe(data => {
      this.listaRecetas = data;
    })
  }

  editarRecipe(id: any) {
    this.router.navigate(['Editar_Receta', id])
  }

  nuevoRecipe() {
    this.router.navigate(['Nueva_Receta'])
  }

}