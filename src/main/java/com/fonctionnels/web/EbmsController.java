package com.fonctionnels.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fonctionnels.entities.AppRole;
import com.fonctionnels.entities.AppUser;
import com.fonctionnels.entities.Invoice;
import com.fonctionnels.service.IEbmsService;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/ebms_api")
public class EbmsController
{
	@Autowired
	IEbmsService ibEbmsService;
	
	@PostMapping(value = "/addInvoice")
	@PostAuthorize("hasAuthority('user')")
	public ResponseEntity<Map<String, String>> addInvoice(@Valid @RequestBody Invoice invoice)
	{
		Invoice invoiceSave = ibEbmsService.addInvoice(invoice);
		Map<String, String> httpMsg = new HashMap<String, String>();
		
		httpMsg.put("msg", "la facture a ete ajoute avec succes");
		httpMsg.put("success", "true");
		return new ResponseEntity<>(httpMsg, HttpStatus.OK);
	}
	
	@PostMapping("/getInvoice")
	public ResponseEntity<?> getInvoice(@RequestBody Signature signature)
	{
		if (signature.getInvoice_signature().equals("") || signature.getInvoice_signature().equals(null))
		{
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("success", "false");
			errors.put("msg", "veuillez la signature de la facture");
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		else 
		{
			Invoice invoice = ibEbmsService.getInvoice(signature.getInvoice_signature());
			if (invoice == null)
			{
				Map<String, String> errors = new HashMap<String, String>();
				errors.put("success", "false");
				errors.put("msg", "signature de la facture inconnu");
				return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
			}
			else
			{
				return new ResponseEntity<>(invoice, HttpStatus.OK);
			}
		}
		
	}
	
	@PostMapping("/checkTIN")
	public ResponseEntity<?> getInvoice(@RequestBody Nif nif)
	{
		if (nif.getTp_TIN().equals("") || nif.getTp_TIN().equals(null))
		{
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("success", "false");
			errors.put("msg", "veuillez fournir le NIF du contribuable");
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		else 
		{
			Invoice invoice = ibEbmsService.getTin(nif.getTp_TIN());
			if (invoice == null)
			{
				Map<String, String> errors = new HashMap<String, String>();
				errors.put("success", "false");
				errors.put("msg", "NIF du contribuable inconnu");
				return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
			}
			else
			{
				Map<String, String> response = new HashMap<String, String>();
				response.put("success", "true");
				response.put("msg", "operation reussi");
				response.put("taxpayer", invoice.getTp_name());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		
	}
	
	@GetMapping(value = "/getInvoices")
	public ResponseEntity<List<Invoice>> getInvoices()
	{
		List<Invoice> invoices = ibEbmsService.getInvoices();
		return new ResponseEntity<>(invoices, HttpStatus.OK);
	}
	
	/****appUser and appRole resource****/
	
	@PostMapping(value = "/users")
	public ResponseEntity<AppUser> addUser(@RequestBody AppUser appUser)
	{
		AppUser appUserSave = ibEbmsService.addAppUser(appUser);
		return new ResponseEntity<AppUser>(appUserSave, HttpStatus.OK);
	}
	
	@PostMapping(value = "/roles")
	public ResponseEntity<AppRole> addUser(@Valid @RequestBody AppRole appRole)
	{
		AppRole appRoleSave = ibEbmsService.addAppRole(appRole);
		return new ResponseEntity<AppRole>(appRoleSave, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addRoleToUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser)
	{
		ibEbmsService.AddRoleToUser(roleToUser.getUsername(), roleToUser.getRolename());
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(value = "/users/{id}")
	public ResponseEntity<?> addRoleToUser(@PathVariable("id") Long id, @RequestBody List<AppRole> listAppRoles)
	{
		 
		ibEbmsService.addRolesToUser(id, listAppRoles);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping(value = "/users")
	public ResponseEntity<List<AppUser>> getAllUsers()
	{
		List<AppUser> listUsers = ibEbmsService.listUsers();
		return new ResponseEntity(listUsers, HttpStatus.OK);
	}
	
	@GetMapping(path = "/refreshToken")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		System.out.println("*********debut refreshToken*********** ");
		String authorizationToken = request.getHeader("Authorization");
		if (authorizationToken != null && authorizationToken.startsWith("Bearer "))
		{
			try
			{
				String jwt = authorizationToken.substring(7);
				Algorithm algorithm = Algorithm.HMAC256("203999");

				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

				String username = decodedJWT.getSubject();
				AppUser appUser = ibEbmsService.loadUserByUsername(username);/**c'est ici qu;on effectue la recherche cad
				 																							voir si le refresh token n'est pas dans la blacklist*/
				
				String jwtAccessToken = JWT.create().withSubject(appUser.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", appUser.getAppRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
						.sign(algorithm);
				
				/**envoie des 2 tokens*/
				Map<String, String> idToken = new HashMap<String, String>();
				
				idToken.put("access-token", jwtAccessToken);
				idToken.put("refresh-token", jwt);
				
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), idToken);
			}
			catch (Exception e)
			{
				response.setHeader("error", e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		else
		{
			throw new RuntimeException("refresh token required");
		}
	}
	
	
}

@Data
@NoArgsConstructor
class RoleToUser
{
	private String username;
	private String rolename;
}

@Data
@NoArgsConstructor
class Signature
{
	private String invoice_signature;
}

@Data
@NoArgsConstructor
class Nif
{
	private String tp_TIN;
}