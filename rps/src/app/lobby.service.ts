import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Urls} from './urls';
import {Subscriber} from 'rxjs/Subscriber';
import {SocketService} from './socket.service';
import {RoomEvent} from './models/room-event';

@Injectable()
export class LobbyService {

    constructor(private http: HttpClient,
                private socketService: SocketService) {
    }

    getCurrentRooms(): Observable<any> {
        return this.http.get(Urls.roomsUrl);
    }

    connectUser(): Observable<any> {
        const openSubscriber = Subscriber.create();

        return this.socketService.createObservableSocket(Urls.lobbyUrl, openSubscriber)
            .map(message => JSON.parse(message));
    }

    changeRoom(event: RoomEvent) {
        this.socketService.send(JSON.stringify(event));
    }

}
