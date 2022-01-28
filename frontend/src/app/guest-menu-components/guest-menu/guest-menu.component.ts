import { Component, OnInit, Input } from '@angular/core';
import { Item } from 'src/app/model/Item';

@Component({
  selector: 'app-guest-menu',
  templateUrl: './guest-menu.component.html',
  styleUrls: ['./guest-menu.component.less'],
})
export class GuestMenuComponent implements OnInit {
  @Input() items: Item[] = [];

  constructor() {}

  ngOnInit(): void {}
}
