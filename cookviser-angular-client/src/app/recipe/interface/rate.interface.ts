import { IUser } from "src/app/auth/interface/user.interface";
import { IRecipe } from "./recipe.interface";

export interface IRate {
    rateValue: number;
    user: IUser | null;
    recipe: IRecipe | null;
}
