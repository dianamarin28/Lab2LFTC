import javafx.util.Pair;
import structures.FA;
import structures.Grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by dianamohanu on 14/11/2016.
 */
public class Controller {
    private Grammar grammar;
    private FA automata;

    public Grammar getGrammar() {
        return grammar;
    }

    public FA getAutomata() {
        return automata;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public void setAutomata(FA automata) {
        this.automata = automata;
    }

    private List<String> readFromFile(String filename) {
        List<String> sourceCode = new ArrayList<>();

        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sourceCode.add(line);
            }

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sourceCode;
    }

    public void readGrammarFromFile() {
        List<String> grammarFromFile = readFromFile("Grammar.txt");

        Set<String> nonterminals = new HashSet<>();
        Set<String> terminals = new HashSet<>();
        List<Pair<String, String>> productions = new ArrayList<>();

        for (int i=0; i<grammarFromFile.size(); i++) {
            StringTokenizer tokens = new StringTokenizer(grammarFromFile.get(i), ",", false);

            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();

                if (i == 0) {
                    token = token.replaceAll("\\s+","");
                    nonterminals.add(token);
                }
                else if (i == 1) {
                    token = token.replaceAll("\\s+","");
                    terminals.add(token);
                }
                else if (i == 2) {
                    StringTokenizer tokensP = new StringTokenizer(token, "->", false);

                    String left = tokensP.nextToken();
                    left = left.replaceAll("\\s+","");

                    String right = tokensP.nextToken();
                    right = right.replaceAll("\\s+","");

                    productions.add(new Pair<>(left, right));
                }
                else if (i == 3) {
                    grammar.setStartSymbol(token);
                }
            }
        }

        grammar.setSetOfNonterminals(nonterminals);
        grammar.setSetOfTerminals(terminals);
        grammar.setProductions(productions);
    }

    public void readFiniteAutomataFromFile() {
        List<String> automataFromFile = readFromFile("FiniteAutomata.txt");

        Set<String> states = new HashSet<>();
        Set<String> alphabet = new HashSet<>();
        List<String> transitions = new ArrayList<>();
        Set<String> finalStates = new HashSet<>();

        for (int i=0; i<automataFromFile.size(); i++) {
            StringTokenizer tokens = new StringTokenizer(automataFromFile.get(i), ",", false);

            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();

                if (i == 0) {
                    token = token.replaceAll("\\s+","");
                    states.add(token);
                }
                else if (i == 1) {
                    token = token.replaceAll("\\s+","");
                    alphabet.add(token);
                }
                else if (i == 3) {
                    automata.setInitialState(token);
                }
                else if (i == 4) {
                    token = token.replaceAll("\\s+","");
                    finalStates.add(token);
                }
            }
        }

        StringTokenizer tokens = new StringTokenizer(automataFromFile.get(2), ";", false);
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();

            if (Character.isWhitespace(token.charAt(0))) {
                StringBuilder sb = new StringBuilder(token);
                sb.deleteCharAt(0);
                token = sb.toString();
            }
            transitions.add(token);
        }

        automata.setStates(states);
        automata.setAlphabet(alphabet);
        automata.setTransitions(transitions);
        automata.setFinalStates(finalStates);
    }

    public List<Pair<String, String>> getProductionsForNonTerminalSymbol(String symbol) {
        List<Pair<String, String>> productionsForSymbol = new ArrayList<>();

        for (Pair<String, String> production : grammar.getProductions()) {
            if (production.getKey().equals(symbol)) {
                productionsForSymbol.add(production);
            }
        }

        return productionsForSymbol;
    }

    public void readGrammarFromKeyboard(Set<String> nonterminals, Set<String> terminals, List<Pair<String, String>> productions, String startSymbol) {
        grammar.setSetOfNonterminals(nonterminals);
        grammar.setSetOfTerminals(terminals);
        grammar.setProductions(productions);
        grammar.setStartSymbol(startSymbol);
    }

    public void readFiniteAutomataFromKeyboard(String states, String alphabet, List<String> transitions, String initialState, String finalStates) {
        automata.setTransitions(transitions);
        automata.setInitialState(initialState);

        Set<String> statesSet = new HashSet<>();
        Set<String> alphabetSet = new HashSet<>();
        Set<String> finalStatesSet = new HashSet<>();

        StringTokenizer tokens1 = new StringTokenizer(states, ",", false);
        while (tokens1.hasMoreTokens()) {
            String token = tokens1.nextToken();
            token = token.replaceAll("\\s+","");
            statesSet.add(token);
        }

        StringTokenizer tokens2 = new StringTokenizer(alphabet, ",", false);
        while (tokens2.hasMoreTokens()) {
            String token = tokens2.nextToken();
            token = token.replaceAll("\\s+","");
            alphabetSet.add(token);
        }

        StringTokenizer tokens3 = new StringTokenizer(finalStates, ",", false);
        while (tokens3.hasMoreTokens()) {
            String token = tokens3.nextToken();
            token = token.replaceAll("\\s+","");
            finalStatesSet.add(token);
        }

        automata.setStates(statesSet);
        automata.setAlphabet(alphabetSet);
        automata.setFinalStates(finalStatesSet);
    }

    private boolean isTerminal(char c) {
        for (String terminal : grammar.getSetOfTerminals()) {
            if (terminal.equals(String.valueOf(c))) {
                return true;
            }
        }

        if (String.valueOf(c).equals("\"")) {
            return true;
        }

        return false;
    }

    private boolean isNonterminal(char c) {
        for (String nonterminal : grammar.getSetOfNonterminals()) {
            if (nonterminal.equals(String.valueOf(c))) {
                return true;
            }
        }

        if (String.valueOf(c).equals("\"")) {
            return true;
        }

        return false;
     }

    private boolean checkIfStartGoesToEpsilon() {
        List<Pair<String, String>> startProductions = getProductionsForNonTerminalSymbol(grammar.getStartSymbol());
        for (Pair p : startProductions) {
            if (p.getValue().equals("\"\"")) {
                return true;
            }
        }
        return false;
    }

    private boolean startInRight() {
        for (Pair p : grammar.getProductions()) {
            String[] cArray = p.getValue().toString().split("(?!^)");

            for (int i=0; i<cArray.length; i++) {
                if (cArray[i].equals(grammar.getStartSymbol())) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean noOtherGoesToEpsilon() {
        for (String nonterminal : grammar.getSetOfNonterminals()) {
            if (!nonterminal.equals(grammar.getStartSymbol())) {
                for (Pair p : getProductionsForNonTerminalSymbol(nonterminal)) {
                    if (p.getValue().equals("\"\"")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean verifyIfRegularGrammar() {
//        verify if RIGHT LINEAR GRAMMAR
        for (Pair p : grammar.getProductions()) {
            StringTokenizer tokens = new StringTokenizer(p.getValue().toString(), "|", false);
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                if (!isTerminal(token.charAt(0))) {
                    return false;
                }
                if (token.length() > 1) {
                    if (!isNonterminal(token.charAt(token.length()-1))) {
                        return false;
                    }
                }
                if (token.length() > 2) {
                    return false;
                }
            }
        }

//        verify Epsilon Rule
        if (checkIfStartGoesToEpsilon()) {
            if (startInRight()) {
                return false;
            }

            if (!noOtherGoesToEpsilon()) {
                return false;
            }
        }

        return true;
    }

    private boolean isTerminal2(char c) {
        for (String terminal : grammar.getSetOfTerminals()) {
            if (terminal.equals(String.valueOf(c))) {
                return true;
            }
        }

        return false;
    }

    public FA transformGrammarInFiniteAutomata() throws Exception {
        FA fa = new FA();

        if (!verifyIfRegularGrammar()) {
            throw new Exception("Grammar must be regular!");
        }

        Set<String> setOfNonterminals = grammar.getSetOfNonterminals();
        int count = 1;
        String c = "";
        while(count != 0) {
            count = 0;
            Random r = new Random();
            c = String.valueOf((char)(r.nextInt(26) + 'a')).toUpperCase();

            for (String nonterminal : setOfNonterminals) {
                if (nonterminal.equals(String.valueOf(c))) {
                    count = 1;
                }
            }
        }
        setOfNonterminals.add(c);
        fa.setStates(setOfNonterminals);

        fa.setAlphabet(grammar.getSetOfTerminals());

        fa.setInitialState(grammar.getStartSymbol());

        Set<String> finalStates = new HashSet<>();
        if (checkIfStartGoesToEpsilon()) {
            finalStates.add(grammar.getStartSymbol());
        }
        finalStates.add(c);
        fa.setFinalStates(finalStates);

        Map<Pair<String, String>, String> transitions= new HashMap<>();
        for (String nonterminal : grammar.getSetOfNonterminals()) {
            for (String terminal : grammar.getSetOfTerminals()) {
                transitions.put(new Pair<>(nonterminal, terminal), "\"\"");
            }

            List<Pair<String, String>> productionsForNonterminal = getProductionsForNonTerminalSymbol(nonterminal);
            for (Pair p : productionsForNonterminal) {
                StringTokenizer tokens = new StringTokenizer(p.getValue().toString(), "|", false);
                while (tokens.hasMoreTokens()) {
                    String token = tokens.nextToken();

                    if (isTerminal2(token.charAt(0))) {
                        if (!transitions.get(new Pair<>(nonterminal, String.valueOf(token.charAt(0)))).equals("\"\"")) {
                            String values = transitions.get(new Pair<>(nonterminal, String.valueOf(token.charAt(0))));
                            if (token.length() > 1) {
                                transitions.put(new Pair<>(nonterminal, String.valueOf(token.charAt(0))), values.concat(",").concat(String.valueOf(token.charAt(1))));
                            }
                            else {
                                transitions.put(new Pair<>(nonterminal, String.valueOf(token.charAt(0))), values.concat(",").concat(nonterminal));
                            }
                        }
                        else {
                            if (token.length() > 1) {
                                transitions.put(new Pair<>(nonterminal, String.valueOf(token.charAt(0))), String.valueOf(token.charAt(1)));
                            }
                            else {
                                transitions.put(new Pair<>(nonterminal, String.valueOf(token.charAt(0))), nonterminal);
                            }
                        }
                    }
                }
            }
        }
        List<String> transitionsToList = new ArrayList<>();
        for (Map.Entry e : transitions.entrySet()) {
            String s = new String();

            Pair p = (Pair) e.getKey();
            s += "(" + p.getKey() + "," + p.getValue() + ")";

            s += "->{" + e.getValue() + "}";

            transitionsToList.add(s);
        }
        fa.setTransitions(transitionsToList);

        return fa;
    }

    public Grammar transformFiniteAutomataInGrammar() {
        Grammar grammar = new Grammar();

        grammar.setSetOfNonterminals(automata.getStates());

        grammar.setSetOfTerminals(automata.getAlphabet());

        grammar.setStartSymbol(automata.getInitialState());

        List<Pair<String, String>> productionsAux = new ArrayList<>();
        List<Pair<String, String>> usedAlphabetElems = new ArrayList<>();
        List<String> transitions = automata.getTransitions();
        for (String t : transitions) {
            StringTokenizer tokens = new StringTokenizer(t, ",->(){}", false);
            List<String> tokensL = new ArrayList<>();
            while (tokens.hasMoreTokens()) {
                tokensL.add(tokens.nextToken().toString());
            }

            for (int i = 2; i < tokensL.size(); i++) {
                Pair p = new Pair(tokensL.get(0), tokensL.get(1).concat(tokensL.get(i)));
                productionsAux.add(p);
                usedAlphabetElems.add(new Pair(tokensL.get(0), tokensL.get(1)));
            }
        }

        if (checkIfStartGoesToEpsilon()) {
            Pair initialStateEpsilonProduction = new Pair(automata.getInitialState(), "\"\"");
            productionsAux.add(initialStateEpsilonProduction);
        }

        for (String finalState : automata.getFinalStates()) {
            for (String alphabet : automata.getAlphabet()) {
                for (Pair p : usedAlphabetElems) {
                    if (finalState.equals(p.getKey())) {
                        if (alphabet.equals(p.getValue())) {
                            productionsAux.add(new Pair<>(finalState, alphabet));
                        }
                    }
                }
            }

        }

        grammar.setProductions(productionsAux);

        return grammar;
    }

}