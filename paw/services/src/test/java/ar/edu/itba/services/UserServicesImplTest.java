package ar.edu.itba.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.interfaces.UserDao;

public class UserServicesImplTest {
        private static final String USERNAME = "Juan";

        private UserServiceImpl us;

        @Mock
        private UserDao userDao;

        @Before
        public void setUp() {
                MockitoAnnotations.initMocks(this);

                us = new UserServiceImpl();
                us.setUserDao(userDao);
        }

        @Test
        public void testGetByUsername() {
                // since the DAO is a mock, and we configured nothing, this will return null
                us.getByUsername(USERNAME);

                // Make sure we asked the DAO for this user, and not another
                Mockito.verify(userDao).getByUsername(Mockito.eq(USERNAME));
        }
}