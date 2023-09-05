package com.bounce.BallBounce.service;

import com.bounce.BallBounce.MySQLAccess.template.ParametersEntity;
import com.bounce.BallBounce.entity.BallParameters;
import com.bounce.BallBounce.entity.NumberOfCalls;
import com.bounce.BallBounce.entity.Pair;
import com.bounce.BallBounce.service.ImpulseCountingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImpulseCountingServiceTest {

    private ImpulseCountingService countImpulse;
    private DataBaseService dataBase;
    private CallsCounterService callsCounter;

    @BeforeEach
    public void setUp(){
        countImpulse = new ImpulseCountingService();
        dataBase = new DataBaseService();
        callsCounter = new CallsCounterService();
    }
    @AfterEach
    public void shutDown(){
        countImpulse = null;
        dataBase = null;
        callsCounter = null;
    }
    @Test
    void countSummaryImpulseTest() throws Exception{
        assertEquals(8.0, countImpulse.getSummaryImpulse(5,10,2,3).getFinalVelocity());
    }
    @Test
    void serverErrorTest() throws Exception{
        Throwable except = assertThrows(ArithmeticException.class, ()->{
           countImpulse.getSummaryImpulse(1,1,1,1);
        });
        assertNotNull(except.getMessage());
    }

    @Test
    void getMaxVelocityTest() throws Exception{
        List<Pair> list = Arrays.asList(new Pair(new BallParameters(5),null),
                new Pair(new BallParameters(6),null),
                new Pair(new BallParameters(7),null));
        assertEquals(7, countImpulse.getMaxVelocity(list));
    }

    @Test
    void getMinVelocityTest() throws Exception{
        List<Pair> list = Arrays.asList(new Pair(new BallParameters(5),null),
                new Pair(new BallParameters(6),null),
                new Pair(new BallParameters(7),null));
        assertEquals(5, countImpulse.getMinVelocity(list));
    }
    @Test
    void getAverageVelocityTest() throws Exception{
        List<Pair> list = Arrays.asList(new Pair(new BallParameters(5),null),
                new Pair(new BallParameters(6),null),
                new Pair(new BallParameters(7),null));
        assertEquals(6, countImpulse.getAverageVelocity(list));
    }

    @Test
    void countCallsTest() throws Exception{
        callsCounter.countCalls();
        assertEquals(NumberOfCalls.getInstance().getCounter(), 1);
    }

}