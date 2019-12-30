package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Chat Window";
    private ListView chatView;
    private EditText chatText;
    private Button sendButton;
    final ArrayList<String> textList = new ArrayList<String>();

    private ChatDatabaseHelper chatDbHelper;
    private SQLiteDatabase database;

    private FrameLayout frameLayout;
    private boolean isFrameLayoutLoaded;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatView = (ListView) findViewById(R.id.chatView);
        chatText = (EditText) findViewById(R.id.chatText);
        sendButton = (Button) findViewById(R.id.sendButton);

        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        if(frameLayout == null){
            isFrameLayoutLoaded = false;
        } else {
            isFrameLayoutLoaded = true;
        }



        final ChatAdapter messageAdapter =new ChatAdapter( this );
        chatView.setAdapter (messageAdapter);
        chatDbHelper = new ChatDatabaseHelper(this);
        database = chatDbHelper.getWritableDatabase();
        fetchAllMessages(database);


        sendButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                textList.add(chatText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                ContentValues contentValues = new ContentValues();
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, chatText.getText().toString());
                database.insert(ChatDatabaseHelper.TABLE_MESSAGES, null, contentValues);
                chatText.setText("");

            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public String getItem(int position) {
            return textList.get(position);
        }

        @Override
        public int getCount() {
            return textList.size();
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

        @Override
        public long getItemId(int position) {
            long id = 0;
            if(cursor != null && !cursor.isClosed()){
                cursor.moveToPosition(position);
                id = Long.parseLong(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
            }
            return id;
        }
    }

    public void fetchAllMessages(SQLiteDatabase database) {

        cursor = database.query(ChatDatabaseHelper.TABLE_MESSAGES, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
           String message = cursor.getString(1);
            int columnCount = cursor.getColumnCount();
            Log.i(ACTIVITY_NAME, "message" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + columnCount );
            for( int i = 0 ; i < columnCount ; i++) {
                Log.i(ACTIVITY_NAME, "Cursor’s  column name =" + cursor.getColumnName(i));
            }
            textList.add(message);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
