package com.te.testservice.base;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;


public class BaseUnitTest {

    @BeforeEach
    public void baseSetup() {
        MockitoAnnotations.initMocks(this);
    }
}
