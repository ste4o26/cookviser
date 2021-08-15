import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms'
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { IUser } from 'src/app/auth/interface/user.interface';
import { ICuisine } from 'src/app/cuisine/interface/cuisine.interface';
import { CuisineService } from 'src/app/cuisine/service/cuisine.service';
import { ConstantMessages } from 'src/app/shered/constants';
import { NotificationService } from 'src/app/shered/notification.service';
import { UserService } from 'src/app/user/user.service';
import { IRate } from '../interface/rate.interface';
import { IRecipe } from '../interface/recipe.interface';
import { RecipeService } from '../service/recipe.service';

@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.css']
})
export class CreateRecipeComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  private file: File | null = null;

  public categories: string[] | null = [];
  public cuisines: ICuisine[] | null = [];
  public createRecipeForm: FormGroup;

  public constructor(private formBuilder: FormBuilder,
    private recipeService: RecipeService,
    private cuisineService: CuisineService,
    private authService: AuthService,
    private userService: UserService,
    private notificationService: NotificationService,
    private router: Router) {

    this.createRecipeForm = this.formBuilder.group(this.buildFormGroup());
  }

  private buildFormGroup(): any {
    return {
      name: [null, [Validators.required, Validators.minLength(5)]],
      description: [null, [Validators.required, Validators.minLength(10)]],    
      recipeImage: [null, [Validators.required]],
      portions: [null, [Validators.required, Validators.min(1)]],
      duration: [null, Validators.required],
      category: [null, Validators.required],
      cuisine: [null, Validators.required],
      ingredients: [null, Validators.required],
      steps: this.formBuilder.array([
        this.formBuilder.group({
          number: [1],
          content: ['', [Validators.required, Validators.minLength(10)]]
        })
      ])
    };
  }

  private categoriesSubscriber(): void {
    const categories$: Subscription = this.recipeService
      .fetchAllCategories()
      .subscribe((data: string[]) => this.categories = data.map(category => category.toLowerCase()),
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(categories$);
  }

  private cuisinesSubscriber(): void {
    const cuisines$: Subscription = this.cuisineService
      .fetchAll()
      .subscribe((data: ICuisine[]) => {
        this.cuisines = data;
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(cuisines$);
  }

  private uploadRecipeImage(id: string): void {
    if (this.file === null) {
      this.notificationService.showError(ConstantMessages.IMAGE_REQUIRED_ERROR);
      return;
    }

    const formData: FormData = new FormData();
    formData.append('image', this.file);
    formData.append('recipeId', id);

    this.recipeService.uploadRecipeImage(formData)
      .subscribe((response: HttpResponse<IRecipe>) => {
        if (!response.ok || response.body === null) {
          this.notificationService.showError(ConstantMessages.PLEASE_TRY_AGAIN_ERROR);
          return;
        }

        this.rateRecipe(response.body);
      }, (errorResponse: HttpErrorResponse) => { this.notificationService.showError(errorResponse.error.message) });
  }

  private rateRecipe(recipe: IRecipe) {
    const user$: Subscription = this.userService
      .fetchByUsername(recipe.publisherUsername)
      .subscribe((response: HttpResponse<IUser>) => {
        if (response.ok) {
          const rating: IRate = {
            rateValue: 0,
            user: response.body,
            recipe
          }

          const rate$: Subscription = this.recipeService.rate(rating)
            .subscribe(data => {
              if (data === null) this.notificationService.showError(ConstantMessages.PLEASE_TRY_AGAIN_ERROR);
            }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

          this.subscriptions.push(rate$);
        }
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(user$);
  }

  public addStepHandler(): void {
    const newStep: FormGroup = this.formBuilder.group({
      number: [this.steps.length + 1],
      content: ['']
    });

    this.steps.push(newStep);
  }

  public selectFileHandler(event: any): void {
    this.file = event.target.files[0];
  }

  public submitHandler(): void {
    const recipe: IRecipe = this.createRecipeForm.value

    // @ts-ignore: Object is possibly 'null'.
    recipe.publisher = this.authService.getLoggedInUsername();
    recipe.ingredients = recipe.ingredients.toString().split(', ');

    const createRecipe$ = this.recipeService.create(recipe)
      .subscribe((response: HttpResponse<IRecipe>) => {
        console.log(response);
        if (response === null || response.status !== 201 || response.body == null) {
          this.notificationService.showError(ConstantMessages.RECIPE_NOT_CREATED_ERROR);
          return;
        }

        this.uploadRecipeImage(response.body?.id);
        this.notificationService.showSucces(ConstantMessages.RECIPE_CREATED_SUCCESS);
        this.router.navigateByUrl(`/recipe/details/${response.body.id}`);
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(createRecipe$);
  }

  public get steps(): FormArray {
    return this.createRecipeForm.get('steps') as FormArray;
  }

  public ngOnInit(): void {
    this.categoriesSubscriber();
    this.cuisinesSubscriber();
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subsription => subsription.unsubscribe());
  }
}
