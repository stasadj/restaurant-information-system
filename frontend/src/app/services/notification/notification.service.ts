import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Notification } from 'src/app/model/Notification';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private readonly wsPath: string = '/api/websocket';
  private readonly path: string = '/api/notifications';
  private stompClient?: Stomp.Client;

  readonly notificationSubject: Subject<Notification> = new Subject();
  readonly finalizedSubject: Subject<number> = new Subject();

  readonly connected$: Subject<void> = new Subject();

  constructor(private http: HttpClient) {}

  getAllForWaiter(): Observable<Notification[]> {
    const id = localStorage.getItem('userId');
    return this.http.get<Notification[]>(`${this.path}/${id}`);
  }

  connect() {
    console.log('Initialize WebSocket connection');
    this.stompClient = Stomp.over(new SockJS(this.wsPath));
    const role = localStorage.getItem('role');
    this.stompClient.connect(
      {},
      () => {
        this.stompClient?.subscribe(`/topic/${role}`, (m) =>
          this.onMessageReceived(m)
        );
        this.stompClient?.subscribe('/topic/finalized-order', (m) =>
          this.onFinalizedOrder(m)
        );
        this.connected$.next();
      },
      this.errorCallBack
    );
  }

  disconnect() {
    this.stompClient?.disconnect(() => {
      console.log('Disconnected');
    });
  }

  errorCallBack(error: any) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this.connect();
    }, 5000);
  }

  private onMessageReceived(message: Stomp.Message) {
    this.notificationSubject.next(JSON.parse(message.body));
  }
  private onFinalizedOrder(message: Stomp.Message) {
    this.finalizedSubject.next(Number(message.body));
  }
}
