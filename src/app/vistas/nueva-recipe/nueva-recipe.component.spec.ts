import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevaRecipeComponent } from './nueva-recipe.component';

describe('NuevaRecipeComponent', () => {
  let component: NuevaRecipeComponent;
  let fixture: ComponentFixture<NuevaRecipeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NuevaRecipeComponent]
    });
    fixture = TestBed.createComponent(NuevaRecipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
