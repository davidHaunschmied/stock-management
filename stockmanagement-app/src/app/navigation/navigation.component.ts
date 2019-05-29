import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {
  private navLinks: any[];
  private activeLinkIndex = -1;

  constructor(private router: Router) {
    this.navLinks = [
      {
        label: 'Depotübersicht',
        link: './overview',
        index: 0
      },
      {
        label: 'Aktienbesitze',
        link: './properties',
        index: 1
      },
      {
        label: 'Alle Aktien',
        link: './stocks',
        index: 2
      },
      {
        label: 'Einstellungen',
        link: './settings',
        index: 3
      }
    ];
  }

  ngOnInit() {
    this.router.events.subscribe((res) => {
      this.activeLinkIndex = this.navLinks.indexOf(this.navLinks.find(tab => tab.link === '.' + this.router.url));
    });
  }

}
