import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'capitalizeWords'
})
export class CapitalizeWordsPipe implements PipeTransform {

  transform(value: string): string {
    return value.replace(/(?:^|\s|["'([{])+\S/g, match => match.toUpperCase());
  }

}
