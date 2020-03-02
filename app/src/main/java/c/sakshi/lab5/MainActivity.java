package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences( "c.sakshi.lab5", Context.MODE_PRIVATE );

        if( !sharedPreferences.getString( usernameKey, "" ).equals("") )
        {
            String name = sharedPreferences.getString( usernameKey, "" );

            Intent intent = new Intent(this, NotesScreen.class );
            intent.putExtra( "fullname", name );
            startActivity( intent );
        }
        else
        {
            setContentView(R.layout.activity_main);
        }
    }

    public void loginClicked( View view )
    {
        EditText nameTextField = (EditText) findViewById( R.id.name );
        String name = nameTextField.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences( "c.sakshi.lab5", Context.MODE_PRIVATE );
        sharedPreferences.edit().putString( "username", name ).apply();

        goToNoteMenu( name );
    }

    public void goToNoteMenu( String name )
    {
        Intent intent = new Intent(this, NotesScreen.class );
        intent.putExtra( "fullname", name );
        startActivity( intent );
    }
}
