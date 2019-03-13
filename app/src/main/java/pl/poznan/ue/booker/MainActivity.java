package pl.poznan.ue.booker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText userEmailEditText;
    String userEmailString;
    CheckBox netflixCheckbox;
    CheckBox amazonCheckbox;
    CheckBox hboGoCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmailEditText = findViewById(R.id.userEmailEditText);
        netflixCheckbox = findViewById(R.id.netflixCheckbox);
        hboGoCheckbox = findViewById(R.id.hboGoCheckbox);
        amazonCheckbox = findViewById(R.id.amazonCheckbox);
    }

    private String preparePackageString(){
        String packageString = "";
        if (netflixCheckbox.isChecked()) {
            packageString = packageString + netflixCheckbox.getText() + " ";
        }
        if (amazonCheckbox.isChecked()) {
            packageString = packageString + amazonCheckbox.getText() + " ";
        }
        if (hboGoCheckbox.isChecked()) {
            packageString = packageString + hboGoCheckbox.getText();
        }
        return packageString;
    }

    /**
     * Method for changing activity.
     */
    public void proceedToDateSelection(View view){
        Intent intent = new Intent(getApplicationContext(), DateSelectionActivity.class);
        userEmailString = userEmailEditText.getText().toString();
        intent.putExtra("userEmail", userEmailString);
        intent.putExtra("packageString", preparePackageString());
        startActivity(intent);
    }
}
