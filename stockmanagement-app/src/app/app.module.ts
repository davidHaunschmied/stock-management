import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MaterialModule} from './material/material.module';
import {CoreModule} from './core/core.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {DepotCreateDialogComponent} from './depots/depot-create-dialog.component';

@NgModule({
    declarations: [
        AppComponent,
        DepotCreateDialogComponent
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
