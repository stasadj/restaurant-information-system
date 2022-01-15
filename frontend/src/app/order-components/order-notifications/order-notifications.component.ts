import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { Notification } from 'src/app/model/Notification';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-order-notifications',
  templateUrl: './order-notifications.component.html',
  styleUrls: ['./order-notifications.component.less'],
})
export class OrderNotificationsComponent implements OnInit, OnDestroy {
  notifications?: Notification[];
  private subscriptions = new Subscription();

  @Output() clickEvent = new EventEmitter<number>();

  constructor(private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.notificationService.connect();
    let c = this.notificationService.connected$.subscribe(() =>
      this.notificationService
        .getAllForWaiter()
        .subscribe((d) => (this.notifications = d))
    );
    this.subscriptions.add(c);
    this.subscriptions.add(
      this.notificationService.notificationSubject.subscribe((n) =>
        this.receiveNotification(n)
      )
    );
    this.subscriptions.add(
      this.notificationService.finalizedSubject.subscribe((id) =>
        this.deleteNotifications(id)
      )
    );
  }
  ngOnDestroy(): void {
    this.notificationService.disconnect();
    this.subscriptions.unsubscribe();
  }

  receiveNotification(notification: Notification) {
    if (localStorage.getItem('userId') !== notification.waiterId + '') return;
    this.notifications?.push(notification);
  }

  deleteNotifications(id: number) {
    this.notifications = this.notifications?.filter((n) => n.orderId !== id);
  }

  onClick(tableId: number) {
    this.clickEvent.emit(tableId);
  }
}
