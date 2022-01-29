import { Pipe, PipeTransform } from '@angular/core';
import { Tag } from 'src/app/model/Tag';

@Pipe({
  name: 'tagsView'
})
export class TagsViewPipe implements PipeTransform {

    transform(tags:Array<Tag>, sep = ','): string {
        return tags.map(tag => tag.name).join(", ")
      }
    }
