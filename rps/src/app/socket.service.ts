import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subscriber} from 'rxjs/Subscriber';

@Injectable()
export class SocketService {
    private ws: WebSocket;

    createObservableSocket(url: string, openSubscriber: Subscriber<any>): Observable<any> {
        this.ws = new WebSocket(url);
        return new Observable(observer => {
            this.ws.onmessage = event => observer.next(event.data);
            this.ws.onerror = event => observer.error(event);
            this.ws.onclose = event => observer.complete();
            this.ws.onopen = event => {
                openSubscriber.next(event);
                openSubscriber.complete();
            };

            return () => this.ws.close();
        });
    }

    send(message: any) {
        this.ws.send(message);
    }
}