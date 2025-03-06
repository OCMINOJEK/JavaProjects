/**
 * AbstractAccount - абстрактный класс, представляющий базовый счет в банке.
 *
 */
package Account;

import Client.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AbstractAccount {

    /**
     \* Банк, в котором находится счет.
     */

    /**
     \* Клиент, которому принадлежит счет.
     */
    protected Client client;

    /**
     \* Идентификатор клиента.
     */
    protected long idClient;

    /**
     \* Идентификатор счета.
     */
    protected long idAccount;

    /**
     \* Предельная сумма, которую можно снять с учетной записи.
     */
    protected double restrictionOperation;

    /**
     \* Текущий баланс счета.
     */
    @Getter
    @Setter
    protected double balance;

    /**
     \* Создает новый базовый счет.
     \*
     \* @param bank банк, в котором создается счет
     \* @param client клиент, которому принадлежит счет
     \* @param idClient идентификатор клиента
     \* @param idAccount идентификатор счета
     */
    public AbstractAccount(Client client, long idClient, long idAccount){
        this.client = client;
        this.idClient = idClient;
        this.idAccount = idAccount;
    }

    /**
     \* Снимает указанную сумму со счета.
     \*
     \* @param amount сумма, которую нужно снять
     */
    public void withdraw(double amount) {}

    /**
     \* Снимает указанную сумму администратором.
     \*
     \* @param amount сумма, которую нужно снять
     */
    public void withdrawAdmin(double amount){
        balance -= amount;
    }

    /**
     \* Пополняет счет на указанную сумму.
     \*
     \* @param amount сумма, которую нужно зачислить
     */
    public void deposit(double amount) {}

    /**
     \* Рассчитывает процент по счету.
     \*
     \* @return процент
     */
    public double calculateInterest() {
        return 0;
    }

    /**
     \* Рассчитывает комиссию за операции со счетом.
     \*
     \* @return комиссия
     */
    public double calculateCommission() {
        return 0;
    }

}