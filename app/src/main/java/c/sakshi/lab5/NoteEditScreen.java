package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteEditScreen extends AppCompatActivity {

    int noteid = -1;
    String usernameKey = "username";
    private static Context context;
    private static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit_screen);
        //otherwise null pointer error occurred
        context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase( "notes", Context.MODE_PRIVATE, null );
        dbHelper = new DBHelper( sqLiteDatabase );

        EditText noteText = (EditText) findViewById( R.id.noteText );

        Intent intent = getIntent();

        noteid = intent.getIntExtra( "noteid", -1 );

        if( noteid != -1 )
        {
            Note note = NotesScreen.notes.get( noteid );
            String noteContent = note.getContent();
            noteText.setText( noteContent );
        }
    }

    public void saveClicked( View view )
    {
        EditText noteText = (EditText) findViewById( R.id.noteText );
        String content = noteText.getText().toString();

        //get username
        SharedPreferences sharedPreferences = getSharedPreferences( "c.sakshi.lab5", Context.MODE_PRIVATE );
        String username = sharedPreferences.getString( usernameKey, "" );

        String title;
        DateFormat dateFormat =  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss" );
        String date = dateFormat.format( new Date() );

        if( noteid == -1 )
        {
            title = "NOTE_" + (NotesScreen.notes.size() + 1 );
            dbHelper.saveNotes( username, title, content, date );
        }
        else
        {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNotes( title, date, content, username );
        }
    }
}
