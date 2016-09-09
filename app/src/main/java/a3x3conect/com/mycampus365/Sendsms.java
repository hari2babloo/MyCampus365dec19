package a3x3conect.com.mycampus365;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Sendsms extends AppCompatActivity {

    Button btn;
    EditText compose;
    TextView receipts;
     ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendsms);
        Bundle bundle = getIntent().getExtras();
        final String numbers = bundle.getString("numbers").toString();
        String names = bundle.getString("names").toString();
        btn = (Button)findViewById(R.id.button);
        pd = new ProgressDialog(Sendsms.this);
        compose = (EditText)findViewById(R.id.compose);
        receipts = (TextView)findViewById(R.id.rec);
        receipts.setText("Recepients :"+names);

       // Strin

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 pd = new ProgressDialog(Sendsms.this);
                pd.setMessage("Sending Message...");
                pd.show();
                new AsyncFetch().execute("http://sms99.co.in/pushsms.php?username=tredu&password=7gFXCU&sender=WELHAM&message="+compose.getText().toString().replaceAll("\\s+","%20")+"&numbers="+numbers);

            }
        });
    }

    public class AsyncFetch extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line= " ";
                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                return  buffer.toString();
                //

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null){
                    connection.disconnect();
                }

                try {
                    if (reader!=null){
                        reader.close();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            pd.dismiss();
            pd.cancel();

            Snackbar.make(findViewById(R.id.root),result, Snackbar.LENGTH_LONG)
                    .show();
            // pdLoading.dismiss();
        }

    }
}
