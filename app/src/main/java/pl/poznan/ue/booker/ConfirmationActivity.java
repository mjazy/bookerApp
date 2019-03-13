package pl.poznan.ue.booker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Class for ConfirmationACtivity.
 * @author MJazy
 */
public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }

    /**
     * Method for quitting the application.
     * @param view
     */
    public void quit(View view) {
     finishAndRemoveTask();
    }

}
