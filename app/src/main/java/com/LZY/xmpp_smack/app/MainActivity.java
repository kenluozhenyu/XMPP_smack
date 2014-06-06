package com.LZY.xmpp_smack.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.jivesoftware.smack.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends ActionBarActivity {

    private TextView tvMsgType;
    private Handler handler;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = (Button) findViewById(R.id.btnSend);
        tvMsgType = (TextView) findViewById(R.id.textViewMsg);

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                String strmsg;
                switch (msg.what)
                {
                    case 3: tvMsgType.setText("thread exception");
                             strmsg = (String) msg.obj;
                             tvMsgType.setText(strmsg);
                            break;
                    case 0: tvMsgType.setText("thread started");
                            break;
        			/*case 1: Bitmap bmp = null;
            				Bundle ble = msg.getData();
            				bmp = (Bitmap) ble.get("bmp");
            				tvMsgType.setText("使用Bundle传递数据");

            				ivInternet.setImageBitmap(bmp);
            				break; */
                    case 2: tvMsgType.setText("image download failed");
                            break;
                    case 4: tvMsgType.setText("XMPP failure");
                            strmsg = (String) msg.obj;
                            tvMsgType.setText(strmsg);
                            break;
                }
            }
        };

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvMsgType.setText("prepare to send meg");

        		new Thread()
        		{
        			@Override
        			public void run()
        			{

        				int xmppPORT =5222;
        				//ConnectionConfiguration config = new ConnectionConfiguration("112.124.71.9", xmppPORT);
                        //ConnectionConfiguration config = new ConnectionConfiguration("115.29.232.62", xmppPORT);
        				ConnectionConfiguration config = new ConnectionConfiguration("115.29.232.62", xmppPORT, "lens.com"); //MUST be in this way

                        config.setSASLAuthenticationEnabled(true);
                        config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
                        config.setDebuggerEnabled(true);
                        config.setReconnectionAllowed(true);
                        //config.setTruststorePath("/system/etc/security/cacerts.bks");
                        //config.setTruststorePassword("changeit");
                        //config.setTruststoreType("bks");

                        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            config.setTruststoreType("AndroidCAStore");
                            config.setTruststorePassword(null);
                            config.setTruststorePath(null);
                        //} else {
                        //    config.setTruststoreType("BKS");
                        //    String path = System.getProperty("javax.net.ssl.trustStore");
                        //    if (path == null)
                        //        path = System.getProperty("java.home") + File.separator + "etc"
                        //                + File.separator + "security" + File.separator
                        //                + "cacerts.bks";
                        //    config.setTruststorePath(path);
                        //}


                        XMPPConnection connection = new XMPPConnection(config);
                        //SASLAuthentication.supportSASLMechanism("PLAIN", 0);

        				try
        				{
        					connection.connect();

                            //connection.login("test01@lens.com", "123456");
                            connection.login("test01", "123456"); // don't add the "@lens.com" in the userID !!!

        					//connection.login("test01@lens.com", "123456", "lens.com");

                            System.out.println(connection.getUser());

                            connection.getChatManager().createChat("kenluo@lens.com",null).sendMessage("Hello World from Java asmack XMPP!");

        					/* Chat chat = connection.getChatManager().createChat("kenluo@lens.com", new MessageListener()
		        			{
		        				//@Override
		        				//public void processMessage(Chat chat, Message message)
		        				//{
		        				//	System.out.println("Received message: " + message);
		        				//}


								@Override
								public void processMessage(Chat arg0,
									org.jivesoftware.smack.packet.Message arg1) {
									// TODO Auto-generated method stub
									//System.out.println("Received message: " + arg1);
								}
		        			});

		        			chat.sendMessage("Hello World from Java XMPP!"); */

        				} catch (XMPPException e_xmpp)
        					{
        						//Message msg4 = new Message();

        						//String strMsg = (String) e_xmpp.getMessage();
        						//msg4.what = 4;
        						//msg4.obj = strMsg;
        						//handler.sendMessage(msg4);

        						//e_xmpp.printStackTrace();

                                throw new IllegalStateException(e_xmpp);
        					}
        				}

        		}.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
