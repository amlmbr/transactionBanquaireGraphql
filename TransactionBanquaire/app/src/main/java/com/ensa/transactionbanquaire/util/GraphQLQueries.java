package com.ensa.transactionbanquaire.util;

public class GraphQLQueries {

    public static final String GET_ACCOUNTS_QUERY = "{ allComptes { id, solde, dateCreation, type } }";

    public static final String CREATE_ACCOUNT_MUTATION =
            "mutation { saveCompte(compte: { solde: 1000, type: COURANT }) { id, solde, dateCreation } }";

    public static final String CREATE_TRANSACTION_MUTATION =
            "mutation { saveTransaction(transaction: { montant: 500, type: VIREMENT, compteId: 1 }) { id, montant, date } }";
}
