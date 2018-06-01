import {Component, OnInit} from '@angular/core';
import {UserService} from '../user.service';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    username: string;
    password: string;

    constructor(private userService: UserService,
                private auth: AuthService,
                private router: Router) {
    }

    ngOnInit() {
    }

    onSubmit() {
        console.log(this.username);
        console.log(this.password);
        this.userService.createUser(
            {
                username: this.username,
                password: this.password
            }
        ).subscribe(
            data => {
                console.log(data);
                this.auth.login(data.username, this.password)
                    .subscribe(
                        login => {
                            this.router.navigate(['lobby']);
                        }
                    );
                this.username = null;
                this.password = null;
            }
        );
    }

}
