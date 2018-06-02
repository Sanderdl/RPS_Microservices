import {Component, OnInit} from '@angular/core';
import {Match} from '../models/match';
import {MatchService} from '../match.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {MatSnackBar} from '@angular/material';

@Component({
    selector: 'app-match',
    templateUrl: './match.component.html',
    styleUrls: ['./match.component.css']
})
export class MatchComponent implements OnInit {

    match: Match;
    previousMatch: Match = new Match();
    roomName: string;

    player: number;
    otherPlayer: number;

    yourWins: number;
    otherWins: number;

    status: string;

    constructor(private matchService: MatchService,
                private auth: AuthService,
                private route: ActivatedRoute,
                private router: Router,
                public snackbar: MatSnackBar) {
    }

    ngOnInit() {
        this.route.params.subscribe(
            params => {
                this.roomName = params['room'];
                this.handleStart();
            }
        );
    }

    handleStart() {
        this.matchService.connectUser()
            .subscribe(
                data => {
                    if (this.match) {
                        this.previousMatch = this.match;
                    }
                    this.match = data;
                    this.determinePlayers();
                    this.updateWins();
                    this.handleRound();
                }
            );

    }

    ready() {
        this.matchService.doMove(
            {
                roomName: this.roomName,
                userId: this.auth.getId(),
                type: 0
            }
        );
    }


    determinePlayers() {
        if (this.auth.getId() === this.match.player1) {
            this.player = 1;
            this.otherPlayer = 2;
        } else {
            this.player = 2;
            this.otherPlayer = 1;
        }
    }

    updateWins() {
        if (this.player === 1) {
            this.yourWins = this.match.winsPlayer1;
            this.otherWins = this.match.winsPlayer2;
        } else {
            this.yourWins = this.match.winsPlayer2;
            this.otherWins = this.match.winsPlayer1;
        }
    }

    doMove(type: number) {
        this.matchService.doMove(
            {
                roomName: this.roomName,
                userId: this.auth.getId(),
                type: type
            }
        );
    }

    handleRound() {
        if (this.match.currentRound === 0) {
            this.status = 'Other player is not ready yet...';
        } else if (this.match.currentRound > this.previousMatch.currentRound && this.previousMatch.currentRound > 0) {
            const otherAnswer = this.getAnswerForPlayer(this.otherPlayer, this.match.currentRound);
            this.status = 'Other player choose ' + this.typeAsString(otherAnswer);
            this.snackbar.open(this.determineWinnerOfRound(), '', {duration: 2000});
        } else {
            this.status = 'Other player is making a decision...';
        }
    }

    private getAnswerForPlayer(player: number, round: number): any {
        round--;
        if (player === 1) {
            return this.match.answersPlayer1[JSON.stringify(round)].type;
        } else if (player === 2) {
            return this.match.answersPlayer2[round].type;
        }
    }

    private typeAsString(type: number): string {
        if (type === 1) {
            return 'rock';
        }

        if (type === 2) {
            return 'scissors';
        }

        if (type === 3) {
            return 'paper';
        }
    }

    private determineWinnerOfRound(): string {
        if (this.match.winsPlayer1 === this.previousMatch.winsPlayer1
            && this.match.winsPlayer2 === this.previousMatch.winsPlayer2) {
            return 'There was a draw';
        }

        if (this.match.winsPlayer1 > this.previousMatch.winsPlayer1) {
            console.log('player 1 won');
            if (this.player === 1) {
                return 'You have won this round';
            } else {
                return 'Other player won this round';
            }

        } else if (this.match.winsPlayer2 > this.previousMatch.winsPlayer2) {
            console.log('player 2 won');
            if (this.player === 2) {
                return 'You have won this round';
            } else {
                return 'Other player won this round';
            }
        }
    }


}
