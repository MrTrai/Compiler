// Generated from test.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class testLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INTLIT=1, FLOATLIT=2, VAR=3, MAIN=4, ARRAY=5, BREAK=6, DO=7, IF=8, ELSE=9, 
		FOR=10, FUNCTION=11, LET=12, IN=13, OF=14, THEN=15, TO=16, WHILE=17, ENDIF=18, 
		BEGIN=19, END=20, ENDDO=21, RETURN=22, MULT=23, DIV=24, ADD=25, SUB=26, 
		EQUAL=27, LT_GT=28, GT=29, LT=30, GTE=31, LTE=32, AND=33, OR=34, DOT=35, 
		INT=36, FLOAT=37, COMMA=38, COLON=39, SEMI_COLON=40, ASSIGNMENT=41, LPAREN=42, 
		RPAREN=43, LBRACK=44, RBRACK=45, ID=46, WS=47, LINE_COMMENT_SKIP=48, COMMENT_SKIP=49;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INTLIT", "FLOATLIT", "VAR", "MAIN", "ARRAY", "BREAK", "DO", "IF", "ELSE", 
			"FOR", "FUNCTION", "LET", "IN", "OF", "THEN", "TO", "WHILE", "ENDIF", 
			"BEGIN", "END", "ENDDO", "RETURN", "MULT", "DIV", "ADD", "SUB", "EQUAL", 
			"LT_GT", "GT", "LT", "GTE", "LTE", "AND", "OR", "DOT", "INT", "FLOAT", 
			"COMMA", "COLON", "SEMI_COLON", "ASSIGNMENT", "LPAREN", "RPAREN", "LBRACK", 
			"RBRACK", "ID", "WS", "DIGIT_PLUS", "DIGIT_STAR", "LINE_COMMENT_SKIP", 
			"COMMENT_SKIP"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'var'", "'main'", "'array'", "'break'", "'do'", "'if'", 
			"'else'", "'for'", "'function'", "'let'", "'in'", "'of'", "'then'", "'to'", 
			"'while'", "'endif'", "'begin'", "'end'", "'enddo'", "'return'", "'*'", 
			"'/'", "'+'", "'-'", "'='", "'<>'", "'>'", "'<'", "'>='", "'<='", "'&'", 
			"'|'", "'.'", "'int'", "'float'", "','", "':'", "';'", "':='", "'('", 
			"')'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INTLIT", "FLOATLIT", "VAR", "MAIN", "ARRAY", "BREAK", "DO", "IF", 
			"ELSE", "FOR", "FUNCTION", "LET", "IN", "OF", "THEN", "TO", "WHILE", 
			"ENDIF", "BEGIN", "END", "ENDDO", "RETURN", "MULT", "DIV", "ADD", "SUB", 
			"EQUAL", "LT_GT", "GT", "LT", "GTE", "LTE", "AND", "OR", "DOT", "INT", 
			"FLOAT", "COMMA", "COLON", "SEMI_COLON", "ASSIGNMENT", "LPAREN", "RPAREN", 
			"LBRACK", "RBRACK", "ID", "WS", "LINE_COMMENT_SKIP", "COMMENT_SKIP"
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


	public testLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "test.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\63\u013e\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\3\2\6\2k\n\2\r\2\16\2l\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3%\3%\3&\3"+
		"&\3&\3&\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/"+
		"\3/\7/\u010f\n/\f/\16/\u0112\13/\3\60\6\60\u0115\n\60\r\60\16\60\u0116"+
		"\3\60\3\60\3\61\6\61\u011c\n\61\r\61\16\61\u011d\3\62\7\62\u0121\n\62"+
		"\f\62\16\62\u0124\13\62\3\63\3\63\3\63\3\63\7\63\u012a\n\63\f\63\16\63"+
		"\u012d\13\63\3\63\3\63\3\64\3\64\3\64\3\64\7\64\u0135\n\64\f\64\16\64"+
		"\u0138\13\64\3\64\3\64\3\64\3\64\3\64\3\u0136\2\65\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'"+
		"M(O)Q*S+U,W-Y.[/]\60_\61a\2c\2e\62g\63\3\2\7\3\2\62;\4\2C\\c|\6\2\62;"+
		"C\\aac|\5\2\13\f\17\17\"\"\4\2\f\f\17\17\2\u0142\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3"+
		"\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2"+
		"\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2"+
		"Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\3j\3"+
		"\2\2\2\5n\3\2\2\2\7r\3\2\2\2\tv\3\2\2\2\13{\3\2\2\2\r\u0081\3\2\2\2\17"+
		"\u0087\3\2\2\2\21\u008a\3\2\2\2\23\u008d\3\2\2\2\25\u0092\3\2\2\2\27\u0096"+
		"\3\2\2\2\31\u009f\3\2\2\2\33\u00a3\3\2\2\2\35\u00a6\3\2\2\2\37\u00a9\3"+
		"\2\2\2!\u00ae\3\2\2\2#\u00b1\3\2\2\2%\u00b7\3\2\2\2\'\u00bd\3\2\2\2)\u00c3"+
		"\3\2\2\2+\u00c7\3\2\2\2-\u00cd\3\2\2\2/\u00d4\3\2\2\2\61\u00d6\3\2\2\2"+
		"\63\u00d8\3\2\2\2\65\u00da\3\2\2\2\67\u00dc\3\2\2\29\u00de\3\2\2\2;\u00e1"+
		"\3\2\2\2=\u00e3\3\2\2\2?\u00e5\3\2\2\2A\u00e8\3\2\2\2C\u00eb\3\2\2\2E"+
		"\u00ed\3\2\2\2G\u00ef\3\2\2\2I\u00f1\3\2\2\2K\u00f5\3\2\2\2M\u00fb\3\2"+
		"\2\2O\u00fd\3\2\2\2Q\u00ff\3\2\2\2S\u0101\3\2\2\2U\u0104\3\2\2\2W\u0106"+
		"\3\2\2\2Y\u0108\3\2\2\2[\u010a\3\2\2\2]\u010c\3\2\2\2_\u0114\3\2\2\2a"+
		"\u011b\3\2\2\2c\u0122\3\2\2\2e\u0125\3\2\2\2g\u0130\3\2\2\2ik\t\2\2\2"+
		"ji\3\2\2\2kl\3\2\2\2lj\3\2\2\2lm\3\2\2\2m\4\3\2\2\2no\5a\61\2op\5G$\2"+
		"pq\5c\62\2q\6\3\2\2\2rs\7x\2\2st\7c\2\2tu\7t\2\2u\b\3\2\2\2vw\7o\2\2w"+
		"x\7c\2\2xy\7k\2\2yz\7p\2\2z\n\3\2\2\2{|\7c\2\2|}\7t\2\2}~\7t\2\2~\177"+
		"\7c\2\2\177\u0080\7{\2\2\u0080\f\3\2\2\2\u0081\u0082\7d\2\2\u0082\u0083"+
		"\7t\2\2\u0083\u0084\7g\2\2\u0084\u0085\7c\2\2\u0085\u0086\7m\2\2\u0086"+
		"\16\3\2\2\2\u0087\u0088\7f\2\2\u0088\u0089\7q\2\2\u0089\20\3\2\2\2\u008a"+
		"\u008b\7k\2\2\u008b\u008c\7h\2\2\u008c\22\3\2\2\2\u008d\u008e\7g\2\2\u008e"+
		"\u008f\7n\2\2\u008f\u0090\7u\2\2\u0090\u0091\7g\2\2\u0091\24\3\2\2\2\u0092"+
		"\u0093\7h\2\2\u0093\u0094\7q\2\2\u0094\u0095\7t\2\2\u0095\26\3\2\2\2\u0096"+
		"\u0097\7h\2\2\u0097\u0098\7w\2\2\u0098\u0099\7p\2\2\u0099\u009a\7e\2\2"+
		"\u009a\u009b\7v\2\2\u009b\u009c\7k\2\2\u009c\u009d\7q\2\2\u009d\u009e"+
		"\7p\2\2\u009e\30\3\2\2\2\u009f\u00a0\7n\2\2\u00a0\u00a1\7g\2\2\u00a1\u00a2"+
		"\7v\2\2\u00a2\32\3\2\2\2\u00a3\u00a4\7k\2\2\u00a4\u00a5\7p\2\2\u00a5\34"+
		"\3\2\2\2\u00a6\u00a7\7q\2\2\u00a7\u00a8\7h\2\2\u00a8\36\3\2\2\2\u00a9"+
		"\u00aa\7v\2\2\u00aa\u00ab\7j\2\2\u00ab\u00ac\7g\2\2\u00ac\u00ad\7p\2\2"+
		"\u00ad \3\2\2\2\u00ae\u00af\7v\2\2\u00af\u00b0\7q\2\2\u00b0\"\3\2\2\2"+
		"\u00b1\u00b2\7y\2\2\u00b2\u00b3\7j\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5"+
		"\7n\2\2\u00b5\u00b6\7g\2\2\u00b6$\3\2\2\2\u00b7\u00b8\7g\2\2\u00b8\u00b9"+
		"\7p\2\2\u00b9\u00ba\7f\2\2\u00ba\u00bb\7k\2\2\u00bb\u00bc\7h\2\2\u00bc"+
		"&\3\2\2\2\u00bd\u00be\7d\2\2\u00be\u00bf\7g\2\2\u00bf\u00c0\7i\2\2\u00c0"+
		"\u00c1\7k\2\2\u00c1\u00c2\7p\2\2\u00c2(\3\2\2\2\u00c3\u00c4\7g\2\2\u00c4"+
		"\u00c5\7p\2\2\u00c5\u00c6\7f\2\2\u00c6*\3\2\2\2\u00c7\u00c8\7g\2\2\u00c8"+
		"\u00c9\7p\2\2\u00c9\u00ca\7f\2\2\u00ca\u00cb\7f\2\2\u00cb\u00cc\7q\2\2"+
		"\u00cc,\3\2\2\2\u00cd\u00ce\7t\2\2\u00ce\u00cf\7g\2\2\u00cf\u00d0\7v\2"+
		"\2\u00d0\u00d1\7w\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3\7p\2\2\u00d3.\3\2"+
		"\2\2\u00d4\u00d5\7,\2\2\u00d5\60\3\2\2\2\u00d6\u00d7\7\61\2\2\u00d7\62"+
		"\3\2\2\2\u00d8\u00d9\7-\2\2\u00d9\64\3\2\2\2\u00da\u00db\7/\2\2\u00db"+
		"\66\3\2\2\2\u00dc\u00dd\7?\2\2\u00dd8\3\2\2\2\u00de\u00df\7>\2\2\u00df"+
		"\u00e0\7@\2\2\u00e0:\3\2\2\2\u00e1\u00e2\7@\2\2\u00e2<\3\2\2\2\u00e3\u00e4"+
		"\7>\2\2\u00e4>\3\2\2\2\u00e5\u00e6\7@\2\2\u00e6\u00e7\7?\2\2\u00e7@\3"+
		"\2\2\2\u00e8\u00e9\7>\2\2\u00e9\u00ea\7?\2\2\u00eaB\3\2\2\2\u00eb\u00ec"+
		"\7(\2\2\u00ecD\3\2\2\2\u00ed\u00ee\7~\2\2\u00eeF\3\2\2\2\u00ef\u00f0\7"+
		"\60\2\2\u00f0H\3\2\2\2\u00f1\u00f2\7k\2\2\u00f2\u00f3\7p\2\2\u00f3\u00f4"+
		"\7v\2\2\u00f4J\3\2\2\2\u00f5\u00f6\7h\2\2\u00f6\u00f7\7n\2\2\u00f7\u00f8"+
		"\7q\2\2\u00f8\u00f9\7c\2\2\u00f9\u00fa\7v\2\2\u00faL\3\2\2\2\u00fb\u00fc"+
		"\7.\2\2\u00fcN\3\2\2\2\u00fd\u00fe\7<\2\2\u00feP\3\2\2\2\u00ff\u0100\7"+
		"=\2\2\u0100R\3\2\2\2\u0101\u0102\7<\2\2\u0102\u0103\7?\2\2\u0103T\3\2"+
		"\2\2\u0104\u0105\7*\2\2\u0105V\3\2\2\2\u0106\u0107\7+\2\2\u0107X\3\2\2"+
		"\2\u0108\u0109\7]\2\2\u0109Z\3\2\2\2\u010a\u010b\7_\2\2\u010b\\\3\2\2"+
		"\2\u010c\u0110\t\3\2\2\u010d\u010f\t\4\2\2\u010e\u010d\3\2\2\2\u010f\u0112"+
		"\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111^\3\2\2\2\u0112"+
		"\u0110\3\2\2\2\u0113\u0115\t\5\2\2\u0114\u0113\3\2\2\2\u0115\u0116\3\2"+
		"\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0118\3\2\2\2\u0118"+
		"\u0119\b\60\2\2\u0119`\3\2\2\2\u011a\u011c\t\2\2\2\u011b\u011a\3\2\2\2"+
		"\u011c\u011d\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011eb\3"+
		"\2\2\2\u011f\u0121\t\2\2\2\u0120\u011f\3\2\2\2\u0121\u0124\3\2\2\2\u0122"+
		"\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123d\3\2\2\2\u0124\u0122\3\2\2\2"+
		"\u0125\u0126\7\61\2\2\u0126\u0127\7\61\2\2\u0127\u012b\3\2\2\2\u0128\u012a"+
		"\n\6\2\2\u0129\u0128\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3\2\2\2\u012b"+
		"\u012c\3\2\2\2\u012c\u012e\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u012f\b\63"+
		"\2\2\u012ff\3\2\2\2\u0130\u0131\7\61\2\2\u0131\u0132\7,\2\2\u0132\u0136"+
		"\3\2\2\2\u0133\u0135\13\2\2\2\u0134\u0133\3\2\2\2\u0135\u0138\3\2\2\2"+
		"\u0136\u0137\3\2\2\2\u0136\u0134\3\2\2\2\u0137\u0139\3\2\2\2\u0138\u0136"+
		"\3\2\2\2\u0139\u013a\7,\2\2\u013a\u013b\7\61\2\2\u013b\u013c\3\2\2\2\u013c"+
		"\u013d\b\64\2\2\u013dh\3\2\2\2\n\2l\u0110\u0116\u011d\u0122\u012b\u0136"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}