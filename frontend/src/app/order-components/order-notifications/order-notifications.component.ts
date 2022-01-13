import { Component, OnDestroy, OnInit } from '@angular/core';
import { Notification } from 'src/app/model/Notification';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-order-notifications',
  templateUrl: './order-notifications.component.html',
  styleUrls: ['./order-notifications.component.less'],
})
export class OrderNotificationsComponent implements OnInit, OnDestroy {
  notifications: Notification[] = [];

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.notificationService.connect();
    this.notificationService
      .getAllForWaiter()
      .subscribe((d) => (this.notifications = d));
    this.notificationService.notificationSubject.subscribe((n) =>
      this.receiveNotification(n)
    );
  }
  ngOnDestroy(): void {
    this.notificationService.disconnect();
    this.notificationService.notificationSubject.unsubscribe();
  }

  receiveNotification(notification: Notification) {
    if (localStorage.getItem('userId') !== notification.waiterId + '') return;
    this.notifications.push(notification);
  }
}
