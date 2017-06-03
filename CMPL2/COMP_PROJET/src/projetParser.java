// $ANTLR 3.5 H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g 2015-04-09 10:41:39

import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class projetParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "ID", "INT", "ML_COMMENT",
		"WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "':'", "':='", "';'",
		"'<'", "'<='", "'<>'", "'='", "'>'", "'>='", "'alors'", "'aut'", "'bool'",
		"'cond'", "'const'", "'debut'", "'def'", "'div'", "'ecrire'", "'ent'",
		"'et'", "'faire'", "'fait'", "'faux'", "'fcond'", "'fin'", "'fixe'", "'fsi'",
		"'lire'", "'mod'", "'module'", "'non'", "'ou'", "'proc'", "'programme'",
		"'ref'", "'si'", "'sinon'", "'ttq'", "'var'", "'vrai'"
	};
	public static final int EOF=-1;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int COMMENT=4;
	public static final int ID=5;
	public static final int INT=6;
	public static final int ML_COMMENT=7;
	public static final int WS=8;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public projetParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public projetParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return projetParser.tokenNames; }
	@Override public String getGrammarFileName() { return "H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g"; }




	// variables globales et methodes utiles a placer ici




	// $ANTLR start "unite"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:35:1: unite : ( unitprog EOF | unitmodule EOF );
	public final void unite() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:35:8: ( unitprog EOF | unitmodule EOF )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==48) ) {
				alt1=1;
			}
			else if ( (LA1_0==44) ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:35:12: unitprog EOF
					{
					pushFollow(FOLLOW_unitprog_in_unite62);
					unitprog();
					state._fsp--;

					match(input,EOF,FOLLOW_EOF_in_unite65);
					PtGen.afftabSymb(); PtGen.pt(123);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:36:12: unitmodule EOF
					{
					pushFollow(FOLLOW_unitmodule_in_unite80);
					unitmodule();
					state._fsp--;

					match(input,EOF,FOLLOW_EOF_in_unite83);
					PtGen.afftabSymb(); PtGen.pt(123);
					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "unite"



	// $ANTLR start "unitprog"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:39:1: unitprog : 'programme' ident ':' declarations corps ;
	public final void unitprog() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:40:3: ( 'programme' ident ':' declarations corps )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:40:5: 'programme' ident ':' declarations corps
			{
			PtGen.pt(54);
			match(input,48,FOLLOW_48_in_unitprog102);
			PtGen.pt(47);
			pushFollow(FOLLOW_ident_in_unitprog106);
			ident();
			state._fsp--;

			match(input,15,FOLLOW_15_in_unitprog108);
			pushFollow(FOLLOW_declarations_in_unitprog115);
			declarations();
			state._fsp--;

			pushFollow(FOLLOW_corps_in_unitprog122);
			corps();
			state._fsp--;

			 System.out.println("succes, arret de la compilation ");
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "unitprog"



	// $ANTLR start "unitmodule"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:45:1: unitmodule : 'module' ident ':' declarations ;
	public final void unitmodule() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:46:3: ( 'module' ident ':' declarations )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:46:5: 'module' ident ':' declarations
			{
			PtGen.pt(54);
			match(input,44,FOLLOW_44_in_unitmodule141);
			PtGen.pt(48);
			pushFollow(FOLLOW_ident_in_unitmodule145);
			ident();
			state._fsp--;

			match(input,15,FOLLOW_15_in_unitmodule147);
			pushFollow(FOLLOW_declarations_in_unitmodule155);
			declarations();
			state._fsp--;

			PtGen.pt(52);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "unitmodule"



	// $ANTLR start "declarations"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:50:1: declarations : ( partiedef )? ( partieref )? ( consts )? ( vars )? ( decprocs )? ;
	public final void declarations() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:3: ( ( partiedef )? ( partieref )? ( consts )? ( vars )? ( decprocs )? )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:5: ( partiedef )? ( partieref )? ( consts )? ( vars )? ( decprocs )?
			{
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:5: ( partiedef )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==30) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:5: partiedef
					{
					pushFollow(FOLLOW_partiedef_in_declarations173);
					partiedef();
					state._fsp--;

					}
					break;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:16: ( partieref )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==49) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:16: partieref
					{
					pushFollow(FOLLOW_partieref_in_declarations176);
					partieref();
					state._fsp--;

					}
					break;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:27: ( consts )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==28) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:27: consts
					{
					pushFollow(FOLLOW_consts_in_declarations179);
					consts();
					state._fsp--;

					}
					break;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:35: ( vars )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==53) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:35: vars
					{
					pushFollow(FOLLOW_vars_in_declarations182);
					vars();
					state._fsp--;

					}
					break;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:41: ( decprocs )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==47) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:51:41: decprocs
					{
					pushFollow(FOLLOW_decprocs_in_declarations185);
					decprocs();
					state._fsp--;

					}
					break;

			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "declarations"



	// $ANTLR start "partiedef"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:54:1: partiedef : 'def' ident ( ',' ident )* ptvg ;
	public final void partiedef() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:55:3: ( 'def' ident ( ',' ident )* ptvg )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:55:5: 'def' ident ( ',' ident )* ptvg
			{
			match(input,30,FOLLOW_30_in_partiedef202);
			pushFollow(FOLLOW_ident_in_partiedef204);
			ident();
			state._fsp--;

			PtGen.pt(53);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:55:33: ( ',' ident )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==13) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:55:34: ',' ident
					{
					match(input,13,FOLLOW_13_in_partiedef209);
					pushFollow(FOLLOW_ident_in_partiedef211);
					ident();
					state._fsp--;

					PtGen.pt(53);
					}
					break;

				default :
					break loop7;
				}
			}

			pushFollow(FOLLOW_ptvg_in_partiedef218);
			ptvg();
			state._fsp--;

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "partiedef"



	// $ANTLR start "partieref"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:58:1: partieref : 'ref' specif ( ',' specif )* ptvg ;
	public final void partieref() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:58:10: ( 'ref' specif ( ',' specif )* ptvg )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:58:12: 'ref' specif ( ',' specif )* ptvg
			{
			match(input,49,FOLLOW_49_in_partieref230);
			pushFollow(FOLLOW_specif_in_partieref233);
			specif();
			state._fsp--;

			PtGen.pt(49);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:58:42: ( ',' specif )*
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( (LA8_0==13) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:58:43: ',' specif
					{
					match(input,13,FOLLOW_13_in_partieref238);
					pushFollow(FOLLOW_specif_in_partieref240);
					specif();
					state._fsp--;

					PtGen.pt(49);
					}
					break;

				default :
					break loop8;
				}
			}

			pushFollow(FOLLOW_ptvg_in_partieref248);
			ptvg();
			state._fsp--;

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "partieref"



	// $ANTLR start "specif"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:61:1: specif : ident ( 'fixe' '(' type ( ',' type )* ')' )? ( 'mod' '(' type ( ',' type )* ')' )? ;
	public final void specif() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:61:9: ( ident ( 'fixe' '(' type ( ',' type )* ')' )? ( 'mod' '(' type ( ',' type )* ')' )? )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:61:11: ident ( 'fixe' '(' type ( ',' type )* ')' )? ( 'mod' '(' type ( ',' type )* ')' )?
			{
			pushFollow(FOLLOW_ident_in_specif262);
			ident();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:61:17: ( 'fixe' '(' type ( ',' type )* ')' )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==40) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:61:19: 'fixe' '(' type ( ',' type )* ')'
					{
					match(input,40,FOLLOW_40_in_specif266);
					match(input,9,FOLLOW_9_in_specif268);
					pushFollow(FOLLOW_type_in_specif270);
					type();
					state._fsp--;

					PtGen.pt(50);
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:61:51: ( ',' type )*
					loop9:
					while (true) {
						int alt9=2;
						int LA9_0 = input.LA(1);
						if ( (LA9_0==13) ) {
							alt9=1;
						}

						switch (alt9) {
						case 1 :
							// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:61:53: ',' type
							{
							match(input,13,FOLLOW_13_in_specif276);
							pushFollow(FOLLOW_type_in_specif278);
							type();
							state._fsp--;

							PtGen.pt(50);
							}
							break;

						default :
							break loop9;
						}
					}

					match(input,10,FOLLOW_10_in_specif285);
					}
					break;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:62:17: ( 'mod' '(' type ( ',' type )* ')' )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==43) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:62:19: 'mod' '(' type ( ',' type )* ')'
					{
					match(input,43,FOLLOW_43_in_specif309);
					match(input,9,FOLLOW_9_in_specif312);
					pushFollow(FOLLOW_type_in_specif314);
					type();
					state._fsp--;

					PtGen.pt(51);
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:62:51: ( ',' type )*
					loop11:
					while (true) {
						int alt11=2;
						int LA11_0 = input.LA(1);
						if ( (LA11_0==13) ) {
							alt11=1;
						}

						switch (alt11) {
						case 1 :
							// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:62:53: ',' type
							{
							match(input,13,FOLLOW_13_in_specif320);
							pushFollow(FOLLOW_type_in_specif322);
							type();
							state._fsp--;

							PtGen.pt(51);
							}
							break;

						default :
							break loop11;
						}
					}

					match(input,10,FOLLOW_10_in_specif329);
					}
					break;

			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "specif"



	// $ANTLR start "consts"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:65:1: consts : 'const' ( ident '=' valeur ptvg )+ ;
	public final void consts() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:65:9: ( 'const' ( ident '=' valeur ptvg )+ )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:65:11: 'const' ( ident '=' valeur ptvg )+
			{
			match(input,28,FOLLOW_28_in_consts347);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:65:19: ( ident '=' valeur ptvg )+
			int cnt13=0;
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==ID) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:65:21: ident '=' valeur ptvg
					{
					pushFollow(FOLLOW_ident_in_consts351);
					ident();
					state._fsp--;

					PtGen.pt(1);
					match(input,21,FOLLOW_21_in_consts355);
					pushFollow(FOLLOW_valeur_in_consts357);
					valeur();
					state._fsp--;

					PtGen.pt(2);
					pushFollow(FOLLOW_ptvg_in_consts361);
					ptvg();
					state._fsp--;

					}
					break;

				default :
					if ( cnt13 >= 1 ) break loop13;
					EarlyExitException eee = new EarlyExitException(13, input);
					throw eee;
				}
				cnt13++;
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "consts"



	// $ANTLR start "vars"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:68:1: vars : 'var' ( type ident ( ',' ident )* ptvg )+ ;
	public final void vars() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:68:7: ( 'var' ( type ident ( ',' ident )* ptvg )+ )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:68:9: 'var' ( type ident ( ',' ident )* ptvg )+
			{
			match(input,53,FOLLOW_53_in_vars381);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:68:15: ( type ident ( ',' ident )* ptvg )+
			int cnt15=0;
			loop15:
			while (true) {
				int alt15=2;
				int LA15_0 = input.LA(1);
				if ( (LA15_0==26||LA15_0==33) ) {
					alt15=1;
				}

				switch (alt15) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:68:17: type ident ( ',' ident )* ptvg
					{
					pushFollow(FOLLOW_type_in_vars385);
					type();
					state._fsp--;

					pushFollow(FOLLOW_ident_in_vars387);
					ident();
					state._fsp--;

					 PtGen.pt(1); PtGen.pt(9);
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:68:58: ( ',' ident )*
					loop14:
					while (true) {
						int alt14=2;
						int LA14_0 = input.LA(1);
						if ( (LA14_0==13) ) {
							alt14=1;
						}

						switch (alt14) {
						case 1 :
							// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:68:60: ',' ident
							{
							match(input,13,FOLLOW_13_in_vars393);
							pushFollow(FOLLOW_ident_in_vars396);
							ident();
							state._fsp--;

							 PtGen.pt(1); PtGen.pt(9);
							}
							break;

						default :
							break loop14;
						}
					}

					pushFollow(FOLLOW_ptvg_in_vars403);
					ptvg();
					state._fsp--;

					}
					break;

				default :
					if ( cnt15 >= 1 ) break loop15;
					EarlyExitException eee = new EarlyExitException(15, input);
					throw eee;
				}
				cnt15++;
			}

			 PtGen.pt(91);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "vars"



	// $ANTLR start "type"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:71:1: type : ( 'ent' | 'bool' );
	public final void type() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:71:7: ( 'ent' | 'bool' )
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==33) ) {
				alt16=1;
			}
			else if ( (LA16_0==26) ) {
				alt16=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 16, 0, input);
				throw nvae;
			}

			switch (alt16) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:71:9: 'ent'
					{
					match(input,33,FOLLOW_33_in_type422);
					PtGen.pt(7);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:72:9: 'bool'
					{
					match(input,26,FOLLOW_26_in_type435);
					PtGen.pt(8);
					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "type"



	// $ANTLR start "decprocs"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:75:1: decprocs : ( decproc ptvg )+ ;
	public final void decprocs() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:75:9: ( ( decproc ptvg )+ )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:75:11: ( decproc ptvg )+
			{
			 PtGen.pt(124);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:75:30: ( decproc ptvg )+
			int cnt17=0;
			loop17:
			while (true) {
				int alt17=2;
				int LA17_0 = input.LA(1);
				if ( (LA17_0==47) ) {
					alt17=1;
				}

				switch (alt17) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:75:31: decproc ptvg
					{
					pushFollow(FOLLOW_decproc_in_decprocs452);
					decproc();
					state._fsp--;

					pushFollow(FOLLOW_ptvg_in_decprocs454);
					ptvg();
					state._fsp--;

					}
					break;

				default :
					if ( cnt17 >= 1 ) break loop17;
					EarlyExitException eee = new EarlyExitException(17, input);
					throw eee;
				}
				cnt17++;
			}

			 PtGen.pt(125);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "decprocs"



	// $ANTLR start "decproc"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:1: decproc : 'proc' ident ( parfixe )? ( parmod )? ( consts )? ( vars )? corps ;
	public final void decproc() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:9: ( 'proc' ident ( parfixe )? ( parmod )? ( consts )? ( vars )? corps )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:12: 'proc' ident ( parfixe )? ( parmod )? ( consts )? ( vars )? corps
			{
			match(input,47,FOLLOW_47_in_decproc472);
			pushFollow(FOLLOW_ident_in_decproc475);
			ident();
			state._fsp--;

			PtGen.pt(40);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:42: ( parfixe )?
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==40) ) {
				alt18=1;
			}
			switch (alt18) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:42: parfixe
					{
					pushFollow(FOLLOW_parfixe_in_decproc479);
					parfixe();
					state._fsp--;

					}
					break;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:51: ( parmod )?
			int alt19=2;
			int LA19_0 = input.LA(1);
			if ( (LA19_0==43) ) {
				alt19=1;
			}
			switch (alt19) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:51: parmod
					{
					pushFollow(FOLLOW_parmod_in_decproc482);
					parmod();
					state._fsp--;

					}
					break;

			}

			PtGen.pt(43);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:75: ( consts )?
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==28) ) {
				alt20=1;
			}
			switch (alt20) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:75: consts
					{
					pushFollow(FOLLOW_consts_in_decproc487);
					consts();
					state._fsp--;

					}
					break;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:83: ( vars )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==53) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:78:83: vars
					{
					pushFollow(FOLLOW_vars_in_decproc490);
					vars();
					state._fsp--;

					}
					break;

			}

			pushFollow(FOLLOW_corps_in_decproc493);
			corps();
			state._fsp--;

			PtGen.pt(44);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "decproc"



	// $ANTLR start "ptvg"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:81:1: ptvg : ( ';' |);
	public final void ptvg() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:81:7: ( ';' |)
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==17) ) {
				alt22=1;
			}
			else if ( (LA22_0==EOF||LA22_0==ID||LA22_0==26||(LA22_0 >= 28 && LA22_0 <= 29)||LA22_0==33||LA22_0==47||LA22_0==49||LA22_0==53) ) {
				alt22=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 22, 0, input);
				throw nvae;
			}

			switch (alt22) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:81:9: ';'
					{
					match(input,17,FOLLOW_17_in_ptvg509);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:83:3:
					{
					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ptvg"



	// $ANTLR start "corps"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:85:1: corps : 'debut' instructions 'fin' ;
	public final void corps() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:85:7: ( 'debut' instructions 'fin' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:85:9: 'debut' instructions 'fin'
			{
			match(input,29,FOLLOW_29_in_corps527);
			pushFollow(FOLLOW_instructions_in_corps529);
			instructions();
			state._fsp--;

			match(input,39,FOLLOW_39_in_corps531);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "corps"



	// $ANTLR start "parfixe"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:88:1: parfixe : 'fixe' '(' pf ( ';' pf )* ')' ;
	public final void parfixe() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:88:8: ( 'fixe' '(' pf ( ';' pf )* ')' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:88:10: 'fixe' '(' pf ( ';' pf )* ')'
			{
			match(input,40,FOLLOW_40_in_parfixe543);
			match(input,9,FOLLOW_9_in_parfixe545);
			pushFollow(FOLLOW_pf_in_parfixe547);
			pf();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:88:24: ( ';' pf )*
			loop23:
			while (true) {
				int alt23=2;
				int LA23_0 = input.LA(1);
				if ( (LA23_0==17) ) {
					alt23=1;
				}

				switch (alt23) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:88:26: ';' pf
					{
					match(input,17,FOLLOW_17_in_parfixe551);
					pushFollow(FOLLOW_pf_in_parfixe553);
					pf();
					state._fsp--;

					}
					break;

				default :
					break loop23;
				}
			}

			match(input,10,FOLLOW_10_in_parfixe557);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "parfixe"



	// $ANTLR start "pf"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:91:1: pf : type ident ( ',' ident )* ;
	public final void pf() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:91:5: ( type ident ( ',' ident )* )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:91:7: type ident ( ',' ident )*
			{
			pushFollow(FOLLOW_type_in_pf571);
			type();
			state._fsp--;

			pushFollow(FOLLOW_ident_in_pf573);
			ident();
			state._fsp--;

			PtGen.pt(41);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:91:34: ( ',' ident )*
			loop24:
			while (true) {
				int alt24=2;
				int LA24_0 = input.LA(1);
				if ( (LA24_0==13) ) {
					alt24=1;
				}

				switch (alt24) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:91:36: ',' ident
					{
					match(input,13,FOLLOW_13_in_pf579);
					pushFollow(FOLLOW_ident_in_pf581);
					ident();
					state._fsp--;

					PtGen.pt(41);
					}
					break;

				default :
					break loop24;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "pf"



	// $ANTLR start "parmod"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:94:1: parmod : 'mod' '(' pm ( ';' pm )* ')' ;
	public final void parmod() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:94:9: ( 'mod' '(' pm ( ';' pm )* ')' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:94:11: 'mod' '(' pm ( ';' pm )* ')'
			{
			match(input,43,FOLLOW_43_in_parmod600);
			match(input,9,FOLLOW_9_in_parmod602);
			pushFollow(FOLLOW_pm_in_parmod604);
			pm();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:94:24: ( ';' pm )*
			loop25:
			while (true) {
				int alt25=2;
				int LA25_0 = input.LA(1);
				if ( (LA25_0==17) ) {
					alt25=1;
				}

				switch (alt25) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:94:26: ';' pm
					{
					match(input,17,FOLLOW_17_in_parmod608);
					pushFollow(FOLLOW_pm_in_parmod610);
					pm();
					state._fsp--;

					}
					break;

				default :
					break loop25;
				}
			}

			match(input,10,FOLLOW_10_in_parmod614);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "parmod"



	// $ANTLR start "pm"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:97:1: pm : type ident ( ',' ident )* ;
	public final void pm() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:97:5: ( type ident ( ',' ident )* )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:97:7: type ident ( ',' ident )*
			{
			pushFollow(FOLLOW_type_in_pm628);
			type();
			state._fsp--;

			pushFollow(FOLLOW_ident_in_pm630);
			ident();
			state._fsp--;

			PtGen.pt(42);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:97:35: ( ',' ident )*
			loop26:
			while (true) {
				int alt26=2;
				int LA26_0 = input.LA(1);
				if ( (LA26_0==13) ) {
					alt26=1;
				}

				switch (alt26) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:97:37: ',' ident
					{
					match(input,13,FOLLOW_13_in_pm637);
					pushFollow(FOLLOW_ident_in_pm639);
					ident();
					state._fsp--;

					PtGen.pt(42);
					}
					break;

				default :
					break loop26;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "pm"



	// $ANTLR start "instructions"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:100:1: instructions : instruction ( ';' instruction )* ;
	public final void instructions() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:101:3: ( instruction ( ';' instruction )* )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:101:5: instruction ( ';' instruction )*
			{
			pushFollow(FOLLOW_instruction_in_instructions659);
			instruction();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:101:17: ( ';' instruction )*
			loop27:
			while (true) {
				int alt27=2;
				int LA27_0 = input.LA(1);
				if ( (LA27_0==17) ) {
					alt27=1;
				}

				switch (alt27) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:101:19: ';' instruction
					{
					match(input,17,FOLLOW_17_in_instructions663);
					pushFollow(FOLLOW_instruction_in_instructions665);
					instruction();
					state._fsp--;

					}
					break;

				default :
					break loop27;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "instructions"



	// $ANTLR start "instruction"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:104:1: instruction : ( inssi | inscond | boucle | lecture | ecriture | affouappel |);
	public final void instruction() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:105:3: ( inssi | inscond | boucle | lecture | ecriture | affouappel |)
			int alt28=7;
			switch ( input.LA(1) ) {
			case 50:
				{
				alt28=1;
				}
				break;
			case 27:
				{
				alt28=2;
				}
				break;
			case 52:
				{
				alt28=3;
				}
				break;
			case 42:
				{
				alt28=4;
				}
				break;
			case 32:
				{
				alt28=5;
				}
				break;
			case ID:
				{
				alt28=6;
				}
				break;
			case 13:
			case 17:
			case 25:
			case 36:
			case 38:
			case 39:
			case 41:
			case 51:
				{
				alt28=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 28, 0, input);
				throw nvae;
			}
			switch (alt28) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:105:5: inssi
					{
					pushFollow(FOLLOW_inssi_in_instruction682);
					inssi();
					state._fsp--;

					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:106:5: inscond
					{
					pushFollow(FOLLOW_inscond_in_instruction688);
					inscond();
					state._fsp--;

					}
					break;
				case 3 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:107:5: boucle
					{
					pushFollow(FOLLOW_boucle_in_instruction694);
					boucle();
					state._fsp--;

					}
					break;
				case 4 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:108:5: lecture
					{
					pushFollow(FOLLOW_lecture_in_instruction700);
					lecture();
					state._fsp--;

					}
					break;
				case 5 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:109:5: ecriture
					{
					pushFollow(FOLLOW_ecriture_in_instruction706);
					ecriture();
					state._fsp--;

					}
					break;
				case 6 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:110:5: affouappel
					{
					pushFollow(FOLLOW_affouappel_in_instruction712);
					affouappel();
					state._fsp--;

					}
					break;
				case 7 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:112:3:
					{
					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "instruction"



	// $ANTLR start "inssi"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:114:1: inssi : 'si' expression 'alors' instructions ( 'sinon' instructions )? 'fsi' ;
	public final void inssi() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:114:7: ( 'si' expression 'alors' instructions ( 'sinon' instructions )? 'fsi' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:114:9: 'si' expression 'alors' instructions ( 'sinon' instructions )? 'fsi'
			{
			match(input,50,FOLLOW_50_in_inssi729);
			pushFollow(FOLLOW_expression_in_inssi731);
			expression();
			state._fsp--;

			 PtGen.pt(30);
			match(input,24,FOLLOW_24_in_inssi737);
			pushFollow(FOLLOW_instructions_in_inssi739);
			instructions();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:116:3: ( 'sinon' instructions )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==51) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:116:4: 'sinon' instructions
					{
					match(input,51,FOLLOW_51_in_inssi745);
					 PtGen.pt(32);
					pushFollow(FOLLOW_instructions_in_inssi749);
					instructions();
					state._fsp--;

					}
					break;

			}

			 PtGen.pt(33);
			match(input,41,FOLLOW_41_in_inssi759);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "inssi"



	// $ANTLR start "inscond"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:120:1: inscond : 'cond' expression ':' instructions ( ',' expression ':' instructions )* ( 'aut' instructions )? 'fcond' ;
	public final void inscond() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:120:9: ( 'cond' expression ':' instructions ( ',' expression ':' instructions )* ( 'aut' instructions )? 'fcond' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:120:11: 'cond' expression ':' instructions ( ',' expression ':' instructions )* ( 'aut' instructions )? 'fcond'
			{
			match(input,27,FOLLOW_27_in_inscond772);
			 PtGen.pt(36);
			pushFollow(FOLLOW_expression_in_inscond777);
			expression();
			state._fsp--;

			 PtGen.pt(30);
			match(input,15,FOLLOW_15_in_inscond782);
			pushFollow(FOLLOW_instructions_in_inscond784);
			instructions();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:121:11: ( ',' expression ':' instructions )*
			loop30:
			while (true) {
				int alt30=2;
				int LA30_0 = input.LA(1);
				if ( (LA30_0==13) ) {
					alt30=1;
				}

				switch (alt30) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:121:12: ',' expression ':' instructions
					{
					match(input,13,FOLLOW_13_in_inscond797);
					 PtGen.pt(37);
					pushFollow(FOLLOW_expression_in_inscond802);
					expression();
					state._fsp--;

					 PtGen.pt(30);
					match(input,15,FOLLOW_15_in_inscond807);
					pushFollow(FOLLOW_instructions_in_inscond809);
					instructions();
					state._fsp--;

					}
					break;

				default :
					break loop30;
				}
			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:122:11: ( 'aut' instructions )?
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==25) ) {
				alt31=1;
			}
			switch (alt31) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:122:12: 'aut' instructions
					{
					 PtGen.pt(38);
					match(input,25,FOLLOW_25_in_inscond828);
					pushFollow(FOLLOW_instructions_in_inscond830);
					instructions();
					state._fsp--;

					}
					break;

			}

			 PtGen.pt(39);
			match(input,38,FOLLOW_38_in_inscond848);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "inscond"



	// $ANTLR start "boucle"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:126:1: boucle : 'ttq' expression 'faire' instructions 'fait' ;
	public final void boucle() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:126:9: ( 'ttq' expression 'faire' instructions 'fait' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:126:11: 'ttq' expression 'faire' instructions 'fait'
			{
			match(input,52,FOLLOW_52_in_boucle863);
			 PtGen.pt(34);
			pushFollow(FOLLOW_expression_in_boucle867);
			expression();
			state._fsp--;

			 PtGen.pt(30);
			match(input,35,FOLLOW_35_in_boucle871);
			pushFollow(FOLLOW_instructions_in_boucle873);
			instructions();
			state._fsp--;

			 PtGen.pt(35);
			match(input,36,FOLLOW_36_in_boucle877);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "boucle"



	// $ANTLR start "lecture"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:129:1: lecture : 'lire' '(' ident ( ',' ident )* ')' ;
	public final void lecture() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:129:8: ( 'lire' '(' ident ( ',' ident )* ')' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:129:10: 'lire' '(' ident ( ',' ident )* ')'
			{
			match(input,42,FOLLOW_42_in_lecture890);
			match(input,9,FOLLOW_9_in_lecture892);
			pushFollow(FOLLOW_ident_in_lecture894);
			ident();
			state._fsp--;

			 PtGen.pt(26);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:129:45: ( ',' ident )*
			loop32:
			while (true) {
				int alt32=2;
				int LA32_0 = input.LA(1);
				if ( (LA32_0==13) ) {
					alt32=1;
				}

				switch (alt32) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:129:47: ',' ident
					{
					match(input,13,FOLLOW_13_in_lecture900);
					pushFollow(FOLLOW_ident_in_lecture902);
					ident();
					state._fsp--;

					 PtGen.pt(26);
					}
					break;

				default :
					break loop32;
				}
			}

			match(input,10,FOLLOW_10_in_lecture909);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "lecture"



	// $ANTLR start "ecriture"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:132:1: ecriture : 'ecrire' '(' expression ( ',' expression )* ')' ;
	public final void ecriture() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:132:9: ( 'ecrire' '(' expression ( ',' expression )* ')' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:132:11: 'ecrire' '(' expression ( ',' expression )* ')'
			{
			match(input,32,FOLLOW_32_in_ecriture922);
			match(input,9,FOLLOW_9_in_ecriture924);
			pushFollow(FOLLOW_expression_in_ecriture926);
			expression();
			state._fsp--;

			 PtGen.pt(27);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:132:53: ( ',' expression )*
			loop33:
			while (true) {
				int alt33=2;
				int LA33_0 = input.LA(1);
				if ( (LA33_0==13) ) {
					alt33=1;
				}

				switch (alt33) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:132:55: ',' expression
					{
					match(input,13,FOLLOW_13_in_ecriture932);
					pushFollow(FOLLOW_expression_in_ecriture934);
					expression();
					state._fsp--;

					 PtGen.pt(27);
					}
					break;

				default :
					break loop33;
				}
			}

			match(input,10,FOLLOW_10_in_ecriture941);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ecriture"



	// $ANTLR start "affouappel"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:135:1: affouappel : ident ( ':=' expression | ( effixes ( effmods )? )? ) ;
	public final void affouappel() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:136:3: ( ident ( ':=' expression | ( effixes ( effmods )? )? ) )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:136:5: ident ( ':=' expression | ( effixes ( effmods )? )? )
			{
			pushFollow(FOLLOW_ident_in_affouappel957);
			ident();
			state._fsp--;

			 PtGen.pt(28);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:136:29: ( ':=' expression | ( effixes ( effmods )? )? )
			int alt36=2;
			int LA36_0 = input.LA(1);
			if ( (LA36_0==16) ) {
				alt36=1;
			}
			else if ( (LA36_0==9||LA36_0==13||LA36_0==17||LA36_0==25||LA36_0==36||(LA36_0 >= 38 && LA36_0 <= 39)||LA36_0==41||LA36_0==51) ) {
				alt36=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 36, 0, input);
				throw nvae;
			}

			switch (alt36) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:136:31: ':=' expression
					{
					match(input,16,FOLLOW_16_in_affouappel963);
					pushFollow(FOLLOW_expression_in_affouappel965);
					expression();
					state._fsp--;

					 PtGen.pt(29);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:137:17: ( effixes ( effmods )? )?
					{
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:137:17: ( effixes ( effmods )? )?
					int alt35=2;
					int LA35_0 = input.LA(1);
					if ( (LA35_0==9) ) {
						alt35=1;
					}
					switch (alt35) {
						case 1 :
							// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:137:18: effixes ( effmods )?
							{
							pushFollow(FOLLOW_effixes_in_affouappel986);
							effixes();
							state._fsp--;

							// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:137:26: ( effmods )?
							int alt34=2;
							int LA34_0 = input.LA(1);
							if ( (LA34_0==9) ) {
								alt34=1;
							}
							switch (alt34) {
								case 1 :
									// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:137:27: effmods
									{
									pushFollow(FOLLOW_effmods_in_affouappel989);
									effmods();
									state._fsp--;

									}
									break;

							}

							}
							break;

					}

					 PtGen.pt(46);
					}
					break;

			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "affouappel"



	// $ANTLR start "effixes"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:141:1: effixes : '(' ( expression ( ',' expression )* )? ')' ;
	public final void effixes() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:141:9: ( '(' ( expression ( ',' expression )* )? ')' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:141:11: '(' ( expression ( ',' expression )* )? ')'
			{
			match(input,9,FOLLOW_9_in_effixes1022);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:141:15: ( expression ( ',' expression )* )?
			int alt38=2;
			int LA38_0 = input.LA(1);
			if ( ((LA38_0 >= ID && LA38_0 <= INT)||LA38_0==9||LA38_0==12||LA38_0==14||LA38_0==37||LA38_0==45||LA38_0==54) ) {
				alt38=1;
			}
			switch (alt38) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:141:16: expression ( ',' expression )*
					{
					pushFollow(FOLLOW_expression_in_effixes1025);
					expression();
					state._fsp--;

					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:141:28: ( ',' expression )*
					loop37:
					while (true) {
						int alt37=2;
						int LA37_0 = input.LA(1);
						if ( (LA37_0==13) ) {
							alt37=1;
						}

						switch (alt37) {
						case 1 :
							// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:141:29: ',' expression
							{
							match(input,13,FOLLOW_13_in_effixes1029);
							pushFollow(FOLLOW_expression_in_effixes1031);
							expression();
							state._fsp--;

							}
							break;

						default :
							break loop37;
						}
					}

					}
					break;

			}

			match(input,10,FOLLOW_10_in_effixes1039);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "effixes"



	// $ANTLR start "effmods"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:144:1: effmods : '(' ( ident ( ',' ident )* )? ')' ;
	public final void effmods() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:144:9: ( '(' ( ident ( ',' ident )* )? ')' )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:144:10: '(' ( ident ( ',' ident )* )? ')'
			{
			match(input,9,FOLLOW_9_in_effmods1051);
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:144:14: ( ident ( ',' ident )* )?
			int alt40=2;
			int LA40_0 = input.LA(1);
			if ( (LA40_0==ID) ) {
				alt40=1;
			}
			switch (alt40) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:144:15: ident ( ',' ident )*
					{
					pushFollow(FOLLOW_ident_in_effmods1054);
					ident();
					state._fsp--;

					 PtGen.pt(45);
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:144:39: ( ',' ident )*
					loop39:
					while (true) {
						int alt39=2;
						int LA39_0 = input.LA(1);
						if ( (LA39_0==13) ) {
							alt39=1;
						}

						switch (alt39) {
						case 1 :
							// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:144:40: ',' ident
							{
							match(input,13,FOLLOW_13_in_effmods1059);
							pushFollow(FOLLOW_ident_in_effmods1061);
							ident();
							state._fsp--;

							 PtGen.pt(45);
							}
							break;

						default :
							break loop39;
						}
					}

					}
					break;

			}

			match(input,10,FOLLOW_10_in_effmods1070);
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "effmods"



	// $ANTLR start "expression"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:147:1: expression : ( exp1 ) ( 'ou' exp1 )* ;
	public final void expression() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:147:11: ( ( exp1 ) ( 'ou' exp1 )* )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:147:13: ( exp1 ) ( 'ou' exp1 )*
			{
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:147:13: ( exp1 )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:147:14: exp1
			{
			pushFollow(FOLLOW_exp1_in_expression1084);
			exp1();
			state._fsp--;

			}

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:147:20: ( 'ou' exp1 )*
			loop41:
			while (true) {
				int alt41=2;
				int LA41_0 = input.LA(1);
				if ( (LA41_0==46) ) {
					alt41=1;
				}

				switch (alt41) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:147:21: 'ou' exp1
					{
					match(input,46,FOLLOW_46_in_expression1088);
					PtGen.pt(10);
					pushFollow(FOLLOW_exp1_in_expression1093);
					exp1();
					state._fsp--;

					PtGen.pt(10);
					PtGen.pt(11);
					}
					break;

				default :
					break loop41;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "expression"



	// $ANTLR start "exp1"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:150:1: exp1 : exp2 ( 'et' exp2 )* ;
	public final void exp1() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:150:7: ( exp2 ( 'et' exp2 )* )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:150:9: exp2 ( 'et' exp2 )*
			{
			pushFollow(FOLLOW_exp2_in_exp11115);
			exp2();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:150:14: ( 'et' exp2 )*
			loop42:
			while (true) {
				int alt42=2;
				int LA42_0 = input.LA(1);
				if ( (LA42_0==34) ) {
					alt42=1;
				}

				switch (alt42) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:150:15: 'et' exp2
					{
					match(input,34,FOLLOW_34_in_exp11118);
					PtGen.pt(10);
					pushFollow(FOLLOW_exp2_in_exp11123);
					exp2();
					state._fsp--;

					PtGen.pt(10);
					PtGen.pt(12);
					}
					break;

				default :
					break loop42;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exp1"



	// $ANTLR start "exp2"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:153:1: exp2 : ( 'non' exp2 | exp3 );
	public final void exp2() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:153:7: ( 'non' exp2 | exp3 )
			int alt43=2;
			int LA43_0 = input.LA(1);
			if ( (LA43_0==45) ) {
				alt43=1;
			}
			else if ( ((LA43_0 >= ID && LA43_0 <= INT)||LA43_0==9||LA43_0==12||LA43_0==14||LA43_0==37||LA43_0==54) ) {
				alt43=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 43, 0, input);
				throw nvae;
			}

			switch (alt43) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:153:9: 'non' exp2
					{
					match(input,45,FOLLOW_45_in_exp21145);
					pushFollow(FOLLOW_exp2_in_exp21147);
					exp2();
					state._fsp--;

					PtGen.pt(10);
					PtGen.pt(13);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:154:5: exp3
					{
					pushFollow(FOLLOW_exp3_in_exp21157);
					exp3();
					state._fsp--;

					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exp2"



	// $ANTLR start "exp3"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:157:1: exp3 : exp4 ( '=' exp4 | '<>' exp4 | '>' exp4 | '>=' exp4 | '<' exp4 | '<=' exp4 )? ;
	public final void exp3() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:157:7: ( exp4 ( '=' exp4 | '<>' exp4 | '>' exp4 | '>=' exp4 | '<' exp4 | '<=' exp4 )? )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:157:9: exp4 ( '=' exp4 | '<>' exp4 | '>' exp4 | '>=' exp4 | '<' exp4 | '<=' exp4 )?
			{
			pushFollow(FOLLOW_exp4_in_exp31171);
			exp4();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:158:3: ( '=' exp4 | '<>' exp4 | '>' exp4 | '>=' exp4 | '<' exp4 | '<=' exp4 )?
			int alt44=7;
			switch ( input.LA(1) ) {
				case 21:
					{
					alt44=1;
					}
					break;
				case 20:
					{
					alt44=2;
					}
					break;
				case 22:
					{
					alt44=3;
					}
					break;
				case 23:
					{
					alt44=4;
					}
					break;
				case 18:
					{
					alt44=5;
					}
					break;
				case 19:
					{
					alt44=6;
					}
					break;
			}
			switch (alt44) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:158:5: '=' exp4
					{
					match(input,21,FOLLOW_21_in_exp31177);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp4_in_exp31181);
					exp4();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(15);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:159:5: '<>' exp4
					{
					match(input,20,FOLLOW_20_in_exp31191);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp4_in_exp31195);
					exp4();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(16);
					}
					break;
				case 3 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:160:5: '>' exp4
					{
					match(input,22,FOLLOW_22_in_exp31205);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp4_in_exp31210);
					exp4();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(17);
					}
					break;
				case 4 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:161:5: '>=' exp4
					{
					match(input,23,FOLLOW_23_in_exp31220);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp4_in_exp31224);
					exp4();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(18);
					}
					break;
				case 5 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:162:5: '<' exp4
					{
					match(input,18,FOLLOW_18_in_exp31234);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp4_in_exp31239);
					exp4();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(19);
					}
					break;
				case 6 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:163:5: '<=' exp4
					{
					match(input,19,FOLLOW_19_in_exp31249);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp4_in_exp31253);
					exp4();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(20);
					}
					break;

			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exp3"



	// $ANTLR start "exp4"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:167:1: exp4 : exp5 ( '+' exp5 | '-' exp5 )* ;
	public final void exp4() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:167:7: ( exp5 ( '+' exp5 | '-' exp5 )* )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:167:9: exp5 ( '+' exp5 | '-' exp5 )*
			{
			pushFollow(FOLLOW_exp5_in_exp41277);
			exp5();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:168:9: ( '+' exp5 | '-' exp5 )*
			loop45:
			while (true) {
				int alt45=3;
				int LA45_0 = input.LA(1);
				if ( (LA45_0==12) ) {
					alt45=1;
				}
				else if ( (LA45_0==14) ) {
					alt45=2;
				}

				switch (alt45) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:168:10: '+' exp5
					{
					match(input,12,FOLLOW_12_in_exp41288);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp5_in_exp41292);
					exp5();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(21);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:169:10: '-' exp5
					{
					match(input,14,FOLLOW_14_in_exp41307);
					 PtGen.pt(14);
					pushFollow(FOLLOW_exp5_in_exp41311);
					exp5();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(22);
					}
					break;

				default :
					break loop45;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exp4"



	// $ANTLR start "exp5"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:173:1: exp5 : primaire ( '*' primaire | 'div' primaire )* ;
	public final void exp5() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:173:7: ( primaire ( '*' primaire | 'div' primaire )* )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:173:9: primaire ( '*' primaire | 'div' primaire )*
			{
			pushFollow(FOLLOW_primaire_in_exp51340);
			primaire();
			state._fsp--;

			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:174:9: ( '*' primaire | 'div' primaire )*
			loop46:
			while (true) {
				int alt46=3;
				int LA46_0 = input.LA(1);
				if ( (LA46_0==11) ) {
					alt46=1;
				}
				else if ( (LA46_0==31) ) {
					alt46=2;
				}

				switch (alt46) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:174:14: '*' primaire
					{
					match(input,11,FOLLOW_11_in_exp51355);
					 PtGen.pt(14);
					pushFollow(FOLLOW_primaire_in_exp51359);
					primaire();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(23);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:175:13: 'div' primaire
					{
					match(input,31,FOLLOW_31_in_exp51377);
					 PtGen.pt(14);
					pushFollow(FOLLOW_primaire_in_exp51382);
					primaire();
					state._fsp--;

					 PtGen.pt(14);
					 PtGen.pt(24);
					}
					break;

				default :
					break loop46;
				}
			}

			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "exp5"



	// $ANTLR start "primaire"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:179:1: primaire : ( valeur | ident | '(' expression ')' );
	public final void primaire() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:179:9: ( valeur | ident | '(' expression ')' )
			int alt47=3;
			switch ( input.LA(1) ) {
			case INT:
			case 12:
			case 14:
			case 37:
			case 54:
				{
				alt47=1;
				}
				break;
			case ID:
				{
				alt47=2;
				}
				break;
			case 9:
				{
				alt47=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 47, 0, input);
				throw nvae;
			}
			switch (alt47) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:179:11: valeur
					{
					pushFollow(FOLLOW_valeur_in_primaire1409);
					valeur();
					state._fsp--;

					 PtGen.pt(25);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:180:5: ident
					{
					pushFollow(FOLLOW_ident_in_primaire1417);
					ident();
					state._fsp--;

					 PtGen.pt(31);
					}
					break;
				case 3 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:181:5: '(' expression ')'
					{
					match(input,9,FOLLOW_9_in_primaire1426);
					pushFollow(FOLLOW_expression_in_primaire1428);
					expression();
					state._fsp--;

					match(input,10,FOLLOW_10_in_primaire1430);
					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "primaire"



	// $ANTLR start "valeur"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:184:1: valeur : ( nbentier | '+' nbentier | '-' nbentier | 'vrai' | 'faux' );
	public final void valeur() throws RecognitionException {
		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:184:9: ( nbentier | '+' nbentier | '-' nbentier | 'vrai' | 'faux' )
			int alt48=5;
			switch ( input.LA(1) ) {
			case INT:
				{
				alt48=1;
				}
				break;
			case 12:
				{
				alt48=2;
				}
				break;
			case 14:
				{
				alt48=3;
				}
				break;
			case 54:
				{
				alt48=4;
				}
				break;
			case 37:
				{
				alt48=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 48, 0, input);
				throw nvae;
			}
			switch (alt48) {
				case 1 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:184:11: nbentier
					{
					pushFollow(FOLLOW_nbentier_in_valeur1444);
					nbentier();
					state._fsp--;

					PtGen.pt(3);
					}
					break;
				case 2 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:185:5: '+' nbentier
					{
					match(input,12,FOLLOW_12_in_valeur1452);
					pushFollow(FOLLOW_nbentier_in_valeur1454);
					nbentier();
					state._fsp--;

					PtGen.pt(3);
					}
					break;
				case 3 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:186:5: '-' nbentier
					{
					match(input,14,FOLLOW_14_in_valeur1462);
					pushFollow(FOLLOW_nbentier_in_valeur1464);
					nbentier();
					state._fsp--;

					PtGen.pt(4);
					}
					break;
				case 4 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:187:5: 'vrai'
					{
					match(input,54,FOLLOW_54_in_valeur1472);
					PtGen.pt(5);
					}
					break;
				case 5 :
					// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:188:5: 'faux'
					{
					match(input,37,FOLLOW_37_in_valeur1480);
					PtGen.pt(6);
					}
					break;

			}
		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "valeur"



	// $ANTLR start "nbentier"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:198:1: nbentier : INT ;
	public final void nbentier() throws RecognitionException {
		Token INT1=null;

		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:198:11: ( INT )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:198:15: INT
			{
			INT1=(Token)match(input,INT,FOLLOW_INT_in_nbentier1510);
			 UtilLex.valNb = Integer.parseInt((INT1!=null?INT1.getText():null));
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "nbentier"



	// $ANTLR start "ident"
	// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:200:1: ident : ID ;
	public final void ident() throws RecognitionException {
		Token ID2=null;

		try {
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:200:7: ( ID )
			// H:\\windows\\git\\Compilation\\COMP_PROJET\\src\\projet.g:200:9: ID
			{
			ID2=(Token)match(input,ID,FOLLOW_ID_in_ident1521);
			 UtilLex.traiterId((ID2!=null?ID2.getText():null), (ID2!=null?ID2.getLine():0));
			}

		}

		catch (RecognitionException e) {reportError (e) ; throw e ; }
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ident"

	// Delegated rules



	public static final BitSet FOLLOW_unitprog_in_unite62 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_unite65 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unitmodule_in_unite80 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_unite83 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_48_in_unitprog102 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_unitprog106 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_unitprog108 = new BitSet(new long[]{0x0022800070000000L});
	public static final BitSet FOLLOW_declarations_in_unitprog115 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_corps_in_unitprog122 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_44_in_unitmodule141 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_unitmodule145 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_unitmodule147 = new BitSet(new long[]{0x0022800050000000L});
	public static final BitSet FOLLOW_declarations_in_unitmodule155 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_partiedef_in_declarations173 = new BitSet(new long[]{0x0022800010000002L});
	public static final BitSet FOLLOW_partieref_in_declarations176 = new BitSet(new long[]{0x0020800010000002L});
	public static final BitSet FOLLOW_consts_in_declarations179 = new BitSet(new long[]{0x0020800000000002L});
	public static final BitSet FOLLOW_vars_in_declarations182 = new BitSet(new long[]{0x0000800000000002L});
	public static final BitSet FOLLOW_decprocs_in_declarations185 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_30_in_partiedef202 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_partiedef204 = new BitSet(new long[]{0x0000000000022000L});
	public static final BitSet FOLLOW_13_in_partiedef209 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_partiedef211 = new BitSet(new long[]{0x0000000000022000L});
	public static final BitSet FOLLOW_ptvg_in_partiedef218 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_49_in_partieref230 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_specif_in_partieref233 = new BitSet(new long[]{0x0000000000022000L});
	public static final BitSet FOLLOW_13_in_partieref238 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_specif_in_partieref240 = new BitSet(new long[]{0x0000000000022000L});
	public static final BitSet FOLLOW_ptvg_in_partieref248 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ident_in_specif262 = new BitSet(new long[]{0x0000090000000002L});
	public static final BitSet FOLLOW_40_in_specif266 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_specif268 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_type_in_specif270 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_13_in_specif276 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_type_in_specif278 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_10_in_specif285 = new BitSet(new long[]{0x0000080000000002L});
	public static final BitSet FOLLOW_43_in_specif309 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_specif312 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_type_in_specif314 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_13_in_specif320 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_type_in_specif322 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_10_in_specif329 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_28_in_consts347 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_consts351 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_consts355 = new BitSet(new long[]{0x0040002000005040L});
	public static final BitSet FOLLOW_valeur_in_consts357 = new BitSet(new long[]{0x0000000000020020L});
	public static final BitSet FOLLOW_ptvg_in_consts361 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_53_in_vars381 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_type_in_vars385 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_vars387 = new BitSet(new long[]{0x0000000204022000L});
	public static final BitSet FOLLOW_13_in_vars393 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_vars396 = new BitSet(new long[]{0x0000000204022000L});
	public static final BitSet FOLLOW_ptvg_in_vars403 = new BitSet(new long[]{0x0000000204000002L});
	public static final BitSet FOLLOW_33_in_type422 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_type435 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_decproc_in_decprocs452 = new BitSet(new long[]{0x0000800000020000L});
	public static final BitSet FOLLOW_ptvg_in_decprocs454 = new BitSet(new long[]{0x0000800000000002L});
	public static final BitSet FOLLOW_47_in_decproc472 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_decproc475 = new BitSet(new long[]{0x0020090030000000L});
	public static final BitSet FOLLOW_parfixe_in_decproc479 = new BitSet(new long[]{0x0020080030000000L});
	public static final BitSet FOLLOW_parmod_in_decproc482 = new BitSet(new long[]{0x0020000030000000L});
	public static final BitSet FOLLOW_consts_in_decproc487 = new BitSet(new long[]{0x0020000020000000L});
	public static final BitSet FOLLOW_vars_in_decproc490 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_corps_in_decproc493 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_17_in_ptvg509 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_29_in_corps527 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instructions_in_corps529 = new BitSet(new long[]{0x0000008000000000L});
	public static final BitSet FOLLOW_39_in_corps531 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_40_in_parfixe543 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_parfixe545 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_pf_in_parfixe547 = new BitSet(new long[]{0x0000000000020400L});
	public static final BitSet FOLLOW_17_in_parfixe551 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_pf_in_parfixe553 = new BitSet(new long[]{0x0000000000020400L});
	public static final BitSet FOLLOW_10_in_parfixe557 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_pf571 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_pf573 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_13_in_pf579 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_pf581 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_43_in_parmod600 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_parmod602 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_pm_in_parmod604 = new BitSet(new long[]{0x0000000000020400L});
	public static final BitSet FOLLOW_17_in_parmod608 = new BitSet(new long[]{0x0000000204000000L});
	public static final BitSet FOLLOW_pm_in_parmod610 = new BitSet(new long[]{0x0000000000020400L});
	public static final BitSet FOLLOW_10_in_parmod614 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_pm628 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_pm630 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_13_in_pm637 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_pm639 = new BitSet(new long[]{0x0000000000002002L});
	public static final BitSet FOLLOW_instruction_in_instructions659 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_17_in_instructions663 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instruction_in_instructions665 = new BitSet(new long[]{0x0000000000020002L});
	public static final BitSet FOLLOW_inssi_in_instruction682 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inscond_in_instruction688 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_boucle_in_instruction694 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_lecture_in_instruction700 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ecriture_in_instruction706 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_affouappel_in_instruction712 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_50_in_inssi729 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_inssi731 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_inssi737 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instructions_in_inssi739 = new BitSet(new long[]{0x0008020000000000L});
	public static final BitSet FOLLOW_51_in_inssi745 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instructions_in_inssi749 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_inssi759 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_27_in_inscond772 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_inscond777 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_inscond782 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instructions_in_inscond784 = new BitSet(new long[]{0x0000004002002000L});
	public static final BitSet FOLLOW_13_in_inscond797 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_inscond802 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_inscond807 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instructions_in_inscond809 = new BitSet(new long[]{0x0000004002002000L});
	public static final BitSet FOLLOW_25_in_inscond828 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instructions_in_inscond830 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_38_in_inscond848 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_52_in_boucle863 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_boucle867 = new BitSet(new long[]{0x0000000800000000L});
	public static final BitSet FOLLOW_35_in_boucle871 = new BitSet(new long[]{0x0014040108020020L});
	public static final BitSet FOLLOW_instructions_in_boucle873 = new BitSet(new long[]{0x0000001000000000L});
	public static final BitSet FOLLOW_36_in_boucle877 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_42_in_lecture890 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_lecture892 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_lecture894 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_13_in_lecture900 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_lecture902 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_10_in_lecture909 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_ecriture922 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_9_in_ecriture924 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_ecriture926 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_13_in_ecriture932 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_ecriture934 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_10_in_ecriture941 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ident_in_affouappel957 = new BitSet(new long[]{0x0000000000010202L});
	public static final BitSet FOLLOW_16_in_affouappel963 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_affouappel965 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_effixes_in_affouappel986 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_effmods_in_affouappel989 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_9_in_effixes1022 = new BitSet(new long[]{0x0040202000005660L});
	public static final BitSet FOLLOW_expression_in_effixes1025 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_13_in_effixes1029 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_effixes1031 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_10_in_effixes1039 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_9_in_effmods1051 = new BitSet(new long[]{0x0000000000000420L});
	public static final BitSet FOLLOW_ident_in_effmods1054 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_13_in_effmods1059 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_ident_in_effmods1061 = new BitSet(new long[]{0x0000000000002400L});
	public static final BitSet FOLLOW_10_in_effmods1070 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_exp1_in_expression1084 = new BitSet(new long[]{0x0000400000000002L});
	public static final BitSet FOLLOW_46_in_expression1088 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_exp1_in_expression1093 = new BitSet(new long[]{0x0000400000000002L});
	public static final BitSet FOLLOW_exp2_in_exp11115 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_34_in_exp11118 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_exp2_in_exp11123 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_45_in_exp21145 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_exp2_in_exp21147 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_exp3_in_exp21157 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_exp4_in_exp31171 = new BitSet(new long[]{0x0000000000FC0002L});
	public static final BitSet FOLLOW_21_in_exp31177 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp4_in_exp31181 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_20_in_exp31191 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp4_in_exp31195 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_22_in_exp31205 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp4_in_exp31210 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_exp31220 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp4_in_exp31224 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_18_in_exp31234 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp4_in_exp31239 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_19_in_exp31249 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp4_in_exp31253 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_exp5_in_exp41277 = new BitSet(new long[]{0x0000000000005002L});
	public static final BitSet FOLLOW_12_in_exp41288 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp5_in_exp41292 = new BitSet(new long[]{0x0000000000005002L});
	public static final BitSet FOLLOW_14_in_exp41307 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_exp5_in_exp41311 = new BitSet(new long[]{0x0000000000005002L});
	public static final BitSet FOLLOW_primaire_in_exp51340 = new BitSet(new long[]{0x0000000080000802L});
	public static final BitSet FOLLOW_11_in_exp51355 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_primaire_in_exp51359 = new BitSet(new long[]{0x0000000080000802L});
	public static final BitSet FOLLOW_31_in_exp51377 = new BitSet(new long[]{0x0040002000005260L});
	public static final BitSet FOLLOW_primaire_in_exp51382 = new BitSet(new long[]{0x0000000080000802L});
	public static final BitSet FOLLOW_valeur_in_primaire1409 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ident_in_primaire1417 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_9_in_primaire1426 = new BitSet(new long[]{0x0040202000005260L});
	public static final BitSet FOLLOW_expression_in_primaire1428 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_primaire1430 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_nbentier_in_valeur1444 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_valeur1452 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_nbentier_in_valeur1454 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_14_in_valeur1462 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_nbentier_in_valeur1464 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_valeur1472 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_37_in_valeur1480 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_nbentier1510 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_ident1521 = new BitSet(new long[]{0x0000000000000002L});
}
