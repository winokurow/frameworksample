# Clear Framework

Clear Test Testingframework kann Web, Desktop und Mobile Anwendungen testen.

## Install

1) Pom Datei anpassen:
a) folgende Dependencies importieren:
    
    * Clear Framework Hauptmodule
    
    	<dependency>
    
		<groupId>de.clearit</groupId>
			
		<artifactId>clearTest</artifactId>
			
		<version>0.0.1-SNAPSHOT</version>
			
	</dependency>
    
    * Clear Framework Module für Web Anwendungen Test
    
	<dependency>
		
		<groupId>de.clearit</groupId>
			
		<artifactId>clearWebTest</artifactId>
			
		<version>0.0.1-SNAPSHOT</version>
			
	</dependency>
    
    * Clear Framework Module für Desktop Anwendungen Test
    
	<dependency> 
		
		<groupId>de.clearit</groupId>
			
		<artifactId>clearWinTest</artifactId>
			
		<version>0.0.1-SNAPSHOT</version>
			
	</dependency>
    
    * Clear Framework Module für Mobile Anwendungen Test
    
	<dependency>
		
		<groupId>org.testng</groupId>
			
		<artifactId>testng</artifactId>
			
		<version>6.9.10</version>
			
	</dependency>
