import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Staff } from 'src/app/model/Staff';
import { StaffService } from 'src/app/services/staff/staff.service';
import { StaffFormComponent } from '../staff-form/staff-form.component';
import { UserProfileComponent } from '../user-profile/user-profile.component';

@Component({
  selector: 'app-staff-card',
  templateUrl: './staff-card.component.html',
  styleUrls: ['./staff-card.component.less']
})
export class StaffCardComponent implements OnInit {

  @Input() staff?: Staff;
  @Output() deleteEvent = new EventEmitter<Staff>();

  constructor(private dialog: MatDialog, private staffService: StaffService, private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  deleteStaff(id: number) {
    if (this.staff) {
      this.staffService.deleteStaff(id);
      this.toastr.success('Successfuly deleted user.');
      this.deleteEvent.emit(this.staff);
    }
  }

  viewProfile(id: number) {
    let dialog = this.dialog.open(UserProfileComponent, { width: '50%', height: '60%' });
    dialog.componentInstance.staffId = id;
  }

  editProfile(staff: Staff) {
    let dialog = this.dialog.open(StaffFormComponent, { width: '50%', height: '60%' });
    dialog.componentInstance.setStaff(staff);
  }

}
