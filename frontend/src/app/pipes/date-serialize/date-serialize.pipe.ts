import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateSerialize'
})
export class DateSerializePipe implements PipeTransform {

  transform(date: Date): string {
    return date.toISOString().slice(0, 10).replace(/-/g, "-");
  }

}
