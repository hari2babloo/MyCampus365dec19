package a3x3conect.com.mycampus365;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText Email,Pass;
    String strusr,strpass;

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = (EditText)findViewById(R.id.email);
        Pass = (EditText)findViewById(R.id.pass);
        strusr = Email.getText().toString();
        strpass = Pass.getText().toString();

      //  Toast.makeText(MainActivity.this, urlfinal, Toast.LENGTH_SHORT).show();
        ImageButton login = (ImageButton)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new AsyncFetch().execute("http://13.76.249.51:8080/school/webservices/login.php?username="+Email.getText().toString()+"&password="+Pass.getText().toString());
              //  Log.e("URL",urlfinal);

            }
        });

        //Make call to AsyncTask

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
           // pdLoading.dismiss();
            try {
                JSONObject reader = new JSONObject(result);
                String s = reader.getString("id");
//               String d = reader.getString("name");
               Toast.makeText(MainActivity.this, s , Toast.LENGTH_SHORT).show();
                if (s.equalsIgnoreCase("001")){

                    Intent intent = new Intent(MainActivity.this,Dashpage.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //this method will be running on UI thread

           //



        }

    }



}