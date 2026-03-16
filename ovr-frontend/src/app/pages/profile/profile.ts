import { Component, DestroyRef, computed, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player, PlayerCard } from '../../services/player';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { interval, switchMap } from 'rxjs';
import { MatchRecapRow } from './match-recap-row/match-recap-row';

@Component({
  selector: 'app-profile',
  imports: [MatchRecapRow],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  private route = inject(ActivatedRoute);
  private playerService = inject(Player);
  private destroyRef = inject(DestroyRef);

  currentPuuid = signal('');
  history = signal<PlayerCard[]>([]);
  isLoading = signal(true);
  error = signal('');

  avgOverall   = computed(() => this.avg(this.history().map(c => c.overallRating)));
  avgMechanics = computed(() => this.avg(this.history().map(c => c.mechanicsScore)));
  avgFarming   = computed(() => this.avg(this.history().map(c => c.farmingScore)));
  avgVision    = computed(() => this.avg(this.history().map(c => c.visionScore)));

  private avg(values: number[]): number {
    if (values.length === 0) return 0;
    return Math.round(values.reduce((a, b) => a + b, 0) / values.length);
  }

  getRatingBg(rating: number): string {
    if (rating >= 90) return 'from-yellow-400 via-amber-500 to-yellow-600';
    if (rating >= 80) return 'from-yellow-500 via-yellow-600 to-amber-700';
    if (rating >= 70) return 'from-gray-300 via-gray-400 to-gray-500';
    return 'from-amber-700 via-amber-800 to-amber-900';
  }

  getRatingColor(rating: number): string {
    if (rating >= 90) return 'text-yellow-400';
    if (rating >= 80) return 'text-yellow-500';
    if (rating >= 70) return 'text-gray-300';
    return 'text-amber-700';
  }

  getMatchBorder(rating: number): string {
    if (rating >= 90) return 'border-yellow-400';
    if (rating >= 80) return 'border-yellow-500';
    if (rating >= 70) return 'border-gray-400';
    return 'border-amber-700';
  }

  winRate = computed(() => {
    const h = this.history();
    if (!h.length) return 0;
    return Math.round((h.filter(c => c.win).length / h.length) * 100);
  });

  ngOnInit(): void {
    this.route.paramMap
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(params => {
        const puuidFromUrl = params.get('puuid');

        if (puuidFromUrl) {
          this.currentPuuid.set(puuidFromUrl);
          
          this.playerService.triggerRecentIngestion(puuidFromUrl)
          .pipe(takeUntilDestroyed(this.destroyRef))
          .subscribe({
            next: () => console.log('🚀 Ingestion lancée !'),
            error: (err) => console.error('❌ Erreur ingestion', err)
          });

          interval(60_000)
            .pipe(
              takeUntilDestroyed(this.destroyRef),
              switchMap(() => this.playerService.triggerRecentIngestion(puuidFromUrl))
            )
            .subscribe();

          this.playerService.getHistory(puuidFromUrl)
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe({
              next: (data) => {
                this.history.update(current => {
                  const existingIds = new Set(current.map(c => c.matchId));
                  const newFromDb = data.filter(c => !existingIds.has(c.matchId));
                  return [...current, ...newFromDb]
                    .sort((a, b) => b.gameCreation - a.gameCreation);
                });
                this.isLoading.set(false);
              },
              error: (err) => {
                console.error('❌ Erreur Historique', err);
                this.error.set('Impossible de charger le profil.');
                this.isLoading.set(false);
              }
            });

          this.playerService.connectToRealTime(puuidFromUrl);

          this.playerService.newCard$
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(card => {
              this.history.update(current => {
                if (current.some(c => c.matchId === card.matchId)) return current;
                return [card, ...current].sort((a, b) => b.gameCreation - a.gameCreation);
              });
            });
        }
      });
  }
}