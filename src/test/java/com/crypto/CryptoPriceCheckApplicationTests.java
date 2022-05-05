package com.crypto;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CryptoPriceCheckApplicationTests {
	
	@LocalServerPort
    int randomServerPort;
    
    @Test
    public void testGetCrytoPriceRequestScreen() throws URISyntaxException 
    {
        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:"+randomServerPort+"/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE).contains("text/html"));
    }
    
    @Test
    public void testGetCrytoPriceDetails_withoutIp() throws URISyntaxException 
    {
        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:"+randomServerPort+"/getcryptoprice?crypto=Bitcoin&ipaddress=";
        URI uri = new URI(baseUrl);
        
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE).contains("text/html"));
    }
    
    @Test
    public void testGetCrytoPriceDetails_withIp() throws URISyntaxException 
    {
        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:"+randomServerPort+"/getcryptoprice?crypto=Bitcoin&ipaddress=104.166.185.255";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE).contains("text/html"));
    }

}
