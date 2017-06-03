// $ANTLR 3.5 H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g 2015-02-05 11:06:58

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class Exp2Lexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__7=7;
	public static final int T__8=8;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int ID=4;
	public static final int INT=5;
	public static final int WS=6;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public Exp2Lexer() {}
	public Exp2Lexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public Exp2Lexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g"; }

	// $ANTLR start "T__7"
	public final void mT__7() throws RecognitionException {
		try {
			int _type = T__7;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:7:6: ( '(' )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:7:8: '('
			{
			match('(');
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__7"

	// $ANTLR start "T__8"
	public final void mT__8() throws RecognitionException {
		try {
			int _type = T__8;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:8:6: ( ')' )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:8:8: ')'
			{
			match(')');
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__8"

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:9:6: ( '*' )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:9:8: '*'
			{
			match('*');
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:10:7: ( '+' )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:10:9: '+'
			{
			match('+');
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:11:7: ( '-' )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:11:9: '-'
			{
			match('-');
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:12:7: ( 'div' )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:12:9: 'div'
			{
			match("div");

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:61:5: ( ( '0' .. '9' )+ )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:61:9: ( '0' .. '9' )+
			{
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:61:9: ( '0' .. '9' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:62:5: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:62:9: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:62:28: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:63:5: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:63:9: ( ' ' | '\\t' | '\\n' | '\\r' )+
			{
			// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:63:9: ( ' ' | '\\t' | '\\n' | '\\r' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||LA3_0=='\r'||LA3_0==' ') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			skip();
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:8: ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | INT | ID | WS )
		int alt4=9;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt4=1;
			}
			break;
		case ')':
			{
			alt4=2;
			}
			break;
		case '*':
			{
			alt4=3;
			}
			break;
		case '+':
			{
			alt4=4;
			}
			break;
		case '-':
			{
			alt4=5;
			}
			break;
		case 'd':
			{
			int LA4_6 = input.LA(2);
			if ( (LA4_6=='i') ) {
				int LA4_10 = input.LA(3);
				if ( (LA4_10=='v') ) {
					int LA4_11 = input.LA(4);
					if ( ((LA4_11 >= '0' && LA4_11 <= '9')||(LA4_11 >= 'A' && LA4_11 <= 'Z')||LA4_11=='_'||(LA4_11 >= 'a' && LA4_11 <= 'z')) ) {
						alt4=8;
					}

					else {
						alt4=6;
					}

				}

				else {
					alt4=8;
				}

			}

			else {
				alt4=8;
			}

			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			alt4=7;
			}
			break;
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case 'a':
		case 'b':
		case 'c':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt4=8;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
		case ' ':
			{
			alt4=9;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 4, 0, input);
			throw nvae;
		}
		switch (alt4) {
			case 1 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:10: T__7
				{
				mT__7();

				}
				break;
			case 2 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:15: T__8
				{
				mT__8();

				}
				break;
			case 3 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:20: T__9
				{
				mT__9();

				}
				break;
			case 4 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:25: T__10
				{
				mT__10();

				}
				break;
			case 5 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:31: T__11
				{
				mT__11();

				}
				break;
			case 6 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:37: T__12
				{
				mT__12();

				}
				break;
			case 7 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:43: INT
				{
				mINT();

				}
				break;
			case 8 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:47: ID
				{
				mID();

				}
				break;
			case 9 :
				// H:\\windows\\COMP_PROJET_ex\\src\\Exp2.g:1:50: WS
				{
				mWS();

				}
				break;

		}
	}



}
