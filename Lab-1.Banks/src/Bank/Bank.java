package Bank;

import Account.AbstractAccount;
import Account.AccountType;
import Account.CreditAccount;
import Account.DebitAccount;
import Exception.FieldNotFound;
import Client.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс Bank представляет банк, который предоставляет услуги по работе с клиентами и счетами.
 * Этот класс использует библиотеку Lombok для генерации конструктора, геттеров и сеттеров.
 */
@Data
@Builder
public class Bank {

    /**
     * Название банкаa.
     */
    @NonNull
    private String name;

    /**
     * Текущий идентификатор клиента.
     */
    @Builder.Default
    private long idClient = 1;

    /**
     * Текущий идентификатор счета.
     */
    @Builder.Default
    private long idAccount = 1;

    /**
     * Центральный банк, в котором регистрируется текущий банк.
     */

    /**
     * Комиссия за ведение счета.
     */
    private double commission;

    /**
     * Процентная ставка по вкладам.
     */
    private double interestRate;

    /**
     * Базовая процентная ставка.
     */
    private double elementaryInterestRate;

    /**
     * Нормальная процентная ставка.
     */
    private double normalInterestRate;

    /**
     * VIP-процентная ставка.
     */
    private double vipInterestRate;

    /**
     * Ограничение на сумму операций для сомнительных счетов.
     */
    private double restrictionOperation;

    /**
     * Кредитный лимит.
     */
    private double creditLimit;

    /**
     * Список клиентов банка.
     */
    @Builder.Default
    private Map<Long, Client> clients = new HashMap<Long, Client>();

    /**
     * Список счетов клиентов банка.
     */
    @Builder.Default
    private Map<Long, Map<Long, Account.AbstractAccount>> clientAccounts = new HashMap<>();



    /**
     * Метод setInterestRate устанавливает новую процентную ставку в зависимости от типа ставки.
     *
     * @param newRate новая процентная ставка
     * @param interestType тип процентной ставки
     */
    public void setInterestRate(double newRate, @NotNull InterestType interestType) {
        switch (interestType) {
            case COMMISSON:
                this.commission = newRate;
                break;
            case INTERESTRATE:
                this.interestRate = newRate;
                break;
            case ELEMENTARYINTERESTRATE:
                this.elementaryInterestRate = newRate;
                break;
            case NORMALINTERESTRATE:
                this.normalInterestRate = newRate;
                break;
            case VIPINTERESTRATE:
                this.vipInterestRate = newRate;
                break;
            case RESTRICTIONOPERATION:
                this.restrictionOperation = newRate;
                break;
            case CREDITLIMIT:
                this.creditLimit = newRate;
                break;
            default:
                throw new IllegalArgumentException("Unknown interest type: " + interestType);
        }

        for (Client client : clients.values()) {
            client.getMessages().add(String.valueOf(new Message(name + " changed " + interestType + " to " + newRate + "%",
                    "" + name)));
        }
    }

    /**
     * Метод addClient добавляет нового клиента в список клиентов банка.
     *
     * @param client новый клиент
     */
    public void addClient(Client client) {
        if(client == null){
            throw new FieldNotFound("lab1.main.Client is null");
        }
        clients.put(idClient, client);
        clientAccounts.put(idClient, null);
        idClient++;
    }

    /**
     * Метод removeClient удаляет клиента из списка клиентов банка.
     *
     * @param id идентификатор клиента
     */
    public void removeClient(long id) {
        if (findClientById(id) == null)
            throw new FieldNotFound("lab1.main.Client is not found");
        clients.remove(id);
    }

    /**
     * Метод findClientById находит клиента по его идентификатору.
     *
     * @param id идентификатор клиента
     * @return найденный клиент
     */
    public Client findClientById(long id) {
        if (clients.get(id) == null)
            throw new FieldNotFound("lab1.main.Client is not found");
        return clients.get(id);
    }
    /**
     * Метод getIdClient находит id клиента по его классу.
     *
     * @param client Class клиента
     * @return id найденного клиента
     */
    public long getIdClient(@NonNull Client client) {
        for (Map.Entry<Long, Client> entry : clients.entrySet()) {
            if (entry.getValue().equals(client)) {
                return entry.getKey();
            }
        }
        throw new FieldNotFound("lab1.main.Client is not found");
    }


    /**
     * Метод addAccount добавляет новый счет для клиента.
     *
     * @param idClient идентификатор клиента
     */
    public void addAccount(long idClient, AccountType type) {
        if (clientAccounts.containsKey(idClient)) {

            clientAccounts.put(idClient, new HashMap<>());
        }
        if (clients.get(idClient) == null)
            throw new FieldNotFound("lab1.main.Client is not found");
        if (type == AccountType.DEBIT) {
            AbstractAccount account = new DebitAccount(findClientById(idClient), idClient, idAccount, LocalDate.now(), interestRate, creditLimit, normalInterestRate, vipInterestRate);
            clientAccounts.get(idClient).put(idAccount, account);
        } else if (type == AccountType.CREDIT) {
            AbstractAccount account = new CreditAccount(findClientById(idClient), idClient, idAccount, LocalDate.now(), commission, creditLimit, normalInterestRate, vipInterestRate);
            clientAccounts.get(idClient).put(idAccount, account);
        } else
            throw new FieldNotFound("AccountType is not found");
        idAccount++;
    }

    /**
     * Метод addAccount добавляет новый депозитный счет для клиента.
     *
     * @param idClient идентификатор клиента
     * @param type тип счета
     * @param term срок депозита
     */
    public void addAccount(long idClient, AccountType type, int term) {
        if (clientAccounts.containsKey(idClient)) {
            clientAccounts.put(idClient, new HashMap<>());
        }
        if (type == AccountType.DEPOSIT) {
            Account.AbstractAccount account = new Account.AbstractAccount(findClientById(idClient), idClient, idAccount);
            clientAccounts.get(idClient).put(idAccount, account);
        } else {
            throw new FieldNotFound("AccountType is not found");
        }
    }

    /**
     * Метод removeAccount удаляет счет клиента.
     *
     * @param idClient идентификатор клиента
     * @param idAccount идентификатор счета
     */
    public void removeAccount(long idClient, long idAccount) {
        if (clients.get(idClient) == null) {
            throw new FieldNotFound("lab1.main.Client is not found");
        }
        clientAccounts.get(idClient).remove(idAccount);
        throw new FieldNotFound("Deletion error, account with the same name was not found");
    }

    /**
     * Метод findAccountById находит счет клиента по его идентификатору.
     *
     * @param idClient идентификатор клиента
     * @param idAccount идентификатор счета
     * @return найденный счет
     */
    public Account.AbstractAccount findAccountById(long idClient, long idAccount) {
        if (clientAccounts.get(idClient) == null)
            throw new FieldNotFound("lab1.main.Client is not found");
        return clientAccounts.get(idClient).get(idAccount);
    }

    /**
     * Метод creditingInterestToBalance начисляет проценты по счету клиента.
     *
     * @param idClient идентификатор клиента
     * @param idAccount идентификатор счета
     */
    public void creditingInterestToBalance(long idClient, long idAccount) {
        if (clients.get(idClient) == null)
            throw new FieldNotFound("lab1.main.Client is not found");
        if (clientAccounts.get(idClient).get(idAccount) == null)
            throw new FieldNotFound("Account is not found");
        Account.AbstractAccount account = clientAccounts.get(idClient).get(idAccount);
        account.deposit(account.calculateInterest());
    }

    /**
     * Метод accelerateTime ускоряет время для счета клиента и начисляет проценты.
     *
     * @param idClient идентификатор клиента
     * @param idAccount идентификатор счета
     * @param days количество дней
     * @return сумма на счету после начисления процентов
     */
    public double accelerateTime(long idClient, long idAccount, int days) {
        double balance = 0;
        for (long i = 0; i < days; i++) {
            Account.AbstractAccount account = clientAccounts.get(idClient).get(idAccount);
            if (balance == 0)
                balance = account.getBalance();
            balance += account.calculateInterest();
        }
        return balance;
    }

    public double accelerateTime(long idClient, long idAccount, int days, double balance) {

        for (long i = 0; i < days; i++) {
            AbstractAccount account = clientAccounts.get(idClient).get(idAccount);
            balance += account.calculateInterest();
        }
        return balance;
    }
}