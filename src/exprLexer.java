// Generated from C:/Users/Ben/Documents/Asignment1/src/expr.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class exprLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT=1, FLOAT=2, STRING=3, PLUS=4, MINUS=5, MULTIPLY=6, DIVIDE=7, ASSIGN=8, 
		PRINT=9, FOR=10, IN_RANGE=11, WHILE=12, IF=13, EQUAL=14, NOT_EQUAL=15, 
		GREATER=16, LESS=17, GREATER_EQUAL=18, LESS_EQUAL=19, WS=20, VARIABLE=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INT", "FLOAT", "STRING", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "ASSIGN", 
			"PRINT", "FOR", "IN_RANGE", "WHILE", "IF", "EQUAL", "NOT_EQUAL", "GREATER", 
			"LESS", "GREATER_EQUAL", "LESS_EQUAL", "WS", "VARIABLE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'+'", "'-'", "'*'", "'/'", "'='", "'print>>'", 
			"'for'", "'in range'", "'while'", "'if'", "'=='", "'!='", "'>'", "'<'", 
			"'>='", "'<='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT", "FLOAT", "STRING", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", 
			"ASSIGN", "PRINT", "FOR", "IN_RANGE", "WHILE", "IF", "EQUAL", "NOT_EQUAL", 
			"GREATER", "LESS", "GREATER_EQUAL", "LESS_EQUAL", "WS", "VARIABLE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public exprLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "expr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 20:
			VARIABLE_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void VARIABLE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 getText().matches("^(?!if$|while$|for$).*$");
			break;
		}
	}

	public static final String _serializedATN =
		"\u0004\u0000\u0015\u0093\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0001\u0000\u0004\u0000-\b\u0000\u000b\u0000\f\u0000.\u0001\u0001\u0004"+
		"\u00012\b\u0001\u000b\u0001\f\u00013\u0001\u0001\u0001\u0001\u0005\u0001"+
		"8\b\u0001\n\u0001\f\u0001;\t\u0001\u0001\u0001\u0001\u0001\u0004\u0001"+
		"?\b\u0001\u000b\u0001\f\u0001@\u0003\u0001C\b\u0001\u0001\u0002\u0001"+
		"\u0002\u0005\u0002G\b\u0002\n\u0002\f\u0002J\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0004\u0013\u0087\b\u0013\u000b\u0013\f\u0013\u0088\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0004\u0014\u008e\b\u0014\u000b\u0014\f"+
		"\u0014\u008f\u0001\u0014\u0001\u0014\u0001H\u0000\u0015\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010"+
		"!\u0011#\u0012%\u0013\'\u0014)\u0015\u0001\u0000\u0003\u0001\u000009\u0003"+
		"\u0000\t\n\r\r  \u0002\u0000AZaz\u009a\u0000\u0001\u0001\u0000\u0000\u0000"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000"+
		"\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000"+
		"\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017"+
		"\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b"+
		"\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f"+
		"\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000"+
		"\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000"+
		"\u0000\u0000)\u0001\u0000\u0000\u0000\u0001,\u0001\u0000\u0000\u0000\u0003"+
		"B\u0001\u0000\u0000\u0000\u0005D\u0001\u0000\u0000\u0000\u0007M\u0001"+
		"\u0000\u0000\u0000\tO\u0001\u0000\u0000\u0000\u000bQ\u0001\u0000\u0000"+
		"\u0000\rS\u0001\u0000\u0000\u0000\u000fU\u0001\u0000\u0000\u0000\u0011"+
		"W\u0001\u0000\u0000\u0000\u0013_\u0001\u0000\u0000\u0000\u0015c\u0001"+
		"\u0000\u0000\u0000\u0017l\u0001\u0000\u0000\u0000\u0019r\u0001\u0000\u0000"+
		"\u0000\u001bu\u0001\u0000\u0000\u0000\u001dx\u0001\u0000\u0000\u0000\u001f"+
		"{\u0001\u0000\u0000\u0000!}\u0001\u0000\u0000\u0000#\u007f\u0001\u0000"+
		"\u0000\u0000%\u0082\u0001\u0000\u0000\u0000\'\u0086\u0001\u0000\u0000"+
		"\u0000)\u008d\u0001\u0000\u0000\u0000+-\u0007\u0000\u0000\u0000,+\u0001"+
		"\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000"+
		"./\u0001\u0000\u0000\u0000/\u0002\u0001\u0000\u0000\u000002\u0007\u0000"+
		"\u0000\u000010\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000031\u0001"+
		"\u0000\u0000\u000034\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u0000"+
		"59\u0005.\u0000\u000068\u0007\u0000\u0000\u000076\u0001\u0000\u0000\u0000"+
		"8;\u0001\u0000\u0000\u000097\u0001\u0000\u0000\u00009:\u0001\u0000\u0000"+
		"\u0000:C\u0001\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000<>\u0005.\u0000"+
		"\u0000=?\u0007\u0000\u0000\u0000>=\u0001\u0000\u0000\u0000?@\u0001\u0000"+
		"\u0000\u0000@>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000AC\u0001"+
		"\u0000\u0000\u0000B1\u0001\u0000\u0000\u0000B<\u0001\u0000\u0000\u0000"+
		"C\u0004\u0001\u0000\u0000\u0000DH\u0005\"\u0000\u0000EG\t\u0000\u0000"+
		"\u0000FE\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HI\u0001\u0000"+
		"\u0000\u0000HF\u0001\u0000\u0000\u0000IK\u0001\u0000\u0000\u0000JH\u0001"+
		"\u0000\u0000\u0000KL\u0005\"\u0000\u0000L\u0006\u0001\u0000\u0000\u0000"+
		"MN\u0005+\u0000\u0000N\b\u0001\u0000\u0000\u0000OP\u0005-\u0000\u0000"+
		"P\n\u0001\u0000\u0000\u0000QR\u0005*\u0000\u0000R\f\u0001\u0000\u0000"+
		"\u0000ST\u0005/\u0000\u0000T\u000e\u0001\u0000\u0000\u0000UV\u0005=\u0000"+
		"\u0000V\u0010\u0001\u0000\u0000\u0000WX\u0005p\u0000\u0000XY\u0005r\u0000"+
		"\u0000YZ\u0005i\u0000\u0000Z[\u0005n\u0000\u0000[\\\u0005t\u0000\u0000"+
		"\\]\u0005>\u0000\u0000]^\u0005>\u0000\u0000^\u0012\u0001\u0000\u0000\u0000"+
		"_`\u0005f\u0000\u0000`a\u0005o\u0000\u0000ab\u0005r\u0000\u0000b\u0014"+
		"\u0001\u0000\u0000\u0000cd\u0005i\u0000\u0000de\u0005n\u0000\u0000ef\u0005"+
		" \u0000\u0000fg\u0005r\u0000\u0000gh\u0005a\u0000\u0000hi\u0005n\u0000"+
		"\u0000ij\u0005g\u0000\u0000jk\u0005e\u0000\u0000k\u0016\u0001\u0000\u0000"+
		"\u0000lm\u0005w\u0000\u0000mn\u0005h\u0000\u0000no\u0005i\u0000\u0000"+
		"op\u0005l\u0000\u0000pq\u0005e\u0000\u0000q\u0018\u0001\u0000\u0000\u0000"+
		"rs\u0005i\u0000\u0000st\u0005f\u0000\u0000t\u001a\u0001\u0000\u0000\u0000"+
		"uv\u0005=\u0000\u0000vw\u0005=\u0000\u0000w\u001c\u0001\u0000\u0000\u0000"+
		"xy\u0005!\u0000\u0000yz\u0005=\u0000\u0000z\u001e\u0001\u0000\u0000\u0000"+
		"{|\u0005>\u0000\u0000| \u0001\u0000\u0000\u0000}~\u0005<\u0000\u0000~"+
		"\"\u0001\u0000\u0000\u0000\u007f\u0080\u0005>\u0000\u0000\u0080\u0081"+
		"\u0005=\u0000\u0000\u0081$\u0001\u0000\u0000\u0000\u0082\u0083\u0005<"+
		"\u0000\u0000\u0083\u0084\u0005=\u0000\u0000\u0084&\u0001\u0000\u0000\u0000"+
		"\u0085\u0087\u0007\u0001\u0000\u0000\u0086\u0085\u0001\u0000\u0000\u0000"+
		"\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000"+
		"\u0088\u0089\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000"+
		"\u008a\u008b\u0006\u0013\u0000\u0000\u008b(\u0001\u0000\u0000\u0000\u008c"+
		"\u008e\u0007\u0002\u0000\u0000\u008d\u008c\u0001\u0000\u0000\u0000\u008e"+
		"\u008f\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u008f"+
		"\u0090\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091"+
		"\u0092\u0006\u0014\u0001\u0000\u0092*\u0001\u0000\u0000\u0000\t\u0000"+
		".39@BH\u0088\u008f\u0002\u0006\u0000\u0000\u0001\u0014\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}