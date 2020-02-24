package com.revature.prestigebank;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panam
 */
public class AccountTest {
    
    public AccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of withdraw method
     */
    @Test
    public void testWithdraw() {
        System.out.println("withdraw");
        double amount = 0.0;
        Account instance = new Account("Test");
        instance.withdraw(amount);
        // TODO review this test code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }

    /**
     * Test of setBalance method
     */
    @Test
    public void testSetBalance() {
        System.out.println("setBalance");
        double amount = 0.0;
        Account instance = new Account("Test");
        instance.setBalance(amount);
        // TODO review this test code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }

    /**
     * Test of getBalance method
     */
    @Test
    public void testGetBalance() {
        System.out.println("getBalance");
        Account instance = new Account("Test");
        double expResult = 0.0;
        double result = instance.getBalance();
        assertEquals(expResult, result, 0.0);
        // TODO review this code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }

    /**
     * Test of toString method
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Account instance = new Account("Test");
        String expResult = "";
        String result = instance.toString();
        // TODO review this test code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }

    /**
     * Test of getName method
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Account instance = new Account("Test");
        String expResult = "";
        String result = instance.getName();
        // TODO review this test code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }

    /**
     * Test of setName method
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Account instance = new Account("Test");
        instance.setName(name);
        // TODO review this test code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }

    /**
     * Test of getNumber method
     */
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        Account instance = new Account("Test");
        String expResult = "";
        String result = instance.getNumber();
        // TODO review this test code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }

    /**
     * Test of setNumber method
     */
    @Test
    public void testSetNumber() {
        System.out.println("setNumber");
        String number = "";
        Account instance = new Account("Test");
        instance.setNumber(number);
        // TODO review this code and remove the default call to assertTrue.
        assertTrue("The test case is a prototype.", true);
    }
    
}
