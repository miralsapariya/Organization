package com.onlineeducationsystemorganization;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.EdittextAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionStatementTypeAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionTypeCheckboxAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionTypeMatrixImgOneAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionTypeMatrixImgSecondAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionTypeMatrixOneAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionTypeMatrixSecondAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionTypeRadioAdapter;
import com.onlineeducationsystemorganization.adapter.QuestionTypeSingleSortingAdapter;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.Exam;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.widget.ItemMoveCallback;
import com.onlineeducationsystemorganization.widget.ItemMoveCallback1;
import com.onlineeducationsystemorganization.widget.ItemMoveCallback2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class ExamActivity extends BaseActivity implements NetworkListener {

    private LinearLayout llContent;
    private ImageView imgPrev, imgNext;
    private View inflatedView;
    private RecyclerView rvRadio, rvCheckbox, rvSorting, rvQuestion, rvSortingMtrix;
    private QuestionTypeRadioAdapter questionTypeRadioAdapter;
    private QuestionTypeCheckboxAdapter questionTypeCheckboxAdapter;
    private QuestionTypeSingleSortingAdapter questionTypeSingleSortingAdapter;
    private QuestionTypeMatrixOneAdapter questionTypeMatrixOneAdapter;
    private QuestionTypeMatrixSecondAdapter questionTypeMatrixSecondAdapter;
    private QuestionTypeMatrixImgOneAdapter questionTypeMatrixImgOneAdapter;
    private QuestionTypeMatrixImgSecondAdapter questionTypeMatrixImgSecondAdapter;
    private int counter = 0;
    private String course_id = "";
    private Exam data;
    private ImageView imgBack;
    private TextView tvOrder, tvCourse, tvTestName;
    private RecyclerView rvEdittext;
    private  ArrayList<Exam.Option> listMatrix,listMatrixImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void typeRadio(ArrayList<Exam.Option> options,String question) {
        inflatedView = View.inflate(this, R.layout.question_type_radio, null);
        llContent.addView(inflatedView);
        rvRadio = inflatedView.findViewById(R.id.rvRadio);

        TextView tvQuestion=inflatedView.findViewById(R.id.tvQuestion);

        tvQuestion.setText(question);


        int mSelectedItem =-1;
        for(int i=0;i<options.size();i++)
        {
            if(options.get(i).getSelected().equalsIgnoreCase("true"))
            {
                mSelectedItem=i;
                options.get(i).setSelected(true);
            }

        }

        questionTypeRadioAdapter = new QuestionTypeRadioAdapter(ExamActivity.this, options,mSelectedItem);
        rvRadio.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvRadio.setLayoutManager(manager);
        rvRadio.setAdapter(questionTypeRadioAdapter);
    }

    private void typeCheckbox(ArrayList<Exam.Option> list,String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_chechkbox, null);
        llContent.addView(inflatedView);
        rvCheckbox = inflatedView.findViewById(R.id.rvCheckbox);
        TextView tvQuestion=inflatedView.findViewById(R.id.tvQuestion);

        tvQuestion.setText(question);

        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getSelected().equalsIgnoreCase("true"))
            {
                list.get(i).setSelected(true);
            }

        }
        questionTypeCheckboxAdapter = new QuestionTypeCheckboxAdapter(ExamActivity.this, list);
        rvCheckbox.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvCheckbox.setLayoutManager(manager);
        rvCheckbox.setAdapter(questionTypeCheckboxAdapter);
    }

    private void typeFillInTheBlank(ArrayList<Exam.Option> list, String question,String paragraph) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_fill_in_the_blank, null);
        llContent.addView(inflatedView);
        rvEdittext = inflatedView.findViewById(R.id.rvEdittext);

        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question);
        TextView tvFillInTheBlank = inflatedView.findViewById(R.id.tvFillInTheBlank);

        /*StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getOption());
            sb.append("________");
        }
        String str = sb.toString();*/

        paragraph =paragraph.replaceAll("\\#Blank#", "________");
                tvFillInTheBlank.setText(paragraph);
        EdittextAdapter edittextAdapter = new EdittextAdapter(ExamActivity.this, list);
        rvEdittext.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvEdittext.setLayoutManager(manager);
        rvEdittext.setAdapter(edittextAdapter);

    }

    private void typeSingleSorting(ArrayList<Exam.Option> list, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_single_shorting, null);
        llContent.addView(inflatedView);

        rvSorting = inflatedView.findViewById(R.id.rvSorting);
        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question);
        questionTypeSingleSortingAdapter = new QuestionTypeSingleSortingAdapter(list);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(questionTypeSingleSortingAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSorting);
        rvSorting.setAdapter(questionTypeSingleSortingAdapter);
    }

    private void typeImgMatrix(ArrayList<Exam.Option> list,ArrayList<Exam.Option> listMatrix ,String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_matrix_shorting, null);
        llContent.addView(inflatedView);
        rvQuestion = inflatedView.findViewById(R.id.rvQuestion);
        TextView tvQuestion=inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question);


        questionTypeMatrixImgOneAdapter = new QuestionTypeMatrixImgOneAdapter(ExamActivity.this, list);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(ExamActivity.this);
        rvQuestion.setLayoutManager(mLayoutManager);
        rvQuestion.setItemAnimator(new DefaultItemAnimator());
        rvQuestion.setAdapter(questionTypeMatrixImgOneAdapter);

        rvSortingMtrix = inflatedView.findViewById(R.id.rvSortingMtrix);
        questionTypeMatrixImgSecondAdapter = new QuestionTypeMatrixImgSecondAdapter(listMatrix);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback2(questionTypeMatrixImgSecondAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSortingMtrix);
        rvSortingMtrix.setAdapter(questionTypeMatrixImgSecondAdapter);
    }

    private void typeMatrix(ArrayList<Exam.Option> list,ArrayList<Exam.Option> shortList, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_matrix_shorting, null);
        llContent.addView(inflatedView);

        rvQuestion = inflatedView.findViewById(R.id.rvQuestion);
        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question);

        questionTypeMatrixOneAdapter = new QuestionTypeMatrixOneAdapter(ExamActivity.this, list);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(ExamActivity.this);
        rvQuestion.setLayoutManager(mLayoutManager);
        rvQuestion.setItemAnimator(new DefaultItemAnimator());
        rvQuestion.setAdapter(questionTypeMatrixOneAdapter);

        rvSortingMtrix = inflatedView.findViewById(R.id.rvSortingMtrix);
        questionTypeMatrixSecondAdapter = new QuestionTypeMatrixSecondAdapter(shortList);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback1(questionTypeMatrixSecondAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvSortingMtrix);
        rvSortingMtrix.setAdapter(questionTypeMatrixSecondAdapter);
    }

    private void typeTrueFalse(ArrayList<Exam.Option> list, String question) {
        inflatedView = View.inflate(ExamActivity.this, R.layout.question_type_true_false, null);
        llContent.addView(inflatedView);
        rvRadio = inflatedView.findViewById(R.id.rvRadio);
        TextView tvQuestion = inflatedView.findViewById(R.id.tvQuestion);
        tvQuestion.setText(question + "");
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getSelected().equalsIgnoreCase("true"))
            {
                list.get(i).setSelected(true);
            }else if(list.get(i).getSelected().equalsIgnoreCase("false"))
            {
                list.get(i).setSelected(false);
            }

        }

        QuestionStatementTypeAdapter questionTypeRadioAdapter = new QuestionStatementTypeAdapter(ExamActivity.this, list);
        rvRadio.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        rvRadio.setLayoutManager(manager);
        rvRadio.setAdapter(questionTypeRadioAdapter);

    }

    private void initUI() {
        course_id = getIntent().getExtras().getString("course_id");
        llContent = findViewById(R.id.llContent);


        imgPrev = findViewById(R.id.imgPrev);
        imgNext = findViewById(R.id.imgNext);
        tvOrder = findViewById(R.id.tvOrder);
        imgBack = findViewById(R.id.imgBack);
        tvTestName = findViewById(R.id.tvTestName);
        tvCourse = findViewById(R.id.tvCourse);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (AppUtils.isInternetAvailable(ExamActivity.this)) {
            callQuizStart();
        }else {
            AppUtils.showAlertDialog(ExamActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
        }

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPrevious("previous");
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPrevious("next");
            }
        });
    }

    private void nextPrevious(String from) {

        JSONArray jsonArray = new JSONArray();

        if (data.getData().get(0).getQueType() == AppConstant.SINGLE_CHOICE) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    if (data.getData().get(0).getOptions().get(i).isSelected() == true) {
                        jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                        jsonArray.put(jobj);
                    }
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //
            if (from.equalsIgnoreCase("next")) {
                if (jsonArray.length() > 0) {
                    llContent.removeView(inflatedView);
                    callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() + 1);
                } else {
                    Toast.makeText(ExamActivity.this, getString(R.string.plz_fill_ans), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                llContent.removeView(inflatedView);
                callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() - 1);
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.MULTIPLE_CHOICE) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    if (data.getData().get(0).getOptions().get(i).isSelected() == true) {
                        jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                        jsonArray.put(jobj);
                    }
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //
            if (from.equalsIgnoreCase("next")) {
                if (jsonArray.length() > 0) {
                    llContent.removeView(inflatedView);
                    callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() + 1);
                } else {
                    Toast.makeText(ExamActivity.this, getString(R.string.plz_fill_ans), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                llContent.removeView(inflatedView);
                callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() - 1);
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.FILL_IN_THE_BLANK) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    View view1 = rvEdittext.getChildAt(i);
                    EditText editText = view1.findViewById(R.id.edittext);
                    String string = editText.getText().toString();
                   // String key = i + "";
                    if(!TextUtils.isEmpty(string)) {
                        JSONObject jobj = new JSONObject();
                        jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", string);
                        jsonArray.put(jobj);
                    }

                    Log.d("fill in  ARRAY :: ", jsonArray.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //
            if (from.equalsIgnoreCase("next")) {
                if (jsonArray.length() == data.getData().get(0).getOptions().size()) {
                    llContent.removeView(inflatedView);
                    callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() + 1);
                } else {
                    Toast.makeText(ExamActivity.this, getString(R.string.plz_fill_ans), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                llContent.removeView(inflatedView);
                callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() - 1);
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.SHORTING) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {
                    JSONObject jobj = new JSONObject();
                    jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getOption());
                    jsonArray.put(jobj);

                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
//
            if (from.equalsIgnoreCase("next")) {
                if (jsonArray.length() > 0) {
                    llContent.removeView(inflatedView);
                    callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() + 1);
                } else {
                    Toast.makeText(ExamActivity.this, getString(R.string.plz_fill_ans), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                llContent.removeView(inflatedView);
                callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() - 1);
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.STATEMENT) {
            try {
                for (int i = 0; i < data.getData().get(0).getOptions().size(); i++) {

                    if(!data.getData().get(0).getOptions().get(i).getSelected().equals("")) {
                        JSONObject jobj = new JSONObject();
                        jobj.put(data.getData().get(0).getOptions().get(i).getKey() + "", data.getData().get(0).getOptions().get(i).getSelected());
                        jsonArray.put(jobj);
                    }
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (from.equalsIgnoreCase("next")) {
                if (jsonArray.length() == data.getData().get(0).getOptions().size()) {
                    llContent.removeView(inflatedView);
                    callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() + 1);
                } else {
                    Toast.makeText(ExamActivity.this, getString(R.string.plz_fill_ans), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                llContent.removeView(inflatedView);
                callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() - 1);
            }
        } else if (data.getData().get(0).getQueType() == AppConstant.MATRIX) {

            try {
                if (data.getData().get(0).getSub_type() == 3) {
                    for (int i = 0; i < listMatrix.size(); i++) {
                        JSONObject jobj = new JSONObject();
                        jobj.put(listMatrix.get(i).getKey() + "", listMatrix.get(i).getOptionMatrix());
                        jsonArray.put(jobj);
                    }
                }else{

                    for (int i = 0; i < listMatrixImage.size(); i++) {
                        JSONObject jobj = new JSONObject();
                        jobj.put(listMatrixImage.get(i).getKey() + "", listMatrixImage.get(i).getOptionMatrix());
                        jsonArray.put(jobj);
                    }
                }
                Log.d("OPTION ARRAY :: ", jsonArray.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //
            if (from.equalsIgnoreCase("next")) {
                if (jsonArray.length() > 0) {
                    llContent.removeView(inflatedView);
                    callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() + 1);
                } else {
                    Toast.makeText(ExamActivity.this, getString(R.string.plz_fill_ans), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                llContent.removeView(inflatedView);
                callNextApi(jsonArray.toString(), data.getData().get(0).getOrder() - 1);
            }

        }


    }

    private void callNextApi(String jsonObject, int navigation) {

        String lang = "";
        AppUtils.showDialog(ExamActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("question_id", data.getData().get(0).getQueId() + "");
        params.put("order", data.getData().get(0).getOrder() + "");
        params.put("navigation", navigation + "");
        params.put("answer", jsonObject);

        if (AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        if(navigation !=0) {
            Call<Exam> call = apiInterface.continueQuiz(lang, AppSharedPreference.getInstance().
                    getString(ExamActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
            ApiCall.getInstance().hitService(ExamActivity.this, call, this, ServerConstents.EXAM);
        }
    }

    private void callQuizStart() {
        String lang = "";
        AppUtils.showDialog(ExamActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        if (AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(ExamActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<Exam> call = apiInterface.startQuizeApi(lang, AppSharedPreference.getInstance().
                getString(ExamActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(ExamActivity.this, call, this, ServerConstents.QUIZ_START);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.EXAM) {
            data = (Exam) response;
            Log.d("response Examp :: ", data.getStatus() + "");
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                if (data.getData().get(0).getQuiz_over().equalsIgnoreCase("no")) {
                    tvCourse.setText(data.getData().get(0).getCourseName());
                    tvTestName.setText("Test");
                    tvOrder.setText(data.getData().get(0).getOrder() + "");
                    if (data.getData().get(0).getQueType() == AppConstant.SINGLE_CHOICE) {
                        typeRadio(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    } else if (data.getData().get(0).getQueType() == AppConstant.MULTIPLE_CHOICE) {
                        typeCheckbox(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    } else if (data.getData().get(0).getQueType() == AppConstant.MATRIX) {
                        if (data.getData().get(0).getSub_type() == 3) {
                            listMatrix =new ArrayList<>();
                            for (int i=0;i<data.getData().get(0).getOptions().size();i++)
                            {
                                for(int j=0;j<data.getData().get(0).getOptions().size();j++)
                                {
                                    if(i== data.getData().get(0).getOptions().get(j).getOption_order())
                                    {
                                        listMatrix.add(data.getData().get(0).getOptions().get(j));
                                    }
                                }

                            }
                            typeMatrix(data.getData().get(0).getOptions(),listMatrix, data.getData().get(0).getQuestion());
                        } else {
                           listMatrixImage =new ArrayList<>();
                            for (int i=0;i<data.getData().get(0).getOptions().size();i++)
                            {
                                for(int j=0;j<data.getData().get(0).getOptions().size();j++)
                                {
                                    if(i== data.getData().get(0).getOptions().get(j).getOption_order())
                                    {
                                        listMatrixImage.add(data.getData().get(0).getOptions().get(j));
                                    }
                                }

                            }
                            typeImgMatrix(data.getData().get(0).getOptions(),listMatrixImage, data.getData().get(0).getQuestion());
                        }
                    } else if (data.getData().get(0).getQueType() == AppConstant.FILL_IN_THE_BLANK) {
                        typeFillInTheBlank(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion(),data.getData().get(0).getParagraph());
                    } else if (data.getData().get(0).getQueType() == AppConstant.SHORTING) {

                        ArrayList<Exam.Option> list =new ArrayList<>();
                        for (int i=0;i<data.getData().get(0).getOptions().size();i++)
                        {
                            for(int j=0;j<data.getData().get(0).getOptions().size();j++)
                            {
                                if(i== data.getData().get(0).getOptions().get(j).getOption_order())
                                {
                                    list.add(data.getData().get(0).getOptions().get(j));
                                }
                            }

                        }
                        for(int i=0;i<list.size();i++)
                        {
                            Log.d("===========> ", list.get(i).getOption_order()+" "+list.get(i).getOption());;
                        }
                        typeSingleSorting(list, data.getData().get(0).getQuestion());
                    } else if (data.getData().get(0).getQueType() == AppConstant.STATEMENT) {
                        typeTrueFalse(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                    }
                } else {
                    Intent intent =new Intent(ExamActivity.this,ReportActivity.class);
                    intent.putExtra("course_id", course_id);
                    startActivity(intent);
                    finish();
                }
            }
        } else if (requestCode == ServerConstents.QUIZ_START) {
            data = (Exam) response;
            Log.d("response Examp :: ", data.getStatus() + "");
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                tvCourse.setText(data.getData().get(0).getCourseName());
                tvTestName.setText("Test");
                tvOrder.setText(data.getData().get(0).getOrder() + "");
                if (data.getData().get(0).getQueType() == AppConstant.SINGLE_CHOICE) {
                    typeRadio(data.getData().get(0).getOptions(),data.getData().get(0).getQuestion());
                } else if (data.getData().get(0).getQueType() == AppConstant.MULTIPLE_CHOICE) {
                    typeCheckbox(data.getData().get(0).getOptions(),data.getData().get(0).getQuestion());
                } else if (data.getData().get(0).getQueType() == AppConstant.MATRIX) {
                    if (data.getData().get(0).getSub_type() == 3) {
                        listMatrix =new ArrayList<>();
                        listMatrix.addAll(data.getData().get(0).getOptions());
                        typeMatrix(data.getData().get(0).getOptions(), listMatrix,data.getData().get(0).getQuestion());
                    } else {
                        listMatrixImage=new ArrayList<>();
                        listMatrixImage.addAll(data.getData().get(0).getOptions());
                        typeImgMatrix(data.getData().get(0).getOptions(),listMatrixImage, data.getData().get(0).getQuestion());
                    }
                } else if (data.getData().get(0).getQueType() == AppConstant.FILL_IN_THE_BLANK) {
                    typeFillInTheBlank(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion(),data.getData().get(0).getParagraph());
                } else if (data.getData().get(0).getQueType() == AppConstant.SHORTING) {
                    typeSingleSorting(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                } else if (data.getData().get(0).getQueType() == AppConstant.STATEMENT) {
                    typeTrueFalse(data.getData().get(0).getOptions(), data.getData().get(0).getQuestion());
                }
            } else {
                Intent intent =new Intent(ExamActivity.this,ReportActivity.class);
                intent.putExtra("course_id", course_id);
                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {
    }


}
