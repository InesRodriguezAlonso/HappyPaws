package com.happypaws;

import com.happypaws.home.lostpets.LostPetsOverviewPresenter;
import com.happypaws.home.lostpets.LostPetsOverviewView;
import com.happypaws.res.RestApi;
import com.happypaws.res.RestApiService;
import com.happypaws.res.model.PetResponse;

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
public class LostPetsOverviewPresenterTest {

    @Rule
    public final RxSchedulersRule mOverrideSchedulersRule = new RxSchedulersRule();

    @Mock
    private RestApiService restApiService;

    @Mock
    private LostPetsOverviewView view;

    @Mock
    private List<PetResponse> mockResponse;

    private LostPetsOverviewPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RestApi restApi = new RestApi(restApiService);
        presenter = new LostPetsOverviewPresenter(restApi, view);
    }

    @After
    public void teardown() {
        presenter.onStop();
    }

    @Test
    public void loadLostPetsOnSuccess() {
        Observable<List<PetResponse>> responseObservable = Observable.just(mockResponse);
        when(restApiService.getLostPets()).thenReturn(responseObservable);

        presenter.getLostPetsList();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).showResultList(Mockito.anyBoolean());
        inOrder.verify(view).showEmptyView(Mockito.anyBoolean());
        inOrder.verify(view).showErrorView(false);
        inOrder.verify(view).stopRefreshing();
        inOrder.verify(view).getLostPetsListSuccess(mockResponse);
    }

    @Test
    public void loadLostPetsOnFailure() {
        when(restApiService.getLostPets()).thenReturn(Observable.error(new Exception(Mockito.anyString())));

        presenter.getLostPetsList();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).showErrorView(true);
        inOrder.verify(view).showEmptyView(false);
        inOrder.verify(view).showResultList(true);
        inOrder.verify(view).stopRefreshing();
        inOrder.verify(view).onFailure(Mockito.anyString());
    }
}