import { Pipe, PipeTransform } from '@angular/core';
import { ItemDatapoint } from 'src/app/model/ItemDatapoint';

@Pipe({
  name: 'addTotalEntryToTable'
})
export class AddTotalEntryPipe implements PipeTransform {

  transform(items: ItemDatapoint[]): ItemDatapoint[] {
    let totalEntry: ItemDatapoint = {
      name: "Total",
      expenses: 0,
      grossIncome: 0,
      quantity: 0,
      netIncome: 0
    };
    items.forEach(item => {
      totalEntry.expenses += item.expenses;
      totalEntry.grossIncome += item.grossIncome;
      totalEntry.quantity += item.quantity;
      if (totalEntry.netIncome && item.netIncome) {
        totalEntry.netIncome += item.netIncome;
      }
    });
    items.push(totalEntry);
    return items;
  }

}
