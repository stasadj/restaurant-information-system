import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'removeSpecialChars'
})
export class RemoveSpecialCharsPipe implements PipeTransform {

  transform(value: string): string {
    return value.replace(/\W|_/g, match => ' ');
  }

}
