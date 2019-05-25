import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
    selector: 'app-end-match',
    templateUrl: './end-match.component.html',
    styleUrls: ['./end-match.component.css']
})
export class EndMatchComponent implements OnInit {

    constructor(@Inject(MAT_DIALOG_DATA) public data: any,
                public dialogRef: MatDialogRef<EndMatchComponent>) {
    }

    ngOnInit() {
    }

    close() {
        this.dialogRef.close();
    }

}
