package Bank;

import Account.Transaction;
import Exception.FieldNotFound;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс CentralBank представляет центральный банк, который управляет всеми банками и предоставляет необходимый функционал для взаимодействия между банками.
 */
@Data
public class CentralBank {

    /**
     * Карта, содержащая все банки, которые находятся в управлении центрального банк.
     */
    @NonNull
    private Map<String, Bank> banks;

    /**
     * Список транзакций, которые были совершены между банками.
     */
    private List<Account.Transaction> transactions;

    /**
     * Конструктор, создающий новый центральный банк.
     */
    public CentralBank() {
        banks = new HashMap<>();
        transactions = new ArrayList<>();
    }

    /**
     * Метод добавляет банк в карту банков центрального банка.
     *
     * @param bank банк, который нужно добавить
     */
    public void addBank(Bank bank) {
        banks.put(bank.getName(), bank);
    }

    /**
     * Метод удаляет банк из карты банков центрального банка.
     *
     * @param name имя банка, который нужно удалить
     */
    public void removeBank(@NonNull String name) {
        if (findBankByName(name) == null)
            throw new FieldNotFound("Bank is not found");
        banks.remove(name);
    }

    /**
     * Метод находит банк по его имени.
     *
     * @param name имя банка, который нужно найти
     * @return банк, если он найден, иначе выбрасывается исключение FieldNotFound
     */
    public Bank findBankByName(@NonNull String name) {
        if (banks.get(name) == null)
            throw new FieldNotFound("Bank is not found");
        return banks.get(name);
    }

    /**
     * Метод переводит деньги между счетами в разных банках.
     *
     * @param senderBankName    имя банка отправителя
     * @param receiverBankName  имя банка получателя
     * @param senderClientId    идентификатор клиента-отправителя
     * @param senderAccountId  идентификатор счета-отправителя
     * @param receiverClientId  идентификатор клиента-получателя
     * @param receiverAccountId идентификатор счета-получателя
     * @param amount           сумма перевода
     */
    public void transferMoney(String senderBankName, String receiverBankName, long senderClientId, long senderAccountId, long receiverClientId, long receiverAccountId, double amount){
        if( findBankByName(senderBankName) == null)
            throw new FieldNotFound("senderBankName is not found");
        if( findBankByName(receiverBankName) == null)
            throw new FieldNotFound("receiverBankName is not found");

        Bank senderBank = findBankByName(senderBankName);
        Bank receiverBank = findBankByName(receiverBankName);

        senderBank.findAccountById(senderClientId, senderAccountId).withdrawAdmin(amount);
        receiverBank.findAccountById(receiverClientId, receiverAccountId).deposit(amount);
        Transaction transaction = new Transaction(amount, receiverBankName ,senderBankName,senderClientId,senderAccountId,receiverClientId,receiverAccountId);
        transactions.add(transaction);
    }
    /**
     \* Отменяет транзакцию по ее идентификатору.
     \*
     \* @param idTransaction идентификатор транзакции
     */
    public void cancelByIdTransaction(int idTransaction){
        Transaction transaction = transactions.get(idTransaction).reverseTransaction();
        transactions.add(transaction);
        transferMoney(transaction.getReceiverBankName(), transaction.getSenderBankName(), transaction.getSenderClientId(),
                transaction.getSenderAccountId(), transaction.getReceiverClientId(), transaction.getReceiverAccountId(), transaction.getAmount());
    }

    /**
     * Метод уведомляет все банки о необходимости начислить проценты на счета клиентов.
     *
     * @param idClient   идентификатор клиента
     * @param idAccount идентификатор счета
     */
    public void notificationInterestAccrual(long idClient, long idAccount){
        for (Bank bank : banks.values()){
            bank.creditingInterestToBalance(idClient, idAccount);
        }
    }
}