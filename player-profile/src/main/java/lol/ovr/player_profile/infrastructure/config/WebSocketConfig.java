package lol.ovr.player_profile.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // C'est l'URL sur laquelle notre Angular va venir se brancher
        registry.addEndpoint("/ws-ovr")
                .setAllowedOriginPatterns("*"); // Évite les erreurs CORS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Le préfixe pour les messages qu'on envoie au Frontend
        registry.enableSimpleBroker("/topic");
        // Le préfixe si le Frontend voulait nous envoyer des messages (pas utilisé pour l'instant)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
