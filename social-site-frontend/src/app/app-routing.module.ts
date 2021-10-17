import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccessGuard } from './AccessGuard';
import { AuthComponent } from './components/auth/auth.component';
import { FeedComponent } from './components/posts/feed/feed.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
    { path: '', component: FeedComponent, data: { requiresLogin: true }, canActivate: [ AccessGuard ] },
    { path: 'auth', component: AuthComponent, data: { requiresLogout: true }, canActivate: [ AccessGuard ] },
    { path: 'profile', component: ProfileComponent, data: { requiresLogin: true }, canActivate: [ AccessGuard ] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: [AccessGuard]
})
export class AppRoutingModule { }
