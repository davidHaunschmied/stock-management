import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DepotOverviewComponent} from "./depots/depot-overview/depot-overview.component";
import {DepotStocksComponent} from "./depots/depot-stocks/depot-stocks.component";

const routes: Routes = [
  {path: 'overview', component: DepotOverviewComponent},
  {path: 'properties', component: DepotStocksComponent},
//  {path: 'stocks', component: },
//  {path: 'transactions', component: },
  {path: '', redirectTo: 'overview', pathMatch: 'full'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
