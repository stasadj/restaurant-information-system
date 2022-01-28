import { Pipe, PipeTransform } from '@angular/core';
import { ItemDatapoint } from 'src/app/model/ItemDatapoint';

@Pipe({
  name: 'fillItemDatapointNetIncome'
})
export class FillItemNetincomePipe implements PipeTransform {

  transform(values: ItemDatapoint[]): ItemDatapoint[] {
    values.forEach(value => {
      value.netIncome = value.grossIncome - value.expenses;
    });
    return values;
  }

}
