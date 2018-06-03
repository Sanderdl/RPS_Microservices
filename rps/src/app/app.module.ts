import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MatInputModule} from '@angular/material/input';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {AppRoutingModule} from './app-routing.module';
import {RouterModule} from '@angular/router';
import {UserService} from './user.service';
import {AuthService} from './auth.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatButtonModule, MatDialogModule, MatExpansionModule, MatSnackBarModule} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {LobbyComponent} from './lobby/lobby.component';
import {AuthInterceptor} from './auth-intercepter';
import {LobbyService} from './lobby.service';
import {SocketService} from './socket.service';
import { MatchComponent } from './match/match.component';
import {MatchService} from './match.service';
import { EndMatchComponent } from './end-match/end-match.component';
import { QuestComponent } from './quest/quest.component';
import {QuestService} from './quest.service';


@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        LobbyComponent,
        MatchComponent,
        EndMatchComponent,
        QuestComponent
    ],
    imports: [
        BrowserModule,
        MatInputModule,
        FormsModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule,
        MatButtonModule,
        MatExpansionModule,
        MatSnackBarModule,
        MatDialogModule
    ],
    providers: [
        UserService,
        AuthService,
        LobbyService,
        SocketService,
        MatchService,
        QuestService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        }
    ],
    bootstrap: [AppComponent],
    entryComponents: [
        EndMatchComponent
    ]
})
export class AppModule {
}
