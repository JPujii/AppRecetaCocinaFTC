import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuariosComponent } from './vistas/usuarios/usuarios.component';
import { RecipesComponent } from './vistas/recipes/recipes.component';
import { EditarRecipeComponent } from './vistas/editar-recipe/editar-recipe.component';
import { EditarUsuarioComponent } from './vistas/editar-usuario/editar-usuario.component';
import { NuevoUsuarioComponent } from './vistas/nuevo-usuario/nuevo-usuario.component';
import { NuevaRecipeComponent } from './vistas/nueva-recipe/nueva-recipe.component';
import { LoginComponent } from './vistas/login/login.component';

const routes: Routes = [
  { path: '', redirectTo: 'Login', pathMatch: 'full' },
  { path: 'Recetas', component: RecipesComponent },
  { path: 'Usuarios', component: UsuariosComponent },
  { path: 'Editar_Usuario/:id', component: EditarUsuarioComponent },
  { path: 'Editar_Receta/:id', component: EditarRecipeComponent },
  { path: 'Nueva_Receta', component: NuevaRecipeComponent },
  { path: 'Nuevo_Usuario', component: NuevoUsuarioComponent },
  { path: 'Login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }