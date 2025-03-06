/**

 Класс CreditAccount представляет кредитный счет банка.
 @version 1.0 */ package Account;
import Client.*;
import Exception.InsufficientFundsException;
import Exception.RestrictionOperation;

import java.time.LocalDate;

public class CreditAccount extends Account.AbstractAccount {

    /**
     * Баланс кредитного счета.
     */
    private double creditLimit;
    private double commission;

    /**
     * Создает новый кредитный счет.
     *
     * @param client             клиент, которому принадлежит счет
     * @param idClient           идентификатор клиента
     * @param idAccount          идентификатор счета
     * @param now
     * @param normalInterestRate
     * @param vipInterestRate
     */
    public CreditAccount(Client client, long idClient, long idAccount, LocalDate now, double commission, double creditLimit, double normalInterestRate, double vipInterestRate) {
        super(client, idClient, idAccount);
    }

    /**
     * Снимает указанную сумму со счета.
     *
     * @param amount сумма, которую нужно снять
     * @throws InsufficientFundsException если превышен кредитный лимит
     * @throws RestrictionOperation если сумма снятия превышает ограничение по операциям
     */
    @Override
    public void withdraw(double amount) {
        if (amount > balance + creditLimit) {
            throw new InsufficientFundsException("Превышен кредитный лимит");
        }
        if (client.getAddress() == null || client.getPassportData() == null){
            if (amount > restrictionOperation)
                throw new RestrictionOperation("Невозможно снять такую сумму со счета");
        }
        super.setBalance(getBalance() - amount);
    }

    /**
     * Вносит указанную сумму на счет.
     *
     * @param amount сумма, которую нужно внести
     */
    @Override
    public void deposit(double amount) {
        if (balance < 0){
            super.setBalance(getBalance() - calculateCommission() + amount);
        }
        super.setBalance(getBalance() + amount);
    }


    /**
     * Рассчитывает процент по счету.
     *
     * @return величина процента
     */
    @Override
    public double calculateInterest() {
        return 0;
    }

    /**
     * Рассчитывает коммисию для счета.
     *
     * @return величина процента
     */
    @Override
    public double calculateCommission() {
        if (balance < 0) {
            return Math.abs(balance) * commission / 100;
        }
        return 0;
    }
}

