package WebSocket;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.fridgetracker.user.User;
import org.springframework.fridgetracker.user.UserRepository;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {
	// Gets users for sockets
	@Autowired
	private UserRepository userRepository;
	
	// Store all socket session and their corresponding username.
    private static Map<Session, User> sessionUserMap = new HashMap<>();
    private static Map<User, Session> userSessionMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    
    @Autowired
    UserRepository userRepo;
    
    @OnOpen
    public void onOpen(
    	      Session session, 
    	      @PathParam("username") String username) throws IOException 
    {
    	
        logger.info("Entered into Open");
        
        User user = userRepo.findByUsername(username).get();

        sessionUsernameMap.put(session, username);
        sessionUserMap.put(session, user);
        userSessionMap.put(user, session);
        usernameSessionMap.put(username, session);
        
        String message="User:" + username + " has Joined the Chat";
        	fridgeBroadcast(message, user.getFridge().getId());
		
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	User user = sessionUserMap.get(session);
    	
    	if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
    	{
    		String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
    		sendMessageToPArticularUser(destUsername, "[DM] " + user.getUsername() + ": " + message);
    		sendMessageToPArticularUser(user.getUsername(), "[DM] " + user.getUsername() + ": " + message);
    	}
    	else // Message to whole chat
    	{
	    	fridgeBroadcast(user.getUsername() + ": " + message, user.getFridge().getId());
    	}
    }
 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");

    	String username = sessionUsernameMap.get(session);
    	User user = sessionUserMap.get(session);
    	sessionUsernameMap.remove(session);
    	sessionUserMap.remove(session);
    	usernameSessionMap.remove(username);
    	userSessionMap.remove(user);
        
    	String message= username + " disconnected";
        fridgeBroadcast(message, user.getFridge().getId());
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
    	sessionUserMap.forEach((session, user) -> {
    		synchronized (session) {
    			if(fridgeId==user.getFridge().getId()) {
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
