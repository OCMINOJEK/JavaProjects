package Account;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс Transaction представляет транзакцию между двумя счетами.
 * Этот класс использует библиотеку Lombok для генерации конструктора, геттеров и сеттеров.
 */
@Data
@AllArgsConstructor
public class Transaction {

    /**
     * Сумма транзакции.
     */
    private double amount;

    /**
     * Название банка-отправителя.
     */
    private String senderBankName;

    /**
     * Название банка-получателя.
     */
    private String receiverBankName;

    /**
     * Идентификатор клиента-отправителя.
     */
    private long senderClientId;

    /**
     * Идентификатор счета-отправителя.
     */
    private long senderAccountId;

    /**
     * Идентификатор клиента-получателя.
     */
    private long receiverClientId;

    /**
     * Идентификатор счета-получателя.
     */
    private long receiverAccountId;

    /**
     * Метод reverseTransaction создает новую транзакцию, которая является обратной к текущей транзакции.
     *
     * @return новая транзакция, которая является обратной к текущей транзакции
     */
    public Transaction reverseTransaction(){
        return new Transaction(this.amount, this.receiverBankName, this.senderBankName,
                this.receiverClientId, this.receiverAccountId, this.senderClientId, this.senderAccountId);
    }
}