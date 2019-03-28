package com.happypaws;

import com.happypaws.adoption.AdoptionPresenter;
import com.happypaws.adoption.AdoptionView;
import com.happypaws.res.RestApi;
import com.happypaws.res.RestApiService;
import com.happypaws.res.model.AdoptionRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import retrofit2.Response;
import rx.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdoptionPresenterTest {

    @Rule
    public final RxSchedulersRule mOverrideSchedulersRule = new RxSchedulersRule();

    @Mock
    private RestApiService restApiService;

    @Mock
    private AdoptionView view;

    @Mock
    private Response mockResponse;

    @Mock
    private AdoptionRequest adoptionRequest;

    private AdoptionPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RestApi restApi = new RestApi(restApiService);
        presenter = new AdoptionPresenter(restApi, view);
    }

    @After
    public void teardown() {
        presenter.onStop();
    }

    @Test
    public void sendAdoptionFormList() {
        Observable responseObservable = Observable.just(mockResponse);
        when(restApiService.sendAdoptionRequest(any(AdoptionRequest.class))).thenReturn(responseObservable);

        presenter.sendAdoptionRequest(adoptionRequest);

        view.formCompleted();
    }
}