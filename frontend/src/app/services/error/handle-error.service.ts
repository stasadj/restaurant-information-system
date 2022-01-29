import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class HandleErrorService {
  constructor(private toastr: ToastrService) {}

  handleError(errorResponse: HttpErrorResponse) {
    if (errorResponse.status === 500)
      this.toastr.error('Internal server error');
    else this.toastr.error(errorResponse.error.message);
  }
}
