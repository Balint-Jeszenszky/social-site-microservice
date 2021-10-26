import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccessGuard } from './access-guard';
import { AuthComponent } from './components/auth/auth.component';
import { FeedComponent } from './components/posts/feed/feed.component';
import { EditProfileComponent } from './components/profile/edit-profile/edit-profile.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ValidateRegistrationComponent } from './components/auth/validate-registration/validate-registration.component';
import { FriendsComponent } from './components/friends/friends.component';

const routes: Routes = [
    { path: '', component: FeedComponent, data: { requiresLogin: true, redirectTo: '/auth' }, canActivate: [ AccessGuard ] },
    { path: 'auth', component: AuthComponent, data: { requiresLogout: true, redirectTo: '/' }, canActivate: [ AccessGuard ] },
    { path: 'profile', component: ProfileComponent, data: { requiresLogin: true, redirectTo: '/auth' }, canActivate: [ AccessGuard ] },
    { path: 'profile/:username', component: ProfileComponent, data: { requiresLogin: true, redirectTo: '/auth' }, canActivate: [ AccessGuard ] },
    { path: 'editProfile', component: EditProfileComponent, data: { requiresLogin: true, redirectTo: '/auth' }, canActivate: [ AccessGuard ] },
    { path: 'validate/:key', component: ValidateRegistrationComponent, data: { requiresLogout: true, redirectTo: '/' }, canActivate: [ AccessGuard ] },
    { path: 'friends', component: FriendsComponent, data: { requiresLogin: true, redirectTo: '/auth' }, canActivate: [ AccessGuard ] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
  providers: [AccessGuard]
})
export class AppRoutingModule { }
