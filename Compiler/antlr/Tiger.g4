
grammar Tiger;

tiger_program			:	'main' 'let' DECLARATION_SEGMENT? 'in' 'begin' STAT_SEQ 'end';
DECLARATION_SEGMENT		:	VAR_DECLARATION_LIST FUNCT_DECLARATION_LIST
						|	VAR_DECLARATION_LIST
						|	FUNCT_DECLARATION_LIST
						;
VAR_DECLARATION_LIST	:	VAR_DECLARATION VAR_DECLARATION_LIST?;

VAR_DECLARATION			:	'var' ID_LIST ':' TYPE OPTIONAL_INIT? ';';
FUNCT_DECLARATION_LIST	:	FUNCT_DECLARATION FUNCT_DECLARATION_LIST?;
FUNCT_DECLARATION		:	'function' ID '(' PARAM_LIST? ')' RET_TYPE? 'begin' STAT_SEQ 'end';
TYPE					:	TYPE_ID
						|	'array' '[' INTLIT ']' 'of' TYPE_ID
						;
TYPE_ID					:	'int'
						|	'float'
						;					
ID_LIST					:	ID
						|	ID ',' ID_LIST
						;
OPTIONAL_INIT			:	':=' CONST;						
PARAM_LIST				:	PARAM PARAM_LIST_TAIL?;			
PARAM_LIST_TAIL			:	',' PARAM PARAM_LIST_TAIL?;
RET_TYPE				:	':' TYPE;				
PARAM					:	ID ':' TYPE;
STAT_SEQ				:	STAT
						|	STAT STAT_SEQ
						;
STAT					:	LVALUE ':=' EXPR ';'
						|	'if' EXPR 'then' STAT_SEQ 'endif' ';'
						|	'if' EXPR 'then' STAT_SEQ 'else' STAT_SEQ 'endif' ';'
						|	'while' EXPR 'do' STAT_SEQ 'enddo' ';'
						|	'for' ID ':=' EXPR 'to' EXPR 'do' STAT_SEQ 'enddo' ';'
						|	OPT_PREFIX? ID '(' EXPR_LIST? ')' ';'
						|	'break' ';'
						|	'return' EXPR ';'
						|	'let' DECLARATION_SEGMENT 'in' STAT_SEQ 'end'
						;						
OPT_PREFIX				:	LVALUE ':=';
EXPR					:	EXPR_BEGIN
						|   EXPR_BEGIN BINARY_OPERATOR EXPR
						;
EXPR_BEGIN				:	CONST
						|	LVALUE
						|	'(' EXPR ')'
						;
CONST					:	INTLIT
						|	FLOATLIT
						;					
BINARY_OPERATOR			:	'*'|'/'|'+'|'-'|'='|'<>'|'>'|'<'|'>='|'<='|'&'|'|';
EXPR_LIST				:	EXPR EXPR_LIST_TAIL?;	
EXPR_LIST_TAIL			:	',' EXPR EXPR_LIST_TAIL?;
LVALUE					:	ID LVALUE_TAIL?;
LVALUE_TAIL				:	'[' EXPR ']';
RESERVED_WORD			:	'main'|'array'|'break'|'do'|'if'|'else'|'for'
						|	'function'|'let'|'in'|'of'|'then'|'to'|'var'
						|	'while'|'endif'|'begin'|'end'|'enddo'|'return'
						;
ID						:	[a-zA-Z][a-zA-Z0-9_]*;
INTLIT					:	[0-9]+;
FLOATLIT				:	INTLIT '.' [0-9]*;
PUNCTUATION				:	','|':'|';'|'('|')'|'['|']';
COMMENT					:	'/*' COMMENT_BODY? '*/';
COMMENT_BODY			:	. COMMENT_BODY?;
WS						:	[ \t\r\n]+ -> skip; // skip spaces, tabs, return, newlines