import { Category } from "./Category";
import { ItemType } from "./ItemType";
import { ItemValue } from "./ItemValue";
import { Tag } from "./Tag";

export interface Item {
    id: number;
    name: string;
    category: Category;
    description: string;
    imageURL: string;
    tags: Tag[];
    inMenu: boolean;
    itemType: ItemType;
    currentItemValue: ItemValue;
    deleted: boolean;
    imageBase64?: string;
  }