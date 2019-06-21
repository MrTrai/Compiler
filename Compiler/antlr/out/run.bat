call antlr4 test.g4
call javac *.java

::CORRECT
::call grun test start ../test_cases/correct/fib.tiger
::call grun test start ../test_cases/correct/func_print.tiger
::call grun test start ../test_cases/correct/loop_arr.tiger
::call grun test start ../test_cases/correct/nestedX.tiger
::call grun test start ../test_cases/correct/precedence.tiger

::LEXICAL
::call grun test start -gui ../test_cases/lexical_errors/err1_identifier.tiger
::call grun test start -gui ../test_cases/lexical_errors/err2_float.tiger
::call grun test start -gui ../test_cases/lexical_errors/err3_modulo.tiger
::call grun test start -gui ../test_cases/lexical_errors/err4_brace.tiger
::call grun test start -gui ../test_cases/lexical_errors/err5_radix.tiger

::SEMANTIC
::call grun test start -gui ../test_cases/semantic_errors/err1_type.tiger
::call grun test start -gui ../test_cases/semantic_errors/err2_type.tiger
::call grun test start -gui ../test_cases/semantic_errors/err3_type.tiger
::call grun test start -gui ../test_cases/semantic_errors/err4_badparam.tiger
::call grun test start -gui ../test_cases/semantic_errors/err5_badparam.tiger
::call grun test start -gui ../test_cases/semantic_errors/err6_break.tiger
::call grun test start -gui ../test_cases/semantic_errors/err7_cond.tiger
::call grun test start -gui ../test_cases/semantic_errors/err8_loop.tiger
::call grun test start -gui ../test_cases/semantic_errors/err9_return.tiger
::call grun test start -gui ../test_cases/semantic_errors/err10_scope.tiger

::SYNTACTIC
::call grun test start -gui ../test_cases/syntactic_errors/err1_begin.tiger
::call grun test start -gui ../test_cases/syntactic_errors/err2_in.tiger
::call grun test start -gui ../test_cases/syntactic_errors/err3_semi.tiger
::call grun test start -gui ../test_cases/syntactic_errors/err4_ite.tiger
::call grun test start -gui ../test_cases/syntactic_errors/err5_expr.tiger