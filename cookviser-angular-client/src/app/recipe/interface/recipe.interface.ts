import { IUser } from "src/app/auth/interface/user.interface";
import { ICuisine } from "src/app/cuisine/interface/cuisine.interface";
import { CategoryName } from "../category";
import { IStep } from "./step.interface";

export interface IRecipe {
    id: string;
    name: string;
    description: string;
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
