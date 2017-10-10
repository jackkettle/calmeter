import { Component } from '@angular/core';
import { FormGroup, AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EmailValidator, EqualPasswordsValidator } from '../../_validators';
import { RegistrationService } from '../../_services';

@Component({
    selector: 'registration',
    templateUrl: './registration.template.html'
})
export class RegistrationComponent {

    public form: FormGroup;
     public username: AbstractControl;
    public firstName: AbstractControl;
    public lastName: AbstractControl;
    public email: AbstractControl;
    public password: AbstractControl;
    public confirmPassword: AbstractControl;
    public passwords: FormGroup;

    public submitted: boolean = false;
    public errorMessage: string = "";

    constructor(fb: FormBuilder, private registrationService: RegistrationService, private router: Router) {

        this.form = fb.group({
            'username': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
            'firstName': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
            'lastName': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
            'email': ['', Validators.compose([Validators.required, EmailValidator.validate])],
            'passwords': fb.group({
                'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
                'confirmPassword': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
            }, { validator: EqualPasswordsValidator.validate('password', 'confirmPassword') })
        });

        this.username = this.form.controls['username'];
        this.firstName = this.form.controls['firstName'];
        this.lastName = this.form.controls['lastName'];
        this.email = this.form.controls['email'];
        this.passwords = <FormGroup>this.form.controls['passwords'];
        this.password = this.passwords.controls['password'];
        this.confirmPassword = this.passwords.controls['confirmPassword'];
    }

    public register(values: Object): void {
        this.submitted = true;
        if (this.form.valid) {
            console.log(values);
            this.registrationService.register(values).subscribe(
                result => {
                    this.errorMessage = "";
                    this.router.navigate(['/login']);
                },
                error => {
                    this.errorMessage = error._body;
                    console.log("Error: {}", error);
                }
            )
        }
    }
}