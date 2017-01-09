import javafx.util.Pair;
import structures.FA;
import structures.Grammar;

import java.util.*;

/**
 * Created by dianamohanu on 14/11/2016.
 */
public class Console {
    private static Scanner input = new Scanner(System.in);

    private Controller controller = new Controller();

    public Console() {
        Grammar g = new Grammar();
        FA fa = new FA();
        this.controller.setGrammar(g);
        this.controller.setAutomata(fa);
    }

    public void printMainMenu() {
        System.out.println("1 - Grammar");
        System.out.println("2 - Finite Automata");
        System.out.println("3 - Given a regular grammar construct the corresponding finite automaton");
        System.out.println("4 - Given a finite automaton construct the corresponding regular grammar");

        int selection1 = input.nextInt();

        switch (selection1) {
            case 1:
                printGrammarMenu();
                break;
            case 2:
                printFiniteAutomataMenu();
                break;
            case 3:
                try {
                    FA fa = controller.transformGrammarInFiniteAutomata();
                    System.out.println("Grammar is regular. Corresponding finite automaton: ");
                    System.out.println("States: " + fa.getStates().toString());
                    System.out.println("Alphabet: " + fa.getAlphabet().toString());
                    System.out.println("Transitions: " + fa.getTransitions());
                    System.out.println("Initial state: " + fa.getInitialState());
                    System.out.println("Final states: " + fa.getFinalStates());
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                printMainMenu();
                break;
            case 4:
                Grammar grammar = controller.transformFiniteAutomataInGrammar();
                System.out.println("Corresponding grammar: ");
                System.out.println("Non-terminals: " + grammar.getSetOfNonterminals());
                System.out.println("Terminals: " + grammar.getSetOfTerminals());
                System.out.println("Productions: ");
                grammar.productionsToString(grammar.getProductions());
                System.out.println("Start symbol: " + grammar.getStartSymbol());
                printMainMenu();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void printDisplayGrammarMenu() {
        System.out.println("0 - Back");
        System.out.println("1 - set of non-terminals");
        System.out.println("2 - set of terminals");
        System.out.println("3 - set of productions");
        System.out.println("4 - the productions of a given non-terminal symbol");

        int selection = input.nextInt();

        switch (selection) {
            case 0:
                printGrammarMenu();
                break;
            case 1:
                System.out.println(controller.getGrammar().getSetOfNonterminals().toString());
                printDisplayGrammarMenu();
                break;
            case 2:
                System.out.println(controller.getGrammar().getSetOfTerminals().toString());
                printDisplayGrammarMenu();
                break;
            case 3:
                controller.getGrammar().productionsToString(controller.getGrammar().getProductions());
                printDisplayGrammarMenu();
                break;
            case 4:
                System.out.println("Give non-terminal symbol: ");
                String symbol = input.next();
                controller.getGrammar().productionsToString(controller.getProductionsForNonTerminalSymbol(symbol));
                printDisplayGrammarMenu();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void printGrammarMenu() {
        System.out.println("0 - Back");
        System.out.println("1 - Read grammar from file");
        System.out.println("2 - Read grammar from keyboard");
        System.out.println("3 - Display the elements of grammar");
        System.out.println("4 - Verify if the grammar is regular");

        int selection2 = input.nextInt();

        switch (selection2) {
            case 0:
                printMainMenu();
                break;
            case 1:
                controller.readGrammarFromFile();
                System.out.println("Grammar read successfully!");
                printGrammarMenu();
                break;
            case 2:
                System.out.println("Number of non-terminals: ");
                int numberN = input.nextInt();
                Set<String> nonterminals = new HashSet<>();
                for (int i=0; i<numberN; i++) {
                    System.out.println("Give non-terminal: ");
                    String nonterminal = input.next();
                    nonterminals.add(nonterminal);
                }

                System.out.println("Number of terminals: ");
                int numberT = input.nextInt();
                Set<String> terminals = new HashSet<>();
                for (int i=0; i<numberT; i++) {
                    System.out.println("Give terminal: ");
                    String terminal = input.next();
                    terminals.add(terminal);
                }

                System.out.println("Number of productions: ");
                int numberP = input.nextInt();
                List<Pair<String, String>> productions = new ArrayList<>();
                for (int i=0; i<numberP; i++) {
                    System.out.println("Give production left: ");
                    String left = input.next();
                    System.out.println("Give production right (\"\" for empty set): ");
                    String right = input.next();
                    productions.add(new Pair<>(left, right));
                }

                System.out.println("Start symbol: ");
                String startSymbol = input.next();

                controller.readGrammarFromKeyboard(nonterminals, terminals, productions, startSymbol);

                System.out.println("Grammar read successfully!");
                printGrammarMenu();
                break;
            case 3:
                printDisplayGrammarMenu();
                break;
            case 4:
                System.out.println(controller.verifyIfRegularGrammar());
                printGrammarMenu();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void printDisplayAutomataMenu() {
        System.out.println("0 - Back");
        System.out.println("1 - set of states");
        System.out.println("2 - alphabet");
        System.out.println("3 - transitions");
        System.out.println("4 - set of final states");

        int selection = input.nextInt();

        switch (selection) {
            case 0:
                printFiniteAutomataMenu();
                break;
            case 1:
                System.out.println(controller.getAutomata().getStates().toString());
                printDisplayAutomataMenu();
                break;
            case 2:
                System.out.println(controller.getAutomata().getAlphabet().toString());
                printDisplayAutomataMenu();
                break;
            case 3:
                System.out.println(controller.getAutomata().getTransitions().toString());
                printDisplayAutomataMenu();
                break;
            case 4:
                System.out.println(controller.getAutomata().getFinalStates().toString());
                printDisplayAutomataMenu();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void printFiniteAutomataMenu() {
        System.out.println("0 - Back");
        System.out.println("1 - Read finite automata from file");
        System.out.println("2 - Read finite automata from keyboard");
        System.out.println("3 - Display the elements of finite automata");

        int selection3 = input.nextInt();

        switch (selection3) {
            case 0:
                printMainMenu();
                break;
            case 1:
                controller.readFiniteAutomataFromFile();
                System.out.println("Finite Automata read successfully!");
                printFiniteAutomataMenu();
                break;
            case 2:
                System.out.println("States: ");
                String states = input.next();

                System.out.println("Alphabet: ");
                String alphabet = input.next();

                System.out.println("Number of transitions: ");
                int numberT = input.nextInt();
                List<String> transitions = new ArrayList<>();
                for (int i=0; i<numberT; i++) {
                    System.out.println("Give transition left: ");
                    String left = input.next();
                    System.out.println("Give transition right (\"\" for empty set): ");
                    String right = input.next();
                    transitions.add("(".concat(left).concat(")").concat("->").concat("{").concat(right).concat("}"));
                }

                System.out.println("Initial state: ");
                String initialState = input.next();

                System.out.println("Final states: ");
                String finalStates = input.next();

                controller.readFiniteAutomataFromKeyboard(states, alphabet, transitions, initialState, finalStates);

                break;
            case 3:
                printDisplayAutomataMenu();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}
