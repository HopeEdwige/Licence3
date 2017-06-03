open Genlex
open Ast

(* La liste des mots cl�s du langage: programmes et annotations *)

let keywords=[",";"(";")";"[";"]";":=";"begin";"end";"while";"do";"done";"if";"then";"else";"skip";";";"+";"-";"*";"=";">";"<";">=";"<=";"->";"!";"&&";"||";"{";"}";"invariant";"endif"]


(* lexer : char Stream.t -> Genlex.token Stream.t
   Le lexer pour les programmes annot�s. 
   On ne traite qu'un cas particulier: le cas des entiers n�gatifs. Les autres
   cas sont trait�s par le lexer de base d'Ocaml (make_lexer) *)

let lexer stream =
    let rec aux = parser
      | [< 'Int n when n<0; t=aux >] -> [< 'Kwd "-"; 'Int (-n); t >]
      | [< 'h; t=aux >] -> [< 'h; t >]
      | [< >] -> [< >] in
  aux(make_lexer keywords stream);;


(* parse_val: Genlex.token Stream.t -> expression
   parse_expression: Genlex.token Stream.t -> expression

   Le parser de constantes/variables et expressions *)

let rec parse_val = parser
    | [< 'Int n >] -> Const n
    | [< 'Ident f >] -> Var f
    | [< 'Kwd "("; e=parse_expression ?? "Syntaxe d'expression incorrecte"; 'Kwd ")" ?? ("Parenthese manquante � la fin de "^(expr2string e)) >] -> e
and parse_expression = parser
    [< e1= parse_val; stream >] ->
      (parser
	   [< 'Kwd "+"; e2=parse_val ?? "Syntaxe d'expression incorrecte apres +">] -> BinExpr(Plus,e1,e2)
	 | [< 'Kwd "-"; e2=parse_val ?? "Syntaxe d'expression incorrecte apres -">] -> BinExpr(Moins,e1,e2)
	 | [< 'Kwd "*"; e2=parse_val ?? "Syntaxe d'expression incorrecte apres *">] -> BinExpr(Mult,e1,e2)
	 | [< 'Ident x; rem >] -> failwith ("Syntax error on "^x)
	 | [< >] -> e1
      )stream


(* Le parser de conditions *)

let parse_cond = parser
    [< e1= parse_val; stream >] ->
      (parser
	 | [< 'Kwd "<"; e2=parse_val ?? "Syntaxe d'expression incorrecte apres <">] -> Comparaison(Inf,e1,e2)
	 | [< 'Kwd ">"; e2=parse_val ?? "Syntaxe d'expression incorrecte apres >">] -> Comparaison(Sup,e1,e2)
	 | [< 'Kwd "<="; e2=parse_val ?? "Syntaxe d'expression incorrecte apres <=">] -> Comparaison(Infeg,e1,e2)
	 | [< 'Kwd ">="; e2=parse_val ?? "Syntaxe d'expression incorrecte apres >=">] -> Comparaison(Supeg,e1,e2)
	 | [< 'Kwd "="; e2=parse_val ?? "Syntaxe d'expression incorrecte apres =">] -> Comparaison(Egal,e1,e2)
	 | [< 'Ident x; rem >] -> failwith ("Syntax error on "^x)
      )stream

(* parse_atom: Genlex.token Stream.t -> formule
   parse_formule: Genlex.token Stream.t -> formule

   Le parser de litt�raux (atomes) et formules *)

let rec parse_atom= parser
    [< 'Kwd "["; f=parse_formule ?? "Syntaxe de formule incorrecte"; 'Kwd "]" ?? ("Parenthese ] manquante a la fin de "^(formule2string f)) >] -> f
  | [< cond= parse_cond >] -> cond
and parse_formule = parser
  | [< 'Kwd "!"; 'Kwd "["; f=parse_formule ?? "!(formule) mal construit" ; 'Kwd "]" ?? ("Parenthese ] manquante a la fin de "^(formule2string f)) >] -> Non f
  | [< first= parse_atom ; stream >] ->
      (parser
	   [< 'Kwd "&&"; second= parse_atom  ?? " f1 && f2 mal construit" >] -> Et(first, second)
	 | [< 'Kwd "||"; second= parse_atom  ?? " f1 || f2 mal construit" >] -> Ou(first, second)
	 | [< 'Kwd "->"; second= parse_atom ?? " f1 -> f2 mal construit">] -> Implique(first, second)
	 | [< 'Ident x; rem >] -> failwith ("Syntax error on "^x)
	 | [< >] -> first
      ) stream


(* parse_programme: Genlex.token Stream.t -> programme
   parse_sequence: Genlex.token Stream.t -> programme

   Le parser de programme et de s�quence de programmes
*)

let rec parse_programme = parser
  | [< 'Kwd "begin"; inner= parse_sequence ?? "Mauvaise sequence d'instructions" ; 'Kwd "end" ??"end manquant en fin de bloc">] -> Bloc(inner)
  | [< 'Ident f ; 'Kwd ":=" ?? ":= manquant dans une affectation"; exp=parse_expression >] -> Affectation(f,exp)
  | [< 'Kwd "skip" >] -> Skip
  | [< 'Kwd "if"; 'Kwd "("; cond=parse_formule ?? "Mauvaise formule dans un if"; 'Kwd ")"; 'Kwd "then" ??"then manquant"; p1=parse_programme ?? "mauvais programme dans un then"; 'Kwd "else" ?? "else manquant";
       p2=parse_programme  ?? "mauvais programme dans un else"; 'Kwd "endif" ??"endif manquant">] -> If(cond,p1,p2)
  | [< 'Kwd "while"; 'Kwd "("; cond=parse_formule ?? "Mauvaise formule dans un while"; 'Kwd ")"; 'Kwd "do" ??"do manquant"; stream >] ->
      (parser
	   [< 'Kwd "{"; 'Kwd "invariant" ?? "mot cle invariant manquant" ; inv=parse_formule ?? "Mauvaise formule dans l'invariant"; 'Kwd "}"; p1=parse_programme ?? "Mauvais programme dans le while"; 'Kwd "done" ?? "Mot cle done manquant">]
	   -> While(cond,p1,inv)
	 | [< p1=parse_programme; 'Kwd "done" ?? "Mot cle done manquant">] -> While(cond,p1,True)
      ) stream
and parse_sequence = parser
    [< first= parse_programme ; stream >] ->
      (parser
	   [< 'Kwd ";" ; rem=parse_sequence ?? "Mauvaise s�quence">] -> first::rem
	 | [< >] -> [first]
      ) stream
  | [< >] -> []


(* parse_programme_annote: Genlex.token Stream.t -> progAnnote
   Le parser de programme annot�.
*)

let parse_programme_annote= parser
    [< 'Kwd "{"; pre= parse_formule ?? "Mauvaise formule dans la precondition"; 'Kwd "}" ?? "accolade fermante manquante dans la precondition"; prog=parse_programme; stream >] ->
      ((parser
	    [< 'Kwd "{" ; post= parse_formule ?? "Mauvaise formule dans la postcondition"; 'Kwd "}" >] -> pre,prog,post
	  | [< >] -> pre,prog,True) stream)
  | [< prog= parse_programme ; stream >] ->
      (parser
	   [< 'Kwd "{" ; f= parse_formule ?? "Mauvaise formule dans la postcondition"; 'Kwd "}">] -> True,prog,f
	 | [< >] -> True,prog,True) stream


(* file_prog_parse: (filename: string) -> programme
   retourne le programme contenu dans le fichier de nom filename
*)

let file_prog_parse (filename:string) =
  let inchan = open_in filename in
  let instream = Stream.of_channel inchan in
    (try(
      let spec = parse_programme (lexer instream) in
      let _ = close_in inchan in spec)
    with x -> let _ = close_in inchan in
      raise x)


(* file_parse: (filename: string) -> progAnnote
   retourne le programme annot� contenu dans le fichier de nom filename
*)
let file_parse (filename:string):progAnnote =
  let inchan = open_in filename in
  let instream = Stream.of_channel inchan in
    (try(
      let spec = parse_programme_annote (lexer instream) in
      let _ = close_in inchan in spec)
    with x -> let _ = close_in inchan in
      raise x);;
