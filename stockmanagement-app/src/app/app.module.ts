import {LOCALE_ID, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MaterialModule} from './material/material.module';
import {CoreModule} from './core/core.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {StockListComponent} from './stock/stock-list/stock-list.component';
import {StockDetailsComponent} from './stock/stock-details/stock-details.component';
import {DepotCreateDialogComponent} from './depots/depot-create-dialog.component';
import {DepotSwitchCreateComponent} from './depots/depot-switch-create.component';
import {HomeComponent} from './home/home.component';
import {NavigationComponent} from './navigation/navigation.component';
import {DepotOverviewComponent} from './depots/depot-overview/depot-overview.component';
import {DepotStocksComponent} from './depots/depot-stocks/depot-stocks.component';
import {StockSellComponent} from './stock/stock-sell/stock-sell.component';
import {StockPurchaseComponent} from './stock/stock-purchase/stock-purchase.component';
import {AlarmCreateDialogComponent} from "./alarm/alarm-create-dialog/alarm-create-dialog.component";
import {AlarmListComponent} from "./topbar/alarm/alarm-list.component";
import {HighchartsChartModule} from 'highcharts-angular';
import {SettingsComponent} from './settings/settings.component';
import {TransactionListComponent} from './transaction/transaction-list/transaction-list.component';
import {DepotDeleteDialogComponent} from './depots/depot-delete-dialog.component';
import {CurrencySwitchComponent} from "./topbar/currency/currency-switch.component";
import {registerLocaleData} from "@angular/common";
import localeDe from '@angular/common/locales/de';
import {DepotImportDialogComponent} from './depots/depot-import-dialog/depot-import-dialog.component';

registerLocaleData(localeDe, 'de'); // moves Currency sign to end of the number

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
    DepotStocksComponent,
    StockSellComponent,
    StockPurchaseComponent,
    DepotStocksComponent,
    AlarmListComponent,
    DepotImportDialogComponent,
    DepotDeleteDialogComponent,
    AlarmListComponent,
    TransactionListComponent,
    CurrencySwitchComponent,
    TransactionListComponent,
    SettingsComponent
  ],
  imports: [
    HighchartsChartModule,
    CoreModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MaterialModule
  ],
  providers: [{
    provide: LOCALE_ID,
    useValue: 'de-DE' // 'de-DE' for Germany, 'fr-FR' for France ...
  },
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    DepotCreateDialogComponent,
    DepotImportDialogComponent,
    AlarmCreateDialogComponent,
    StockSellComponent,
    StockPurchaseComponent,
    DepotDeleteDialogComponent
  ]
})
export class AppModule {

}
