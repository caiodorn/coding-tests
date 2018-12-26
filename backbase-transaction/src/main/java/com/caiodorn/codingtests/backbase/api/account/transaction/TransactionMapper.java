package com.caiodorn.codingtests.backbase.api.account.transaction;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TransactionMapper {

    public Transaction fromTransactionNode(JsonNode transactionNode) {
        Transaction transaction = new Transaction();
        transaction.setId(getText(transactionNode.get("id")));
        fromThisAccountNode(transaction, transactionNode.get("this_account"));
        fromOtherAccountNode(transaction, transactionNode.get("other_account"));
        fromDetailsNode(transaction, transactionNode.get("details"));

        return transaction;
    }

    private void fromThisAccountNode(Transaction transaction, JsonNode thisAccountNode) {
        if (thisAccountNode != null) {
            transaction.setAccountId(getText(thisAccountNode.get("id")));
        }
    }

    private void fromOtherAccountNode(Transaction transaction, JsonNode otherAccountNode) {
        if (otherAccountNode != null) {
            transaction.setCounterpartyAccount(getText(otherAccountNode.get("number")));
            fromHolderNode(transaction, otherAccountNode.get("holder"));
            fromMetadataNode(transaction, otherAccountNode.get("metadata"));
        }
    }

    private void fromHolderNode(Transaction transaction, JsonNode holderNode) {
        if (holderNode != null) {
            transaction.setCounterpartyName(getText(holderNode.get("name")));
        }
    }

    private void fromMetadataNode(Transaction transaction, JsonNode metadataNode) {
        if (metadataNode != null) {
            transaction.setCounterPartyLogoPath(getText(metadataNode.get("image_URL")));
        }
    }

    private void fromDetailsNode(Transaction transaction, JsonNode detailsNode) {
        if (detailsNode != null) {
            transaction.setTransactionType(getText(detailsNode.get("type")));
            transaction.setDescription(getText(detailsNode.get("description")));
            fromValueNode(transaction, detailsNode.get("value"));
        }
    }

    private void fromValueNode(Transaction transaction, JsonNode valueNode) {
        if (valueNode != null) {
            transaction.setInstructedAmount(getAmountValue(valueNode.get("amount")));
            transaction.setInstructedCurrency(getText(valueNode.get("currency")));
            transaction.setTransactionAmount(getAmountValue(valueNode.get("amount")));
            transaction.setTransactionCurrency(getText(valueNode.get("currency")));
        }
    }

    private BigDecimal getAmountValue(JsonNode node) {
        final String text = getText(node);
        BigDecimal amount = null;

        if (isValidAmount(text)) {
            amount = new BigDecimal(text).setScale(2, RoundingMode.UNNECESSARY);
        }

        return amount;
    }

    private boolean isValidAmount(String text) {
        return StringUtils.hasText(text) && !("+".equals(text) || "-".equals(text));
    }

    private String getText(JsonNode node) {
        String value = null;

        if (!(node == null || node.isNull())) {
            value = node.asText();
        }

        return value;
    }

}
