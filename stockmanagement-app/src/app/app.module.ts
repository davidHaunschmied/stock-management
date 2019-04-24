import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MaterialModule} from './material/material.module';
import {CoreModule} from './core/core.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {DepotCreateDialogComponent} from './depots/depot-create-dialog.component';
import {DepotSwitchCreateComponent} from './depots/depot-switch-create.component';
import {HomeComponent} from './home/home.component';
import { NavigationComponent } from './navigation/navigation.component';
import { DepotOverviewComponent } from './depots/depot-overview/depot-overview.component';
import { DepotStocksComponent } from './depots/depot-stocks/depot-stocks.component';

@NgModule({
  declarations: [
    AppComponent,
    DepotCreateDialogComponent,
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
  entryComponents: [DepotCreateDialogComponent]
})
export class AppModule {
}
