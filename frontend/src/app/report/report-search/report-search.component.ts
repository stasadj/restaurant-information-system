import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Item } from 'src/app/model/Item';
import { ReportGranularity } from 'src/app/model/ReportGranularity';
import { ReportQuery } from 'src/app/model/ReportQuery';
import { ReportType } from 'src/app/model/ReportType';
import { DateSerializePipe } from 'src/app/pipes/date-serialize/date-serialize.pipe';
import { ItemService } from 'src/app/services/item/item.service';
import { EMPTY, Observable } from 'rxjs';
import { map, startWith, filter } from 'rxjs/operators';

@Component({
  selector: 'app-report-search',
  templateUrl: './report-search.component.html',
  styleUrls: ['./report-search.component.less']
})
export class ReportSearchComponent implements OnInit {

  itemFormControl: FormControl = new FormControl();

  granularities: ReportGranularity[] = [
    ReportGranularity.DAILY,
    ReportGranularity.WEEKLY,
    ReportGranularity.MONTHLY,
    ReportGranularity.QUARTERLY,
    ReportGranularity.YEARLY
  ];

  reportTypes: ReportType[] = [
    ReportType.PROFIT,
    ReportType.PRICE_HISTORY
  ];

  @Input() query: ReportQuery = {
    fromDate: this.dataSerialize.transform(new Date()),
    toDate: this.dataSerialize.transform(new Date()),
    reportGranularity: ReportGranularity.DAILY,
    reportType: ReportType.PROFIT,
    itemId: this.selectedItem?.id
  };

  @Output() queryChangedEvent = new EventEmitter<ReportQuery>();

  items: Item[] = [];
  filteredItems?: Observable<Item[]>;
  selectedItem?: Item;

  constructor(private dataSerialize: DateSerializePipe, private itemService: ItemService) { }

  ngOnInit(): void {
    this.itemService.getItems().subscribe(items => {
      this.items = items;
      this.filteredItems = this.itemFormControl.valueChanges.pipe(
        startWith(''),
        map(value => this._filterItem(value))
      )
    });

    this.itemFormControl.valueChanges.subscribe(value => this.changeItemId(value));
  }

  onQueryChange() {
    this.queryChangedEvent.emit(this.query);
  }

  changeItemId(selectedItem: any) {
    const oldId = this.query.itemId;
    if (selectedItem) {
      if (typeof selectedItem !== 'string') {
        this.query.itemId = selectedItem.id;
        if (this.query.itemId !== oldId) {
          this.onQueryChange();
        }
      }
    } else {
      if (this.query.itemId) {
        console.log('test')
        delete this.query.itemId;
        this.onQueryChange();
      }
    }
  }

  displayItemName(item?: Item): string {
    if (item) {
      return item.name;
    }
    return '';
  }

  private _filterItem(value: any): Item[] {
    function _instanceOfItem(object: any): object is Item {
      return 'name' in object;
    }

    let filterValue = '';
    if (value) {
      if (typeof value === 'string') {
        filterValue = value.toLowerCase();
      } else {
        if (_instanceOfItem(value)) {
          filterValue = value.name.toLowerCase();
        }
      }
      if (this.items) {
        /** Put all items that match up at the top and sort them.
         * The rest of the items that don't match are also sorted.
         *
         * Used filter before to remove items that don't match but
         * it ended up being annoying to delete the old query so the
         * user can click the new one instead of just clicking on the
         * new one if it was conventient.
         */
        return this.items.sort((a, b) => {
          let aa = a.name.toLowerCase().includes(filterValue);
          let bb = b.name.toLowerCase().includes(filterValue);
          return (
            aa && bb ? a.name.localeCompare(b.name)
              : aa && !bb ? -1
                : !aa && bb ? 1
                  : a.name.localeCompare(b.name)
          )
        })
        // return this.items.filter((item => item.name.toLowerCase().includes(filterValue)));
      }
    }
    return this.items.sort(((a, b) => a.name.localeCompare(b.name)));
  }

}
