import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  private readonly path: string = '/api/websocket';
  private stompClient: Stomp.Client;

  private subject: Subject<{ message: string }> = new Subject();

  constructor() {
    this.stompClient = Stomp.over(new SockJS(this.path));
  }

  _connect() {
    console.log('Initialize WebSocket connection');
    this.stompClient = Stomp.over(new SockJS(this.path));
    this.stompClient.connect(
      {},
      () =>
        this.stompClient.subscribe('/topic/cook', (m) =>
          this.onMessageReceived(m)
        ),
      this.errorCallBack
    );
  }

  _disconnect() {
    this.stompClient?.disconnect(() => console.log('Disconnected'));
  }

  errorCallBack(error: any) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  private onMessageReceived(message: Stomp.Message) {
    this.subject.next({ message: message.body });
  }
  getSub() {
    return this.subject;
  }
}
