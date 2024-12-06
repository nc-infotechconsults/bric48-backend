package it.infotechconsults.bric48.backend.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // Extract STOMP headers
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // Intercept CONNECT frames to authenticate the user
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authHeader = accessor.getNativeHeader("Authorization");
            if (authHeader != null && !authHeader.isEmpty()) {
                String authorizationHeader = authHeader.get(0);
                String username = null;
                String jwt = null;

                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    jwt = authorizationHeader.substring(7);
                    username = jwtService.extractUsername(jwt);
                }

                if (jwt != null && !jwtService.isTokenExpired(jwt)) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
                } else {
                    throw new IllegalArgumentException("Invalid Token");
                }
            } else {
                throw new IllegalArgumentException("Authorization header is missing");
            }
        }

        return message;
    }

}
