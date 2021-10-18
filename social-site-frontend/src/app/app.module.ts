import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MaterialModule } from './modules/material/material.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

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
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ValidateRegistrationComponent } from './components/validate-registration/validate-registration.component';

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
    ValidateRegistrationComponent
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
