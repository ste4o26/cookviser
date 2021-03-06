import { IAuthority } from "./authority.interface";
import { IRole } from "./role.interface";

export interface IUser {
    id: string;
    username: string;
    profileImageUrl: string;
    description: string;
    email: string;
    role: IRole;
    authorities: IAuthority[];
    myRecipes: any[];
    myCookedRecipes: any[];
}
