package org.springframework.fridgetracker.websockets;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fridgetracker.system.NotFoundException;
import org.springframework.fridgetracker.user.User;
import org.springframework.fridgetracker.user.UserRepository;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{fridgeId}/{username}")
@Component
public class WebSocketServer {
	
	// Store all socket session and their corresponding username.
    private static Map<String, Integer> usernameFridgeMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    
    @OnOpen
    public void onOpen(
    	      Session session, 
    	      @PathParam("username") String username,
    	      @PathParam("fridgeId") Integer id) throws IOException 
    {
    	
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameFridgeMap.put(username, id);
        usernameSessionMap.put(username, session);
        
        String message="User: " + username + " has Joined the Chat";
        	fridgeBroadcast(message, id);
		
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	String user = sessionUsernameMap.get(session);
    	
    	if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
    	{
    		String destUsername = message.split(" ")[0].substring(1);
    		sendMessageToPArticularUser(destUsername, "[DM] " + user + ": " + message);
    		sendMessageToPArticularUser(user, "[DM] " + user + ": " + message);
    	}
    	else // Message to whole chat
    	{
	    	fridgeBroadcast(user + ": " + message, usernameFridgeMap.get(user));
    	}
    }
 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");

    	String username = sessionUsernameMap.get(session);
    	Integer id = usernameFridgeMap.get(username);
    	sessionUsernameMap.remove(session);
    	usernameSessionMap.remove(username);
    	
        
    	String message= username + " disconnected";
        fridgeBroadcast(message, id);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
    	logger.info("Entered into Error");
    }
    
	private void sendMessageToPArticularUser(String username, String message) 
    {	
    	try {
    		usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private static void broadcast(String message) 
    	      throws IOException 
    {	  
    	sessionUsernameMap.forEach((session, username) -> {
    		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

    private static void fridgeBroadcast(String message, Integer fridgeId) 
    	      throws IOException 
    {	  
    	sessionUsernameMap.forEach((session, user) -> {
    		synchronized (session) {
    			if(fridgeId==usernameFridgeMap.get(user)) {
		            try {
		                session.getBasicRemote().sendText(message);
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
    			}
	        }
	    });
	}
}
