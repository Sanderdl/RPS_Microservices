import {Injectable} from '@angular/core';
import {Urls} from './urls';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from './models/user';

@Injectable()
export class UserService {

    constructor(private http: HttpClient) {
    }

    public createUser(model: any): Observable<User> {
        return this.http.post<User>(Urls.userUrl, model);
    }

    public getUserById(id: number): Observable<User> {
        return this.http.get<User>(Urls.userUrl + '/' + id);
    }

}
