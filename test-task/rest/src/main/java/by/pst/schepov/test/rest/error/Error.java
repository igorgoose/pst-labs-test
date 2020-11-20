package by.pst.schepov.test.rest.error;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The {@code Error} class is used to send com.epam.esm.schepov.rest.error messages to the user
 * in a convenient way. Includes a {@link String} containing the com.epam.esm.schepov.rest.error message
 * and the com.epam.esm.schepov.rest.error code.
 *
 * @author Igor Schepov
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class Error {

    private final int errorCode;
    private final String errorMessage;



}
