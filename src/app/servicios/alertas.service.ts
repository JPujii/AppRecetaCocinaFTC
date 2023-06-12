import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr'

@Injectable({
  providedIn: 'root'
})
export class AlertasService {

  constructor(private toast: ToastrService) { }

  showSuccess(texto: string, titulo: string) {
    this.toast.success(texto, titulo);
  }

  showError(texto: string, titulo: string) {
    this.toast.error(texto, titulo);
  }
}
