package io.seoLeir.assosiation;

import java.util.Arrays;

public enum Operator {
    ADDITION("+", 0, Associativity.LEFT),

    SUBTRACTION("-", 0, Associativity.LEFT),

    DIVISION("/", 5, Associativity.LEFT),

    MULTIPLICATION("*", 5, Associativity.LEFT),

    LEFT_PARENTHESIS("(", 15, null),

    RIGHT_PARENTHESIS(")", 15, null),

    POWER("^", 10, Associativity.RIGHT);

    private final int precedence;

    private final String symbol;

    private Associativity associativity;

    Operator(String symbol, int precedence, Associativity associativity) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.associativity = associativity;
    }

    public int getPrecedence() {
        return precedence;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Operator getFunctionOperation(String operation) {
        return Arrays.stream(Operator.values())
                .filter(operator -> operator.getSymbol().equals(operation))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
