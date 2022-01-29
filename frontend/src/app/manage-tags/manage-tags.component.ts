import { Component, Input, OnInit } from '@angular/core';
import { TagService } from '../services/tag/tag.service';
import { Tag } from '../model/Tag';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-manage-tags',
    templateUrl: './manage-tags.component.html',
    styleUrls: ['./manage-tags.component.less']
})
export class ManageTagsComponent implements OnInit {

    public newTag: Tag = {
        id:0,
        name:""
    }
    displayedColumns: string[] = ['id', 'name'];

    @Input() tags: Tag[] = []
    @Input() onTagCreated = () => {

    };
    constructor(private tagService: TagService, private toastr: ToastrService) { }

    ngOnInit(): void {}

    onSaveClick(): void{
        if (this.tags.filter(tag => tag.name === this.newTag.name).length === 0){
            this.tagService.create(this.newTag).subscribe(res => {
                this.toastr.success("Successfully saved tag " + res.name);
                this.onTagCreated();

                //resetting form value
                this.newTag.name = "";
                this.newTag.id = 0;

            })
        }
        else{
            this.toastr.error("Tag with name " + this.newTag.name + " already exists.");
        }
    }


}
