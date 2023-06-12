import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertasService } from 'src/app/servicios/alertas.service';
import { ServiciosService } from 'src/app/servicios/servicios.service';

@Component({
  selector: 'app-nueva-recipe',
  templateUrl: './nueva-recipe.component.html',
  styleUrls: ['./nueva-recipe.component.css']
})
export class NuevaRecipeComponent implements OnInit {

  sideNavStatus: boolean = false;

  recipeForm!: FormGroup;

  constructor(private router: Router, private api: ServiciosService, private formBuilder: FormBuilder, private route: ActivatedRoute, private alerta: AlertasService) { }

  ngOnInit(): void {
    this.recipeForm = this.formBuilder.group({
      id: ['', Validators.required],
      nombre: ['', Validators.required],
      ingredientes: ['', Validators.required],
      pasos: ['', Validators.required],
      tiempo: ['', Validators.required],
      userID: ['default', Validators.required],
    })
  }

  submitForm() {
    const body = this.recipeForm.value;
    this.api.createRecipes(body).subscribe(response => {
      this.alerta.showSuccess('Receta creada con exito', 'Hecho')
      this.salir()
    });
  }

  salir() {
    this.router.navigate(['Recetas'])
  }

}
