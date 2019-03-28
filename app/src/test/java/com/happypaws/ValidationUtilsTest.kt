package com.happypaws

import com.happypaws.utils.ValidationUtils
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ValidationUtilsTest {

    @Before
    fun setup() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun addition_isValidPhoneNumber() {
        assertFalse(ValidationUtils.isValidPhoneNumber(""))
        assertFalse(ValidationUtils.isValidPhoneNumber("t674"))
        assertFalse(ValidationUtils.isValidPhoneNumber("49/4849"))
        assertFalse(ValidationUtils.isValidPhoneNumber("?7444"))
        assertFalse(ValidationUtils.isValidPhoneNumber("+9382722"))
        assertFalse(ValidationUtils.isValidPhoneNumber(""))
        assertFalse(ValidationUtils.isValidPhoneNumber("123456789"))
    }
}