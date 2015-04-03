
type variable = string

type operateur = Plus | Moins | Mult

type comparateur= Inf | Sup | Infeg | Supeg | Egal

type expression= Const of int | Var of variable 
		 | BinExpr of operateur * expression * expression

type formule= Comparaison of comparateur * expression * expression 
              | Et of formule * formule 
	      | Ou of formule * formule | Non of formule 
	      | Implique of formule * formule | True | False 
	      | Qqsoit of (variable list) * formule
		  

type programme= Affectation of variable * expression 
		| If of formule * programme * programme
		| While of formule * programme * formule
		| Bloc of programme list
                | Skip

type progAnnote= formule * programme * formule

let exempleProg= Bloc([Affectation("x",BinExpr(Plus,Var("x"),Const(1)));
		       While(Comparaison(Supeg,Var("x"),Const(0)),
			     If(Comparaison(Egal,Var("x"),Const(0)),
				Affectation("x",BinExpr(Plus,Var("x"),Const(1))),
				Affectation("x",BinExpr(Moins,Var("x"),Const(2)))),True)]);;

(* op2string : operateur -> string 
   Donne la chaine de caractère correspondant à l'opérateur
*)

let op2string op= 
  match op with 
      Plus -> "+" 
    | Moins -> "-" 
    | Mult -> "*" 

let comp2string cp=
  match cp with
    | Inf -> "<" 
    | Sup -> ">" 
    | Infeg -> "<=" 
    | Supeg -> ">=" 
    | Egal -> "="


(* expr2string : (e: expression) -> string 
   Donne la chaine de caractère représentant l'expression e *)

let rec expr2string e: string=
  match e with
      Const(i) -> string_of_int i
    | Var(v) -> v
    | BinExpr(op,e1,e2) -> "("^(expr2string e1)^" "^(op2string op)^" "^(expr2string e2)^")"


(* formule2string : (f: formule) -> string
   Donne la chaine représentant la formule f
*)

let rec formule2string (f: formule): string=
  match f with
      Comparaison(cp,e1,e2) -> ((expr2string e1)^" "^(comp2string cp)^" "^(expr2string e2))
    | True -> "True" 
    | False -> "False"
    | Ou(e1,e2) -> "["^(formule2string e1)^" || "^(formule2string e2)^"]"
    | Et(e1,e2) -> "["^(formule2string e1)^" && "^(formule2string e2)^"]"
    | Non(e1) -> "!"^(formule2string e1)
    | Implique(e1,e2) -> "["^(formule2string e1)^" -> "^(formule2string e2)^"]"
    | Qqsoit([],f1) -> (formule2string f1)
    | Qqsoit(x::rem,f1) -> 
	"(Qqsoit "^x^")"^(formule2string (Qqsoit(rem,f1)))


(* Pretty printer de programme *)

let decalage=4;;

let indentation i= String.make i ' '

let rec prog2stringAux p i: string=
  match p with 
    | Affectation(v,e) -> 
	(indentation i)^v^":= "^(expr2string e)
    | If(f,p1,p2) -> 
	(indentation i)^"if "^
	(formule2string f)^" then\n"^
	(prog2stringAux p1 (i + decalage))^"\n"^(indentation i)^"else\n"^
	(prog2stringAux p2 (i + decalage))
    | While(f,p,f2) -> 
	(indentation i)^"while "^(formule2string f)^" do "^
	(if f2=True then "\n" else ("{invariant "^(formule2string f2)^"}\n"))^
	(prog2stringAux p (i + decalage))^(indentation i)^"\n"^(indentation i)^"done"
    | Bloc(l) -> (indentation i)^"begin\n"^
	(sequence_to_string l (i + decalage))^"\n"^(indentation i)^"end"
    | Skip -> (indentation i)^"skip"

and sequence_to_string l i=
  match l with
      [] -> ""
    | [p] -> (prog2stringAux p i)
    | p::rem -> (prog2stringAux p i)^";\n"^(sequence_to_string rem i);;


(* programme2string: (p: programme) -> string
   Donne la chaîne de caractère correspondant au programme p *)

let programme2string p= prog2stringAux p 0;;


				  
  





