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
		INT=1, FLOAT=2, STRING=3, VARIABLE=4, PLUS=5, MINUS=6, MULTIPLY=7, DIVIDE=8, 
		ASSIGN=9, PRINT=10, WS=11;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INT", "FLOAT", "STRING", "VARIABLE", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", 
			"ASSIGN", "PRINT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'+'", "'-'", "'*'", "'/'", "'='", "'print>>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT", "FLOAT", "STRING", "VARIABLE", "PLUS", "MINUS", "MULTIPLY", 
			"DIVIDE", "ASSIGN", "PRINT", "WS"
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

	public static final String _serializedATN =
		"\u0004\u0000\u000b^\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000"+
		"\u0004\u0000\u0019\b\u0000\u000b\u0000\f\u0000\u001a\u0001\u0001\u0004"+
		"\u0001\u001e\b\u0001\u000b\u0001\f\u0001\u001f\u0001\u0001\u0001\u0001"+
		"\u0005\u0001$\b\u0001\n\u0001\f\u0001\'\t\u0001\u0001\u0001\u0001\u0001"+
		"\u0004\u0001+\b\u0001\u000b\u0001\f\u0001,\u0003\u0001/\b\u0001\u0001"+
		"\u0002\u0001\u0002\u0005\u00023\b\u0002\n\u0002\f\u00026\t\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0004\u0003;\b\u0003\u000b\u0003\f\u0003"+
		"<\u0001\u0003\u0004\u0003@\b\u0003\u000b\u0003\f\u0003A\u0003\u0003D\b"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0004\nY\b\n\u000b\n"+
		"\f\nZ\u0001\n\u0001\n\u00014\u0000\u000b\u0001\u0001\u0003\u0002\u0005"+
		"\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n"+
		"\u0015\u000b\u0001\u0000\u0003\u0001\u000009\u0002\u0000AZaz\u0003\u0000"+
		"\t\n\r\r  g\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0001\u0018\u0001\u0000\u0000\u0000"+
		"\u0003.\u0001\u0000\u0000\u0000\u00050\u0001\u0000\u0000\u0000\u0007C"+
		"\u0001\u0000\u0000\u0000\tE\u0001\u0000\u0000\u0000\u000bG\u0001\u0000"+
		"\u0000\u0000\rI\u0001\u0000\u0000\u0000\u000fK\u0001\u0000\u0000\u0000"+
		"\u0011M\u0001\u0000\u0000\u0000\u0013O\u0001\u0000\u0000\u0000\u0015X"+
		"\u0001\u0000\u0000\u0000\u0017\u0019\u0007\u0000\u0000\u0000\u0018\u0017"+
		"\u0001\u0000\u0000\u0000\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u0018"+
		"\u0001\u0000\u0000\u0000\u001a\u001b\u0001\u0000\u0000\u0000\u001b\u0002"+
		"\u0001\u0000\u0000\u0000\u001c\u001e\u0007\u0000\u0000\u0000\u001d\u001c"+
		"\u0001\u0000\u0000\u0000\u001e\u001f\u0001\u0000\u0000\u0000\u001f\u001d"+
		"\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000\u0000 !\u0001\u0000"+
		"\u0000\u0000!%\u0005.\u0000\u0000\"$\u0007\u0000\u0000\u0000#\"\u0001"+
		"\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000%#\u0001\u0000\u0000\u0000"+
		"%&\u0001\u0000\u0000\u0000&/\u0001\u0000\u0000\u0000\'%\u0001\u0000\u0000"+
		"\u0000(*\u0005.\u0000\u0000)+\u0007\u0000\u0000\u0000*)\u0001\u0000\u0000"+
		"\u0000+,\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000,-\u0001\u0000"+
		"\u0000\u0000-/\u0001\u0000\u0000\u0000.\u001d\u0001\u0000\u0000\u0000"+
		".(\u0001\u0000\u0000\u0000/\u0004\u0001\u0000\u0000\u000004\u0005\"\u0000"+
		"\u000013\t\u0000\u0000\u000021\u0001\u0000\u0000\u000036\u0001\u0000\u0000"+
		"\u000045\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000057\u0001\u0000"+
		"\u0000\u000064\u0001\u0000\u0000\u000078\u0005\"\u0000\u00008\u0006\u0001"+
		"\u0000\u0000\u00009;\u0007\u0001\u0000\u0000:9\u0001\u0000\u0000\u0000"+
		";<\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000"+
		"\u0000=D\u0001\u0000\u0000\u0000>@\u0007\u0000\u0000\u0000?>\u0001\u0000"+
		"\u0000\u0000@A\u0001\u0000\u0000\u0000A?\u0001\u0000\u0000\u0000AB\u0001"+
		"\u0000\u0000\u0000BD\u0001\u0000\u0000\u0000C:\u0001\u0000\u0000\u0000"+
		"C?\u0001\u0000\u0000\u0000D\b\u0001\u0000\u0000\u0000EF\u0005+\u0000\u0000"+
		"F\n\u0001\u0000\u0000\u0000GH\u0005-\u0000\u0000H\f\u0001\u0000\u0000"+
		"\u0000IJ\u0005*\u0000\u0000J\u000e\u0001\u0000\u0000\u0000KL\u0005/\u0000"+
		"\u0000L\u0010\u0001\u0000\u0000\u0000MN\u0005=\u0000\u0000N\u0012\u0001"+
		"\u0000\u0000\u0000OP\u0005p\u0000\u0000PQ\u0005r\u0000\u0000QR\u0005i"+
		"\u0000\u0000RS\u0005n\u0000\u0000ST\u0005t\u0000\u0000TU\u0005>\u0000"+
		"\u0000UV\u0005>\u0000\u0000V\u0014\u0001\u0000\u0000\u0000WY\u0007\u0002"+
		"\u0000\u0000XW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000ZX\u0001"+
		"\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000"+
		"\\]\u0006\n\u0000\u0000]\u0016\u0001\u0000\u0000\u0000\u000b\u0000\u001a"+
		"\u001f%,.4<ACZ\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}