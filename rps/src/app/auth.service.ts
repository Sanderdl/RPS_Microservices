import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {TokenLogin} from './models/token-login';
import {Urls} from './urls';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthService {

    constructor(private http: HttpClient) {
    }

    login(username: string, password: string): Observable<TokenLogin> {
        const body = {username: username, password: password};
        return this.http.post<TokenLogin>(Urls.authUrl, body).map(
            res => {
                this.setSession(res);
                return res;
            }
        );

    }

    private setSession(reply: TokenLogin) {
        localStorage.setItem('rps_token', reply.token);
        localStorage.setItem('rps_user', JSON.stringify(reply.user));
    }

    logout() {
        localStorage.removeItem('rps_token');
        localStorage.removeItem('rps_user');
    }

    public isLoggedIn() {
        return localStorage.getItem('rps_user') != null;
    }

    getId(): number {
        const user = localStorage.getItem('rps_user');
        const json = JSON.parse(user);
        return json.id;
    }

}
