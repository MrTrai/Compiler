// Generated from Tiger.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TigerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, DECLARATION_SEGMENT=6, VAR_DECLARATION_LIST=7, 
		VAR_DECLARATION=8, FUNCT_DECLARATION_LIST=9, FUNCT_DECLARATION=10, TYPE=11, 
		TYPE_ID=12, ID_LIST=13, OPTIONAL_INIT=14, PARAM_LIST=15, PARAM_LIST_TAIL=16, 
		RET_TYPE=17, PARAM=18, STAT_SEQ=19, STAT=20, OPT_PREFIX=21, EXPR=22, EXPR_BEGIN=23, 
		CONST=24, BINARY_OPERATOR=25, EXPR_LIST=26, EXPR_LIST_TAIL=27, LVALUE=28, 
		LVALUE_TAIL=29, RESERVED_WORD=30, ID=31, INTLIT=32, FLOATLIT=33, PUNCTUATION=34, 
		COMMENT=35, COMMENT_BODY=36, WS=37;
	public static final int
		RULE_tiger_program = 0;
	public static final String[] ruleNames = {
		"tiger_program"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'main'", "'let'", "'in'", "'begin'", "'end'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "DECLARATION_SEGMENT", "VAR_DECLARATION_LIST", 
		"VAR_DECLARATION", "FUNCT_DECLARATION_LIST", "FUNCT_DECLARATION", "TYPE", 
		"TYPE_ID", "ID_LIST", "OPTIONAL_INIT", "PARAM_LIST", "PARAM_LIST_TAIL", 
		"RET_TYPE", "PARAM", "STAT_SEQ", "STAT", "OPT_PREFIX", "EXPR", "EXPR_BEGIN", 
		"CONST", "BINARY_OPERATOR", "EXPR_LIST", "EXPR_LIST_TAIL", "LVALUE", "LVALUE_TAIL", 
		"RESERVED_WORD", "ID", "INTLIT", "FLOATLIT", "PUNCTUATION", "COMMENT", 
		"COMMENT_BODY", "WS"
	};
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

	@Override
	public String getGrammarFileName() { return "Tiger.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TigerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Tiger_programContext extends ParserRuleContext {
		public TerminalNode STAT_SEQ() { return getToken(TigerParser.STAT_SEQ, 0); }
		public TerminalNode DECLARATION_SEGMENT() { return getToken(TigerParser.DECLARATION_SEGMENT, 0); }
		public Tiger_programContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tiger_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TigerListener ) ((TigerListener)listener).enterTiger_program(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TigerListener ) ((TigerListener)listener).exitTiger_program(this);
		}
	}

	public final Tiger_programContext tiger_program() throws RecognitionException {
		Tiger_programContext _localctx = new Tiger_programContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_tiger_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2);
			match(T__0);
			setState(3);
			match(T__1);
			setState(5);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DECLARATION_SEGMENT) {
				{
				setState(4);
				match(DECLARATION_SEGMENT);
				}
			}

			setState(7);
			match(T__2);
			setState(8);
			match(T__3);
			setState(9);
			match(STAT_SEQ);
			setState(10);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\'\17\4\2\t\2\3\2"+
		"\3\2\3\2\5\2\b\n\2\3\2\3\2\3\2\3\2\3\2\3\2\2\2\3\2\2\2\2\16\2\4\3\2\2"+
		"\2\4\5\7\3\2\2\5\7\7\4\2\2\6\b\7\b\2\2\7\6\3\2\2\2\7\b\3\2\2\2\b\t\3\2"+
		"\2\2\t\n\7\5\2\2\n\13\7\6\2\2\13\f\7\25\2\2\f\r\7\7\2\2\r\3\3\2\2\2\3"+
		"\7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}