import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingFriendsComponent } from './pending-friends.component';

describe('PendingFriendsComponent', () => {
  let component: PendingFriendsComponent;
  let fixture: ComponentFixture<PendingFriendsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingFriendsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingFriendsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
