package com.fonctionnels.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthorizationFilter extends OncePerRequestFilter
{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		if (request.getServletPath().equals("/ebms_api/refreshToken") || request.getServletPath().equals("/login"))
		{
			System.out.println("*********refreshToken**********");
			try
			{
				filterChain.doFilter(request, response);/**
																		 * permet de dire a ce filtre de continuer son chemin si le path
																		 * correspond a '/refreshtoken'
																		 */
			}
			catch (Exception e)
			{
				System.out.println("********exception--null******");
				Map<String, String> errors = new HashMap<String, String>();

				errors.put("msg", "nom d'utilisateur ou mot de passe incorrect");
				errors.put("success", "false");

				response.setContentType("application/json");
				response.setStatus(401);
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}
		}
//		else if ()
//		{
//			
//		}
		else
		{
			System.out.println("************JwtAuthorizationFilter********************" + request.getHeader("Authorization"));
			
			String authorizationToken = request.getHeader("Authorization");

			if (authorizationToken != null && authorizationToken.startsWith("Bearer "))
			{
				System.out.println("************JwtAuthorizationFilter-if********************");
				try
				{
					String jwt = authorizationToken.substring(7);
					Algorithm algorithm = Algorithm.HMAC256("203999");

					JWTVerifier jwtVerifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

					Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

					for (String role : roles)
					{
						grantedAuthorities.add(new SimpleGrantedAuthority(role));
					}

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, grantedAuthorities);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					filterChain.doFilter(request, response);
				}
				catch (Exception e)
				{
					System.out.println("*****authorization----exception***********");
//					response.setHeader("error", e.getMessage());
//					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					
					Map<String, String> errors = new HashMap<String, String>();
					
					if (e.getMessage().contains("invalid"))
					{
						errors.put("success", "false,");
						errors.put("msg", "le token n'est pas valide");

						response.setContentType("application/json"); 
						response.setStatus(403);
						new ObjectMapper().writeValue(response.getOutputStream(), errors);
					}
					else
					{
						errors.put("success", "false,");
						errors.put("msg", "le token a expiré");

						response.setContentType("application/json"); 
						response.setStatus(403);
						new ObjectMapper().writeValue(response.getOutputStream(), errors);
					}
					
				}

			}
			else
			{
				Map<String, String> errors = new HashMap<String, String>();

				errors.put("success", "false");
				errors.put("msg", "la cle est manquante");

				response.setContentType("application/json"); 
				response.setStatus(403);
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
				/** filterChain.doFilter(request, response); permet de dire a ce filtre de continuer son chemin */
			}
		}
	}

}
