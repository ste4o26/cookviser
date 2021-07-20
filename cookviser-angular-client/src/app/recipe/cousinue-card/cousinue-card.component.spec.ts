import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CousinueCardComponent } from './cousinue-card.component';

describe('CousinueCardComponent', () => {
  let component: CousinueCardComponent;
  let fixture: ComponentFixture<CousinueCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CousinueCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CousinueCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
