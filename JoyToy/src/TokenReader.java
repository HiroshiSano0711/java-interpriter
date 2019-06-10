import java.io.IOException;
import java.io.Reader;

public class TokenReader extends Reader {
	private boolean unget_p = false;
	private int ch;
	private int tok;
	private Object val;

	public TokenReader(Reader r) {
		super(r);
	}

	// 現在のトークンの種類を返す
	public int token() {
		return tok;
	}

	// 現在のトークンの値を返す
	public Object value() {
		return val;
	}

	public void unread(int c) {
		unget_p = true;
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (unget_p) {
			unget_p = false;
		} else {
			ch = super.read();
		}
		return ch;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
	}

	// 次のトークンに進む
	// 返す値：次のトークンがあればtrue,なければfalse
	public boolean advance() {
		// もし次のトークンがあれば読み込んでtrueを返し、トークンがなければfalseを返す
		try {
			skipWhiteSpace();
			int c = super.read();
			if (c < 0) {
				return false;
			}

			switch (c) {
			case ';':
			case '+':
			case '-':
			case '*':
			case '/':
				tok = c;
				break;
			default:
				if (Character.isDigit((char) c)) {
					unread(c);
					lexDigit();
					tok = TokenType.INT;
				} else {
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
		while (true) {
			int c = super.read();
			if (c < 0) {
				break;
			}
			if (!Character.isDigit((char) c)) {
				unread(c);
				break;
			}
			num = (num * 10) + (c - '0');
		}
		val = new Integer(num);
	}

	private void skipWhiteSpace() throws Exception {
		int c = super.read();
		while ((c != -1) && Character.isWhitespace((char) c)) {
			c = super.read();
		}
		unread(c);
	}
}
