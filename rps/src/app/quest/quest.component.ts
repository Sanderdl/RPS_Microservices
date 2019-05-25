import {Component, OnInit} from '@angular/core';
import {QuestService} from '../quest.service';
import {Quest} from '../quest';
import {AuthService} from '../auth.service';

@Component({
    selector: 'app-quest',
    templateUrl: './quest.component.html',
    styleUrls: ['./quest.component.css']
})
export class QuestComponent implements OnInit {

    quests: Quest[];

    constructor(private questService: QuestService,
                private auth: AuthService) {
    }

    ngOnInit() {
        this.questService.getQuestsForUser(this.auth.getId())
            .subscribe(
                data => {
                    this.quests = data;
                }
            );
    }

}
