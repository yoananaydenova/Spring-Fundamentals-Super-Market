package softuni.supermarket.utils;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatorUtil {
    // Метода подаваме ентити и връща дали е валидно или не е
    <E> boolean isValid(E entity);

    // Метода получава ентити и връща всичките негови грешки
    // ConstraintViolation - грешките от констрейтите от анотациите с тяхните съобщения
    <E> Set<ConstraintViolation<E>> violations(E entity);
}
