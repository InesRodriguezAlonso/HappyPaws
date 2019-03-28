package com.happypaws;

import com.happypaws.onglocations.OngLocationsPresenter;
import com.happypaws.onglocations.OngLocationsView;
import com.happypaws.res.RestApi;
import com.happypaws.res.RestApiService;
import com.happypaws.res.model.LocationResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OngLocationsPresenterTest {

    @Rule
    public final RxSchedulersRule mOverrideSchedulersRule = new RxSchedulersRule();

    @Mock
    private RestApiService restApiService;

    @Mock
    private OngLocationsView view;

    @Mock
    private List<LocationResponse> mockResponse;

    private OngLocationsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RestApi restApi = new RestApi(restApiService);
        presenter = new OngLocationsPresenter(restApi, view);
    }

    @After
    public void teardown() {
        presenter.onStop();
    }

    @Test
    public void loadLostPetsOnSuccess() {
        Observable<List<LocationResponse>> responseObservable = Observable.just(mockResponse);
        when(restApiService.geOngLocations()).thenReturn(responseObservable);

        presenter.getOngLocationsList();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).getOngLocationsListSuccess(mockResponse);
    }
}