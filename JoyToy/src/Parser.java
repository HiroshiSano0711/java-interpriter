public class Parser {
	private Lexer lex;
	
	public JTCode parse(Lexer lexer) {
		JTCode code = null;
		lex = lexer;
		
		try {
			code = program();
		} catch (Exception e){
			e.printStackTrace();
		}
		return code;
	}
	
	private JTCode program() throws Exception {
		return expr();
	}
	
	private JTCode expr() throws Exception {
		JTCode code = null;
		if(lex.advance()) {
			int token = lex.token();
			switch(token) {
			case TokenType.INT:
				code = new JTInt((Integer)lex.value());
				break;
			default:
				throw new Exception("文法エラーぽよ"); // 数値以外はエラーを投げる
			}
		}
		return code;
	}
}
