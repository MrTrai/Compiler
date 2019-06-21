// Generated from test.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class testParser extends Parser {
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
	public static final int
		RULE_start = 0, RULE_declaration_segment = 1, RULE_var_declaration_list = 2, 
		RULE_var_declaration = 3, RULE_funct_declaration_list = 4, RULE_funct_declaration = 5, 
		RULE_param_list = 6, RULE_param_list_tail = 7, RULE_param = 8, RULE_var_type = 9, 
		RULE_stat_seq = 10, RULE_stat = 11, RULE_optional_init = 12, RULE_type = 13, 
		RULE_opt_prefix = 14, RULE_binary_operator = 15, RULE_id_list = 16, RULE_lvalue = 17, 
		RULE_expr_list = 18, RULE_expr = 19, RULE_expr_op = 20, RULE_type_id = 21, 
		RULE_constant = 22;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "declaration_segment", "var_declaration_list", "var_declaration", 
			"funct_declaration_list", "funct_declaration", "param_list", "param_list_tail", 
			"param", "var_type", "stat_seq", "stat", "optional_init", "type", "opt_prefix", 
			"binary_operator", "id_list", "lvalue", "expr_list", "expr", "expr_op", 
			"type_id", "constant"
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

	@Override
	public String getGrammarFileName() { return "test.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public testParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class StartContext extends ParserRuleContext {
		public TerminalNode MAIN() { return getToken(testParser.MAIN, 0); }
		public TerminalNode LET() { return getToken(testParser.LET, 0); }
		public Declaration_segmentContext declaration_segment() {
			return getRuleContext(Declaration_segmentContext.class,0);
		}
		public TerminalNode IN() { return getToken(testParser.IN, 0); }
		public TerminalNode BEGIN() { return getToken(testParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(testParser.END, 0); }
		public Stat_seqContext stat_seq() {
			return getRuleContext(Stat_seqContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitStart(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(MAIN);
			setState(47);
			match(LET);
			setState(48);
			declaration_segment();
			setState(49);
			match(IN);
			setState(50);
			match(BEGIN);
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << IF) | (1L << FOR) | (1L << LET) | (1L << WHILE) | (1L << RETURN) | (1L << ID))) != 0)) {
				{
				setState(51);
				stat_seq();
				}
			}

			setState(54);
			match(END);
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

	public static class Declaration_segmentContext extends ParserRuleContext {
		public Var_declaration_listContext var_declaration_list() {
			return getRuleContext(Var_declaration_listContext.class,0);
		}
		public Funct_declaration_listContext funct_declaration_list() {
			return getRuleContext(Funct_declaration_listContext.class,0);
		}
		public Declaration_segmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_segment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterDeclaration_segment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitDeclaration_segment(this);
		}
	}

	public final Declaration_segmentContext declaration_segment() throws RecognitionException {
		Declaration_segmentContext _localctx = new Declaration_segmentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration_segment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VAR) {
				{
				setState(56);
				var_declaration_list();
				}
			}

			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FUNCTION) {
				{
				setState(59);
				funct_declaration_list();
				}
			}

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

	public static class Var_declaration_listContext extends ParserRuleContext {
		public Var_declarationContext var_declaration() {
			return getRuleContext(Var_declarationContext.class,0);
		}
		public Var_declaration_listContext var_declaration_list() {
			return getRuleContext(Var_declaration_listContext.class,0);
		}
		public Var_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_declaration_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterVar_declaration_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitVar_declaration_list(this);
		}
	}

	public final Var_declaration_listContext var_declaration_list() throws RecognitionException {
		Var_declaration_listContext _localctx = new Var_declaration_listContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_var_declaration_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			var_declaration();
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VAR) {
				{
				setState(63);
				var_declaration_list();
				}
			}

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

	public static class Var_declarationContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(testParser.VAR, 0); }
		public Id_listContext id_list() {
			return getRuleContext(Id_listContext.class,0);
		}
		public Var_typeContext var_type() {
			return getRuleContext(Var_typeContext.class,0);
		}
		public TerminalNode SEMI_COLON() { return getToken(testParser.SEMI_COLON, 0); }
		public Optional_initContext optional_init() {
			return getRuleContext(Optional_initContext.class,0);
		}
		public Var_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterVar_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitVar_declaration(this);
		}
	}

	public final Var_declarationContext var_declaration() throws RecognitionException {
		Var_declarationContext _localctx = new Var_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_var_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(VAR);
			setState(67);
			id_list();
			setState(68);
			var_type();
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGNMENT) {
				{
				setState(69);
				optional_init();
				}
			}

			setState(72);
			match(SEMI_COLON);
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

	public static class Funct_declaration_listContext extends ParserRuleContext {
		public Funct_declarationContext funct_declaration() {
			return getRuleContext(Funct_declarationContext.class,0);
		}
		public Funct_declaration_listContext funct_declaration_list() {
			return getRuleContext(Funct_declaration_listContext.class,0);
		}
		public Funct_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funct_declaration_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterFunct_declaration_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitFunct_declaration_list(this);
		}
	}

	public final Funct_declaration_listContext funct_declaration_list() throws RecognitionException {
		Funct_declaration_listContext _localctx = new Funct_declaration_listContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_funct_declaration_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			funct_declaration();
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FUNCTION) {
				{
				setState(75);
				funct_declaration_list();
				}
			}

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

	public static class Funct_declarationContext extends ParserRuleContext {
		public TerminalNode FUNCTION() { return getToken(testParser.FUNCTION, 0); }
		public TerminalNode ID() { return getToken(testParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(testParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(testParser.RPAREN, 0); }
		public TerminalNode BEGIN() { return getToken(testParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(testParser.END, 0); }
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public Var_typeContext var_type() {
			return getRuleContext(Var_typeContext.class,0);
		}
		public Stat_seqContext stat_seq() {
			return getRuleContext(Stat_seqContext.class,0);
		}
		public Funct_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funct_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterFunct_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitFunct_declaration(this);
		}
	}

	public final Funct_declarationContext funct_declaration() throws RecognitionException {
		Funct_declarationContext _localctx = new Funct_declarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_funct_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(FUNCTION);
			setState(79);
			match(ID);
			setState(80);
			match(LPAREN);
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(81);
				param_list();
				}
			}

			setState(84);
			match(RPAREN);
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(85);
				var_type();
				}
			}

			setState(88);
			match(BEGIN);
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << IF) | (1L << FOR) | (1L << LET) | (1L << WHILE) | (1L << RETURN) | (1L << ID))) != 0)) {
				{
				setState(89);
				stat_seq();
				}
			}

			setState(92);
			match(END);
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

	public static class Param_listContext extends ParserRuleContext {
		public ParamContext param() {
			return getRuleContext(ParamContext.class,0);
		}
		public Param_list_tailContext param_list_tail() {
			return getRuleContext(Param_list_tailContext.class,0);
		}
		public Param_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterParam_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitParam_list(this);
		}
	}

	public final Param_listContext param_list() throws RecognitionException {
		Param_listContext _localctx = new Param_listContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			param();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(95);
				param_list_tail();
				}
			}

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

	public static class Param_list_tailContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(testParser.COMMA, 0); }
		public ParamContext param() {
			return getRuleContext(ParamContext.class,0);
		}
		public Param_list_tailContext param_list_tail() {
			return getRuleContext(Param_list_tailContext.class,0);
		}
		public Param_list_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_list_tail; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterParam_list_tail(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitParam_list_tail(this);
		}
	}

	public final Param_list_tailContext param_list_tail() throws RecognitionException {
		Param_list_tailContext _localctx = new Param_list_tailContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_param_list_tail);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(COMMA);
			setState(99);
			param();
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(100);
				param_list_tail();
				}
			}

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

	public static class ParamContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(testParser.ID, 0); }
		public Var_typeContext var_type() {
			return getRuleContext(Var_typeContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitParam(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(ID);
			setState(104);
			var_type();
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

	public static class Var_typeContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(testParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Var_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterVar_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitVar_type(this);
		}
	}

	public final Var_typeContext var_type() throws RecognitionException {
		Var_typeContext _localctx = new Var_typeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_var_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(COLON);
			setState(107);
			type();
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

	public static class Stat_seqContext extends ParserRuleContext {
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public Stat_seqContext stat_seq() {
			return getRuleContext(Stat_seqContext.class,0);
		}
		public Stat_seqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat_seq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterStat_seq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitStat_seq(this);
		}
	}

	public final Stat_seqContext stat_seq() throws RecognitionException {
		Stat_seqContext _localctx = new Stat_seqContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_stat_seq);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			stat();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BREAK) | (1L << IF) | (1L << FOR) | (1L << LET) | (1L << WHILE) | (1L << RETURN) | (1L << ID))) != 0)) {
				{
				setState(110);
				stat_seq();
				}
			}

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

	public static class StatContext extends ParserRuleContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(testParser.ASSIGNMENT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode SEMI_COLON() { return getToken(testParser.SEMI_COLON, 0); }
		public TerminalNode IF() { return getToken(testParser.IF, 0); }
		public TerminalNode THEN() { return getToken(testParser.THEN, 0); }
		public List<Stat_seqContext> stat_seq() {
			return getRuleContexts(Stat_seqContext.class);
		}
		public Stat_seqContext stat_seq(int i) {
			return getRuleContext(Stat_seqContext.class,i);
		}
		public TerminalNode ENDIF() { return getToken(testParser.ENDIF, 0); }
		public TerminalNode ELSE() { return getToken(testParser.ELSE, 0); }
		public TerminalNode WHILE() { return getToken(testParser.WHILE, 0); }
		public TerminalNode DO() { return getToken(testParser.DO, 0); }
		public TerminalNode ENDDO() { return getToken(testParser.ENDDO, 0); }
		public TerminalNode FOR() { return getToken(testParser.FOR, 0); }
		public TerminalNode ID() { return getToken(testParser.ID, 0); }
		public TerminalNode TO() { return getToken(testParser.TO, 0); }
		public TerminalNode LPAREN() { return getToken(testParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(testParser.RPAREN, 0); }
		public Opt_prefixContext opt_prefix() {
			return getRuleContext(Opt_prefixContext.class,0);
		}
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public TerminalNode BREAK() { return getToken(testParser.BREAK, 0); }
		public TerminalNode RETURN() { return getToken(testParser.RETURN, 0); }
		public TerminalNode LET() { return getToken(testParser.LET, 0); }
		public Declaration_segmentContext declaration_segment() {
			return getRuleContext(Declaration_segmentContext.class,0);
		}
		public TerminalNode IN() { return getToken(testParser.IN, 0); }
		public TerminalNode END() { return getToken(testParser.END, 0); }
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitStat(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_stat);
		int _la;
		try {
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(113);
				lvalue();
				setState(114);
				match(ASSIGNMENT);
				setState(115);
				expr(0);
				setState(116);
				match(SEMI_COLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				match(IF);
				setState(119);
				expr(0);
				setState(120);
				match(THEN);
				setState(121);
				stat_seq();
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(122);
					match(ELSE);
					setState(123);
					stat_seq();
					}
				}

				setState(126);
				match(ENDIF);
				setState(127);
				match(SEMI_COLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				match(WHILE);
				setState(130);
				expr(0);
				setState(131);
				match(DO);
				setState(132);
				stat_seq();
				setState(133);
				match(ENDDO);
				setState(134);
				match(SEMI_COLON);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(136);
				match(FOR);
				setState(137);
				match(ID);
				setState(138);
				match(ASSIGNMENT);
				setState(139);
				expr(0);
				setState(140);
				match(TO);
				setState(141);
				expr(0);
				setState(142);
				match(DO);
				setState(143);
				stat_seq();
				setState(144);
				match(ENDDO);
				setState(145);
				match(SEMI_COLON);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(148);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(147);
					opt_prefix();
					}
					break;
				}
				setState(150);
				match(ID);
				setState(151);
				match(LPAREN);
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTLIT) | (1L << FLOATLIT) | (1L << LPAREN) | (1L << ID))) != 0)) {
					{
					setState(152);
					expr_list();
					}
				}

				setState(155);
				match(RPAREN);
				setState(156);
				match(SEMI_COLON);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(157);
				match(BREAK);
				setState(158);
				match(SEMI_COLON);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(159);
				match(RETURN);
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTLIT) | (1L << FLOATLIT) | (1L << LPAREN) | (1L << ID))) != 0)) {
					{
					setState(160);
					expr(0);
					}
				}

				setState(163);
				match(SEMI_COLON);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(164);
				match(LET);
				setState(165);
				declaration_segment();
				setState(166);
				match(IN);
				setState(167);
				stat_seq();
				setState(168);
				match(END);
				}
				break;
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

	public static class Optional_initContext extends ParserRuleContext {
		public TerminalNode ASSIGNMENT() { return getToken(testParser.ASSIGNMENT, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public Optional_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optional_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterOptional_init(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitOptional_init(this);
		}
	}

	public final Optional_initContext optional_init() throws RecognitionException {
		Optional_initContext _localctx = new Optional_initContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_optional_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(ASSIGNMENT);
			setState(173);
			constant();
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

	public static class TypeContext extends ParserRuleContext {
		public Type_idContext type_id() {
			return getRuleContext(Type_idContext.class,0);
		}
		public TerminalNode ARRAY() { return getToken(testParser.ARRAY, 0); }
		public TerminalNode LBRACK() { return getToken(testParser.LBRACK, 0); }
		public TerminalNode INTLIT() { return getToken(testParser.INTLIT, 0); }
		public TerminalNode RBRACK() { return getToken(testParser.RBRACK, 0); }
		public TerminalNode OF() { return getToken(testParser.OF, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ARRAY) {
				{
				setState(175);
				match(ARRAY);
				setState(176);
				match(LBRACK);
				setState(177);
				match(INTLIT);
				setState(178);
				match(RBRACK);
				setState(179);
				match(OF);
				}
			}

			setState(182);
			type_id();
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

	public static class Opt_prefixContext extends ParserRuleContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(testParser.ASSIGNMENT, 0); }
		public Opt_prefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opt_prefix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterOpt_prefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitOpt_prefix(this);
		}
	}

	public final Opt_prefixContext opt_prefix() throws RecognitionException {
		Opt_prefixContext _localctx = new Opt_prefixContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_opt_prefix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			lvalue();
			setState(185);
			match(ASSIGNMENT);
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

	public static class Binary_operatorContext extends ParserRuleContext {
		public TerminalNode MULT() { return getToken(testParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(testParser.DIV, 0); }
		public TerminalNode ADD() { return getToken(testParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(testParser.SUB, 0); }
		public TerminalNode EQUAL() { return getToken(testParser.EQUAL, 0); }
		public TerminalNode LT_GT() { return getToken(testParser.LT_GT, 0); }
		public TerminalNode GT() { return getToken(testParser.GT, 0); }
		public TerminalNode LT() { return getToken(testParser.LT, 0); }
		public TerminalNode GTE() { return getToken(testParser.GTE, 0); }
		public TerminalNode LTE() { return getToken(testParser.LTE, 0); }
		public TerminalNode AND() { return getToken(testParser.AND, 0); }
		public TerminalNode OR() { return getToken(testParser.OR, 0); }
		public Binary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binary_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterBinary_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitBinary_operator(this);
		}
	}

	public final Binary_operatorContext binary_operator() throws RecognitionException {
		Binary_operatorContext _localctx = new Binary_operatorContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_binary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << ADD) | (1L << SUB) | (1L << EQUAL) | (1L << LT_GT) | (1L << GT) | (1L << LT) | (1L << GTE) | (1L << LTE) | (1L << AND) | (1L << OR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public static class Id_listContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(testParser.ID, 0); }
		public TerminalNode COMMA() { return getToken(testParser.COMMA, 0); }
		public Id_listContext id_list() {
			return getRuleContext(Id_listContext.class,0);
		}
		public Id_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterId_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitId_list(this);
		}
	}

	public final Id_listContext id_list() throws RecognitionException {
		Id_listContext _localctx = new Id_listContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_id_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(ID);
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(190);
				match(COMMA);
				setState(191);
				id_list();
				}
			}

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

	public static class LvalueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(testParser.ID, 0); }
		public TerminalNode LBRACK() { return getToken(testParser.LBRACK, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(testParser.RBRACK, 0); }
		public LvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterLvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitLvalue(this);
		}
	}

	public final LvalueContext lvalue() throws RecognitionException {
		LvalueContext _localctx = new LvalueContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_lvalue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(ID);
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACK) {
				{
				setState(195);
				match(LBRACK);
				setState(196);
				expr(0);
				setState(197);
				match(RBRACK);
				}
			}

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

	public static class Expr_listContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(testParser.COMMA, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public Expr_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterExpr_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitExpr_list(this);
		}
	}

	public final Expr_listContext expr_list() throws RecognitionException {
		Expr_listContext _localctx = new Expr_listContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_expr_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			expr(0);
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(202);
				match(COMMA);
				setState(203);
				expr_list();
				}
			}

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

	public static class ExprContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode ID() { return getToken(testParser.ID, 0); }
		public TerminalNode LBRACK() { return getToken(testParser.LBRACK, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RBRACK() { return getToken(testParser.RBRACK, 0); }
		public TerminalNode LPAREN() { return getToken(testParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(testParser.RPAREN, 0); }
		public Binary_operatorContext binary_operator() {
			return getRuleContext(Binary_operatorContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTLIT:
			case FLOATLIT:
				{
				setState(207);
				constant();
				}
				break;
			case ID:
				{
				setState(208);
				match(ID);
				setState(213);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(209);
					match(LBRACK);
					setState(210);
					expr(0);
					setState(211);
					match(RBRACK);
					}
					break;
				}
				}
				break;
			case LPAREN:
				{
				setState(215);
				match(LPAREN);
				setState(216);
				expr(0);
				setState(217);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(227);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExprContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_expr);
					setState(221);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(222);
					binary_operator();
					setState(223);
					expr(2);
					}
					} 
				}
				setState(229);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Expr_opContext extends ParserRuleContext {
		public Binary_operatorContext binary_operator() {
			return getRuleContext(Binary_operatorContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Expr_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterExpr_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitExpr_op(this);
		}
	}

	public final Expr_opContext expr_op() throws RecognitionException {
		Expr_opContext _localctx = new Expr_opContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_expr_op);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			binary_operator();
			setState(231);
			expr(0);
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

	public static class Type_idContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(testParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(testParser.FLOAT, 0); }
		public Type_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterType_id(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitType_id(this);
		}
	}

	public final Type_idContext type_id() throws RecognitionException {
		Type_idContext _localctx = new Type_idContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_type_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==FLOAT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public static class ConstantContext extends ParserRuleContext {
		public TerminalNode INTLIT() { return getToken(testParser.INTLIT, 0); }
		public TerminalNode FLOATLIT() { return getToken(testParser.FLOATLIT, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof testListener ) ((testListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_la = _input.LA(1);
			if ( !(_la==INTLIT || _la==FLOATLIT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\63\u00f0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\5\2\67\n\2\3\2\3\2\3\3\5\3<\n\3\3\3\5\3?\n\3\3\4\3\4\5"+
		"\4C\n\4\3\5\3\5\3\5\3\5\5\5I\n\5\3\5\3\5\3\6\3\6\5\6O\n\6\3\7\3\7\3\7"+
		"\3\7\5\7U\n\7\3\7\3\7\5\7Y\n\7\3\7\3\7\5\7]\n\7\3\7\3\7\3\b\3\b\5\bc\n"+
		"\b\3\t\3\t\3\t\5\th\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\5\fr\n\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\177\n\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\5\r\u0097\n\r\3\r\3\r\3\r\5\r\u009c\n\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r"+
		"\u00a4\n\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00ad\n\r\3\16\3\16\3\16\3"+
		"\17\3\17\3\17\3\17\3\17\5\17\u00b7\n\17\3\17\3\17\3\20\3\20\3\20\3\21"+
		"\3\21\3\22\3\22\3\22\5\22\u00c3\n\22\3\23\3\23\3\23\3\23\3\23\5\23\u00ca"+
		"\n\23\3\24\3\24\3\24\5\24\u00cf\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\5\25\u00d8\n\25\3\25\3\25\3\25\3\25\5\25\u00de\n\25\3\25\3\25\3\25\3"+
		"\25\7\25\u00e4\n\25\f\25\16\25\u00e7\13\25\3\26\3\26\3\26\3\27\3\27\3"+
		"\30\3\30\3\30\2\3(\31\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,."+
		"\2\5\3\2\31$\3\2&\'\3\2\3\4\2\u00f7\2\60\3\2\2\2\4;\3\2\2\2\6@\3\2\2\2"+
		"\bD\3\2\2\2\nL\3\2\2\2\fP\3\2\2\2\16`\3\2\2\2\20d\3\2\2\2\22i\3\2\2\2"+
		"\24l\3\2\2\2\26o\3\2\2\2\30\u00ac\3\2\2\2\32\u00ae\3\2\2\2\34\u00b6\3"+
		"\2\2\2\36\u00ba\3\2\2\2 \u00bd\3\2\2\2\"\u00bf\3\2\2\2$\u00c4\3\2\2\2"+
		"&\u00cb\3\2\2\2(\u00dd\3\2\2\2*\u00e8\3\2\2\2,\u00eb\3\2\2\2.\u00ed\3"+
		"\2\2\2\60\61\7\6\2\2\61\62\7\16\2\2\62\63\5\4\3\2\63\64\7\17\2\2\64\66"+
		"\7\25\2\2\65\67\5\26\f\2\66\65\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\7\26"+
		"\2\29\3\3\2\2\2:<\5\6\4\2;:\3\2\2\2;<\3\2\2\2<>\3\2\2\2=?\5\n\6\2>=\3"+
		"\2\2\2>?\3\2\2\2?\5\3\2\2\2@B\5\b\5\2AC\5\6\4\2BA\3\2\2\2BC\3\2\2\2C\7"+
		"\3\2\2\2DE\7\5\2\2EF\5\"\22\2FH\5\24\13\2GI\5\32\16\2HG\3\2\2\2HI\3\2"+
		"\2\2IJ\3\2\2\2JK\7*\2\2K\t\3\2\2\2LN\5\f\7\2MO\5\n\6\2NM\3\2\2\2NO\3\2"+
		"\2\2O\13\3\2\2\2PQ\7\r\2\2QR\7\60\2\2RT\7,\2\2SU\5\16\b\2TS\3\2\2\2TU"+
		"\3\2\2\2UV\3\2\2\2VX\7-\2\2WY\5\24\13\2XW\3\2\2\2XY\3\2\2\2YZ\3\2\2\2"+
		"Z\\\7\25\2\2[]\5\26\f\2\\[\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\7\26\2\2_\r"+
		"\3\2\2\2`b\5\22\n\2ac\5\20\t\2ba\3\2\2\2bc\3\2\2\2c\17\3\2\2\2de\7(\2"+
		"\2eg\5\22\n\2fh\5\20\t\2gf\3\2\2\2gh\3\2\2\2h\21\3\2\2\2ij\7\60\2\2jk"+
		"\5\24\13\2k\23\3\2\2\2lm\7)\2\2mn\5\34\17\2n\25\3\2\2\2oq\5\30\r\2pr\5"+
		"\26\f\2qp\3\2\2\2qr\3\2\2\2r\27\3\2\2\2st\5$\23\2tu\7+\2\2uv\5(\25\2v"+
		"w\7*\2\2w\u00ad\3\2\2\2xy\7\n\2\2yz\5(\25\2z{\7\21\2\2{~\5\26\f\2|}\7"+
		"\13\2\2}\177\5\26\f\2~|\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081"+
		"\7\24\2\2\u0081\u0082\7*\2\2\u0082\u00ad\3\2\2\2\u0083\u0084\7\23\2\2"+
		"\u0084\u0085\5(\25\2\u0085\u0086\7\t\2\2\u0086\u0087\5\26\f\2\u0087\u0088"+
		"\7\27\2\2\u0088\u0089\7*\2\2\u0089\u00ad\3\2\2\2\u008a\u008b\7\f\2\2\u008b"+
		"\u008c\7\60\2\2\u008c\u008d\7+\2\2\u008d\u008e\5(\25\2\u008e\u008f\7\22"+
		"\2\2\u008f\u0090\5(\25\2\u0090\u0091\7\t\2\2\u0091\u0092\5\26\f\2\u0092"+
		"\u0093\7\27\2\2\u0093\u0094\7*\2\2\u0094\u00ad\3\2\2\2\u0095\u0097\5\36"+
		"\20\2\u0096\u0095\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0098\3\2\2\2\u0098"+
		"\u0099\7\60\2\2\u0099\u009b\7,\2\2\u009a\u009c\5&\24\2\u009b\u009a\3\2"+
		"\2\2\u009b\u009c\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009e\7-\2\2\u009e"+
		"\u00ad\7*\2\2\u009f\u00a0\7\b\2\2\u00a0\u00ad\7*\2\2\u00a1\u00a3\7\30"+
		"\2\2\u00a2\u00a4\5(\25\2\u00a3\u00a2\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00ad\7*\2\2\u00a6\u00a7\7\16\2\2\u00a7\u00a8\5\4"+
		"\3\2\u00a8\u00a9\7\17\2\2\u00a9\u00aa\5\26\f\2\u00aa\u00ab\7\26\2\2\u00ab"+
		"\u00ad\3\2\2\2\u00acs\3\2\2\2\u00acx\3\2\2\2\u00ac\u0083\3\2\2\2\u00ac"+
		"\u008a\3\2\2\2\u00ac\u0096\3\2\2\2\u00ac\u009f\3\2\2\2\u00ac\u00a1\3\2"+
		"\2\2\u00ac\u00a6\3\2\2\2\u00ad\31\3\2\2\2\u00ae\u00af\7+\2\2\u00af\u00b0"+
		"\5.\30\2\u00b0\33\3\2\2\2\u00b1\u00b2\7\7\2\2\u00b2\u00b3\7.\2\2\u00b3"+
		"\u00b4\7\3\2\2\u00b4\u00b5\7/\2\2\u00b5\u00b7\7\20\2\2\u00b6\u00b1\3\2"+
		"\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\5,\27\2\u00b9"+
		"\35\3\2\2\2\u00ba\u00bb\5$\23\2\u00bb\u00bc\7+\2\2\u00bc\37\3\2\2\2\u00bd"+
		"\u00be\t\2\2\2\u00be!\3\2\2\2\u00bf\u00c2\7\60\2\2\u00c0\u00c1\7(\2\2"+
		"\u00c1\u00c3\5\"\22\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3#\3"+
		"\2\2\2\u00c4\u00c9\7\60\2\2\u00c5\u00c6\7.\2\2\u00c6\u00c7\5(\25\2\u00c7"+
		"\u00c8\7/\2\2\u00c8\u00ca\3\2\2\2\u00c9\u00c5\3\2\2\2\u00c9\u00ca\3\2"+
		"\2\2\u00ca%\3\2\2\2\u00cb\u00ce\5(\25\2\u00cc\u00cd\7(\2\2\u00cd\u00cf"+
		"\5&\24\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\'\3\2\2\2\u00d0"+
		"\u00d1\b\25\1\2\u00d1\u00de\5.\30\2\u00d2\u00d7\7\60\2\2\u00d3\u00d4\7"+
		".\2\2\u00d4\u00d5\5(\25\2\u00d5\u00d6\7/\2\2\u00d6\u00d8\3\2\2\2\u00d7"+
		"\u00d3\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00de\3\2\2\2\u00d9\u00da\7,"+
		"\2\2\u00da\u00db\5(\25\2\u00db\u00dc\7-\2\2\u00dc\u00de\3\2\2\2\u00dd"+
		"\u00d0\3\2\2\2\u00dd\u00d2\3\2\2\2\u00dd\u00d9\3\2\2\2\u00de\u00e5\3\2"+
		"\2\2\u00df\u00e0\f\3\2\2\u00e0\u00e1\5 \21\2\u00e1\u00e2\5(\25\4\u00e2"+
		"\u00e4\3\2\2\2\u00e3\u00df\3\2\2\2\u00e4\u00e7\3\2\2\2\u00e5\u00e3\3\2"+
		"\2\2\u00e5\u00e6\3\2\2\2\u00e6)\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e8\u00e9"+
		"\5 \21\2\u00e9\u00ea\5(\25\2\u00ea+\3\2\2\2\u00eb\u00ec\t\3\2\2\u00ec"+
		"-\3\2\2\2\u00ed\u00ee\t\4\2\2\u00ee/\3\2\2\2\32\66;>BHNTX\\bgq~\u0096"+
		"\u009b\u00a3\u00ac\u00b6\u00c2\u00c9\u00ce\u00d7\u00dd\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}