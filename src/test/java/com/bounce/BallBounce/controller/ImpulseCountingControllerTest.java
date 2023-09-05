package com.bounce.BallBounce.controller;

import com.bounce.BallBounce.entity.BallParameters;
import com.bounce.BallBounce.entity.InMemoryStorage;
import com.bounce.BallBounce.entity.PostCharactersList;
import com.bounce.BallBounce.entity.ValidationImpulseError;
import com.bounce.BallBounce.service.CallsCounterService;
import com.bounce.BallBounce.service.DataBaseService;
import com.bounce.BallBounce.service.ImpulseCountingService;
import com.bounce.BallBounce.validator.VelocityValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


class ImpulseCountingControllerTest {
    @Mock
    private ImpulseCountingService mockImpulseService = mock(ImpulseCountingService.class);
    @Mock
    private VelocityValidator mockValid = mock(VelocityValidator.class);
    @Mock
    private InMemoryStorage mockStorage = mock(InMemoryStorage.class);
    @Mock
    private CallsCounterService mockCalls = mock(CallsCounterService.class);
    @Mock
    private DataBaseService mockDataBase = mock(DataBaseService.class);
    @InjectMocks
    private ImpulseCountingController impulseController = new ImpulseCountingController(mockImpulseService, mockValid, mockStorage, mockCalls, mockDataBase);
    @Test
    void getImpulseTestTrue() {
        BallParameters sumVelocity =  new BallParameters(8.0);
        ValidationImpulseError errorRange = new ValidationImpulseError();

        doNothing().when(mockCalls).countCalls();
        when(mockImpulseService.getSummaryImpulse(5, 10, 2, 3)).thenReturn(sumVelocity);
        when(mockValid.validateParameters(5, 10, 2, 3)).thenReturn(errorRange);

        ResponseEntity<Object> result = impulseController.getImpulse(5, 2, 10,3);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(mockCalls, times(1)).countCalls();
    }

    @Test
    void getImpulseTestFalseCase1(){
        ValidationImpulseError errorRange = new ValidationImpulseError();
        errorRange.addErrorMessage("Velocity of the first object can't be negative");

        when(mockValid.validateParameters(-1, 10, 2, 3)).thenReturn(errorRange);

        ResponseEntity<Object> result = impulseController.getImpulse(-1, 2, 10,3);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getImpulseTestFalseCase2(){
        BallParameters sumVelocity =  new BallParameters(0);
        ValidationImpulseError errorRange = new ValidationImpulseError();

        when(mockImpulseService.getSummaryImpulse(5, -10, 2, 1)).thenReturn(sumVelocity);
        when(mockValid.validateParameters(5, -10, 2, 1)).thenReturn(errorRange);

        ResponseEntity<Object> result = impulseController.getImpulse(5, 2, -10,1);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    @Test
    void getImpulseTestFalseCase3(){
        ValidationImpulseError errorRange = new ValidationImpulseError();

        when(mockImpulseService.getSummaryImpulse(1, 1, 1, 1)).thenReturn(null);
        when(mockValid.validateParameters(1, 1, 1, 1)).thenReturn(errorRange);

        ResponseEntity<Object> result = impulseController.getImpulse(1, 1, 1,1);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void inMemoryStorageTest(){
        ResponseEntity<Object> resultList = impulseController.getAllCashParams();
        ResponseEntity<Object> resultNum = impulseController.getAllCashParamsNumber();

        Assertions.assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(resultNum.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void inMemoryStorageActiveTest(){
        ResponseEntity<Object> resultList = impulseController.getAllCashParams();
        ResponseEntity<Object> resultNum = impulseController.getAllCashParamsNumber();

        Assertions.assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(resultNum.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void callsCounterTest(){
        ResponseEntity<Object> result = impulseController.getNumberOfCalls();
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void postVelocityParamsTestTrue(){

        BallParameters sumVelocity =  new BallParameters(8.0);
        ValidationImpulseError errorRange = new ValidationImpulseError();

        List<BallParameters> params = new ArrayList<>();
        params.add(new BallParameters(8, 5, 10,2,3));

        when(mockImpulseService.getSummaryImpulse(5, 10, 2, 3)).thenReturn(sumVelocity);
        when(mockValid.validateParameters(5, 10, 2, 3)).thenReturn(errorRange);

        ResponseEntity<PostCharactersList> result = impulseController.postVelocityParams(params);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void postVelocityParamsTestFalseCase1(){
        ValidationImpulseError errorRange = new ValidationImpulseError();
        errorRange.addErrorMessage("Velocity of the first object can't be negative");

        List<BallParameters> params = new ArrayList<>();
        params.add(new BallParameters(0, -1, 10, 2, 3));

        when(mockValid.validateParameters(-1, 10, 2, 3)).thenReturn(errorRange);

        ResponseEntity<PostCharactersList> result = impulseController.postVelocityParams(params);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void postVelocityParamsTestFalseCase2(){
        BallParameters sumVelocity =  new BallParameters(0);
        ValidationImpulseError errorRange = new ValidationImpulseError();

        List<BallParameters> params = new ArrayList<>();
        params.add(new BallParameters(0, 5, -10, 2, 1));

        when(mockImpulseService.getSummaryImpulse(5, -10, 2, 1)).thenReturn(sumVelocity);
        when(mockValid.validateParameters(5, -10, 2, 1)).thenReturn(errorRange);

        ResponseEntity<PostCharactersList> result = impulseController.postVelocityParams(params);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void postVelocityParamsTestFalseCase3(){
        ValidationImpulseError errorRange = new ValidationImpulseError();

        List<BallParameters> params = new ArrayList<>();
        params.add(new BallParameters(1, 1, 1, 1, 1));

        when(mockImpulseService.getSummaryImpulse(1, 1, 1, 1)).thenReturn(null);
        when(mockValid.validateParameters(1, 1, 1, 1)).thenReturn(errorRange);

        ResponseEntity<PostCharactersList> result = impulseController.postVelocityParams(params);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAllDataBaseParams(){
        ResponseEntity<Object> result = impulseController.getAllDataBaseParams();
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void checkSavedInCashParam(){
        double[] arr = {4, 2, 3, 2};
        ValidationImpulseError errorRange = new ValidationImpulseError();

        when(mockValid.validateParameters(4, 2, 3, 2)).thenReturn(errorRange);
        when(mockStorage.isContainsInMap(arr)).thenReturn(true);

        ResponseEntity<Object> result = impulseController.getImpulse(4, 3, 2 ,2);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

/*    @Test
    void checkSavedInDataBaseParam(){
        double[] arr = {4, 2, 3, 2};
        ValidationImpulseError errorRange = new ValidationImpulseError();
        BallParameters params = new BallParameters(4, 4, 2, 3, 2);

        when(mockValid.validateParameters(4, 2, 3, 2)).thenReturn(errorRange);
        when(mockStorage.isContainsInMap(arr)).thenReturn(false);
        when(mockDataBase.isContainsInDataBase(new BallParameters(0, params.getVelocity1(), params.getVelocity2(),
                params.getMass1(), params.getMass2()))).thenReturn(new BallParameters(4));

        ResponseEntity<Object> result = impulseController.getImpulse(4, 3, 2 ,2);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }*/
}