import { forwardRef, NgModule, Provider } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MaterialModule } from './modules/material/material.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';
import { PostComponent } from './components/posts/post/post.component';
import { FeedComponent } from './components/posts/feed/feed.component';
import { NewPostComponent } from './components/posts/new-post/new-post.component';
import { AuthComponent } from './components/auth/auth.component';
import { ApiModule as AuthApiModule } from './api/auth/api.module';
import { ApiModule as SocialApiModule } from './api/social/api.module';
import { ApiInterceptor } from './api/api-interceptor';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ValidateRegistrationComponent } from './components/auth/validate-registration/validate-registration.component';
import { EditProfileComponent } from './components/profile/edit-profile/edit-profile.component';
import { FindFriendsComponent } from './components/friends/find-friends/find-friends.component';
import { ListFriendsComponent } from './components/friends/list-friends/list-friends.component';
import { FriendsComponent } from './components/friends/friends.component';
import { PendingFriendsComponent } from './components/friends/pending-friends/pending-friends.component';
import { PasswordResetComponent } from './components/auth/password-reset/password-reset.component';
import { CommentComponent } from './components/posts/post/comment-section/comment/comment.component';
import { CommentSectionComponent } from './components/posts/post/comment-section/comment-section.component';

const API_INTERCEPTOR_PROVIDER: Provider = {
  provide: HTTP_INTERCEPTORS,
  useExisting: forwardRef(() => ApiInterceptor),
  multi: true
};

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    PostComponent,
    FeedComponent,
    NewPostComponent,
    AuthComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    ValidateRegistrationComponent,
    EditProfileComponent,
    FindFriendsComponent,
    ListFriendsComponent,
    FriendsComponent,
    PendingFriendsComponent,
    PasswordResetComponent,
    CommentComponent,
    CommentSectionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    AuthApiModule.forRoot({ rootUrl: '/api/auth' }),
    SocialApiModule.forRoot({ rootUrl: '/api/social' })
  ],
  providers: [
    ApiInterceptor,
    API_INTERCEPTOR_PROVIDER
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
