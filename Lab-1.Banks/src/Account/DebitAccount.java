package Account;

import Client.*;
import Exception.NegativeAmountException;
import Exception.RestrictionOperation;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * Класс DebitAccount представляет дебетовый счет банка.
 * Наследуется от абстрактного класса AbstractAccount.
 */
public class DebitAccount extends Account.AbstractAccount {

    /**
     * Баланс счета.
     */
    @NonNull
    private double balance;
    private double interestRate;

    /**
     * Конструктор класса DebitAccount.
     *
     * @param client             клиент, которому принадлежит счет
     * @param idClient           идентификатор клиента
     * @param idAccount          идентификатор счета
     * @param now
     * @param creditLimit
     * @param normalInterestRate
     * @param vipInterestRate
     */
    public DebitAccount(Client client, long idClient, long idAccount, LocalDate now, double interestRate, double creditLimit, double normalInterestRate, double vipInterestRate) {
        super(client, idClient, idAccount);
    }

    /**
     * Метод withdraw() позволяет снять деньги со счета.
     *
     * @param amount сумма, которую нужно снять со счета
     * @throws NegativeAmountException    исключение, которое выбрасывается при попытке снять отрицательную сумму
     * @throws RestrictionOperation исключение, которое выбрасывается при попытке снять сумму, превышающую ограничение
     */
    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            throw new NegativeAmountException("Недостаточно средств на счету");
        }
        if (client.getAddress() == null || client.getPassportData() == null) {
            if (amount > restrictionOperation)
                throw new RestrictionOperation("Невозможно снять такую сумму со счета");
        }
        super.setBalance(getBalance() - amount);
    }

    /**
     * Метод deposit() позволяет внести деньги на счет.
     *
     * @param amount сумма, которую нужно внести на счет
     */
    @Override
    public void deposit(double amount) {
        super.setBalance(getBalance() + amount);;
    }

    /**
     * Метод calculateInterest() рассчитывает процент на остаток счета.
     *
     * @return сумма процентов
     */
    @Override
    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    /**
     * Метод calculateCommission() рассчитывает комиссию за ведение счета.
     * Для дебетового счета комиссия равна нулю.
     *
     * @return сумма комиссии
     */
    @Override
    public double calculateCommission() {
        return 0;
    }
}