package com.example.fridge_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * Using web sockets, this is a live chat between fridge users
 */
public class ChatActivity extends AppCompatActivity {
    //Button  b1;
    Button b2;
    //EditText e1;
    EditText e2;
    TextView t1;


    private WebSocketClient cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //b1 = (Button) findViewById(R.id.bt1);
        b2 = (Button) findViewById(R.id.bt2);
        //e1 = (EditText) findViewById(R.id.et1);
        e2 = (EditText) findViewById(R.id.et2);
        t1 = (TextView) findViewById(R.id.tx1);

                Draft[] drafts = {new Draft_6455()};

                /**
                 * If running this on an android device, make sure it is on the same network as your
                 * computer, and change the ip address to that of your computer.
                 * If running on the emulator, you can use localhost.
                 */
                String w = "ws://cs309-af-1.misc.iastate.edu:8080/websocket/" + ((GlobalVariables) getApplication()).getFridgeID() + "/" + ((GlobalVariables) getApplication()).getUsername();

                try {
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {

                        /**
                         * When a message is received, show on screen
                         *
                         * @param message
                         */
                        @Override
                        public void onMessage(String message) {
                            Log.d("", "run() returned: " + message);
                            t1.append("\n " + message);
                        }

                        /**
                         *  Opening a web socket
                         *
                         * @param handshake
                         */
                        @Override
                        public void onOpen(ServerHandshake handshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }

                        /**
                         * Close the web socket, show on log
                         *
                         * @param code
                         * @param reason
                         * @param remote
                         */
                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "onClose() returned: " + reason);
                        }

                        /**
                         * Show the error on the log
                         *
                         * @param e
                         */
                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }
                cc.connect();


        b2.setOnClickListener(new View.OnClickListener() {

            /**
             * Takes the value typed in by user and sends it across the web socket.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                try {
                    cc.send(String.valueOf(e2.getText()));
                }
                catch (Exception e)
                {
                    Log.d("ExceptionSendMessage:", "there was an error");
                    t1.setText("We're sorry, your message is not able to be sent right now. Please try again later.");
                }
            }
        });

    }
}
