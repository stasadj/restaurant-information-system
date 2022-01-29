import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Staff } from 'src/app/model/Staff';
import { CapitalizeWordsPipe } from 'src/app/pipes/text/capitalize-words/capitalize-words.pipe';
import { StaffService } from 'src/app/services/staff/staff.service';

@Component({
  selector: 'app-staff-form',
  templateUrl: './staff-form.component.html',
  styleUrls: ['./staff-form.component.less']
})
export class StaffFormComponent implements OnInit {

  @Input() staff?: Staff;
  oldPin?: number;
  isCreating: boolean = true;

  roles: string[] = ['waiter', 'cook', 'barman'];

  firstNameControl?: FormControl;
  lastNameControl?: FormControl;
  emailControl?: FormControl;
  phoneNumberControl?: FormControl;
  monthlyWageControl?: FormControl;
  pinControl?: FormControl;
  roleControl?: FormControl;

  @Output() createEvent = new EventEmitter<Staff>();

  constructor(private capitalizeWordsPipe: CapitalizeWordsPipe, private staffService: StaffService, private dialogRef: MatDialogRef<StaffFormComponent>, private toastr: ToastrService) {
  }

  ngOnInit(): void {
  }

  editStaff() {
    if (this.staff
      && this.firstNameControl && this.firstNameControl.value
      && this.lastNameControl && this.lastNameControl.value
      && this.phoneNumberControl && this.phoneNumberControl.value
      && this.monthlyWageControl && this.monthlyWageControl.value) {
      this.staffService.updateStaff(this.staff).subscribe(() => {
        if (this.staff && this.staff.id) {
          this.staffService.changeSalary(this.staff.id, this.staff.monthlyWage).subscribe(() => {
            if (this.oldPin && this.staff && this.staff.pin && this.oldPin !== this.staff.pin) {
              this.staffService.changePin(this.oldPin, this.staff.pin).subscribe(staff => {
                this.staff = staff;
                this._successfulEditClose();
              });
            } else {
              this._successfulEditClose();
            }
          });
        }
      });
    }
  }

  createStaff() {
    if (this.staff
      && this.firstNameControl && this.firstNameControl.value
      && this.lastNameControl && this.lastNameControl.value
      && this.phoneNumberControl && this.phoneNumberControl.value
      && this.monthlyWageControl && this.monthlyWageControl.value) {
      this.staffService.createStaff(this.staff).subscribe(staff => {
        this.staff = staff;
        this._successfulCreateClose();
      });
    }
  }

  private _successfulEditClose() {
    this.toastr.success('Successfuly edited user.');
    this._closeDialog();
  }

  private _successfulCreateClose() {
    this.toastr.success('Successfuly created user.');
    this.createEvent.emit(this.staff);
    this._closeDialog();
  }

  setupCreation() {
    this.staff = {
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      monthlyWage: 0,
      pin: 0
    };

    this.createFormControls();
  }

  setStaff(staff: Staff) {
    this.isCreating = false;
    this.staff = staff;
    this.oldPin = staff.pin;

    this.createFormControls();
  }

  private createFormControls() {
    if (this.staff) {
      this.firstNameControl = new FormControl(this.staff.firstName, [
        Validators.required,
      ]);

      this.lastNameControl = new FormControl(this.staff.lastName, [
        Validators.required,
      ]);

      this.emailControl = new FormControl(this.staff.email, [
        Validators.email,
        Validators.required,
      ]);

      this.phoneNumberControl = new FormControl(this.staff.phoneNumber, [
        Validators.required,
        Validators.pattern('^([+][0-9]{3}[ ]?)?[0-9 -]*$'),
      ]);

      this.monthlyWageControl = new FormControl(this.staff.monthlyWage, [
        Validators.required,
        Validators.min(0),
      ]);

      this.pinControl = new FormControl(this.staff.pin, [
        Validators.required,
        Validators.pattern('^[0-9]{4}$'),
      ]);

      this.roleControl = new FormControl(this.staff.role, [
        Validators.required
      ]);

      this.firstNameControl.valueChanges.subscribe((v: string) => {
        if (this.staff && this.firstNameControl && this.firstNameControl.valid)
          this.staff.firstName = v;
      });

      this.lastNameControl.valueChanges.subscribe((v: string) => {
        if (this.staff && this.lastNameControl && this.lastNameControl.valid)
          this.staff.lastName = v;
      });

      this.phoneNumberControl.valueChanges.subscribe((v: string) => {
        if (this.staff && this.phoneNumberControl && this.phoneNumberControl.valid)
          this.staff.phoneNumber = v;
      });

      this.monthlyWageControl.valueChanges.subscribe((v: string) => {
        if (this.staff && this.monthlyWageControl && this.monthlyWageControl.valid)
          this.staff.monthlyWage = Number(v);
      });

      this.pinControl.valueChanges.subscribe((v: string) => {
        if (this.staff && this.pinControl && this.pinControl.valid)
          this.staff.pin = Number(v);
      });

      this.roleControl.valueChanges.subscribe((v: string) => {
        if (this.staff && this.roleControl && this.roleControl.valid)
          this.staff.role = v;
      });
    }
  }

  private _closeDialog() {
    this.dialogRef.close();
  }

}
