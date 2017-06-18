import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { RegistrationService } from '../../_services/registration.service';
import { PasswordValidation } from '../../_validators/password-validator';


@Component({
    selector: 'registration',
    templateUrl: 'registration.template.html'
})

export class RegistrationComponent implements OnInit {

    formGroup: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private registrationService: RegistrationService) { }

    ngOnInit() {

        this.formGroup = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            userName: ['', Validators.required],
            email: ['', Validators.required],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required]
        }, {
            validator: PasswordValidation.MatchPassword // your validation method
        });

    }

    register(model) {
        console.log(model);
    }
}