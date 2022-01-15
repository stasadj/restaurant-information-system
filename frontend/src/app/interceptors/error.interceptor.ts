import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HandleErrorService } from '../services/error/handle-error.service';

@Injectable()
export class HandleErrorInterceptor implements HttpInterceptor {
  constructor(private errorService: HandleErrorService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return new Observable((observer) => {
      next.handle(request).subscribe({
        next: (res: HttpEvent<any>) => {
          if (res instanceof HttpResponse) observer.next(res);
        },
        error: (err: HttpErrorResponse) => {
          this.errorService.handleError(err);
        },
      });
    });
  }
}
