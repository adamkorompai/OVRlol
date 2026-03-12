import { Component, inject, signal } from '@angular/core';
import { Player } from '../../services/player';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  private playerService = inject(Player);
  private router = inject(Router);

  gameName = '';
  tagLine = '';
  isLoading = signal(false);
  errorMessage = signal('');

  searchPlayer() {
    if (!this.gameName || !this.tagLine) return;

    this.isLoading.set(true);
    this.errorMessage.set('');

    this.playerService.getPuuid(this.gameName, this.tagLine).subscribe({
      next: (puuid: string) => {
        console.log('✅ PUUID trouvé :', puuid);
        this.isLoading.set(false);
        this.router.navigate(['/profile', puuid]);
      },
      error: (err) => {
        console.error('Erreur API:', err);
        this.errorMessage.set('Joueur introuvable. Vérifie le pseudo et le #tag.');
        this.isLoading.set(false);
      }
    });
  }
}