package com.pravin.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText comment;
    Button submit_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link Object to ID
        comment = (EditText) findViewById(R.id.comment);
        submit_Button = (Button) findViewById(R.id.submit);

        //ToneAnalyzer
        final ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-07-01");
        toneAnalyzer.setUsernameAndPassword("1c5d23e2-1e1b-4393-a2ae-9212d41a5127", "arfNQ5RvTnOv");

        submit_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Get Comment
                String text = comment.getText().toString();

                //Select Specific Object type(Emotion) from Tone Identified JSON file
                ToneOptions options = new ToneOptions.Builder()
                        .addTone(Tone.EMOTION)
                        .html(false).build();

                //Pass Tone and Specific Object to IBM Watson Cloud
                toneAnalyzer.getTone(text, options).enqueue(
                        new ServiceCallback<ToneAnalysis>() {
                            @Override
                            public void onResponse(ToneAnalysis response) {
                                // More code here
                                List<ToneScore> scores = response.getDocumentTone()
                                        .getTones()
                                        .get(0)
                                        .getTones();
                                String detectedTones = "";

                                for (ToneScore score : scores) {
                                    if (score.getScore() > 0.5f) {
                                        detectedTones += score.getName() + " ";
                                    }
                                }

                                final String toastMessage =
                                        "The following emotions were detected:\n\n"
                                                + detectedTones.toUpperCase();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getBaseContext(),
                                                toastMessage, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });

    }
}
