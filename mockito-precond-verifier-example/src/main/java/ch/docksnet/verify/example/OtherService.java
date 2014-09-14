package ch.docksnet.verify.example;

import javax.validation.constraints.NotNull;

/**
 * @author Stefan Zeller
 */
public class OtherService {
    /**
     * This method throws a NPE if {@code input} is {@code null}.
     *
     * @param input {@code null} is not allowed
     * @return square of input.
     */
    @SuppressWarnings("UnnecessaryUnboxing")
    public long square(@NotNull Long input) {
        return input.longValue() * input.longValue();
    }
}
