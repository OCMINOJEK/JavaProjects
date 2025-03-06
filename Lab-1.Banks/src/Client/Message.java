package Client;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс Message представляет сообщение, которое может быть отправлено клиенту.
 */
@AllArgsConstructor
@Data
public class Message {

    /**
     * Текст сообщения.
     */
    private String message;

    /**
     * Отправитель сообщения.
     */
    private String sender;
}