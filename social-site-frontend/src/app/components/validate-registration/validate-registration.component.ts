import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RegisterService } from 'src/app/api/services';

@Component({
    selector: 'app-validate-registration',
    templateUrl: './validate-registration.component.html',
    styleUrls: ['./validate-registration.component.css']
})
export class ValidateRegistrationComponent implements OnInit {
    activated: boolean = false;
    failed: boolean = false;

    constructor(private registerService: RegisterService, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const key: string = params['key'];
            this.registerService.getRegisterValidate({key}).subscribe(
                res => {
                    this.activated = true;
                },
                err => {
                    this.failed = true;
                }
            )
        });
    }
}
