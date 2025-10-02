package com.srayen.whatsappclone.interceptor;

import java.io.IOException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.srayen.whatsappclone.user.UserSynchronizer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Component // To run this Filter Automatically
@RequiredArgsConstructor
public class UserSynchronizerFilter extends OncePerRequestFilter {

    private final UserSynchronizer userSynchronizer;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                    .getAuthentication();

            userSynchronizer.synchronizeWithIdp((token.getToken()));
        }

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }

}
