import { Component } from '@angular/core';
import { PlayerSearch } from '../../components/player-search/player-search';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [PlayerSearch],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {}