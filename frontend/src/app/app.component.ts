import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SideNavComponent } from './components/side-nav/side-nav.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, SideNavComponent],
  template: `
    <app-side-nav>
      <router-outlet></router-outlet>
    </app-side-nav>
  `,
  styles: ``,
})
export class AppComponent {
  title = 'financeManager';
}
