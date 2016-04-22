package ar.edu.itba.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.edu.itba.interfaces.UserDao;

public class UserServiceImplTest {
        private static final String USERNAME = "Juan";

        private UserServiceImpl us;

        @Mock
        private UserDao userDao;

        @Before
        public void setUp() {
                
        }

        @Test
        public void testGetByUsername() {
                
        }
}