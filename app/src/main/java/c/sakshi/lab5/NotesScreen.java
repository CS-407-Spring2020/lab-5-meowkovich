package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Context;


public class NotesScreen extends AppCompatActivity {

    TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_screen);

        welcomeMessage = (TextView) findViewById( R.id.welcomeText );
        Intent intent = getIntent();
        String name = intent.getStringExtra("fullname" );
        welcomeMessage.setText( "Welcome " + name );
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

        }

        return true;
    }
}
