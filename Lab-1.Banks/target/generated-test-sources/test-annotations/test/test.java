package test;

import Bank.Bank;
import Bank.CentralBank;
import Client.Client;
import org.testng.annotations.Test;

import static Account.AccountType.DEBIT;
import static Account.AccountType.DEPOSIT;
import static org.junit.Assert.assertEquals;

public class test {
    @Test
    public void transferBetweenAccountsDifferentBanks(){
        CentralBank centralBank = new CentralBank();
        Bank bank1 = Bank.builder()
                .name("Tinkoff")
                .commission(1)
                .interestRate(5)
                .elementaryInterestRate(3)
                .normalInterestRate(7)
                .vipInterestRate(1)
                .restrictionOperation(1000)
                .creditLimit(10000)
                .build();
        Bank bank2 = Bank.builder()
                .name("Sber")
                .commission(2)
                .interestRate(3)
                .elementaryInterestRate(5)
                .normalInterestRate(8)
                .vipInterestRate(9)
                .restrictionOperation(1000)
                .creditLimit(10000)
                .build();
        centralBank.addBank(bank1);
        centralBank.addBank(bank2);
        Client client1 = Client.builder()
                .firstName("aboba")
                .lastName("human")
                .build();
        Client client2 = Client.builder()
                .firstName("biba")
                .lastName("carrot")
                .build();
        centralBank.findBankByName("Tinkoff").addClient(client1);
        centralBank.findBankByName("Sber").addClient(client2);
        centralBank.findBankByName("Tinkoff").addAccount(bank1.getIdClient(client1), DEBIT);
        centralBank.findBankByName("Sber").addAccount(bank2.getIdClient(client2), DEBIT);

        centralBank.findBankByName("Tinkoff").findAccountById(1,1).deposit(1000);
        centralBank.transferMoney("Tinkoff", "Sber", 1,1,1,1, 1000);
        assertEquals(0, bank1.findAccountById(1, 1).getBalance(), 0.0);
        assertEquals(1000, bank2.findAccountById(1, 1).getBalance(), 0.0);
    }

    @Test
    public void settlementTheDepositAccountInWeek(){
        CentralBank centralBank = new CentralBank();
        Bank bank1 = Bank.builder()
                .name("Tinkoff")
                .commission(1)
                .interestRate(5)
                .elementaryInterestRate(3)
                .normalInterestRate(7)
                .vipInterestRate(1)
                .restrictionOperation(1000)
                .creditLimit(10000)
                .build();
        centralBank.addBank(bank1);
        Client client1 = Client.builder()
                .firstName("aboba")
                .lastName("human")
                .build();
        Bank tinkoff = centralBank.findBankByName("Tinkoff");
        tinkoff.addClient(client1);
        tinkoff.addAccount(bank1.getIdClient(client1), DEPOSIT,  7);
        tinkoff.findAccountById(1,1).deposit(56000);
        double temp = tinkoff.accelerateTime(1,1,7);
        assertEquals(temp, 59920.0, 0.0);

    }
}
