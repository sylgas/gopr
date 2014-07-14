//package com.agh.gopr.app.ui.chat;
//
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.agh.gopr.app.R;
//import com.agh.gopr.app.database.Message;
//import com.googlecode.androidannotations.annotations.ViewById;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DiscussArrayAdapter extends ArrayAdapter<Message> {
//
//	private LayoutInflater inflater;
//
//	public DiscussArrayAdapter(Context context, int textViewResourceId) {
//		super(context, textViewResourceId);
//        this.inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View row = convertView;
//		if (row == null) {
//			row = inflater.inflate(R.layout.conversation_list, parent, false);
//		}
//
//		LinearLayout container = (LinearLayout) row.findViewById(R.id.container);
//
//		Message message = getItem(position);
//		TextView messageView = (TextView) row.findViewById(R.id.message);
//		messageView.setText(coment.Message);
//
//		int resId = coment.left ? R.drawable.bubble_yellow : R.drawable.bubble_green;
//
//		MessageLayout = (LinearLayout) row.findViewById(R.id.MessageLayout);
//
//		MessageLayout.setBackgroundResource(resId);
//
//		wrapper.setGravity(coment.left ? Gravity.LEFT : Gravity.RIGHT);
//
//		imageAppendix = (ImageView) row.findViewById(R.id.imageAppendix);
//
//		if (coment.isHasAttachment()) {
//			imageAppendix.setVisibility(View.VISIBLE);
//			MessageTextView.setOnClickListener(new OnMessageClickListener(coment));
//		} else
//			imageAppendix.setVisibility(View.INVISIBLE);
//		return row;
//	}
//
//	public Bitmap decodeToBitmap(byte[] decodedByte) {
//		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
//	}
//
//	/*class OnMessageClickListener implements OnClickListener {
//
//		private Message com;
//
//		public OnMessageClickListener(Message coment) {
//			com = coment;
//		}
//
//		@Override
//		public void onClick(View v) {
//			currentClickedMessage = com;
//			Builder ab = new Builder(context);
//			ab.setMessage("Download a file?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
//
//		}
//
//	}*/
//
///*	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			switch (which) {
//			case DialogInterface.BUTTON_POSITIVE:
//				try {
//					Server.getFile(currentClickedMessage.message.getFile());
//				} catch (NotLoggedInException e) {
//					e.printStackTrace();
//				}
//				// new
//				// DownloadFileAsyncTask().execute("http://195.234.21.203:4321/localization/api/file/token/god-token/fileId/40");
//				break;
//
//			case DialogInterface.BUTTON_NEGATIVE:
//				break;
//			}
//		}
//	};*/
//
//}