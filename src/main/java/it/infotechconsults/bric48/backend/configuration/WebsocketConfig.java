package it.infotechconsults.bric48.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import it.infotechconsults.bric48.backend.security.JwtAuthHandshake;
import it.infotechconsults.bric48.backend.security.JwtService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
// @EnableWebSocketSecurity
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").addInterceptors(new JwtAuthHandshake(jwtService)).setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    // @Bean
    // AuthorizationManager<Message<?>> messageAuthorizationManager(
    //         MessageMatcherDelegatingAuthorizationManager.Builder messages) {
    //     return messages.anyMessage().authenticated().build();
    // }

}
