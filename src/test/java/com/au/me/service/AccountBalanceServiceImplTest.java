package com.au.me.service;

import com.au.me.util.Utils;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountBalanceServiceImplTest {

    AccountBalanceService accountBalanceService;

    @Before
    public void setUp() {
        accountBalanceService = new AccountBalanceServiceImpl();
    }

    @Test
    public void testAccountBalance() {
        Pair<BigDecimal, Integer> balancePair = accountBalanceService.getAccountBalance("ACC334455", LocalDateTime.parse("20/10/2018 12:00:00", Utils.DATE_TIME_FORMATTER), LocalDateTime.parse("20/10/2018 19:00:00", Utils.DATE_TIME_FORMATTER));
        Assert.assertEquals(java.util.Optional.of(2), java.util.Optional.of(balancePair.getValue1()));
        Assert.assertEquals(java.util.Optional.of(new BigDecimal("35.50").negate()), java.util.Optional.of(balancePair.getValue0()));
    }

    @Test
    public void testNoAccountBalance() {
        Pair<BigDecimal, Integer> balancePair = accountBalanceService.getAccountBalance("ACC334455", LocalDateTime.parse("21/10/2018 12:00:00", Utils.DATE_TIME_FORMATTER), LocalDateTime.parse("22/10/2018 19:00:00", Utils.DATE_TIME_FORMATTER));
        Assert.assertEquals(java.util.Optional.of(0), java.util.Optional.of(balancePair.getValue1()));
        Assert.assertEquals(java.util.Optional.of(new BigDecimal("0").negate()), java.util.Optional.of(balancePair.getValue0()));
    }

    @Test
    public void testReversalAccountBalance() {
        Pair<BigDecimal, Integer> balancePair = accountBalanceService.getAccountBalance("ACC334455", LocalDateTime.parse("20/10/2018 12:00:00", Utils.DATE_TIME_FORMATTER), LocalDateTime.parse("20/10/2018 20:00:00", Utils.DATE_TIME_FORMATTER));
        Assert.assertEquals(java.util.Optional.of(3), java.util.Optional.of(balancePair.getValue1()));
        Assert.assertEquals(java.util.Optional.of(new BigDecimal("25.00").negate()), java.util.Optional.of(balancePair.getValue0()));
    }

}
