import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoUsuarioComponent } from './nuevo-usuario.component';

describe('NuevoUsuarioComponent', () => {
  let component: NuevoUsuarioComponent;
  let fixture: ComponentFixture<NuevoUsuarioComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NuevoUsuarioComponent]
    });
    fixture = TestBed.createComponent(NuevoUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
