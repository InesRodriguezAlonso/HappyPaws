package com.happypaws;

import com.happypaws.home.adoption.AdoptionOverviewPresenter;
import com.happypaws.home.adoption.AdoptionOverviewView;
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
public class PetsForAdoptionOverviewPresenterTest {

    @Rule
    public final RxSchedulersRule mOverrideSchedulersRule = new RxSchedulersRule();

    @Mock
    private RestApiService restApiService;

    @Mock
    private AdoptionOverviewView view;

    @Mock
    private List<PetResponse> mockResponse;

    private AdoptionOverviewPresenter presenter;

    @Before
    public void setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        RestApi restApi = new RestApi(restApiService);
        presenter = new AdoptionOverviewPresenter(restApi, view);
    }

    @After
    public void teardown() {
        presenter.onStop();
    }

    @Test
    public void loadPetsForAdoptionOnSuccess() {
        Observable<List<PetResponse>> responseObservable = Observable.just(mockResponse);
        when(restApiService.getPetsForAdoption()).thenReturn(responseObservable);

        presenter.getPetsForAdoptionList();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).showLoadingSpinner(false);
        inOrder.verify(view).showResultList(Mockito.anyBoolean());
        inOrder.verify(view).showEmptyView(Mockito.anyBoolean());
        inOrder.verify(view).showErrorView(false);
        inOrder.verify(view).stopRefreshing();
        inOrder.verify(view).getPetsForAdoptionListSuccess(mockResponse);
    }
}