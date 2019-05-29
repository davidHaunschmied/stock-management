import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DepotOverviewComponent} from "./depots/depot-overview/depot-overview.component";
import {DepotStocksComponent} from "./depots/depot-stocks/depot-stocks.component";
import {StockListComponent} from "./stock/stock-list/stock-list.component";
import {StockDetailsComponent} from "./stock/stock-details/stock-details.component";
import {SettingsComponent} from "./settings/settings.component";

const routes: Routes = [
  {path: 'overview', component: DepotOverviewComponent},
  {path: 'properties', component: DepotStocksComponent},
  {path: 'stocks', component: StockListComponent},
  {path: 'settings', component: SettingsComponent},
  {path: 'stocks/stock-details/:id', component: StockDetailsComponent},
//  {path: 'transactions', component: },
  {path: '', redirectTo: 'overview', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
