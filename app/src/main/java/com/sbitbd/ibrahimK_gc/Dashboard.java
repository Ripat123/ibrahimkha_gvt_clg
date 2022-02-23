package com.sbitbd.ibrahimK_gc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.sbitbd.ibrahimK_gc.Config.config;
import com.sbitbd.ibrahimK_gc.Config.database;
import com.sbitbd.ibrahimK_gc.Model.class_model;
import com.sbitbd.ibrahimK_gc.Model.four_model;
import com.sbitbd.ibrahimK_gc.Model.user_model;
import com.sbitbd.ibrahimK_gc.attend_form.attend;
import com.sbitbd.ibrahimK_gc.settings.settings;
import com.sbitbd.ibrahimK_gc.ui.update_attendance.update_attendance;
import com.sbitbd.ibrahimK_gc.website.website;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MenuItem web,online,teacher,teacher_gap;
    private config config = new config();
    private List<class_model> class_list = new ArrayList<>();
    private List<String> class_name = new ArrayList<>();
    private List<four_model> section_list = new ArrayList<>();
    private List<String> section_name = new ArrayList<>();
    private List<four_model> period_list = new ArrayList<>();
    private List<String> period_name = new ArrayList<>();
    private List<String> semester_l = new ArrayList<>();
    private List<four_model> semester_list = new ArrayList<>();
    private String class_id, section_id, period_id,semester_t;
    private dashboard_controller dashboard_controller = new dashboard_controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(Dashboard.this).inflate(R.layout.textfield4, null);
                AutoCompleteTextView class_s = view1.findViewById(R.id.class_s);
                AutoCompleteTextView section = view1.findViewById(R.id.section_s);
                AutoCompleteTextView period = view1.findViewById(R.id.period_s);
                AutoCompleteTextView semester = view1.findViewById(R.id.semester);

                get_class(Dashboard.this);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Dashboard.this,
                        R.layout.item_name, class_name);
                class_s.setAdapter(dataAdapter);
                class_s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        class_model class_model = class_list.get(position);
                        class_id = class_model.getId();
                        get_semester(Dashboard.this);
                        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(Dashboard.this,
                                R.layout.item_name,semester_l);
                        semester.setAdapter(dataAdapter6);
                        semester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                semester_t = semester_list.get(position).getOne();
                                get_section(Dashboard.this, semester_t);
                                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(Dashboard.this,
                                        R.layout.item_name, section_name);
                                section.setAdapter(dataAdapter1);
                                section.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        four_model four_model = section_list.get(position);
                                        section_id = four_model.getOne();
                                    }
                                });
                                get_period(Dashboard.this, class_id);
                                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Dashboard.this,
                                        R.layout.item_name, period_name);
                                period.setAdapter(dataAdapter2);
                                period.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        four_model four_model1 = period_list.get(position);
                                        period_id = four_model1.getOne();
                                    }
                                });
                            }
                        });


                    }
                });




                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(Dashboard.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Selection Required");
                dialogBuilder.setView(view1);
                dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {

                    dialog.cancel();
                });
                dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                    if (class_s.getText().toString().equals("")) {
                        Toast.makeText(Dashboard.this, "Please Select a Class", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (section.getText().toString().equals("")) {
                        Toast.makeText(Dashboard.this, "Please Select a Section", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (period.getText().toString().equals("")) {
                        Toast.makeText(Dashboard.this, "Please Select a Period", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (semester.getText().toString().equals("")) {
                        Toast.makeText(Dashboard.this, "Please Select a Group", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    initAttend(view);
                });
                dialogBuilder.show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_all_student, R.id.nav_website,
                R.id.nav_offline,R.id.nav_online,R.id.nav_teacher_gap,R.id.nav_teacher)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        web = navigationView.getMenu().findItem(R.id.nav_website);
        online = navigationView.getMenu().findItem(R.id.nav_online);
        teacher = navigationView.getMenu().findItem(R.id.nav_teacher);
        teacher_gap = navigationView.getMenu().findItem(R.id.nav_teacher_gap);
        web.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(Dashboard.this, website.class));
                return true;
            }
        });
        online.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(Dashboard.this, update_attendance.class));
                return true;
            }
        });
        user_model user_model = config.User_info(Dashboard.this);
        if (user_model.getStatus() == null || user_model.getStatus().equals("0") || user_model.getStatus().equals("")){
            teacher.setVisible(false);
            teacher_gap.setVisible(false);
        }

//        check_offline_data_upload();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(Dashboard.this, settings.class));
                return true;
            }
        });
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(Dashboard.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Confirmation");
                dialogBuilder.setMessage("Are you Sure you want to Log Out?");

                dialogBuilder.setNegativeButton("No", (dialog, which) -> {

                    dialog.cancel();
                });
                dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                    config.deleteuser(Dashboard.this);
                    startActivity(new Intent(Dashboard.this, MainActivity.class));
                    finish();
                });
                dialogBuilder.show();
                return true;
            }
        });
        menu.getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(Dashboard.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Confirmation");
                dialogBuilder.setMessage("Are you Sure you want to exit?");

                dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {

                    dialog.cancel();
                });
                dialogBuilder.setPositiveButton("exit", (dialog, which) -> {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                });
                dialogBuilder.show();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void get_class(Context context) {
        database sqliteDB = new database(context);
        class_model class_model;
        try {
            class_list.clear();
            class_name.clear();
            Cursor cursor = sqliteDB.getUerData("SELECT * FROM class where id in (select class_id " +
                    "  from teacher_priority where teacher_id = '"+config.User_info(context).getId()+"')");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    class_model = new class_model(cursor.getString(cursor.getColumnIndexOrThrow("id"))
                            , cursor.getString(cursor.getColumnIndexOrThrow("class_name")));
                    class_list.add(class_model);
                    class_name.add(cursor.getString(cursor.getColumnIndexOrThrow("class_name")));
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                sqliteDB.close();
            } catch (Exception e) {
            }
        }
    }

    public void get_section(Context context, String id) {
        database sqliteDB = new database(context);
        four_model class_model;
        try {
            section_list.clear();
            section_name.clear();
            Cursor cursor = sqliteDB.getUerData("SELECT * FROM section where class_id = '" + class_id + "' " +
                    "and group_id = '"+id+"' and id in (select section_id from teacher_priority where " +
                    "teacher_id = '"+config.User_info(context).getId()+"' and class_id = '"+class_id+"' and group_id = '"+id+"')");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    class_model = new four_model(cursor.getString(cursor.getColumnIndexOrThrow("id"))
                            , cursor.getString(cursor.getColumnIndexOrThrow("section_name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("class_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("group_id")));
                    section_list.add(class_model);
                    section_name.add(cursor.getString(cursor.getColumnIndexOrThrow("section_name")));
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                sqliteDB.close();
            } catch (Exception e) {
            }
        }
    }

    public void get_period(Context context, String id) {
        database sqliteDB = new database(context);
        four_model class_model;
        try {
            period_list.clear();
            period_name.clear();

            Cursor cursor = sqliteDB.getUerData("SELECT * FROM period where class_id = '"+id+"'" +
                    " and subject_name = '"+semester_t+"' and id in (select subject_name from teacher_priority " +
                    "where teacher_id = '"+config.User_info(context).getId()+"' and class_id = '"+class_id+"' and group_id = '"+semester_t+"')");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    class_model = new four_model(cursor.getString(cursor.getColumnIndexOrThrow("id"))
                            , cursor.getString(cursor.getColumnIndexOrThrow("class_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("period_name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("subject_name")));
                    period_list.add(class_model);
                    period_name.add(cursor.getString(cursor.getColumnIndexOrThrow("period_name")));
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                sqliteDB.close();
            } catch (Exception e) {
            }
        }
    }

    public void get_semester(Context context) {
        database sqliteDB = new database(context);
        try {
            semester_l.clear();
            semester_list.clear();
            Cursor cursor = sqliteDB.getUerData("SELECT * FROM all_group where class_id = '"+class_id+"' and " +
                    "id in (select group_id from teacher_priority where class_id = '"+class_id+"' and " +
                    "teacher_id = '"+config.User_info(context).getId()+"')");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    semester_list.add(new four_model(cursor.getString(cursor.getColumnIndexOrThrow("id"))
                            , cursor.getString(cursor.getColumnIndexOrThrow("class_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("group_name")),
                            ""));
                    semester_l.add(cursor.getString(cursor.getColumnIndexOrThrow("group_name")));
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                sqliteDB.close();
            } catch (Exception e) {
            }
        }
    }

    private void initAttend(View v){
        database sqliteDB = new database(Dashboard.this);
        try {
            Cursor cursor = sqliteDB.getUerData("SELECT * FROM attendance where attend_date=" +
                    "'"+config.attend_date()+"' and class_id='"+class_id+"' and section_id='"+section_id+"'" +
                    " and period_id='"+period_id+"' and group_id = '"+semester_t+"'");
            if (cursor.getCount() > 0) {
                config.regularSnak(v,"Attendance already taken!");
            }
            else {
                Intent intent = new Intent(Dashboard.this, attend.class);
                intent.putExtra("class_id", class_id);
                intent.putExtra("section_id", section_id);
                intent.putExtra("period_id", period_id);
                intent.putExtra("group_id", semester_t);
                startActivity(intent);
            }
        } catch (Exception e) {
        } finally {
            try {
                sqliteDB.close();
            } catch (Exception e) {
            }
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (!config.checkdate(Dashboard.this)) {
//            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(Dashboard.this, R.style.RoundShapeTheme);
//            dialogBuilder.setMessage("To take attendance and update data, you first need to go to the settings activity and download all the data.\n" +
//                    "Do you want to go to the settings activity?");
//            dialogBuilder.setIcon(R.drawable.ic_check);
//            dialogBuilder.setCancelable(false);
//            dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
//                startActivity(new Intent(Dashboard.this, settings.class));
//                dialog.cancel();
//            });
//            dialogBuilder.setNegativeButton("cancel", (dialogInterface, i) -> {
//                dialogInterface.cancel();
//            });
//            dialogBuilder.show();
//            config.updatedate(Dashboard.this,config.attend_date());
//        }
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        check_offline_data_upload();
//    }




}