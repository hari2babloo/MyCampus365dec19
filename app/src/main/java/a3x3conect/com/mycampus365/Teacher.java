package a3x3conect.com.mycampus365;

import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Teacher extends AppCompatActivity {
    private RecyclerView mRVFishPrice;
    EditText search;
    String testval;
    JSONArray jArray;
    JSONObject json_data;

    List<DataFish> filterdata=new ArrayList<>();

    private AdapterFish mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher);
        search = (EditText) findViewById( R.id.search);

        new JsonAsync().execute("http://13.76.249.51:8080/school/webservices/teacher.php");

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

            //this method will be running on UI thread

            Log.e("result",result);

            //  pdLoading.dismiss();
            List<DataFish> data=new ArrayList<>();

            //  pdLoading.dismiss();
            try {

                jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    //   JSONArray jsonObject = sys.getJSONArray(i);
                    json_data = jArray.getJSONObject(i);
                   // Log.e("json",json_data.toString());
                    DataFish fishData = new DataFish();
                    fishData.class1 = json_data.getString("class");
//                    Log.e("prefname",fishData.course);
                    fishData.course=json_data.getString("course");
                    // fishData.Id=json_data.getString("Id");
                    fishData.preferredName=json_data.getString("preferredName");
                    fishData.surname= json_data.getString("surname");

                    data.add(fishData);

                }

                // Setup and Handover data to recyclerview
                mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                mAdapter = new AdapterFish(Teacher.this, data);
             //   Log.e("data",data.toString());
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(Teacher.this));
                 addTextListener();

            } catch (JSONException e) {
                Toast.makeText(Teacher.this, e.toString(), Toast.LENGTH_LONG).show();
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
                    query = query.toString().toLowerCase();
                    mAdapter.notifyDataSetChanged();

                    filterdata.clear();

                    for(int i=0;i<jArray.length();i++) {

                        try {

                            json_data = jArray.getJSONObject(i);
                            String s  = json_data.getString("class").toLowerCase();
                            String d = json_data.getString("course").toLowerCase();
                            DataFish fishData = new DataFish();
                            if (s.contains(query)||d.contains(query)){
                                fishData.class1 = json_data.getString("class");
                                fishData.course = json_data.getString("course");
                                fishData.preferredName = json_data.getString("preferredName");
                                fishData.surname = json_data.getString("surname");
                                filterdata.add(fishData);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // fishData.Id=json_data.getString("Id");

                    }
                    // Setup and Handover data to recyclerview
                    mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                    mAdapter = new AdapterFish(Teacher.this, filterdata);

                    mRVFishPrice.setAdapter(mAdapter);
                    mRVFishPrice.setLayoutManager(new LinearLayoutManager(Teacher.this));
                    mAdapter.notifyDataSetChanged();

                }






                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }
    public class DataFish {

        public String class1;
        public String course;
        public String preferredName;
        public String surname;

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

            myHolder.one.setText("Class: " +current.class1);
            myHolder.two.setText("Course: " +current.course);
            // myHolder.two.setVisibility(View.GONE);
            myHolder.three.setText("Date: " +current.preferredName);
            myHolder.three.setVisibility(View.GONE);
            myHolder.four.setText("Name: " +current.preferredName +"Time End"+current.surname);
            myHolder.five.setVisibility(View.GONE);
            myHolder.six.setVisibility(View.GONE);
            myHolder.seven.setVisibility(View.GONE);
            myHolder.eight.setVisibility(View.GONE);
            myHolder.nine.setVisibility(View.GONE);
            myHolder.ten.setVisibility(View.GONE);
//            myHolder.imageView.setVisibility(View.INVISIBLE);

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
}
