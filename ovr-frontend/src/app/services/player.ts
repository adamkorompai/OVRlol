import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Client, Message } from '@stomp/stompjs';

export interface PlayerCard {
  puuid: string;
  matchId: string;
  overallRating: number;
  mechanicsScore: number;
  farmingScore: number;
  visionScore: number;
  gameCreation: number;

  championName: string;
  kills: number;
  deaths: number;
  assists: number;
  creepScore: number;
  win: boolean;
  gameDuration: number;
  itemIds: number[];
  primaryRuneIds: number[];
  secondaryRuneIds: number[];
  enemyChampions: string[];
}

@Injectable({
  providedIn: 'root'
})
export class Player {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8082/api/v1/players';
  
  getPuuid(gameName: string, tagLine: string): Observable<string> {
    const url = `http://localhost:8080/api/v1/accounts/by-riot-id/${gameName}/${tagLine}`;
    return this.http.get(url, { responseType: 'text' });
  }

  private stompClient!: Client;
  public newCard$ = new Subject<PlayerCard>();

  getHistory(puuid: string): Observable<PlayerCard[]> {
    return this.http.get<PlayerCard[]>(`${this.apiUrl}/${puuid}/cards`);
  }

  connectToRealTime(puuid: string) {
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8082/ws-ovr',
      onConnect: () => {
        console.log('⚡ Connecté au serveur WebSocket avec succès !');
        
        this.stompClient.subscribe(`/topic/cards/${puuid}`, (message: Message) => {
          const newCard: PlayerCard = JSON.parse(message.body);
          console.log('🎁 NOUVELLE CARTE REÇUE EN DIRECT !', newCard);
          
          this.newCard$.next(newCard); 
        });
      },
      onWebSocketError: (error) => console.error('❌ Erreur WebSocket', error),
      onStompError: (frame) => console.error('❌ Erreur STOMP', frame)
    });
    
    this.stompClient.activate();
  }

  triggerRecentIngestion(puuid: string, count: number = 20): Observable<void> {
    return this.http.post<void>(
      'http://localhost:8080/api/v1/ingest/recent',
      { puuid, count }
    );
  }
}