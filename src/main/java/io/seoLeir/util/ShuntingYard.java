package io.seoLeir.util;

import io.seoLeir.assosiation.Operator;

import java.util.*;

import static io.seoLeir.assosiation.Associativity.LEFT;
import static io.seoLeir.assosiation.Associativity.RIGHT;

public class ShuntingYard {

    private ShuntingYard() {
        throw new UnsupportedOperationException();
    }

    final static Map<String, Operator> OPS = new HashMap<>();

    static {
        for (Operator operator : Operator.values()) {
            OPS.put(operator.symbol, operator);
        }
    }

    public static List<String> shuntingYard(List<String> tokens) {
        List<String> output = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            if (OPS.containsKey(token)) {
                while (!stack.isEmpty() && OPS.containsKey(stack.peek())) {
                    Operator cOp = OPS.get(token);
                    Operator lOp = OPS.get(stack.peek());
                    if ((cOp.associativity == LEFT && cOp.comparePrecedence(lOp) <= 0) ||
                            (cOp.associativity == RIGHT && cOp.comparePrecedence(lOp) < 0)) {
                        output.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            } else if ("(".equals(token)) {
                stack.push(token);
            } else if (")".equals(token)) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return output;
    }

    public static int evalRPN(String[] tokens) {
        if (tokens.length == 0)
            return -1;
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            }else if (token.equals("^")){
                stack.push((int) Math.pow(stack.pop(), stack.pop()));
            }else if (token.equals("%")){
                int a = stack.pop();
                int b = stack.pop();
                stack.push(a % b);
            }
            else if (token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            }
            else if (token.equals("-")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a - b);
            }
            else if (token.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a / b);
            }
            else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.pop();
    }
}