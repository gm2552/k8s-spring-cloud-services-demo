package com.example.tanzu.atmloc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.example.tanzu.atmloc.exchange.ATMClient;
import com.example.tanzu.atmloc.exchange.BranchClient;
import com.example.tanzu.atmloc.exchange.LocTranslationClient;

@Configuration
public class DeclarativeClientConfig 
{
	@Value("${services.location-transation.identifier}")
	protected String locServiceIdentifier;

	@Value("${services.atm.identifier}")
	protected String atmServiceIdentifier;
	
	@Value("${services.branch.identifier}")
	protected String branchServiceIdentifier;
	
	/*
	 * Only use with the Eureka profile. 
	 * 
	 */
	@Profile("eureka")
	@LoadBalanced
	@Bean
	public WebClient.Builder loadBalancedWebClientBuilder() 
	{
	    return WebClient.builder();
	}
	
	@Profile("!eureka")
	@Bean
	public WebClient.Builder webClientBuilder() 
	{
	    return WebClient.builder();
	}
	
	@Bean
	public LocTranslationClient getLocTranslationClient(WebClient.Builder builder)
	{
		final var client = builder.baseUrl(locServiceIdentifier).build();
		final var factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();

		return factory.createClient(LocTranslationClient.class);
	}
	
	@Bean
	public ATMClient getATMClient(WebClient.Builder builder)
	{
		final var client = builder.baseUrl(atmServiceIdentifier).build();
		final var factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();

		return factory.createClient(ATMClient.class);
	}
	
	@Bean
	public BranchClient getBranchClient(WebClient.Builder builder)
	{
		final var client = builder.baseUrl(branchServiceIdentifier).build();
		final var factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();

		return factory.createClient(BranchClient.class);
	}
}
