package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferDTO implements Validator {

    private String senderId;
    private String recipientId;
    private String transferAmount;

    @Override
    public boolean supports(Class<?> clazz) {
        return TransferDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransferDTO transferDTO = (TransferDTO) target;

        String senderId = transferDTO.getSenderId();
        String recipientId = transferDTO.getRecipientId();
        String transferAmount = transferDTO.getTransferAmount();

        if (senderId.length() == 0) {
            errors.rejectValue("senderId", "senderId.null", "Sender ID is required");
        } else {
            if (!senderId.matches("(^$|[0-9]*$)")) {
                errors.rejectValue("senderId", "senderId.matches", "Sender ID accept only number");
            }
        }

        if (recipientId.length() == 0) {
            errors.rejectValue("recipientId", "recipientId.null", "Recipient ID is required");
        } else {
            if (!recipientId.matches("(^$|[0-9]*$)")) {
                errors.rejectValue("recipientId", "recipientId.matches", "Recipient ID accept only number");
            }
        }

        if (transferAmount.length() == 0) {
            errors.rejectValue("transferAmount", "transferAmount.null", "Transfer amount ID is required");
        } else {
            if (!transferAmount.matches("(^$|[0-9]*$)")) {
                errors.rejectValue("transferAmount", "transferAmount.matches", "Transfer amount accept only number");
            }
        }
    }
}
