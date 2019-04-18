import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "../material/material.module";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";

@NgModule({
  exports: [
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    RouterTestingModule,
    HttpClientTestingModule
  ]
})
export class TestcoreModule {
}
