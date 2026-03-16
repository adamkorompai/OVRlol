import { Component, Input, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Player } from '../../services/player';

@Component({
  selector: 'app-player-search',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './player-search.html',
  styleUrl: './player-search.css',
})
export class PlayerSearch {
  private playerService = inject(Player);
  private router = inject(Router);

  @Input() compact = false;
  @Input() showLabel = true;
  @Input() label = 'Rechercher un joueur (Riot ID)';
  @Input() gamePlaceholder = 'Ex: Faker';
  @Input() tagPlaceholder = 'KR1';
  @Input() buttonLabel = 'Analyser le profil';
  @Input() loadingLabel = 'Recherche dans la base Riot...';

  gameName = '';
  tagLine = '';
  isLoading = signal(false);
  errorMessage = signal('');

  searchPlayer(): void {
    if (!this.gameName || !this.tagLine || this.isLoading()) return;

    this.isLoading.set(true);
    this.errorMessage.set('');

    this.playerService.getPuuid(this.gameName.trim(), this.tagLine.trim()).subscribe({
      next: (puuid: string) => {
        this.isLoading.set(false);
        this.router.navigate(['/profile', puuid]);
      },
      error: () => {
        this.errorMessage.set('Joueur introuvable. Verifie le pseudo et le #tag.');
        this.isLoading.set(false);
      },
    });
  }
}
