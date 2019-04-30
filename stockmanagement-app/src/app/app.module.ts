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
import {AlarmCreateDialogComponent} from './alert/alert-create-dialog/alarm-create-dialog.component';
import {DepotSwitchCreateComponent} from './depots/depot-switch-create.component';
import {HomeComponent} from './home/home.component';
import {NavigationComponent} from './navigation/navigation.component';
import {DepotOverviewComponent} from './depots/depot-overview/depot-overview.component';
import {DepotStocksComponent} from './depots/depot-stocks/depot-stocks.component';

@NgModule({
  declarations: [
    AppComponent,
    DepotCreateDialogComponent,
    StockListComponent,
    StockDetailsComponent,
    AlarmCreateDialogComponent,
    DepotSwitchCreateComponent,
    HomeComponent,
    NavigationComponent,
    DepotOverviewComponent,
    DepotStocksComponent
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
    AlarmCreateDialogComponent
  ]
})
export class AppModule {
}
