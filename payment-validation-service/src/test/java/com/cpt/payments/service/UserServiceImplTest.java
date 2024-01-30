package com.cpt.payments.service;

import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.dao.UserDao;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.service.impl.UserServiceImpl;
import com.cpt.payments.util.TestDataProviderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void createUserSuccessfully() {
        Long expectedUserId = 123L;
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUser(TestDataProviderUtil.getTestUserBean());
        paymentRequest.setPayment(TestDataProviderUtil.getTestPayment());

        when(userDao.getUserDetails(any())).thenReturn(null);
        when(userDao.insertUserDetails(any())).thenReturn(expectedUserId);

        assertDoesNotThrow(() -> userService.createUser(paymentRequest));
    }

    @Test
    public void createUserReturnsExistingUserId() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUser(TestDataProviderUtil.getTestUserBean());
        paymentRequest.setPayment(TestDataProviderUtil.getTestPayment());

        when(userDao.getUserDetails(any())).thenReturn(TestDataProviderUtil.getTestUserDto());

        Long actualUserId = userService.createUser(paymentRequest);

        assertEquals(123L, actualUserId);
    }

    @Test
    public void createUserFails() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUser(TestDataProviderUtil.getTestUserBean());
        paymentRequest.setPayment(TestDataProviderUtil.getTestPayment());

        when(userDao.getUserDetails(any())).thenReturn(null);
        when(userDao.insertUserDetails(any())).thenReturn(null);

        assertThrows(ValidationException.class, () -> userService.createUser(paymentRequest));
    }

}
