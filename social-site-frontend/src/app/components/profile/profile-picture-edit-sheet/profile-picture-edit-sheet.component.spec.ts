import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePictureEditSheetComponent } from './profile-picture-edit-sheet.component';

describe('ProfilePictureEditSheetComponent', () => {
  let component: ProfilePictureEditSheetComponent;
  let fixture: ComponentFixture<ProfilePictureEditSheetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfilePictureEditSheetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfilePictureEditSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
