package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class NotesScreen extends AppCompatActivity {

    TextView welcomeMessage;
    public static ArrayList<Note> notes = new ArrayList<>();
    private static Context context;
    private static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_screen);
        context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase( "notes", Context.MODE_PRIVATE, null );
        dbHelper = new DBHelper( sqLiteDatabase );

        //Set Welcome message
        welcomeMessage = (TextView) findViewById( R.id.welcomeText );
        Intent intent = getIntent();
        String name = intent.getStringExtra("fullname" );
        welcomeMessage.setText( "Welcome " + name );

        notes = dbHelper.readNotes( name );

        if( !notes.isEmpty() )
        {
            String theName = notes.get(0).getContent();
            Log.i("notes", theName );
        }
        else
        {
            Log.i( "notes", "empty" );
        }

        ArrayList<String> displayNotes = new ArrayList<>();
        for( Note note: notes )
        {
            displayNotes.add( String.format( "Title:%s\nDate:%s", note.getTitle(), note.getDate() ) );
        }

        ArrayAdapter adapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, displayNotes );
        ListView listView = (ListView) findViewById( R.id.notes_list );
        listView.setAdapter( adapter );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), NoteEditScreen.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.note_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item )
    {
        switch( item.getItemId() )
        {
            case R.id.menu_logout:
                SharedPreferences sharedPreferences = getSharedPreferences( "c.sakshi.lab5", Context.MODE_PRIVATE );
                sharedPreferences.edit().remove( "username" ).apply();
                Intent intent = new Intent( this, MainActivity.class );
                startActivity( intent );
                return true;
            case R.id.add_note:
                Intent intent2 = new Intent( this, NoteEditScreen.class );
                startActivity( intent2 );
                return true;
        }

        return true;
    }
}
