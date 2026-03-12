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
  // Ce "Subject" est un haut-parleur interne à Angular. 
  // Quand une carte arrive du WebSocket, on crie l'info dans toute l'app !
  public newCard$ = new Subject<PlayerCard>();

  // 1. L'appel classique (Historique)
  getHistory(puuid: string): Observable<PlayerCard[]> {
    return this.http.get<PlayerCard[]>(`${this.apiUrl}/${puuid}/cards`);
  }

  // 2. LA MAGIE : La connexion Temps Réel
  connectToRealTime(puuid: string) {
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8082/ws-ovr', // L'URL de notre Spring Boot
      onConnect: () => {
        console.log('⚡ Connecté au serveur WebSocket avec succès !');
        
        // On s'abonne UNIQUEMENT au canal de ce joueur précis
        this.stompClient.subscribe(`/topic/cards/${puuid}`, (message: Message) => {
          const newCard: PlayerCard = JSON.parse(message.body);
          console.log('🎁 NOUVELLE CARTE REÇUE EN DIRECT !', newCard);
          
          // On transmet la carte à notre composant visuel
          this.newCard$.next(newCard); 
        });
      },
      onWebSocketError: (error) => console.error('❌ Erreur WebSocket', error),
      onStompError: (frame) => console.error('❌ Erreur STOMP', frame)
    });
    
    this.stompClient.activate(); // Allume le tuyau !
  }

  triggerRecentIngestion(puuid: string, count: number = 20): Observable<void> {
    return this.http.post<void>(
      'http://localhost:8080/api/v1/ingest/recent',
      { puuid, count }
    );
  }
}