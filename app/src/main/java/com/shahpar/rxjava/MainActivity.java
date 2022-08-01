package com.shahpar.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "RxJava";
    CompositeDisposable compositeDisposable;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.txt_number);


        Observable<Integer> intObservable = Observable.just(12, 13, 14);
        Observer<Integer> inObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };

        intObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inObserver);

// handle a observable with just and subscribe on String-Observer, using map function
        Observable<String> stringObservable = Observable.just("one", "two", "three");
        Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        stringObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        if (s.equals("one"))
                            return "1";
                        else if (s.equals("two"))
                            return "2";
                        else
                            return "3";
                    }
                })
                .subscribe(stringObserver);

// Using range and filter even numbers
        Observable.range(0, 20)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        // filter and just return even number
                        if (integer % 2 == 0)
                            return true;
                        return false;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "using range and filter even number: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

// Make a list and composition of observable and can dispose all observers at the same time
// Using the Filter function and map to filter and map result data
        compositeDisposable = new CompositeDisposable();

        Observable<String> animalsObservable = getAnimalObservable();
        DisposableObserver<String> animalObserver = getAnimalObserver();
        DisposableObserver<String> animalObserver2 = getSecondObserver();

        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("s");
                            }
                        })
                        .map(new Function<String, String>() {

                            @Override
                            public String apply(String s) throws Exception {
                                return s.toUpperCase();
                            }
                        })
                        .subscribeWith(animalObserver));

        compositeDisposable.add(
                animalsObservable
                        .observeOn(Schedulers.trampoline())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(animalObserver2));

// Using Flowable instead Observable (Flowable can control bask pressure and can buffer results
        Flowable.range(0, 1000000)
                .onBackpressureBuffer()
                .observeOn(Schedulers.computation())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

// Observe on an Object
        Task task = new Task("Hello Amin");
        Observable<Task> singleObs = Observable.create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(ObservableEmitter<Task> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(task);
                            emitter.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        singleObs.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "on subscribe");
            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: single task: " + task.getDescription());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    class Task {
        String description;

        public Task(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private DisposableObserver<String> getAnimalObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {

                Log.d(TAG, "11 " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Finished!");
            }
        };
    }

    private DisposableObserver<String> getSecondObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                textView.append(s + " , ");
                Log.d(TAG, "22 " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Finished second!");
            }
        };
    }

    private Observable<String> getAnimalObservable() {
        return Observable.fromArray("Eines", "Zwei", "Drei", "Vier", "Fünf", "Sechs", "Sieben", "Acht", "Neun");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}