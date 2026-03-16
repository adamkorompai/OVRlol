import { Component, Input } from '@angular/core';
import { PlayerCard, TeamParticipant } from '../../../services/player';

@Component({
  selector: 'app-match-recap-row',
  standalone: true,
  imports: [],
  templateUrl: './match-recap-row.html',
  styleUrl: './match-recap-row.css',
})
export class MatchRecapRow {
  @Input({ required: true }) card!: PlayerCard;

  private readonly ddragonVersion = '15.6.1';

  championImg(championName: string): string {
    return `https://ddragon.leagueoflegends.com/cdn/${this.ddragonVersion}/img/champion/${championName}.png`;
  }

  itemImg(itemId: number): string {
    return `https://ddragon.leagueoflegends.com/cdn/${this.ddragonVersion}/img/item/${itemId}.png`;
  }

  spellImg(spellId: number): string {
    return `https://ddragon.leagueoflegends.com/cdn/${this.ddragonVersion}/img/spell/${this.spellKey(spellId)}.png`;
  }

  formatDuration(totalSeconds: number): string {
    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;
    return `${minutes}:${String(seconds).padStart(2, '0')}`;
  }

  kdaText(): string {
    const ratio = (this.card.kills + this.card.assists) / Math.max(1, this.card.deaths);
    return ratio.toFixed(2);
  }

  queueLabel(queueId: number): string {
    const queues: Record<number, string> = {
      420: 'Solo/Duo',
      440: 'Flex',
      450: 'ARAM',
      400: 'Draft',
      430: 'Blind',
      490: 'Quickplay',
    };

    return queues[queueId] ?? `Queue ${queueId}`;
  }

  itemSlots(): number[] {
    const fromSlots = [
      this.card.item0,
      this.card.item1,
      this.card.item2,
      this.card.item3,
      this.card.item4,
      this.card.item5,
    ];

    if (fromSlots.some(value => value !== undefined)) {
      return fromSlots.map(value => value ?? 0);
    }

    const fallback = [...(this.card.itemIds ?? [])];
    while (fallback.length < 6) fallback.push(0);
    return fallback.slice(0, 6);
  }

  trinketSlot(): number {
    if (this.card.item6 !== undefined) return this.card.item6 ?? 0;
    return this.card.itemIds?.[6] ?? 0;
  }

    private readonly POSITION_ORDER = ['TOP', 'JUNGLE', 'MIDDLE', 'BOTTOM', 'UTILITY'];

    teamRows(): { position: string; team100: TeamParticipant | null; team200: TeamParticipant | null }[] {
      const participants = this.card.participants ?? [];
      const team100 = participants.filter(p => p.teamId === 100);
      const team200 = participants.filter(p => p.teamId === 200);

      return this.POSITION_ORDER.map(position => ({
        position,
        team100: team100.find(p => p.teamPosition === position) ?? null,
        team200: team200.find(p => p.teamPosition === position) ?? null,
      }));
    }

    positionLabel(position: string): string {
      const labels: Record<string, string> = {
        TOP: 'TOP',
        JUNGLE: 'JGL',
        MIDDLE: 'MID',
        BOTTOM: 'ADC',
        UTILITY: 'SUP',
      };
      return labels[position] ?? position;
    }

  private spellKey(spellId: number): string {
    const map: Record<number, string> = {
      1: 'SummonerBoost',
      3: 'SummonerExhaust',
      4: 'SummonerFlash',
      6: 'SummonerHaste',
      7: 'SummonerHeal',
      11: 'SummonerSmite',
      12: 'SummonerTeleport',
      13: 'SummonerMana',
      14: 'SummonerDot',
      21: 'SummonerBarrier',
      32: 'SummonerSnowball',
    };

    return map[spellId] ?? 'SummonerFlash';
  }
}
