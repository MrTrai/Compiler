// Generated from test.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link testParser}.
 */
public interface testListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link testParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(testParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(testParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#declaration_segment}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration_segment(testParser.Declaration_segmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#declaration_segment}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration_segment(testParser.Declaration_segmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#var_declaration_list}.
	 * @param ctx the parse tree
	 */
	void enterVar_declaration_list(testParser.Var_declaration_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#var_declaration_list}.
	 * @param ctx the parse tree
	 */
	void exitVar_declaration_list(testParser.Var_declaration_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#var_declaration}.
	 * @param ctx the parse tree
	 */
	void enterVar_declaration(testParser.Var_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#var_declaration}.
	 * @param ctx the parse tree
	 */
	void exitVar_declaration(testParser.Var_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#funct_declaration_list}.
	 * @param ctx the parse tree
	 */
	void enterFunct_declaration_list(testParser.Funct_declaration_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#funct_declaration_list}.
	 * @param ctx the parse tree
	 */
	void exitFunct_declaration_list(testParser.Funct_declaration_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#funct_declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunct_declaration(testParser.Funct_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#funct_declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunct_declaration(testParser.Funct_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(testParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(testParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#param_list_tail}.
	 * @param ctx the parse tree
	 */
	void enterParam_list_tail(testParser.Param_list_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#param_list_tail}.
	 * @param ctx the parse tree
	 */
	void exitParam_list_tail(testParser.Param_list_tailContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(testParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(testParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#var_type}.
	 * @param ctx the parse tree
	 */
	void enterVar_type(testParser.Var_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#var_type}.
	 * @param ctx the parse tree
	 */
	void exitVar_type(testParser.Var_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#stat_seq}.
	 * @param ctx the parse tree
	 */
	void enterStat_seq(testParser.Stat_seqContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#stat_seq}.
	 * @param ctx the parse tree
	 */
	void exitStat_seq(testParser.Stat_seqContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(testParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(testParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#optional_init}.
	 * @param ctx the parse tree
	 */
	void enterOptional_init(testParser.Optional_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#optional_init}.
	 * @param ctx the parse tree
	 */
	void exitOptional_init(testParser.Optional_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(testParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(testParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#opt_prefix}.
	 * @param ctx the parse tree
	 */
	void enterOpt_prefix(testParser.Opt_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#opt_prefix}.
	 * @param ctx the parse tree
	 */
	void exitOpt_prefix(testParser.Opt_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#binary_operator}.
	 * @param ctx the parse tree
	 */
	void enterBinary_operator(testParser.Binary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#binary_operator}.
	 * @param ctx the parse tree
	 */
	void exitBinary_operator(testParser.Binary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#id_list}.
	 * @param ctx the parse tree
	 */
	void enterId_list(testParser.Id_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#id_list}.
	 * @param ctx the parse tree
	 */
	void exitId_list(testParser.Id_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(testParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(testParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list(testParser.Expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list(testParser.Expr_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(testParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(testParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#expr_op}.
	 * @param ctx the parse tree
	 */
	void enterExpr_op(testParser.Expr_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#expr_op}.
	 * @param ctx the parse tree
	 */
	void exitExpr_op(testParser.Expr_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#type_id}.
	 * @param ctx the parse tree
	 */
	void enterType_id(testParser.Type_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#type_id}.
	 * @param ctx the parse tree
	 */
	void exitType_id(testParser.Type_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link testParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(testParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(testParser.ConstantContext ctx);
}