package com.nbn.ms.IMS.tools;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nbn.ms.IMS.controller.IncidentController;


@Component
public class NRLogService {
    private String jsonString;
    private File file;
	private static final Logger logger = Logger.getLogger(IncidentController.class.getName());

    public NRLogService() {
	  this.jsonString  = "";
	  this.file = new File("logs/log4j1-app.log");
    }


    public void log(Priority priority, Object message) {
    	logger.log(priority, message);
    	sendLogToNewRelic();
    }
    
    public void info(Object message) {
    	logger.info(message);
    	sendLogToNewRelic();
    }

    public void warn(Object message) {
    	logger.warn(message);
    	sendLogToNewRelic();
    }   
 
    public void error(Object message) {
    	logger.error(message);
    	sendLogToNewRelic();
    }   
    
      
    public void sendLogToNewRelic() {
		
		jsonString = readFromLast(file);
        jsonString.replace("timestamp", "mytimestamp");
		
		System.out.println("Sending Log to New Relic...");
		System.out.println("##### " + jsonString + " #####");

		RestTemplate restTemplate = new RestTemplate();
	        
	    String url = "https://log-api.newrelic.com/log/v1"; 
	    System.out.println(responserEntityValue(jsonString,restTemplate,url,HttpMethod.POST,String.class));

	}

	public ResponseEntity<String> responserEntityValue(final String body, final RestTemplate restTemplate,
            final String uRL, final HttpMethod requestMethod, final Class<String> stringClass) {

        HttpHeaders headers = new HttpHeaders();

        headers.add("X-License-Key", "d857ee3e095018151886e849035e4ac1f122NRAL");

        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        return restTemplate.exchange(uRL, requestMethod, request, stringClass);

    }

    public static String readFromLast(File file){  
	    RandomAccessFile fileHandler = null;
	    try {
	        fileHandler = new RandomAccessFile( file, "r" );
	        long fileLength = fileHandler.length() - 1;
	        StringBuilder sb = new StringBuilder();

	        for(long filePointer = fileLength; filePointer != -1; filePointer--){
	            fileHandler.seek( filePointer );
	            int readByte = fileHandler.readByte();

	            if( readByte == 0xA ) {
	                if( filePointer == fileLength ) {
	                    continue;
	                }
	                break;

	            } else if( readByte == 0xD ) {
	                if( filePointer == fileLength - 1 ) {
	                    continue;
	                }
	                break;
	            }

	            sb.append( ( char ) readByte );
	        }

	        String lastLine = sb.reverse().toString();
	        return lastLine;
	    } catch( java.io.FileNotFoundException e ) {
	        e.printStackTrace();
	        return null;
	    } catch( java.io.IOException e ) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        if (fileHandler != null )
	            try {
	                fileHandler.close();
	            } catch (IOException e) {
	                /* ignore */
	            }
	    }
	}    

	
}
