package Bank;

/**
 * Перечисление InterestType определяет типы процентных ставок и комиссий, которые используются в банке.
 */
public enum InterestType {
    /**
     * Комиссия.
     */
    COMMISSON,
    /**
     * Процентная ставка.
     */
    INTERESTRATE,
    /**
     * Базовая процентная ставка.
     */
    ELEMENTARYINTERESTRATE,
    /**
     * Обычная процентная ставка.
     */
    NORMALINTERESTRATE,
    /**
     * VIP процентная ставка.
     */
    VIPINTERESTRATE,
    /**
     * Ограничение на операции.
     */
    RESTRICTIONOPERATION,
    /**
     * Кредитный лимит.
     */
    CREDITLIMIT;
}
