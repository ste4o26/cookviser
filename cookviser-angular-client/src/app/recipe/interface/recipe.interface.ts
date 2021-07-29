import { CategoryName } from "../category";
import { IStep } from "./step.interface";

export interface IRecipe {
    id: string;
    name: string;
    description: string;
    recipeImageThumbnail: string;
    portions: number;
    duration: number;
    category: CategoryName;
    cuisine: string;
    ingredients: string[];
    steps: IStep[];
    publisher: string;
}
