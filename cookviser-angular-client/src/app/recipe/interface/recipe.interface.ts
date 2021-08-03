import { IUser } from "src/app/auth/interface/user.interface";
import { CategoryName } from "../category";
import { ICuisine } from "./cuisine.interface";
import { IStep } from "./step.interface";

export interface IRecipe {
    id: string;
    name: string;
    description: string;
    // recipeImageThumbnail: string;
    recipeThumbnail: string;
    portions: number;
    duration: number;
    category: CategoryName;
    cuisine: ICuisine;
    ingredients: string[];
    steps: IStep[];
    publisherUsername: string;
    cookedBy: IUser[];
    overallRating: number;
}
