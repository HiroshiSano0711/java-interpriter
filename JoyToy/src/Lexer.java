import java.io.Reader;

public class Lexer {
  private int tok;
  private Object val;
  private LexerReader reader;

  // readerはトークンの読み込み元
  public Lexer(Reader r){
    reader = new LexerReader(r);
  }

  // 現在のトークンの種類を返す
  public int token(){
    return tok;
  }

  // 現在のトークンの値を返す
  public Object value(){
    return val;
  }

  // 次のトークンに進む
  // 返す値：次のトークンがあればtrue,なければfalse
  public boolean advance(){
    // もし次のトークンがあれば読み込んでtrueを返し、トークンがなければfalseを返す
    try {
      skipWhiteSpace();
      int c = reader.read();
      if (c < 0) {
        return false;
      }
      
      switch(c) {
      case ';':
      case '+':
      case '-':
      case '*':
      case '/':
    	  tok = c;
    	  break;
      default:
    	  if(Character.isDigit((char)c)){
    		  reader.unread(c);
    		  lexDigit();
    		  tok = TokenType.INT;
    	  }else{
    		  throw new Exception("数字じゃないとだめぽよ");
    	  }
    	  break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true; // 次のトークンが存在したのでtrue
  }

  private void lexDigit() throws Exception {
    int num = 0;
    while(true){
      int c = reader.read();
      if(c < 0){
        break;
      }
      if(!Character.isDigit((char)c)){
        reader.unread(c);
        break;
      }
      num = (num * 10) + (c - '0');
    }
    val = new Integer(num);
  }
  
  private void skipWhiteSpace() throws Exception {
	  int c = reader.read();
	  while((c != -1) && Character.isWhitespace((char)c)){
		  c = reader.read();
	  }
	  reader.unread(c);
  }
}
