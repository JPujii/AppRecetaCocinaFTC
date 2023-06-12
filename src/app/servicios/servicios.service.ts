import { Injectable } from '@angular/core';
import { IRecipes } from '../modelos/recipes.interface';
import { IUsuarios } from '../modelos/usuarios.interface';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {

  url: string = "http://localhost:8080/";

  constructor(private http: HttpClient) { }

  // Recetas
  getAllRecipes(): Observable<IRecipes[]> {
    let direccion = `${this.url}recipes/list`
    return this.http.get<IRecipes[]>(direccion);
  }

  createRecipes(form: IRecipes): Observable<any> {
    let direccion = `${this.url}recipes/add`
    return this.http.post<any>(direccion, form);
  }

  getRecipeUnico(id: any): Observable<IRecipes> {
    let direccion = `${this.url}recipes/list/${id}`
    return this.http.get<IRecipes>(direccion);
  }

  updateRecipe(form: IRecipes): Observable<any> {
    let direccion = `${this.url}recipes/update/${form.id}`
    return this.http.put<any>(direccion, form);
  }

  deleteRecipe(id: number): Observable<any> {
    return this.http.delete(`${this.url}recipes/delete/${id}`);
  }

  // Usuarios
  getAllUsers(): Observable<IUsuarios[]> {
    let direccion = `${this.url}usuarios/list`
    return this.http.get<IUsuarios[]>(direccion);
  }

  createUser(form: IUsuarios): Observable<any> {
    let direccion = `${this.url}usuarios/add`
    return this.http.post<any>(direccion, form);
  }

  getUserUnico(id: any): Observable<IUsuarios> {
    let direccion = `${this.url}usuarios/list/${id}`
    return this.http.get<IUsuarios>(direccion);
  }

  updateUser(form: IUsuarios): Observable<any> {
    let direccion = `${this.url}usuarios/update/${form.id}`
    return this.http.put<any>(direccion, form);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.url}usuarios/delete/${id}`);
  }

  login(form: IUsuarios): Observable<any> {
    let direccion = `${this.url}usuarios/login`
    return this.http.post<any>(direccion, form);
  }
}
