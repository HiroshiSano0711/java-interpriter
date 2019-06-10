public class Parser {
	private TokenReader lex;
	
	public JTCode parse(TokenReader reader) {
		JTCode code = null;
		lex = reader;
		
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
