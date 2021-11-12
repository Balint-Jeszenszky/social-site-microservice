import { NgModule } from '@angular/core';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatProgressBarModule } from '@angular/material/progress-bar';

const material = [
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    MatSidenavModule,
    MatButtonModule,
    MatTabsModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,
    MatProgressBarModule
];

@NgModule({
  imports: [
    material
  ],
  exports: [
    material
  ]
})
export class MaterialModule { }
