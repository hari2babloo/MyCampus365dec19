package a3x3conect.com.mycampus365;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class SMS extends AppCompatActivity {
    EditText search;
    ListViewAdapter listviewadapter;
    String testval;
    JSONArray jArray;
    JSONObject json_data;
    Spinner spinner;
    ProgressDialog pd;
    ListView myList;
    Button getChoice;
    String[] names;
    ArrayAdapter<String> adapter;
    List<String> ls = new ArrayList<>();
    List<String> ms = new ArrayList<>();
    private RecyclerView mRVFishPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);
        getSupportActionBar().setTitle("SMS");
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.usrsmal);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        spinner = (Spinner)findViewById(R.id.spinner);

        onitemselect();
        // Spinner click listener
//        spinner.setOnItemSelectedListener(Users.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Category");
        categories.add("Administrator");
        categories.add("Teacher");
        categories.add("Student");
        categories.add("Parent");
        categories.add("Support Staff");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        pd = new ProgressDialog(SMS.this);
        pd.setMessage("Getting Data from Server...");
        pd.show();
        new JsonAsync().execute("http://183.82.106.77:8080/welham/webservices/user.php");

    }

    private void onitemselect() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
                String s = spinner.getSelectedItem().toString();
                if (s.equalsIgnoreCase("Select Category")){

                }
                if (s.equalsIgnoreCase("Administrator")){

                    ls.clear();
                    ms.clear();

                    try {
                        JSONArray jrar = new  JSONArray(testval);

                        names=new String[jrar.length()];

                        for(int k=0;k<jrar.length();k++) {

                            JSONObject  json_data1 = jrar.getJSONObject(k);


                            String m = json_data1.getString("Role").toLowerCase();

                            if (m.equalsIgnoreCase("001")) {
                                String pref = json_data1.getString("prefferredName");
                                String mb = json_data1.getString("phone");
                                ls.add(pref);
                                ms.add(mb);
                            }

                        }
//                        adapter.clear();
                        Sendsmsstart();
                    } catch (JSONException e) {
                        Toast.makeText(SMS.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                else if(s.equalsIgnoreCase("Teacher")){
                    ls.clear();
                    ms.clear();
                    try {
                        JSONArray jrar = new  JSONArray(testval);

                        names=new String[jrar.length()];

                        for(int k=0;k<jrar.length();k++) {

                            JSONObject  json_data1 = jrar.getJSONObject(k);


                            String m = json_data1.getString("Role").toLowerCase();

                            if (m.equalsIgnoreCase("002")) {
                                String pref = json_data1.getString("prefferredName");
                                String mb = json_data1.getString("phone");
                                ls.add(pref);
                                ms.add(mb);
                            }

                        }
                     //   adapter.clear();
                        Sendsmsstart();
                    } catch (JSONException e) {
                        Toast.makeText(SMS.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                else if (s.equalsIgnoreCase("Student")){
                    ls.clear();
                    ms.clear();
                    try {
                        JSONArray jrar = new  JSONArray(testval);

                        names=new String[jrar.length()];

                        for(int k=0;k<jrar.length();k++) {

                            JSONObject  json_data1 = jrar.getJSONObject(k);


                            String m = json_data1.getString("Role").toLowerCase();

                            if (m.equalsIgnoreCase("003")) {
                                String pref = json_data1.getString("prefferredName");
                                String mb = json_data1.getString("phone");
                                ls.add(pref);
                                ms.add(mb);
                            }

                        }
                       // adapter.clear();
                        Sendsmsstart();
                    } catch (JSONException e) {
                        Toast.makeText(SMS.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                else if (s.equalsIgnoreCase("Parent")){
                    ls.clear();
                    ms.clear();
                    try {
                        JSONArray jrar = new  JSONArray(testval);

                        names=new String[jrar.length()];

                        for(int k=0;k<jrar.length();k++) {

                            JSONObject  json_data1 = jrar.getJSONObject(k);


                            String m = json_data1.getString("Role").toLowerCase();

                            if (m.equalsIgnoreCase("004")) {
                                String pref = json_data1.getString("prefferredName");
                                String mb = json_data1.getString("phone");
                                ls.add(pref);
                                ms.add(mb);
                            }

                        }
                     //   adapter.clear();
                        Sendsmsstart();
                    } catch (JSONException e) {
                        Toast.makeText(SMS.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                else if (s.equalsIgnoreCase("Support Staff")){
                    ls.clear();
                    ms.clear();
                    try {
                        JSONArray jrar = new  JSONArray(testval);

                        names=new String[jrar.length()];

                        for(int k=0;k<jrar.length();k++) {

                            JSONObject  json_data1 = jrar.getJSONObject(k);


                            String m = json_data1.getString("Role").toLowerCase();

                            if (m.equalsIgnoreCase("006")) {
                                String pref = json_data1.getString("prefferredName");
                                String mb = json_data1.getString("phone");
                                ls.add(pref);
                                ms.add(mb);
                            }

                        }
                     //   adapter.clear();
                        Sendsmsstart();
                    } catch (JSONException e) {
                        Toast.makeText(SMS.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void Sendsmsstart() {


        myList = (ListView)findViewById(R.id.listView);
        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getChoice = (Button)findViewById(R.id.getlist);
adapter = new ArrayAdapter<String>(SMS.this,

                android.R.layout.simple_list_item_multiple_choice,

                ls);
        myList.setAdapter(adapter);

        getChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = "";
                String names = "";
                int cntChoice = myList.getCount();

                SparseBooleanArray sparseBooleanArray = myList.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){

                    if(sparseBooleanArray.get(i)){
                        selected += ms.get(i);
                        names += ls.get(i);
                        selected+=",";
                        names+=",";
                    }
                }
                if(selected != null && !selected.isEmpty()){
                    Intent intent = new Intent(SMS.this, Sendsms.class);
                    intent.putExtra("numbers", selected);
                    intent.putExtra("names",names);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SMS.this, "Please Select Receipient", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public class JsonAsync extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = " ";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();
                //

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                try {
                    if (reader != null) {
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

            testval = result;
            pd.dismiss();
            pd.cancel();

            Log.e("result", result);


        }

    }
}
