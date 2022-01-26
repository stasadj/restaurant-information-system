import { Component, OnInit } from '@angular/core';
import { ItemService } from 'src/app/services/item/item.service';

@Component({
  selector: 'app-manager-page',
  templateUrl: './manager-page.component.html',
  styleUrls: ['./manager-page.component.less']
})
export class ManagerPageComponent implements OnInit {

  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
      
  }

}
