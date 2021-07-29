import { HttpErrorResponse, HttpEvent, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators, FormArray } from '@angular/forms'
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { CategoryName } from '../category';
import { ICuisine } from '../interface/cuisine.interface';
import { IRecipe } from '../interface/recipe.interface';
import { IStep } from '../interface/step.interface';
import { CuisineService } from '../service/cuisine.service';
import { RecipeService } from '../service/recipe.service';

@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.css']
})
export class CreateRecipeComponent implements OnInit, OnDestroy {
  private formBuilder: FormBuilder;
  private recipeService: RecipeService;
  private cuisineService: CuisineService;
  private authService: AuthService;
  private subscriptions: Subscription[];
  private file: File | null;

  public categories: string[] | null;
  public cuisines: ICuisine[] | null;
  public createRecipeForm: FormGroup;

  public constructor(formBuilder: FormBuilder, recipeService: RecipeService, cuisineService: CuisineService, authService: AuthService) {
    this.formBuilder = formBuilder;
    this.recipeService = recipeService;
    this.cuisineService = cuisineService;
    this.authService = authService;
    this.subscriptions = [];

    this.file = null;
    this.categories = [];
    this.cuisines = [];
    this.createRecipeForm = this.formBuilder.group(this.buildFormGroup());
  }

  private buildFormGroup(): any {
    return {
      name: [null, [Validators.required, Validators.minLength(5)]],
      description: [null, [Validators.required, Validators.minLength(10)]],
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
      .subscribe((data: string[]) => {
        this.categories = data;
        this.categories = this.categories;
      }, (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse.error.message);
      });

    this.subscriptions.push(categories$);
  }

  private cuisinesSubscriber(): void {
    const cuisines$: Subscription = this.cuisineService
      .fetchAll()
      .subscribe((data: ICuisine[]) => { this.cuisines = data; },
        (errorResponse: HttpErrorResponse) => {
          console.log(errorResponse.error.message);
        });

    this.subscriptions.push(cuisines$);
  }

  public get steps(): FormArray {
    return this.createRecipeForm.get('steps') as FormArray;
  }

  public addStepHandler() {
    const newStep: FormGroup = this.formBuilder.group({
      number: [this.steps.length + 1],
      content: ['']
    });

    this.steps.push(newStep);
  }

  private uploadRecipeImage(id: string) {
    if (this.file === null) {
      console.log('Recipe image is reuired!');
      return;
    }

    const formData: FormData = new FormData();
    formData.append('image', this.file);
    formData.append('recipeId', id);

    // TODO look what is the return type from the server
    this.recipeService.uploadRecipeImage(formData)
      .subscribe((data: string) => {
        console.log(data);
      }, (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse);
      });
  }

  public selectFileHandler(event: any) {
    this.file = event.target.files[0];
  }

  public submitHandler() {
    const recipe: IRecipe = this.createRecipeForm.value

    // @ts-ignore: Object is possibly 'null'.
    recipe.publisher = this.authService.getLoggedInUsername();
    recipe.ingredients = recipe.ingredients.toString().split(', ');

    const createRecipe$ = this.recipeService.create(recipe)
      .subscribe((response: HttpResponse<IRecipe>) => {
        console.log('response: ', response);
        if (response.status === 200) {
          // @ts-ignore: Object is possibly 'null'.
          this.uploadRecipeImage(response.body?.id);
        } else {
          console.log('Your recipe has NOT been posted successfully!')
        }
      });
    this.subscriptions.push(createRecipe$);
  }

  public ngOnInit(): void {
    this.categoriesSubscriber();
    this.cuisinesSubscriber();
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subsription => subsription.unsubscribe());
  }
}
