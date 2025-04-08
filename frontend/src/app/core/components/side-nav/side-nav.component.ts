import { Component } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatListItem,
  MatListItemIcon,
  MatListItemTitle,
  MatNavList,
} from '@angular/material/list';
import {
  MatSidenav,
  MatSidenavContainer,
  MatSidenavContent,
} from '@angular/material/sidenav';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-side-nav',
  imports: [
    MatSidenav,
    MatSidenavContainer,
    MatSidenavContent,
    MatNavList,
    MatListItem,
    MatIcon,
    MatListItemTitle,
    MatListItemIcon,
    MatButton,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: 'side-nav.component.html',
  styleUrl: 'side-nav.component.css',
})
export class SideNavComponent {
  save() {
    alert('Saved');
  }
}
