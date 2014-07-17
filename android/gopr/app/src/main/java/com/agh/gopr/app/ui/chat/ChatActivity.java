//package com.agh.gopr.app.ui.chat;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnKeyListener;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.TreeSet;
//
//import pl.masa.android.R;
//import pl.masa.android.activities.authentication.AuthenticationActivity;
//import pl.masa.android.activities.settings.AppPreferences;
//import pl.masa.android.model.Message;
//import pl.masa.android.model.Message;
//import pl.masa.android.model.User;
//import pl.masa.android.server.NotLoggedInException;
//import pl.masa.android.server.Server;
//
//public class ChatActivity extends Activity {
//
//	private static int REFRESH_DELAY = 3000;
//
//	public final static String SELECTED_USER = "pl.masa.android.activities.Main.MarkerClickDialog";
//
//	private pl.masa.android.activities.chat.DiscussArrayAdapter adapter;
//	private ListView lv;
//	private EditText inputMessageText;
//	private ImageButton sendMessageButton;
//
//	private String loggedUserLogin;
//	private String userLogin;
//	private String userName;
//	private Refreshthread refreshThread;
//
//	private Comparator<Message> MessageComparator = new Comparator<Message>() {
//		@Override
//		public int compare(Message o1, Message o2) {
//			return (int) (o1.getTime() - o2.getTime());
//		}
//	};
//
//	private TreeSet<Message> messages = new TreeSet<Message>(MessageComparator);
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_discuss);
//
//		lv = (ListView) findViewById(R.id.listView1);
//
//		loggedUserLogin = AppPreferences.getCurrentLoggedUserLogin();
//
//		Intent intent = getIntent();
//		String message = intent.getStringExtra(ChatActivity.SELECTED_USER);
//		userName = message.split(":")[0];
//		userLogin = message.split(":")[1];
//		setTitle(userName);
//
//		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.discuss_listitem, this);
//
//		lv.setAdapter(adapter);
//
//		inputMessageText = (EditText) findViewById(R.id.inputMessageText);
//		inputMessageText.setOnKeyListener(new OnKeyListener() {
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//
//					if (inputMessageText.getText() == null)
//						return false;
//
//					long time = (new Date()).getTime();
//
//					Message message = new Message(time, new User(AppPreferences.getCurrentLoggedUserLogin()), new User(userLogin), inputMessageText
//							.getText().toString());
//					try {
//						Server.sendMessage(message);
//					} catch (NotLoggedInException e) {
//						e.printStackTrace();
//					}
//					// boolean ok = messages.add(message);
//					// System.out.println("Dodaje: " + ok + " Rozmiar: " +
//					// messages.size());
//					// adapter.add(new Message(false, message.toString(),
//					// false));
//					// adapter.notifyDataSetChanged();
//					// lv.setSelection(adapter.getCount() - 1);
//					inputMessageText.setText("");
//					return true;
//				}
//				return false;
//			}
//		});
//
//		sendMessageButton = (ImageButton) findViewById(R.id.sendMessageButton);
//		sendMessageButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (inputMessageText.getText() == null)
//					return;
//
//				long time = (new Date()).getTime();
//
//				Message message = new Message(time, new User(AppPreferences.getCurrentLoggedUserLogin()), new User(userLogin), inputMessageText
//						.getText().toString());
//				try {
//					Server.sendMessage(message);
//				} catch (NotLoggedInException e) {
//					e.printStackTrace();
//				}
//				// boolean ok = messages.add(message);
//				// System.out.println("Dodaje: " + ok + " Rozmiar: " +
//				// messages.size());
//				// adapter.add(new Message(false, message.toString(), false));
//				// adapter.notifyDataSetChanged();
//				// lv.setSelection(adapter.getCount() - 1);
//				inputMessageText.setText("");
//			}
//
//		});
//
//		try {
//			System.out.println(userLogin);
//			ArrayList<Message> messagesFromServer = (ArrayList<Message>) Server.getMessages(new User(userLogin));
//			messages.addAll(messagesFromServer);
//		} catch (NotLoggedInException e) {
//			Intent i = new Intent(this, AuthenticationActivity.class);
//			startActivity(i);
//
//		}
//		addItems();
//		lv.setSelection(adapter.getCount() - 1);
//		refreshThread = new Refreshthread();
//		refreshThread.start();
//
//	}
//
//	private void addItem(Message message) {
//		Message Message = null;
//		if (message.getFrom().getLogin().equals(loggedUserLogin) && message.getTo().getLogin().equals(userLogin))
//			Message = new Message(false, message);
//		else if (message.getFrom().getLogin().equals(userLogin) && message.getTo().getLogin().equals(loggedUserLogin))
//			Message = new Message(true, message);
//		if (!adapter.getMessages().contains(Message))
//			adapter.add(Message);
//	}
//
//	private void addItems() {
//
//		for (Message message : messages) {
//			addItem(message);
//		}
//	}
//
//	@Override
//	protected void onStop() {
//		super.onStop();
//
//		refreshThread.cancel();
//	}
//
//	class Refreshthread extends Thread {
//
//		private boolean isStarted;
//
//		public Refreshthread() {
//			isStarted = true;
//		}
//
//		@Override
//		public void run() {
//			while (isStarted) {
//				System.out.println("watek updatujacy wiadomosci!(" + messages.size() + ")");
//				try {
//					List<Message> updatedMessages = (ArrayList<Message>) Server.getMessages(new User(userLogin));
//					System.out.println("Messages from server list: " + updatedMessages.size());
//
//					for (final Message message : updatedMessages) {
//						if (isNewest(message)) {
//
//							messages.add(message);
//							System.out.println("dodaje... " + messages.size());
//							ChatActivity.this.runOnUiThread(new Runnable() {
//								@Override
//								public void run() {
//
//									addItem(message);
//									adapter.notifyDataSetChanged();
//									lv.setSelection(adapter.getCount() - 1);
//
//								}
//
//							});
//							System.out.println("dodalem...");
//
//						}
//					}
//
//					Thread.sleep(REFRESH_DELAY);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (NotLoggedInException e) {
//					e.printStackTrace();
//				}
//			}
//			System.out.println("watek refreshujacy cancel!");
//		}
//
//		public void cancel() {
//			isStarted = false;
//		}
//
//	}
//
//	public boolean isNewest(Message m) {
//		Iterator<Message> it = messages.iterator();
//		while (it.hasNext()) {
//			long t = it.next().getTime();
//			if (m.getTime() <= t)
//				return false;
//		}
//		// System.out.println(m.getTime());
//		return true;
//	}
//}