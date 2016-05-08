package com.example.zq.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.start)
    Button start;
    private Subscription _subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
       _subscription = getButtonAciton();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _subscription.unsubscribe();
    }

    public Subscription getButtonAciton(){
        return RxView.clickEvents(start).map(new Func1<ViewClickEvent, Integer>() {
            @Override
            public Integer call(ViewClickEvent viewClickEvent) {
                Log.d("zq","Button tap once");
                return 1;
            }
        }).buffer(2,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d("zq","Button taped" + integers.size());
                    }
                });
    }
}
