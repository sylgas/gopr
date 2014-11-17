package com.agh.gopr.app.service.rest.service.method;

import com.agh.gopr.app.GoprTestRunner;
import com.agh.gopr.app.exception.MethodException;
import com.agh.gopr.app.service.rest.RequestService;
import com.google.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

@RunWith(GoprTestRunner.class)
public class GetAllPointsMethodTest {

    @Mock
    private RequestService requestServiceMock;

    @Mock
    private RequestService.HttpCallback callback;

    @Inject
    private GetAllPointsMethod method;

    @Test
    public void testRun() throws Exception {
        method.requestService = requestServiceMock;
        method.run(callback, "111");
        verify(requestServiceMock).get(method.createUrl("111"), callback);
    }

    @Test
    public void testCheckingArguments() throws Exception {
        method.checkArguments();
    }

    @Test(expected = MethodException.WrongNumberOfParametersException.class)
    public void testWrongNrOfArg() throws Exception {
        method.checkArguments("aaa");
    }

    @Test(expected = MethodException.EmptyMethodDefinitionException.class)
    public void testCreateUrlWithNullUrl() throws Exception {
        method.createUrl(null);
    }

    @Test(expected = MethodException.EmptyMethodDefinitionException.class)
    public void testCreateUrlWithEmptyUrl() throws Exception {
        method.createUrl("");
    }

    @Test(expected = MethodException.WrongParameterException.class)
    public void testCheckingNotNumericActionId() throws Exception {
        method.createUrl("aaa");
    }

    @Test
    public void testCheckingNumericActionId() throws Exception {
        method.createUrl("111");
        //assertEquals(method.getMethodUrl());
    }
}
