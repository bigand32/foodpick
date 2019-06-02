package com.example.sosimtapa;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MainTabFragment1 extends Fragment {
    TextToSpeech tts;
    private Button button;
    View view2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       view2=inflater.inflate(R.layout.tab_fragment1,container,false);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 5);
            toast("순순히 권한을 넘기지 않으면, 음성 인식 기능을 사용할 수 없다!");
        }
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.title_no);
        final TextView txt = new TextView(getActivity());
        txt.setBackgroundColor(Color.WHITE);
        txt.setText("\n");

        txt.setTextSize(15);

        LinearLayout layoutin=new LinearLayout((getActivity()));
        layoutin.setOrientation(LinearLayout.VERTICAL);

        layoutin.setPadding(290,200,290,70);
        layout.addView(layoutin);

        ScrollView scroll = new ScrollView(getActivity());
        scroll.addView(layout);

        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.KOREAN);
            }
        });
        Button input = new Button(getActivity());

        input.setBackgroundResource(R.drawable.logo_noback1);

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputVoice(txt);
            }
        });
        layoutin.addView(input);
        layout.addView(txt);

        return scroll;
    }
    public void inputVoice(final TextView txt) {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getActivity().getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
            final SpeechRecognizer stt = SpeechRecognizer.createSpeechRecognizer(getActivity());
            stt.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    toast("음성 입력 시작...");
                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                }

                @Override
                public void onEndOfSpeech() {
                    toast("음성 입력 종료");
                }

                @Override
                public void onError(int error) {
                    toast("오류 발생 : " + error);
                    stt.destroy();
                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = (ArrayList<String>) results.get(SpeechRecognizer.RESULTS_RECOGNITION);
                    txt.append("     [나] "+result.get(0)+"\n");
                    replyAnswer(result.get(0), txt);
                    stt.destroy();
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
            stt.startListening(intent);
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    private void replyAnswer(String input, TextView txt){
        try{
            if(input.equals("안녕")){
                txt.append("     [픽푸드] 안녕하세요? 좋은 하루에요\n");
                tts.speak("안녕하세요? 좋은 하루에요", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.equals("날씨에 따라 메뉴 추천해 줘")){
                txt.append("     [픽푸드] 네. 오늘 날씨는 어떤가요?\n");
                tts.speak(" 네. 오늘 날씨는 어떤가요?", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.equals("종류에 따라 메뉴 추천해 줘")){
                txt.append("     [픽푸드] 네. 중식,일식,한식,분식 중에 선택해주세요.\n");
                tts.speak("  네. 중식,일식,한식,분식 중에 선택해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.equals("흐림")){
                txt.append("     [픽푸드] 그럼 비 올때 생각나는 파전이 좋을 것 같아요.\n");
                tts.speak(" 그럼 비 올때 생각나는 파전이 좋을 것 같아요.", TextToSpeech.QUEUE_FLUSH, null);
                Context mContext = view2.getContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup, (ViewGroup) view2.findViewById(R.id.popup));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(view2.getContext());
                aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅 //그냥 닫기버튼을 위한 부분

                aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name="체부동잔치집";
                        Double lat,lon;
                        String address="대한민국 서울특별시 종로구 내자동 12";
                        lat=37.5762932;
                        lon=126.9694476;
                        Intent intent = new Intent(getActivity(),MapsActivity.class);
                        intent.putExtra("latitude",lat);
                        intent.putExtra("longitude",lon);
                        intent.putExtra("name",name);
                        intent.putExtra("address",address);
                        startActivity(intent);
                    }
                });
                    AlertDialog ad = aDialog.create();

                    ad.show();//보여줌!

                }

            else if(input.equals("일식")){
                txt.append("      [픽푸드] 튀김,덮밥,면 중에 선택해주세요.\n");
                tts.speak(" 튀김,덮밥,면 중에 선택해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.equals("튀김")){
                txt.append("     [픽푸드] 돈까스 추천해요.\n");
                tts.speak(" 돈까스 추천해요.", TextToSpeech.QUEUE_FLUSH, null);
            }

            else if(input.equals("종료")){
               // finish();
            }
            else {
                txt.append("     [픽푸드] 다시한 번 말씀해 주세요\n");
                tts.speak("다시한 번 말씀해 주세요", TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    private void toast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

}

