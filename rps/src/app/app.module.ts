import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MatInputModule} from '@angular/material/input';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {AppRoutingModule} from './app-routing.module';
import { RouterModule } from '@angular/router';
import {UserService} from './user.service';
import {AuthService} from './auth.service';
import {HttpClientModule} from '@angular/common/http';
import {MatButtonModule} from '@angular/material';
import {FormsModule} from '@angular/forms';
import { LobbyComponent } from './lobby/lobby.component';


@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        LobbyComponent
    ],
    imports: [
        BrowserModule,
        MatInputModule,
        FormsModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule,
        MatButtonModule
    ],
    providers: [UserService, AuthService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
