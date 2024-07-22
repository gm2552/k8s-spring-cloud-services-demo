package com.example.tanzu.atm;


import java.sql.Connection;

import org.h2gis.functions.factory.H2GISFunctions;
import org.h2gis.functions.spatial.metadata.FindGeometryMetadata;
import org.h2gis.functions.spatial.properties.ST_Distance;
import org.locationtech.jts.geom.Geometry;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(AtmApplication.MyRuntimeHints.class)
public class AtmApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(AtmApplication.class, args);
	}


	static class MyRuntimeHints implements RuntimeHintsRegistrar 
	{

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) 
		{
			try {
				var load1 = H2GISFunctions.class.getDeclaredMethod("load", Connection.class);
				var load2 = H2GISFunctions.class.getDeclaredMethod("load", Connection.class, String.class, String.class);
				
				var extactMetadata = FindGeometryMetadata.class.getDeclaredMethod("extractMetadata", Connection.class,
                        String.class, String.class, String.class,
                        String.class, String.class, String.class, String.class);
				
				var distance = ST_Distance.class.getDeclaredMethod("distance", Geometry.class, Geometry.class);
				
				hints.reflection()
				.registerMethod(load1, ExecutableMode.INVOKE)
				.registerMethod(load2, ExecutableMode.INVOKE)
				.registerMethod(extactMetadata, ExecutableMode.INVOKE)
				.registerMethod(distance, ExecutableMode.INVOKE);
			}
			catch (Exception e)
			{
				// no-op
			}
		}
	}	
	
}
