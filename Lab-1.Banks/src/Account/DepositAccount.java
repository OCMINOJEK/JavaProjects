package Account;

import Client.*;
import Exception.DepositTermNotExpiredException;
import Exception.RestrictionOperation;

import java.time.LocalDate;

import Exception.NegativeAmountException;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс DepositAccount представляет депозитный счет в банке.
 * Этот класс наследуется от абстрактного класса AbstractAccount.
 */
@Setter
@Getter
public class DepositAccount extends Account.AbstractAccount {

    /**
     * Дата открытия депозитного счета.
     */

    private LocalDate openDate;

    /**
     * Срок депозитного счета в днях.
     */
    private int term;
    private double elementaryInterestRate;
    private double normalInterestRate;
    private double vipInterestRate;

    /**
     * Текущий баланс депозитного счета.
     */

    /**
     * Конструктор класса DepositAccount.
     *
     * @param client   клиент, которому принадлежит депозитный счет
     * @param idClient идентификатор клиента
     * @param idAccount идентификатор депозитного счета
     * @param openDate дата открытия депозитного счета
     * @param term     срок депозитного счета в днях
     */
    public DepositAccount(Client client, long idClient, long idAccount, LocalDate openDate, int term, double elementaryInterestRate,
                          double normalInterestRate, double vipInterestRate) {
        super(client, idClient, idAccount);
        this.elementaryInterestRate = elementaryInterestRate;
        this.normalInterestRate = normalInterestRate;
        this.vipInterestRate = vipInterestRate;
        this.openDate = openDate;
        this.term = term;
        this.balance = 0;
    }

    /**
     * Метод isExpired проверяет, истек ли срок депозитного счета.
     *
     * @return true, если срок депозитного счета истек, false - в противном случае
     */
    private boolean isExpired() {
        LocalDate today = LocalDate.now();
        LocalDate expireDate = openDate.plusDays(term);
        return today.isAfter(expireDate);
    }

    /**
     * Метод withdraw позволяет снять деньги со депозитного счета.
     *
     * @param amount сумма, которую необходимо снять со счета
     * @throws DepositTermNotExpiredException  если срок депозитного счета еще не истек
     * @throws NegativeAmountException         если сумма снятия превышает баланс счета
     * @throws RestrictionOperation           если снятие ограничено для клиента, у которого нет адреса или паспортных данных
     */
    @Override
    public void withdraw(double amount) {
        if(!isExpired())
            throw new DepositTermNotExpiredException("Снятие денег со вклада не разрешено");
        else if (amount > balance)
            throw new NegativeAmountException("Недостаточно средств на счету");
        if (client.getAddress() == null || client.getPassportData() == null){
            if (amount > restrictionOperation)
                throw new RestrictionOperation("Снятие такой суммы со счета невозможно");
        }
        super.setBalance(getBalance() - amount);
    }

    /**
     * Метод deposit позволяет внести деньги на депозитный счет.
     *
     * @param amount сумма, которую необходимо внести на счет
     */
    @Override
    public void deposit(double amount) {
        super.setBalance(getBalance() + amount);;
    }

    /**
     * Метод calculateInterest рассчитывает процент по депозитному счету.
     *
     * @return сумма процентов, начисленных по депозитному счету
     */
    @Override
    public double calculateInterest() {
        if (balance < 50_000)
            return balance * elementaryInterestRate/ 100;
        else if (balance > 100_000)
            return balance * normalInterestRate / 100;
        else
            return balance * vipInterestRate / 100;
    }
}