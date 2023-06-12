import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IRecipes } from 'src/app/modelos/recipes.interface';
import { AlertasService } from 'src/app/servicios/alertas.service';
import { ServiciosService } from 'src/app/servicios/servicios.service';

@Component({
  selector: 'app-editar-recipe',
  templateUrl: './editar-recipe.component.html',
  styleUrls: ['./editar-recipe.component.css']
})
export class EditarRecipeComponent implements OnInit {

  sideNavStatus: boolean = false;

  recipesForm!: FormGroup;
  recipe!: IRecipes;

  constructor(private router: Router, private api: ServiciosService, private formBuilder: FormBuilder, private route: ActivatedRoute, private alerta: AlertasService) {
    this.recipesForm = new FormGroup({
      id: new FormControl(),
      nombre: new FormControl(),
      ingredientes: new FormControl(),
      pasos: new FormControl(),
      tiempo: new FormControl(),
      userID: new FormControl()
    });
  }


  ngOnInit(): void {
    const recipeId = this.route.snapshot.paramMap.get('id');
    this.api.getRecipeUnico(recipeId).subscribe((data: IRecipes) => {
      this.recipe = data;
      this.recipesForm = this.formBuilder.group({
        id: [this.recipe.id, Validators.required],
        nombre: [this.recipe.nombre, Validators.required],
        ingredientes: [this.recipe.ingredientes, Validators.required],
        pasos: [this.recipe.pasos, Validators.required],
        tiempo: [this.recipe.tiempo, Validators.required],
        userID: [this.recipe.userID, Validators.required],
      });

    });
  }

  submitForm() {
    this.api.updateRecipe(this.recipesForm.value).subscribe(data => {
      this.alerta.showSuccess('Receta editado con exito', 'Hecho')
    });

  }

  eliminar() {
    this.api.deleteRecipe(this.recipesForm.value.id)
      .subscribe(resultado => {
        this.alerta.showSuccess('Receta eliminado correctamente', 'Hecho');
        this.router.navigate(['Recetas'])
      });
  }

  salir() {
    this.router.navigate(['Recetas'])
  }
}
