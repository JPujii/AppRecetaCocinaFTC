import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http'
import { HeaderComponent } from './plantillas/header/header.component';
import { RecipesComponent } from './vistas/recipes/recipes.component';
import { UsuariosComponent } from './vistas/usuarios/usuarios.component';
import { SidebarComponent } from './plantillas/sidebar/sidebar.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'

import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { EditarUsuarioComponent } from './vistas/editar-usuario/editar-usuario.component';
import { EditarRecipeComponent } from './vistas/editar-recipe/editar-recipe.component';
import { NuevoUsuarioComponent } from './vistas/nuevo-usuario/nuevo-usuario.component';
import { NuevaRecipeComponent } from './vistas/nueva-recipe/nueva-recipe.component';
import { LoginComponent } from './vistas/login/login.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RecipesComponent,
    UsuariosComponent,
    SidebarComponent,
    EditarUsuarioComponent,
    EditarRecipeComponent,
    NuevoUsuarioComponent,
    NuevaRecipeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
