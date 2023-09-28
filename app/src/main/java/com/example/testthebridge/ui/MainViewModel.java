package com.example.testthebridge.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testthebridge.common.NetworkResult;
import com.example.testthebridge.data.Item;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@HiltViewModel
public class MainViewModel extends ViewModel implements Serializable {

    private final MainRepository mainRepository;
    private MutableLiveData<NetworkResult<List<Item>>> movieResponse = new MutableLiveData<>();

    @Inject
    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
        fetchList();
    }

    public LiveData<NetworkResult<List<Item>>> getListResponse() {
        return movieResponse;
    }

    public void fetchList() {
        Observable<NetworkResult<List<Item>>> disposable = null;
        if (disposable != null && !disposable.subscribe().isDisposed()) {
            disposable.subscribe().dispose();
        }
        disposable = mainRepository.getList();
        disposable.subscribe(new Observer<NetworkResult<List<Item>>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(NetworkResult<List<Item>> result) {
                if (result instanceof NetworkResult.Success) {
                    movieResponse.postValue(result);

                } else if (result instanceof NetworkResult.Failure) {
                    movieResponse.postValue(result);

                } else if (result instanceof NetworkResult.Loading) {
                    movieResponse.postValue(result);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
