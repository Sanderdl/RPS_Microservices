import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {Quest} from './quest';
import {Urls} from './urls';

@Injectable()
export class QuestService {

    constructor(private http: HttpClient) {
    }

    getQuestsForUser(userId: number): Observable<Quest[]> {
        return this.http.get<Quest[]>(Urls.questUrl + userId);
    }
}
