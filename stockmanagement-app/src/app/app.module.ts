import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MaterialModule} from './material/material.module';
import {CoreModule} from './core/core.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {StockListComponent} from './stock/stock-list/stock-list.component';
import {StockDetailsComponent} from './stock/stock-details/stock-details.component';
import {DepotCreateDialogComponent} from './depots/depot-create-dialog.component';
import { AlertCreateDialogComponent } from './alert/alert-create-dialog/alert-create-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    DepotCreateDialogComponent,
    StockListComponent,
    StockDetailsComponent,
    AlertCreateDialogComponent,
  ],
  imports: [
    CoreModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    DepotCreateDialogComponent,
    AlertCreateDialogComponent
  ]
})
export class AppModule {
}
