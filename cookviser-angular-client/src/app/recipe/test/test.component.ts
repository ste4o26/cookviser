import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  private file: File | null = null;
  private http: HttpClient;
  public count: any = 5;

  constructor(http: HttpClient) {
    this.http = http;
  }

  ngOnInit(): void {
  }

  onRateHandler(event: any){
    console.log('old value: ', event.oldValue);
    console.log('new value: ', event.newValue);
  }

  public fileSelectHandler(event: any): void {
    this.file = event.target.files[0];
    console.log(this.file);
  }

  public submitFileHandler() {
    const formData: FormData = new FormData();
    if (this.file === null) {
      console.log('File is required!');
      return;
    }

    formData.append('image', this.file);
    formData.append('recipeId', 'gefbegrfbef');

    const observable: Observable<HttpResponse<string>> =
      this.http.post<string>(`${environment.domain}/recipe/upload-recipe-image`, formData, { observe: 'response' });

    observable.subscribe((response: any) => {
      console.log(response.status);
      console.log(response.body);
      console.log(response.headers);
    }, (errorResponse: HttpErrorResponse) => {
      console.log(errorResponse);
    })
  }
}
