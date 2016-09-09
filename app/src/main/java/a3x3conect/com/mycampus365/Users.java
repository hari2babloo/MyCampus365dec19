package a3x3conect.com.mycampus365;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Collections;
import java.util.List;

public class Users extends AppCompatActivity {
    private RecyclerView mRVFishPrice;
    EditText search;
    String testval;
    JSONArray jArray;
    JSONObject json_data;
    Spinner spinner;
    ProgressDialog pd;
    List<DataFish> data=new ArrayList<>();
    List<DataFish> filterdata=new ArrayList<>();
    List<DataFish> spinnerdata=new ArrayList<>();
    private AdapterFish mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);
        getSupportActionBar().setTitle("Users");
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.usrsmal);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        search = (EditText) findViewById( R.id.search);
        spinner = (Spinner)findViewById(R.id.spinner);
        addTextListener();
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
        pd = new ProgressDialog(Users.this);
        pd.setMessage("Getting Data from Server...");
        pd.show();
        new JsonAsync().execute("http://13.76.249.51:8080/school/webservices/user.php");

    }




    public class JsonAsync extends AsyncTask<String,String,String> {
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

            testval =result;

            pd.dismiss();
            pd.cancel();
            //this method will be running on UI thread

            Log.e("result",result);

            //  pdLoading.dismiss();


            //  pdLoading.dismiss();
            try {

                jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){


                    //   JSONArray jsonObject = sys.getJSONArray(i);
                    json_data = jArray.getJSONObject(i);
//                    // Log.e("json",json_data.toString());
                    DataFish fishData = new DataFish();
                    fishData.dob = json_data.getString("dob");
//                    Log.e("prefname",fishData.course);
                    fishData.email=json_data.getString("email");
                    // fishData.Id=json_data.getString("Id");
                    fishData.phone=json_data.getString("phone");
                    fishData.prefferredName= json_data.getString("prefferredName");
                    fishData.Role = json_data.getString("Role");
                    fishData.username = json_data.getString("username");

                    data.add(fishData);

                }

                // Setup and Handover data to recyclerview
                mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                mAdapter = new AdapterFish(Users.this, data);
                //   Log.e("data",data.toString());
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(Users.this));


            } catch (JSONException e) {
                Toast.makeText(Users.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }



    private void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                filterdata.clear();
                mAdapter.notifyDataSetChanged();

                query = query.toString().toLowerCase();
                //Toast.makeText(Users.this,jArray.length(), Toast.LENGTH_SHORT).show();

                try {

                    JSONArray jrar = new  JSONArray(testval);
                   // Toast.makeText(Users.this,jrar.length(), Toast.LENGTH_SHORT).show();


                    // Extract data from json and store into ArrayList as class objects
                    for(int i=0;i<jrar.length();i++) {


                        //   JSONArray jsonObject = sys.getJSONArray(i);
                      JSONObject  json_data1 = jrar.getJSONObject(i);
//                    // Log.e("json",json_data.toString());
                        DataFish fishData = new DataFish();
                        // fishData.dob = json_data.getString("dob");
                        String s = json_data1.getString("prefferredName").toLowerCase();
                        String d = json_data1.getString("username").toLowerCase();
                        if (s.contains(query)||d.contains(query)) {
                            fishData.email = json_data1.getString("email");
                            // fishData.Id=json_data.getString("Id");
                            fishData.phone = json_data1.getString("phone");
                            fishData.prefferredName = json_data1.getString("prefferredName");
                            fishData.Role = json_data.getString("Role");
                            fishData.username = json_data1.getString("username");
                            filterdata.add(fishData);
                        }


                        // Setup and Handover data to recyclerview
                        mRVFishPrice = (RecyclerView) findViewById(R.id.fishPriceList);
                        mAdapter = new AdapterFish(Users.this, filterdata);
                        //   Log.e("data",data.toString());
                        mRVFishPrice.setAdapter(mAdapter);
                        mRVFishPrice.setLayoutManager(new LinearLayoutManager(Users.this));
                      //  addTextListener();
                        //  onitemselect();
                    }

                } catch (JSONException e) {
                    Toast.makeText(Users.this, e.toString(), Toast.LENGTH_LONG).show();
                }



            }






            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void onitemselect() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
                String s = spinner.getSelectedItem().toString();
                if (s.equalsIgnoreCase("Select Category")){
                   // Toast.makeText(Users.this, "Do Nothing", Toast.LENGTH_SHORT).show();
                }
                if (s.equalsIgnoreCase("Administrator")){
                    spinnerdata.clear();
                    mAdapter.notifyDataSetChanged();
                    //Toast.makeText(Users.this,jArray.length(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jrar = new  JSONArray(testval);
                        // Toast.makeText(Users.this,jrar.length(), Toast.LENGTH_SHORT).show();

                        // Extract data from json and store into ArrayList as class objects
                        for(int k=0;k<jrar.length();k++) {


                            //   JSONArray jsonObject = sys.getJSONArray(i);
                            JSONObject  json_data1 = jrar.getJSONObject(k);
//                    // Log.e("json",json_data.toString());
                            DataFish fishData = new DataFish();
                            // fishData.dob = json_data.getString("dob");
                            String m = json_data1.getString("Role").toLowerCase();

                        //    Toast.makeText(Users.this, m, Toast.LENGTH_SHORT).show();
                            if (m.equalsIgnoreCase("001")) {
                                fishData.email = json_data1.getString("email");
                                // fishData.Id=json_data.getString("Id");
                                fishData.phone = json_data1.getString("phone");
                                fishData.prefferredName = json_data1.getString("prefferredName");
                                fishData.Role = json_data.getString("Role");
                                fishData.username = json_data1.getString("username");
                                spinnerdata.add(fishData);
                            }


                            // Setup and Handover data to recyclerview
                            mRVFishPrice = (RecyclerView) findViewById(R.id.fishPriceList);
                            mAdapter = new AdapterFish(Users.this, spinnerdata);
                            //   Log.e("data",data.toString());
                            mRVFishPrice.setAdapter(mAdapter);
                            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Users.this));
                            //  addTextListener();
                            //  onitemselect();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Users.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                if (s.equalsIgnoreCase("Teacher")){

                    spinnerdata.clear();
                    mAdapter.notifyDataSetChanged();
                    //Toast.makeText(Users.this,jArray.length(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jrar = new  JSONArray(testval);
                        // Toast.makeText(Users.this,jrar.length(), Toast.LENGTH_SHORT).show();

                        // Extract data from json and store into ArrayList as class objects
                        for(int k=0;k<jrar.length();k++) {


                            //   JSONArray jsonObject = sys.getJSONArray(i);
                            JSONObject  json_data1 = jrar.getJSONObject(k);
//                    // Log.e("json",json_data.toString());
                            DataFish fishData = new DataFish();
                            // fishData.dob = json_data.getString("dob");
                            String m = json_data1.getString("Role").toLowerCase();

                            //    Toast.makeText(Users.this, m, Toast.LENGTH_SHORT).show();
                            if (m.equalsIgnoreCase("002")) {
                                fishData.email = json_data1.getString("email");
                                // fishData.Id=json_data.getString("Id");
                                fishData.phone = json_data1.getString("phone");
                                fishData.prefferredName = json_data1.getString("prefferredName");
                                fishData.Role = json_data.getString("Role");
                                fishData.username = json_data1.getString("username");
                                spinnerdata.add(fishData);
                            }


                            // Setup and Handover data to recyclerview
                            mRVFishPrice = (RecyclerView) findViewById(R.id.fishPriceList);
                            mAdapter = new AdapterFish(Users.this, spinnerdata);
                            //   Log.e("data",data.toString());
                            mRVFishPrice.setAdapter(mAdapter);
                            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Users.this));
                            //  addTextListener();
                            //  onitemselect();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Users.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                if (s.equalsIgnoreCase("Student")){

                    spinnerdata.clear();
                    mAdapter.notifyDataSetChanged();
                    //Toast.makeText(Users.this,jArray.length(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jrar = new  JSONArray(testval);
                        // Toast.makeText(Users.this,jrar.length(), Toast.LENGTH_SHORT).show();

                        // Extract data from json and store into ArrayList as class objects
                        for(int k=0;k<jrar.length();k++) {


                            //   JSONArray jsonObject = sys.getJSONArray(i);
                            JSONObject  json_data1 = jrar.getJSONObject(k);
//                    // Log.e("json",json_data.toString());
                            DataFish fishData = new DataFish();
                            // fishData.dob = json_data.getString("dob");
                            String m = json_data1.getString("Role").toLowerCase();

                            //    Toast.makeText(Users.this, m, Toast.LENGTH_SHORT).show();
                            if (m.equalsIgnoreCase("003")) {
                                fishData.email = json_data1.getString("email");
                                // fishData.Id=json_data.getString("Id");
                                fishData.phone = json_data1.getString("phone");
                                fishData.prefferredName = json_data1.getString("prefferredName");
                                fishData.Role = json_data.getString("Role");
                                fishData.username = json_data1.getString("username");
                                spinnerdata.add(fishData);
                            }


                            // Setup and Handover data to recyclerview
                            mRVFishPrice = (RecyclerView) findViewById(R.id.fishPriceList);
                            mAdapter = new AdapterFish(Users.this, spinnerdata);
                            //   Log.e("data",data.toString());
                            mRVFishPrice.setAdapter(mAdapter);
                            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Users.this));
                            //  addTextListener();
                            //  onitemselect();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Users.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                if (s.equalsIgnoreCase("Parent")){

                    spinnerdata.clear();
                    mAdapter.notifyDataSetChanged();
                    //Toast.makeText(Users.this,jArray.length(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jrar = new  JSONArray(testval);
                        // Toast.makeText(Users.this,jrar.length(), Toast.LENGTH_SHORT).show();

                        // Extract data from json and store into ArrayList as class objects
                        for(int k=0;k<jrar.length();k++) {


                            //   JSONArray jsonObject = sys.getJSONArray(i);
                            JSONObject  json_data1 = jrar.getJSONObject(k);
//                    // Log.e("json",json_data.toString());
                            DataFish fishData = new DataFish();
                            // fishData.dob = json_data.getString("dob");
                            String m = json_data1.getString("Role").toLowerCase();

                            //    Toast.makeText(Users.this, m, Toast.LENGTH_SHORT).show();
                            if (m.equalsIgnoreCase("004")) {
                                fishData.email = json_data1.getString("email");
                                // fishData.Id=json_data.getString("Id");
                                fishData.phone = json_data1.getString("phone");
                                fishData.prefferredName = json_data1.getString("prefferredName");
                                fishData.Role = json_data.getString("Role");
                                fishData.username = json_data1.getString("username");
                                spinnerdata.add(fishData);
                            }


                            // Setup and Handover data to recyclerview
                            mRVFishPrice = (RecyclerView) findViewById(R.id.fishPriceList);
                            mAdapter = new AdapterFish(Users.this, spinnerdata);
                            //   Log.e("data",data.toString());
                            mRVFishPrice.setAdapter(mAdapter);
                            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Users.this));
                            //  addTextListener();
                            //  onitemselect();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Users.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                if (s.equalsIgnoreCase("Support Staff")){

                    spinnerdata.clear();
                    mAdapter.notifyDataSetChanged();
                    //Toast.makeText(Users.this,jArray.length(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jrar = new  JSONArray(testval);
                        // Toast.makeText(Users.this,jrar.length(), Toast.LENGTH_SHORT).show();

                        // Extract data from json and store into ArrayList as class objects
                        for(int k=0;k<jrar.length();k++) {


                            //   JSONArray jsonObject = sys.getJSONArray(i);
                            JSONObject  json_data1 = jrar.getJSONObject(k);
//                    // Log.e("json",json_data.toString());
                            DataFish fishData = new DataFish();
                            // fishData.dob = json_data.getString("dob");
                            String m = json_data1.getString("Role").toLowerCase();

                            //    Toast.makeText(Users.this, m, Toast.LENGTH_SHORT).show();
                            if (m.equalsIgnoreCase("006")) {
                                fishData.email = json_data1.getString("email");
                                // fishData.Id=json_data.getString("Id");
                                fishData.phone = json_data1.getString("phone");
                                fishData.prefferredName = json_data1.getString("prefferredName");
                                fishData.Role = json_data.getString("Role");
                                fishData.username = json_data1.getString("username");
                                spinnerdata.add(fishData);
                            }


                            // Setup and Handover data to recyclerview
                            mRVFishPrice = (RecyclerView) findViewById(R.id.fishPriceList);
                            mAdapter = new AdapterFish(Users.this, spinnerdata);
                            //   Log.e("data",data.toString());
                            mRVFishPrice.setAdapter(mAdapter);
                            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Users.this));
                            //  addTextListener();
                            //  onitemselect();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Users.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }


            }





            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public class DataFish{

        public String dob;
        public String email;
        public String gender;
        public String phone;
        public String prefferredName;
        public String Role;
        public String username;

    }

    public class AdapterFish extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;
        private LayoutInflater inflater;
        List<DataFish> data= Collections.emptyList();
        DataFish current;
        int currentPos=0;

        // create constructor to innitilize context and data sent from MainActivity
        public AdapterFish(Context context, List<DataFish> data){
            this.context=context;
            inflater= LayoutInflater.from(context);
            this.data=data;
        }

        // Inflate the layout when viewholder created


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.container_fish, parent,false);
            MyHolder holder=new MyHolder(view);
            return holder;
        }

        // Bind data
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            // Get current position of item in recyclerview to bind data and assign values from list
            MyHolder myHolder= (MyHolder) holder;
            DataFish current=data.get(position);

            myHolder.one.setText("Name: " +current.prefferredName);
            myHolder.two.setText("Username: " +current.username);
            // myHolder.two.setVisibility(View.GONE);
            myHolder.three.setText("Gender: " +current.gender);

            myHolder.four.setText("Email: " +current.email +"Phone:  "+current.phone);
            myHolder.five.setVisibility(View.GONE);
            myHolder.six.setVisibility(View.GONE);
            myHolder.seven.setVisibility(View.GONE);
            myHolder.eight.setVisibility(View.GONE);
            myHolder.nine.setVisibility(View.GONE);
            myHolder.ten.setVisibility(View.GONE);
           // myHolder.imageView.setVisibility(View.GONE);

            //  myHolder.textPrice.setText("Rs. " + current.Title + "\\Kg");
            // myHolder.four.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

            // load image into imageview using glide
//            Glide.with(context).load("http://192.168.1.7/test/images/" + current.fishImage)
//                    .placeholder(R.drawable.ic_img_error)
//                    .error(R.drawable.ic_img_error)
//                    .into(myHolder.ivFish);

        }

        // return total item from List
        @Override
        public int getItemCount() {
            return data.size();
        }


        class MyHolder extends RecyclerView.ViewHolder{

            TextView one;
            TextView two;
            TextView three;
            TextView four;
            TextView five;
            TextView six;
            TextView seven;
            TextView eight;
            TextView nine;
            ImageView imageView;
            TextView ten;

            // create constructor to get widget reference
            public MyHolder(View itemView) {
                super(itemView);
                one= (TextView) itemView.findViewById(R.id.one);
                imageView =(ImageView)findViewById(R.id.img);
              //  imageView.setVisibility(View.GONE);

                two = (TextView) itemView.findViewById(R.id.two);
                three = (TextView) itemView.findViewById(R.id.three);
                four = (TextView) itemView.findViewById(R.id.four);
                five= (TextView) itemView.findViewById(R.id.five);

                six = (TextView) itemView.findViewById(R.id.six);
                seven = (TextView) itemView.findViewById(R.id.seven);
                eight = (TextView) itemView.findViewById(R.id.eight);
                nine = (TextView)itemView.findViewById(R.id.nine);
                ten = (TextView)itemView.findViewById(R.id.ten);
            }

        }

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
}
