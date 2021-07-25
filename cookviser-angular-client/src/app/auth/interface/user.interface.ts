import { IAuthority } from "./authority.interface";
import { IRole } from "./role.interface";

export interface IUser {
    username: string;
    email: string;
    role: IRole;
    authorities: IAuthority[];
    myRecipes: any[];
    myCookedRecipes: any[];
}
