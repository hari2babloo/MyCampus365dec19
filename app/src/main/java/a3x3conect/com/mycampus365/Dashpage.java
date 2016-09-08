package a3x3conect.com.mycampus365;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashpage extends AppCompatActivity {


    Button atendance,planner,teacher,library,users,courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashpage);
        atendance = (Button)findViewById(R.id.atendance);
        planner = (Button)findViewById(R.id.planner);
        teacher = (Button)findViewById(R.id.teacher);
        library = (Button)findViewById(R.id.library);
        users = (Button)findViewById(R.id.users);
        courses = (Button)findViewById(R.id.courses);

        atendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashpage.this,Attendance.class);
                startActivity(intent);

            }
        });

        planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashpage.this,Planner.class);
                startActivity(intent);

            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashpage.this,Teacher.class);
                startActivity(intent);

            }
        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashpage.this,Librar.class);
                startActivity(intent);

            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashpage.this,Users.class);
                startActivity(intent);

            }
        });

        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashpage.this,Courses.class);
                startActivity(intent);

            }
        });




    }
}
