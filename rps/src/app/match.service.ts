import {Injectable} from '@angular/core';
import {SocketService} from './socket.service';
import {Observable} from 'rxjs/Observable';
import {Subscriber} from 'rxjs/Subscriber';
import {Urls} from './urls';
import {MatchRequest} from './models/match-request';

@Injectable()
export class MatchService {

    constructor(private socketService: SocketService) {
    }

    connectUser(): Observable<any> {
        const openSubscriber = Subscriber.create();

        return this.socketService.createObservableSocket(Urls.matchUrl, openSubscriber)
            .map(message => JSON.parse(message));
    }

    doMove(request: MatchRequest) {
        this.socketService.send(JSON.stringify(request));
    }
}
