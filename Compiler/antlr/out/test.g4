grammar test;

start       			:	MAIN LET declaration_segment IN BEGIN stat_seq? END;

declaration_segment		:	var_declaration_list? funct_declaration_list?
						;

var_declaration_list	:	var_declaration var_declaration_list?;

var_declaration			:   VAR id_list var_type optional_init? SEMI_COLON; // var x : float := 0.0;

funct_declaration_list	:   funct_declaration funct_declaration_list?;

funct_declaration		:	FUNCTION ID LPAREN param_list? RPAREN var_type? BEGIN stat_seq? END;

param_list				:	param param_list_tail?;
param_list_tail			:	COMMA param param_list_tail?;
param					:	ID var_type;

var_type				:	COLON type;

stat_seq				:	stat stat_seq?;

stat					:	lvalue ASSIGNMENT expr SEMI_COLON // lval := expr;
						|	IF expr THEN stat_seq (ELSE stat_seq)? ENDIF SEMI_COLON
						|	WHILE expr DO stat_seq ENDDO SEMI_COLON
						|	FOR ID ASSIGNMENT expr TO expr DO stat_seq ENDDO SEMI_COLON
						|	opt_prefix? ID LPAREN expr_list? RPAREN SEMI_COLON // x := ID(...);
						|	BREAK SEMI_COLON
						|	RETURN expr? SEMI_COLON
						|	LET declaration_segment IN stat_seq END
						;

optional_init			:	ASSIGNMENT constant;

type					:	(ARRAY LBRACK INTLIT RBRACK OF)? type_id // array [ 2 ] of int
						;
opt_prefix				:	lvalue ASSIGNMENT;

binary_operator			:	MULT | DIV | ADD | SUB | EQUAL | LT_GT | GT | LT | GTE | LTE | AND | OR;

id_list					:	ID (COMMA id_list)?;

lvalue					:	ID (LBRACK expr RBRACK)?; // ID[ expr ]

expr_list				:	expr (COMMA expr_list)?;

expr					:	constant // const
                        |   ID (LBRACK expr RBRACK)? // ID [ expr ]
						|	LPAREN expr RPAREN // ( expr )
                        |   expr binary_operator expr // expr op expr
                        ;

expr_op                 :   binary_operator expr;

type_id					:	INT
						|	FLOAT
						;

constant				:	INTLIT
						|	FLOATLIT
						;

INTLIT					:	[0-9]+;
FLOATLIT				:	DIGIT_PLUS DOT DIGIT_STAR;


VAR: 'var';
MAIN: 'main';
ARRAY: 'array';
BREAK: 'break';
DO: 'do';
IF: 'if';
ELSE: 'else';
FOR: 'for';
FUNCTION: 'function';
LET: 'let';
IN: 'in';
OF: 'of';
THEN: 'then';
TO: 'to';
WHILE: 'while';
ENDIF: 'endif';
BEGIN: 'begin';
END: 'end';
ENDDO: 'enddo';
RETURN: 'return';

MULT: '*';
DIV: '/';
ADD: '+';
SUB: '-';
EQUAL: '=';
LT_GT: '<>';
GT: '>';
LT: '<';
GTE: '>=';
LTE: '<=';
AND: '&';
OR: '|';


DOT: '.';

INT: 'int';
FLOAT: 'float';

COMMA: ',';
COLON: ':';
SEMI_COLON: ';';
ASSIGNMENT: ':=';
LPAREN: '(';
RPAREN: ')';
LBRACK: '[';
RBRACK: ']';



ID						:	[a-zA-Z][a-zA-Z0-9_]*;
WS						:	[ \t\r\n]+ -> skip; // skip spaces, tabs, newlines

fragment DIGIT_PLUS              :   [0-9]+;
fragment DIGIT_STAR              :   [0-9]*;

LINE_COMMENT_SKIP:       '//' ~[\r\n]*    -> skip;
COMMENT_SKIP:            '/*' .*? '*/'    -> skip;
