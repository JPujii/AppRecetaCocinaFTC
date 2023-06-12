import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarRecipeComponent } from './editar-recipe.component';

describe('EditarRecipeComponent', () => {
  let component: EditarRecipeComponent;
  let fixture: ComponentFixture<EditarRecipeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditarRecipeComponent]
    });
    fixture = TestBed.createComponent(EditarRecipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
