package com.example.tanzu.atmloc;

import java.util.List;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.loadbalancer.config.LoadBalancerCacheAutoConfiguration;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication(exclude = {LoadBalancerCacheAutoConfiguration.class})
@EnableCaching
@ImportRuntimeHints(AtmLocatorSearchApplication.MyRuntimeHints.class)
public class AtmLocatorSearchApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(AtmLocatorSearchApplication.class, args);
	}

	static class MyRuntimeHints implements RuntimeHintsRegistrar 
	{

		/*
		 * At time of writing, reachability metadata is only include up to version Caffeine 3.1.2.  The following need to be added
		 * for Spring Boot 3.2 and Caffeine 3.1.8.
		 */
		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) 
		{
			hints.reflection()
			.registerType(TypeReference.of("com.github.benmanes.caffeine.cache.SSMSA"),
							builder -> builder.withConstructor(
								List.of(TypeReference.of("com.github.benmanes.caffeine.cache.Caffeine"),
									TypeReference.of("com.github.benmanes.caffeine.cache.AsyncCacheLoader"),
									TypeReference.of("boolean")), ExecutableMode.INVOKE))
			.registerType(TypeReference.of("com.github.benmanes.caffeine.cache.PSAMS"),
					builder -> builder.withConstructor(
						List.of(), ExecutableMode.INVOKE))
			.registerType(TypeReference.of("com.github.benmanes.caffeine.cache.PSAMW"),
					builder -> builder.withConstructor(
						List.of(), ExecutableMode.INVOKE));	
		}
	}
}
