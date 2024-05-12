package com.example.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chatsRV;

    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;
    private static final String TAG = "MainActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        chatsRV = findViewById(R.id.idRVChats);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        sendMsgFAB = findViewById(R.id.btn_send);
        chatsModalArrayList= new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);
        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter your message",Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });

    }

    private void getResponse(String message){
        chatsModalArrayList.add(new ChatsModal(message, "user"));
        chatRVAdapter.notifyDataSetChanged();
//        String BASE_URL = "https://df55-2402-800-61c6-3c82-18ae-bae9-f170-3b7f.ngrok-free.app/chat/";
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
//
//
//        Call<MsgModal> call = retrofitAPI.getMessage(message);
//        call.enqueue(new Callback<MsgModal>() {
//            @Override
//            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
//                if(response.isSuccessful()) {
//                    MsgModal modal = response.body();
//                    chatsModalArrayList.add(new ChatsModal(modal.getCnt(), "bot"));
//                    chatRVAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MsgModal> call, Throwable t) {
//                Log.e(TAG, "API call failed: " + t.getMessage());
//                chatsModalArrayList.add(new ChatsModal("Please revert your question", "bot"));
//                chatRVAdapter.notifyDataSetChanged();
//            }
//        });

        OkHttpClient client = new OkHttpClient();

        JSONObject postBody = new JSONObject();
        try {
            postBody.put("Message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(mediaType,postBody.toString());

        Request request = new Request.Builder()
                .url("https://eec7-2402-800-61c3-7250-a80e-b119-814d-7815.ngrok-free.app" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "" +
                        "/chat/")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "API call failed: " + e.getMessage());
                chatsModalArrayList.add(new ChatsModal("Please revert your question", "bot"));
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        chatRVAdapter.notifyDataSetChanged();

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
                Object json = null;
                try {
                    json = new JSONObject(response.body().string()).get("chatBotReply");
                    if (json instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) json;
                        StringBuilder results = new StringBuilder();
                        for (int i = 0;i < jsonArray.length();i++) {
                            results.append(jsonArray.getString(i)).append("\n");
                        }
                        chatsModalArrayList.add(new ChatsModal(results.toString(), "bot"));
                    } else {
                        String result = json.toString();
                        chatsModalArrayList.add(new ChatsModal(result, "bot"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        chatRVAdapter.notifyDataSetChanged();

                    }
                });

            }
        });
    }
}