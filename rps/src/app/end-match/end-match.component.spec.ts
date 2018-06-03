import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EndMatchComponent } from './end-match.component';

describe('EndMatchComponent', () => {
  let component: EndMatchComponent;
  let fixture: ComponentFixture<EndMatchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EndMatchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EndMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
