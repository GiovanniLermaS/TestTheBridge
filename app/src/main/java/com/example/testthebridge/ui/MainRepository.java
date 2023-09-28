package com.example.testthebridge.ui;

import com.example.testthebridge.common.NetworkResult;
import com.example.testthebridge.data.ApiService;
import com.example.testthebridge.data.Item;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainRepository {

    private final ApiService apiService;

    @Inject
    public MainRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<NetworkResult<List<Item>>> getList() {
        return (Observable) Observable.create(emitter -> {
            emitter.onNext(new NetworkResult.Loading(true));

            Response<List<Item>> response = apiService.getList().execute();

            if (response.isSuccessful()) {
                List<Item> items = response.body();
                items = items.subList(0, Math.min(items.size(), 15));
                emitter.onNext(new NetworkResult.Success(items));
                emitter.onComplete();
            } else {
                String errorMessage = response.message();
                emitter.onNext(new NetworkResult.Failure(errorMessage != null ? errorMessage : "Unknown Error"));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }
}
