package Client;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс lab1.main.Client представляет клиента банка.
 */
@Data
@Builder
public class Client {

    /**
     * Имя клиента.
     */
    @NonNull
    private final String firstName;

    /**
     * Фамилия клиента.
     */
    @NonNull
    private final String lastName;

    /**
     * Адрес клиента.
     */
    @Builder.Default
    private String address = null;

    /**
     * Паспортные данные клиента.
     */
    @Builder.Default
    private String passportData = null;

    /**
     * Список сообщений для клиента.
     */
    @Builder.Default
    private List<String> messages = new ArrayList<String>();


}