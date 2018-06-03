import {Component, OnInit} from '@angular/core';
import {LobbyService} from '../lobby.service';
import {Room} from '../models/room';
import {RoomEvent} from '../models/room-event';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {MessageEvent} from '../models/message-event';

@Component({
    selector: 'app-lobby',
    templateUrl: './lobby.component.html',
    styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit {

    rooms: Room[] = [];
    currentRoom: Room;

    constructor(private lobbyService: LobbyService,
                private auth: AuthService,
                private router: Router) {
    }

    ngOnInit() {
        this.getRooms();
        this.connectUser();
    }

    private getRooms() {
        this.lobbyService.getCurrentRooms()
            .subscribe(
                data => {
                    this.rooms = data;
                }
            );
    }

    private connectUser() {
        this.lobbyService.connectUser()
            .subscribe(
                data => {
                    console.log(data)
                    if (data.status && data.status === 'CREATED') {
                        this.sendToMatch(data.name);
                    } else {
                        this.updateRoom(data);
                    }
                }
            );
    }

    enterOrLeaveRoom(room: Room, intention: string) {
        const roomEvent: RoomEvent = {
            name: room.name,
            userId: this.auth.getId(),
            intention: intention
        };

        if (intention === 'ENTER') {
            this.currentRoom = room;
        } else {
            this.currentRoom = null;
        }

        this.lobbyService.changeRoom(roomEvent);
    }

    updateRoom(room: Room) {
        const updateItem = this.rooms.find(o => o.name === room.name);

        const index = this.rooms.indexOf(updateItem);

        this.rooms[index] = room;

    }

    private sendToMatch(roomName: string) {
        this.router.navigate(['match', roomName]);
    }

}
