package pl.poznan.ue.booker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class for DateSelectionActivity.
 * @author MJazy .
 */
public class DateSelectionActivity extends AppCompatActivity {

    String userEmail;
    CalendarView calendarView;
    TextView pickedDates;
    String pickedDatesText;
    Set<String> pickedDatesSet;
    String packageString;
    POSTHandler postHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("userEmail");
            packageString = extras.getString("packageString");
        }
        postHandler = new POSTHandler();
        pickedDatesSet = new TreeSet<String>();
        pickedDates = findViewById(R.id.pickedDates);
        pickedDatesText = "";
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                preparePickedDatesText(year, month, dayOfMonth);
            }
        });
    }

    private void preparePickedDatesText(int year, int month, int day){
        String pickedDate = year + "-" + month + "-" + day;
        pickedDatesText = "";
        pickedDatesSet.add(pickedDate);
        for (int index = 0; index < pickedDatesSet.size(); index ++) {
            pickedDatesText =pickedDatesText + pickedDatesSet.toArray()[index].toString() + " ";
        }
        pickedDates.setText(pickedDatesText);
    }

    /**
     * Method triggering email creation and activity change.
     * @param view
     */
    public void proceedToConfirmationActivity(View view) {
        try {
            sendEmail();
            postHandler.doInBackground("http://localhost:8080/registration", prepareJson(packageString, pickedDatesText));
            Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
            startActivity(intent);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String prepareEmailBody(){
        return "Drogi Kliencie \n Potwierdzamy zamówienie pakietu złożonego z " + packageString + " będzie on obowiązywał w terminach widocznych poniżej \n" + pickedDatesText;
    }

    /**
     * Method for creating email.
     */
    public void sendEmail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{userEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Potwierdzenie zamówienia");
        intent.putExtra(Intent.EXTRA_TEXT   , prepareEmailBody());
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private String prepareJson(String servicesSelected, String daysSelected) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("servicesSelected", servicesSelected);
            jsonObject.put("daysSelected", daysSelected);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}
