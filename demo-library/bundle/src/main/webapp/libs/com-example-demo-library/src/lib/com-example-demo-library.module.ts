import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RxLocalizationService } from '@helix/platform/shared/api';
import * as defaultApplicationStrings from './i18n/localized-strings.json';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class ComExampleDemoLibraryModule {constructor(private rxLocalizationService: RxLocalizationService) {
 this.rxLocalizationService.setDefaultApplicationStrings(defaultApplicationStrings["default"]); 
} }
