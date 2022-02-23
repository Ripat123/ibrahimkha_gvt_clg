package com.sbitbd.ibrahimK_gc.settings;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sbitbd.ibrahimK_gc.Config.config;
import com.sbitbd.ibrahimK_gc.Config.database;
import com.sbitbd.ibrahimK_gc.Dashboard;
import com.sbitbd.ibrahimK_gc.R;
import com.sbitbd.ibrahimK_gc.download.download;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class settings extends AppCompatActivity {

    private ImageView settings_back;
    private MaterialCardView student, teacher, class_card, section, period, sub_card, priority_card,
            group_card;
    private config config = new config();
    private AllTask allTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initview();
    }

    private void initview() {
        settings_back = findViewById(R.id.settings_back);
        student = findViewById(R.id.std_card);
        teacher = findViewById(R.id.teacherD_card);
        class_card = findViewById(R.id.class_card);
        section = findViewById(R.id.sectionD_card);
        period = findViewById(R.id.period_card);
        sub_card = findViewById(R.id.sub_card);
        priority_card = findViewById(R.id.priority_card);
        group_card = findViewById(R.id.group_card);

        settings_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Student");
                    dialogBuilder.setMessage("Are you sure, you want to download student again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("1", "select student_personal_info.id,student_name,father_name,mother_name," +
                                " class_id,class_roll,group_id,section_id,student_guardian_information.guardian_contact as 'contact_no' " +
                                " from student_personal_info inner join running_student_info on " +
                                " student_personal_info.id = running_student_info.student_id inner join " +
                                " student_guardian_information on student_personal_info.id = " +
                                " student_guardian_information.id  ORDER BY class_roll ASC", config.STUDENT_URL);
                        //where class_id = '311611180001'
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(v, "No Internet Connection!");
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Teacher");
                    dialogBuilder.setMessage("Are you sure, you want to download teacher again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("2", "SELECT teachers_id AS 'one',teachers_name AS 'two',mobile_no AS 'three' " +
                                " from teachers_information WHERE Type = 'Teacher' order by index_no ASC", config.FOUR_DIMENSION);
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(v, "No Internet Connection!");
            }
        });
        class_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Class");
                    dialogBuilder.setMessage("Are you sure, you want to download class again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("3", "SELECT id as 'one',class_name as 'two' FROM " +
                                " `add_class` ORDER BY `index` ASC LIMIT 4", config.TWO_DIMENSION);
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(v, "No Internet Connection!");
            }
        });
        section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Section");
                    dialogBuilder.setMessage("Are you sure, you want to download section again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("4", "SELECT id AS 'one',class_id AS 'two',group_id AS 'three'," +
                                " section_name AS 'four' from add_section ORDER BY id ASC", config.FOUR_DIMENSION);
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(v, "No Internet Connection!");
            }
        });
        period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Period");
                    dialogBuilder.setMessage("Are you sure, you want to download period again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("5", "SELECT id AS 'one',class_id AS 'two',subject_name AS 'three'," +
                                " group_id AS 'four' from add_subject_info ORDER BY id ASC", config.FOUR_DIMENSION);
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(v, "No Internet Connection!");
            }
        });

        sub_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Registered Subject");
                    dialogBuilder.setMessage("Are you sure, you want to download registered subject again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("6", "select std_id as 'one', class_id as 'two',group_id " +
                                "as 'three',subject_id as 'four' from subject_registration_table where std_id IN " +
                                "(SELECT running_student_info.student_id from running_student_info) ", config.FOUR_DIMENSION);
                        //and class_id = '311611180001'
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(v, "No Internet Connection!");
            }
        });

        priority_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Subject Priority");
                    dialogBuilder.setMessage("Are you sure, you want to download Subject Priority again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("7", "select user as 'one', class as 'two',`group` " +
                                "as 'three',section as 'four',subjectName as 'five' from subject_priority ", config.FIVE_DIMENSION);
                        //where class ='311611180001'
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(v, "No Internet Connection!");
            }
        });

        group_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (config.isOnline(settings.this)) {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                    dialogBuilder.setTitle("Download Group");
                    dialogBuilder.setMessage("Are you sure, you want to download Group again?");
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    });
                    dialogBuilder.setPositiveButton("yes", (dialog, which) -> {
                        allTask = new AllTask();
                        allTask.execute("8", "select id as 'one', group_name as 'two',class_id as 'three' from " +
                                "add_group", config.FOUR_DIMENSION);
                    });
                    dialogBuilder.show();
                } else
                    config.regularSnak(view, "No Internet Connection!");
            }
        });
    }


    private class AllTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, strings[2],
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                switch (strings[0]) {
                                    case "1":
                                        store_student(response.trim());
                                        break;
                                    case "2":
                                        store_teacher(response.trim());
                                        break;
                                    case "3":
                                        store_class(response.trim());
                                        break;
                                    case "4":
                                        store_section(response.trim());
                                        break;
                                    case "5":
                                        store_period(response.trim());
                                        break;
                                    case "6":
                                        store_subject_reg(response.trim());
                                        break;
                                    case "7":
                                        store_teacher_priority(response.trim());
                                        break;
                                    default:
                                        store_group(response.trim());

                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                        dialogBuilder.setTitle("Internet Error!");
                        dialogBuilder.setMessage(error.toString());
                        dialogBuilder.setCancelable(false);
                        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.cancel();
                            cancel(true);
                        });
                        dialogBuilder.show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(config.QUERY, strings[1]);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(settings.this);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(settings.this, "", "Downloading...", false, false);
        }

        private void store_student(String response) {
            database sqlite_db = new database(settings.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String id = "";
                    String name = "";
                    String phone = "";
                    String father_name = "";
                    String mother_name = "";
                    String class_id = "";
                    String section_id = "";
                    String group_id = "";
                    String roll = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray(config.RESULT);
                        if (result.length() > 0)
                            config.all_delete(settings.this, "student");
                        for (int i = 0; i <= result.length(); i++) {
                            try {
                                JSONObject collegeData = result.getJSONObject(i);
                                phone = collegeData.getString(config.PHONE);
                                id = collegeData.getString(config.ID);
                                name = collegeData.getString(config.STUDENT_NAME);
                                father_name = collegeData.getString(config.FATHER);
                                mother_name = collegeData.getString(config.MOTHER);
                                class_id = collegeData.getString(config.CLASS_ID);
                                section_id = collegeData.getString(config.SECTION_ID);
                                group_id = collegeData.getString(config.GROUP_ID);
                                roll = collegeData.getString(config.CLASS_ROLL);
//                                config.add_student(settings.this, id, name, phone, class_id, section_id, group_id,
//                                        roll, father_name, mother_name);

                                try {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("id", id);
                                    contentValues.put("student_name", name);
                                    contentValues.put("phone", phone);
                                    contentValues.put("roll", roll);
                                    contentValues.put("class_id", class_id);
                                    contentValues.put("section_id", section_id);
                                    contentValues.put("group_id", group_id);
                                    contentValues.put("father_name", father_name);
                                    contentValues.put("mother_name", mother_name);
                                    boolean ch = sqlite_db.DataOperation(contentValues, "insert", "student", null);
                                    if (!ch) {
                                        Toast.makeText(settings.this, "Failed to add student", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                }

                            } catch (Exception e) {
                            }
                        }
                        progressDialog.dismiss();
                        Toast.makeText(settings.this, "Downloaded", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    } finally {
                        try {
                            sqlite_db.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }).start();
        }

        private void store_group(String response) {
            String id = "";
            String name = "";
            String class_id = "";
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.RESULT);
                if (result.length() > 0)
                    config.all_delete(settings.this, "all_group");
                for (int i = 0; i <= result.length(); i++) {
                    try {
                        JSONObject collegeData = result.getJSONObject(i);
                        id = collegeData.getString(config.ONE);
                        name = collegeData.getString(config.TWO);
                        class_id = collegeData.getString(config.THREE);
                        config.add_group(settings.this, id, name, class_id);
                    } catch (Exception e) {
                    }
                }
                progressDialog.dismiss();
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Download Successful");
                dialogBuilder.setIcon(R.drawable.ic_check);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
                    dialog.cancel();
                });
                dialogBuilder.show();
            } catch (Exception e) {
            }
        }

        private void store_section(String response) {
            String id = "";
            String name = "";
            String class_id = "";
            String group_id = "";
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.RESULT);
                if (result.length() > 0)
                    config.all_delete(settings.this, "section");
                for (int i = 0; i <= result.length(); i++) {
                    try {
                        JSONObject collegeData = result.getJSONObject(i);
                        name = collegeData.getString(config.FOUR);
                        id = collegeData.getString(config.ONE);
                        class_id = collegeData.getString(config.TWO);
                        group_id = collegeData.getString(config.THREE);
                        config.add_section(settings.this, id, name, class_id, group_id);
                    } catch (Exception e) {
                    }
                }
                progressDialog.dismiss();
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Download Successful");
                dialogBuilder.setIcon(R.drawable.ic_check);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
                    dialog.cancel();
                });
                dialogBuilder.show();
            } catch (Exception e) {
            }
        }

        private void store_period(String response) {
            String id = "";
            String name = "";
            String class_id = "";
            String subject_name = "";
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.RESULT);
                if (result.length() > 0)
                    config.all_delete(settings.this, "period");
                for (int i = 0; i <= result.length(); i++) {
                    try {
                        JSONObject collegeData = result.getJSONObject(i);
                        name = collegeData.getString(config.THREE);
                        id = collegeData.getString(config.ONE);
                        class_id = collegeData.getString(config.TWO);
                        subject_name = collegeData.getString(config.FOUR);
                        config.add_period(settings.this, id, name, class_id, subject_name);
                    } catch (Exception e) {
                    }
                }
                progressDialog.dismiss();
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Download Successful");
                dialogBuilder.setIcon(R.drawable.ic_check);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
                    dialog.cancel();
                });
                dialogBuilder.show();
            } catch (Exception e) {
            }
        }

        private void store_class(String response) {
            String id = "";
            String name = "";
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.RESULT);
                if (result.length() > 0)
                    config.all_delete(settings.this, "class");
                for (int i = 0; i <= result.length(); i++) {
                    try {
                        JSONObject collegeData = result.getJSONObject(i);
                        name = collegeData.getString(config.TWO);
                        id = collegeData.getString(config.ONE);
                        config.add_class(settings.this, id, name);
                    } catch (Exception e) {
                    }
                }
                progressDialog.dismiss();
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Download Successful");
                dialogBuilder.setIcon(R.drawable.ic_check);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
                    dialog.cancel();
                });
                dialogBuilder.show();
            } catch (Exception e) {
            }
        }

        private void store_teacher(String response) {
            String id = "";
            String name = "";
            String phone = "";
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.RESULT);
                if (result.length() > 0)
                    config.all_delete(settings.this, "teacher");
                for (int i = 0; i <= result.length(); i++) {
                    try {
                        JSONObject collegeData = result.getJSONObject(i);
                        phone = collegeData.getString(config.THREE);
                        id = collegeData.getString(config.ONE);
                        name = collegeData.getString(config.TWO);
                        config.add_teacher(settings.this, id, name, phone);
                    } catch (Exception e) {
                    }
                }
                progressDialog.dismiss();
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                dialogBuilder.setTitle("Download Successful");
                dialogBuilder.setIcon(R.drawable.ic_check);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
                    dialog.cancel();
                });
                dialogBuilder.show();
            } catch (Exception e) {
            }
        }

//        private void store_subject_reg(String response) {
//            String student_id = "";
//            String class_id = "";
//            String group_id = "";
//            String subject_id = "";
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                JSONArray result = jsonObject.getJSONArray(config.RESULT);
//                if (result.length() > 0)
//                    config.all_delete(settings.this, "subject_registration");
//                for (int i = 0; i <= result.length(); i++) {
//                    try {
//                        JSONObject collegeData = result.getJSONObject(i);
//                        group_id = collegeData.getString(config.THREE);
//                        student_id = collegeData.getString(config.ONE);
//                        class_id = collegeData.getString(config.TWO);
//                        subject_id = collegeData.getString(config.FOUR);
//                        config.add_sub_reg(settings.this, student_id, group_id, class_id, subject_id);
//                    } catch (Exception e) {
//                    }
//                }
//                progressDialog.dismiss();
//                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this);
//                dialogBuilder.setTitle("Download Successful");
//                dialogBuilder.setIcon(R.drawable.ic_check);
//                dialogBuilder.setCancelable(false);
//                dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
//                    dialog.cancel();
//                });
//                dialogBuilder.show();
//            } catch (Exception e) {
//            }
//        }

        private void store_subject_reg(String response) {
            database sqlite_db = new database(settings.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String id = "";
                    String group = "";
                    String class_id = "";
                    String subject = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray(config.RESULT);
                        if (result.length() > 0)
                            config.all_delete(settings.this, "subject_registration");
                        for (int i = 0; i <= result.length(); i++) {
                            try {
                                JSONObject collegeData = result.getJSONObject(i);
                                id = collegeData.getString(config.ONE);
                                class_id = collegeData.getString(config.TWO);
                                group = collegeData.getString(config.THREE);
                                subject = collegeData.getString(config.FOUR);
//                                config.add_sub_reg(download.this, id, class_id,group,subject);

                                try {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("student_id", id);
                                    contentValues.put("class_id", class_id);
                                    contentValues.put("group_id", group);
                                    contentValues.put("subject_id", subject);
                                    boolean ch = sqlite_db.DataOperation(contentValues, "insert", "subject_registration", null);
                                    if (!ch) {
                                        Toast.makeText(settings.this, "Failed to add sub reg", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                }

                            } catch (Exception e) {
                            }
                        }
                        progressDialog.dismiss();
                        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                        dialogBuilder.setTitle("Download Successful");
                        dialogBuilder.setIcon(R.drawable.ic_check);
                        dialogBuilder.setCancelable(false);
                        dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
                            dialog.cancel();
                        });
                        dialogBuilder.show();
                    } catch (Exception e) {
                    } finally {
                        try {
                            sqlite_db.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }).start();

        }

        private void store_teacher_priority(String response) {
            database sqlite_db = new database(settings.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String user = "";
                    String group = "";
                    String class_id = "";
                    String section = "";
                    String subject = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray(config.RESULT);
                        if (result.length() > 0)
                            config.all_delete(settings.this, "teacher_priority");
                        for (int i = 0; i <= result.length(); i++) {
                            try {
                                JSONObject collegeData = result.getJSONObject(i);
                                user = collegeData.getString(config.ONE);
                                class_id = collegeData.getString(config.TWO);
                                group = collegeData.getString(config.THREE);
                                section = collegeData.getString(config.FOUR);
                                subject = collegeData.getString(config.FIVE);
//                                config.add_sub_reg(download.this, id, class_id,group,subject);

                                try {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("teacher_id", user);
                                    contentValues.put("class_id", class_id);
                                    contentValues.put("group_id", group);
                                    contentValues.put("section_id", section);
                                    contentValues.put("subject_name", subject);
                                    boolean ch = sqlite_db.DataOperation(contentValues, "insert", "teacher_priority", null);
                                    if (!ch) {
                                        Toast.makeText(settings.this, "Failed to add teacher priority", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                }

                            } catch (Exception e) {
                            }
                        }
                        progressDialog.dismiss();
                        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(settings.this,R.style.RoundShapeTheme);
                        dialogBuilder.setTitle("Download Successful");
                        dialogBuilder.setIcon(R.drawable.ic_check);
                        dialogBuilder.setCancelable(false);
                        dialogBuilder.setPositiveButton("ok", (dialog, which) -> {
                            dialog.cancel();
                        });
                        dialogBuilder.show();
                    } catch (Exception e) {
                    } finally {
                        try {
                            sqlite_db.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }).start();

        }
    }
}