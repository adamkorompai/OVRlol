import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { PlayerSearch } from '../player-search/player-search';

@Component({
  selector: 'app-top-nav',
  standalone: true,
  imports: [RouterLink, PlayerSearch],
  templateUrl: './top-nav.html',
  styleUrl: './top-nav.css',
})
export class TopNav {
  private router = inject(Router);

  isHomeRoute(): boolean {
    return this.router.url === '/';
  }
}
