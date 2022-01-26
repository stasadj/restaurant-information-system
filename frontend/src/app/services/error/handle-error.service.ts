import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class HandleErrorService {
  constructor(private toastr: ToastrService) {}

  handleError(errorResponse: HttpErrorResponse) {
    this.toastr.error(errorResponse.error.message);
  }
}
