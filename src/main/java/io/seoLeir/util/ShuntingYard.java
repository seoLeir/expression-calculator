package io.seoLeir.util;

import io.seoLeir.assosiation.Associativity;
import io.seoLeir.assosiation.Operator;

import java.util.*;
import java.util.regex.Pattern;

import static io.seoLeir.assosiation.Operator.*;

public final class ShuntingYard {

    private static final Pattern NUMBER_REGEX = Pattern.compile("\\d+");

    private final static List<String> OPS = List.of(
            SUBTRACTION.getSymbol(),
            ADDITION.getSymbol(),
            MULTIPLICATION.getSymbol(),
            DIVISION.getSymbol(),
            POWER.getSymbol()
    );

    public static Double calculate(String expression) {
        String[] expressionAsArray = parseExpressionToStringArray(expression);
        Queue<String> rpn = shuntingYard(expressionAsArray);

        return evalRPN(rpn.toArray(new String[0]));
    }

    private static Queue<String> shuntingYard(String[] tokens) {
        Queue<String> rpn = new LinkedList<>();
        Stack<Operator> operators = new Stack<>();

        for (String token : tokens) {
            if (NUMBER_REGEX.matcher(token).matches()) {
                rpn.add(token);
            } else if (OPS.contains(token)) {
                Operator tokenOperator = getFunctionOperation(token);

                if (!operators.isEmpty()) {
                    Operator topOperator = operators.peek();

                    while (!topOperator.getSymbol().equals("(") &&
                            (topOperator.getPrecedence() > tokenOperator.getPrecedence() ||
                                    (topOperator.getPrecedence() == tokenOperator.getPrecedence()
                                            && tokenOperator.getAssociativity() == Associativity.LEFT))) {
                        rpn.add(topOperator.getSymbol());
                        operators.pop();
                        try {
                            topOperator = operators.peek();
                        } catch (EmptyStackException e) {
                            break;
                        }
                    }

                    operators.push(tokenOperator);
                } else {
                    operators.push(tokenOperator);
                }
            } else if (token.equals("(")) {
                operators.push(getFunctionOperation(token));
            } else if (token.equals(")")) {
                if (!operators.isEmpty()) {
                    Operator topOperator = operators.peek();

                    while (!topOperator.getSymbol().equals("(")) {
                        rpn.add(operators.pop().getSymbol());
                        topOperator = operators.peek();

                        if (topOperator == null) {
                            throw new IllegalArgumentException("Illegal expression");
                        }
                    }

                    operators.pop();
                } else {
                    throw new IllegalArgumentException("Illegal expression");
                }
            }
        }

        if (!operators.isEmpty()) {
            Operator topOperator = operators.peek();

            while (topOperator != null) {
                if (topOperator.getSymbol().equals(")")) {
                    throw new IllegalArgumentException("The parenthesis is missing in the expression");
                }

                rpn.add(topOperator.getSymbol());
                operators.pop();

                try {
                    topOperator = operators.peek();
                } catch (EmptyStackException e) {
                    break;
                }
            }
        }

        return rpn;
    }

    private static String[] parseExpressionToStringArray(String expression) {
        String removedExpression = expression.replaceAll("\\s+", "");
        String[] expressionAsArray = removedExpression.replace("", " ").trim().split(" ");

        List<String> parsedExpression = new ArrayList<>(expressionAsArray.length);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < expressionAsArray.length; i++) {
            if (NUMBER_REGEX.matcher(expressionAsArray[i]).matches()) {
                builder.append(expressionAsArray[i]);
            } else {
                if (builder.length() > 0) {
                    parsedExpression.add(builder.toString());
                    builder.delete(0, builder.length());
                }

                parsedExpression.add(expressionAsArray[i]);
            }
        }

        if (builder.length() > 0) {
            parsedExpression.add(builder.toString());
        }

        return parsedExpression.toArray(new String[0]);
    }

    private static Double evalRPN(String[] expr) {
        LinkedList<Double> stack = new LinkedList<>();

        for (String token : expr) {

            if (token.equals(MULTIPLICATION.getSymbol())) {
                Double secondOperand = stack.pop();
                Double firstOperand = stack.pop();

                stack.push(firstOperand * secondOperand);
            } else if (token.equals(DIVISION.getSymbol())) {
                Double secondOperand = stack.pop();
                Double firstOperand = stack.pop();

                stack.push(firstOperand / secondOperand);
            } else if (token.equals(SUBTRACTION.getSymbol())) {
                Double secondOperand = stack.pop();
                Double firstOperand = stack.pop();

                stack.push(firstOperand - secondOperand);
            } else if (token.equals(ADDITION.getSymbol())) {
                Double secondOperand = stack.pop();
                Double firstOperand = stack.pop();

                stack.push(firstOperand + secondOperand);
            } else if (token.equals(POWER.getSymbol())) {
                Double secondOperand = stack.pop();
                Double firstOperand = stack.pop();

                stack.push(Math.pow(firstOperand, secondOperand));
            } else {
                try {
                    stack.push(Double.parseDouble(token + ""));
                } catch (NumberFormatException e) {
                    System.out.println("\nError: invalid token " + token);
                }
            }
        }
        if (stack.size() > 1) {
            System.out.println("Error, too many operands: " + stack);
        }

        return stack.pop();
    }
}
