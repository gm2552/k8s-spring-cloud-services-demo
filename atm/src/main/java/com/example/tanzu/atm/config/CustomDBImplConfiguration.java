package com.example.tanzu.atm.config;

import org.h2gis.functions.factory.H2GISFunctions;
import org.h2gis.functions.spatial.metadata.FindGeometryMetadata;
import org.h2gis.functions.spatial.properties.ST_Distance;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.r2dbc.core.DatabaseClient;

import com.example.tanzu.atm.repository.impl.LocSearchATMRepositoryCustom;
import com.example.tanzu.atm.repository.impl.h2.LocSearchATMRepositoryCustomH2;
import com.example.tanzu.atm.repository.impl.mariadb.LocSearchATMRepositoryCustomMariaDB;
import com.example.tanzu.atm.repository.impl.postgresql.LocSearchATMRepositoryCustomPostgresqlDB;

@Configuration
public class CustomDBImplConfiguration 
{
	
	@Profile("mysql")
	@Primary
	@Bean
	public LocSearchATMRepositoryCustom mariaDBCustomRepoImpl(DatabaseClient client)
	{
		return new LocSearchATMRepositoryCustomMariaDB(client);
	}	
	
	@Profile("postgres")
	@Primary
	@Bean
	public LocSearchATMRepositoryCustom postgresqlDBCustomRepoImpl(DatabaseClient client)
	{
		return new LocSearchATMRepositoryCustomPostgresqlDB(client);
	}	
	
	@ConditionalOnMissingBean
	@Bean
	@RegisterReflectionForBinding({H2GISFunctions.class, FindGeometryMetadata.class, ST_Distance.class})
	public LocSearchATMRepositoryCustom h2CustomRepoImpl(DatabaseClient client)
	{
		return new LocSearchATMRepositoryCustomH2(client);
	}
	

}
