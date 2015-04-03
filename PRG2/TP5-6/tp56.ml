open Ast
open Parser


(* Substitution d'expressions, formules *)

(* subst_expr :
   (einit: expression) -> (x: variable) -> (e: expression) -> expression
   
   Substitue la variable x par l'expression e dans einit
*)


let rec subst_expr einit x e=
  match einit with
	  Var(v) -> if x=v then e else einit
	| BinExpr(op,e1,e2) -> BinExpr(op,subst_expr e1 x e, subst_expr e2 x e)
	| _ -> einit;;


let e1= BinExpr(Plus, Var("x"), BinExpr(Moins, Const 4, Const 3));;
assert((subst_expr e1 "x" (Const 5))= BinExpr(Plus, Const(5), BinExpr(Moins, Const 4, Const 3)));;
assert((subst_expr e1 "y" (Const 5))= e1);;


(* Substitution dans les formules *)

let renommerVar x= x^"'";;
let renommerLVars= List.map renommerVar;;

let rec renommerExpr einit lx=
  match einit with
	  Var(v) -> if List.mem v lx then Var(renommerVar v) else einit
	| BinExpr(op,e1,e2) -> BinExpr(op, renommerExpr e1 lx, renommerExpr e2 lx)
	| _ -> einit;;


(* Fonction de renommage nécessaire pour éviter que le résultat de la substitution de x par y dans 
   \forall y. 1+ x soit \forall y'. 1 + y *)

let rec renommerFormule f lx=
  match f with
	  Comparaison(op,e1,e2) -> Comparaison(op, (renommerExpr e1 lx), (renommerExpr e2 lx))
	| Non(f2) -> Non(renommerFormule f2 lx)
	| Et(f1,f2) -> Et(renommerFormule f1 lx, renommerFormule f2 lx)
	| Ou(f1,f2) -> Ou(renommerFormule f1 lx, renommerFormule f2 lx)
	| Implique(f1,f2) -> Implique(renommerFormule f1 lx, renommerFormule f2 lx)
	| Qqsoit(v,f1) -> Qqsoit(renommerLVars v, renommerFormule f1 (v@lx))
	| _ -> f;;


(* subst_formule :
   (f: formule) -> (x: variable) -> (e: expression) -> formule 

   Substitue la variable x par l'expression e dans la formule f *)


let rec subst_formule f x e=
  match f with
	  Comparaison(op,e1,e2) -> Comparaison(op, (subst_expr e1 x e), (subst_expr e2 x e))
	| Non(f2) -> Non(subst_formule f2 x e)
	| Et(f1,f2) -> Et(subst_formule f1 x e, subst_formule f2 x e)
	| Ou(f1,f2) -> Ou(subst_formule f1 x e, subst_formule f2 x e)
	| Implique(f1,f2) -> Implique(subst_formule f1 x e, subst_formule f2 x e)
	| Qqsoit(v,f1) -> if List.mem x v then f else Qqsoit(v,subst_formule f1 x e)
	| _ -> f;;



let e2= Comparaison(Egal, e1, (subst_expr e1 "x" (Const 5)));;
let f1= Et(e2,e2);;
(subst_formule f1 "x" (Const 10));;

assert((subst_formule f1 "x" (Const 10))=
Et
 (Comparaison(Egal,
	 BinExpr (Plus, Const 10, BinExpr (Moins, Const 4, Const 3)),
	 BinExpr (Plus, Const 5, BinExpr (Moins, Const 4, Const 3))),
 Comparaison(Egal,
	BinExpr (Plus, Const 10, BinExpr (Moins, Const 4, Const 3)),
	BinExpr (Plus, Const 5, BinExpr (Moins, Const 4, Const 3)))));;
assert((subst_formule f1 "y" (Const 10))=f1);;



(* ######################### Partie complétée ######################### *)

(* ----- Simplifie les expressions arithmétiques binaires ----- *)
let rec partEval e =
	match e with
		| Const i -> Const i
		| Var v -> Var v
		| BinExpr (op,e1,e2) ->
			match op, partEval e1, partEval e2 with
				| Plus, Const i, Const j -> Const (i+j)
				| Plus, Const 0, res2 -> res2
				| Plus, res1, Const 0 -> res1

				| Moins, Const i, Const j -> Const (i-j)
				| Moins, res1, Const 0 -> res1
				| Moins, res1, res2 ->
					if res1 = res2 then Const 0
					else BinExpr(op, res1, res2)

				| Mult, Const i, Const j -> Const (i*j)
				| Mult, Const 0, res2 -> Const 0
				| Mult, res1, Const 0 -> Const 0
				| Mult, Const 1, res2 -> res2
				| Mult, res1, Const 1 -> res1

				| op, res1, res2 -> BinExpr(op, res1, res2);;


(* ----- Simplifie une expression (et en donne le résultat) ----- *)
let rec simplif f =
	match f with
		| True -> True
		| False -> False
		| Qqsoit(v, f1) -> Qqsoit(v, simplif f1)
		| Comparaison(op, e1, e2) ->
			(match op, partEval e1, partEval e2 with
				| Egal, Const i, Const j ->
					if i = j then True
					else False
				| Egal, res1, res2 ->
					if res1 = res2 then True
					else Comparaison(Egal, res1, res2)

				| Sup, Const i, Const j ->
					if i > j then True
					else False
				| Sup, res1, res2 ->
					if res1 = res2 then False
					else Comparaison(Sup, res1, res2)

				| Supeg, Const i, Const j ->
					if i >= j then True
					else False
				| Supeg, res1, res2 ->
					if res1 = res2 then True
					else Comparaison(Supeg, res1, res2)

				| Inf, Const i, Const j ->
					if i < j then True
					else False
				| Inf, res1, res2 ->
					if res1 = res2 then False
					else Comparaison(Inf, res1, res2)

				| Infeg, Const i, Const j ->
					if i <= j then True
					else False
				| Infeg, res1, res2 ->
					if res1 = res2 then True
					else Comparaison(Infeg, res1, res2))

		| Et(f1, f2) ->
			(match simplif f1, simplif f2 with
				| False, res2 -> False
				| res1, False -> False
				| True, res2 -> res2
				| res1, True -> res1
				| res1, res2 ->
					if res1 = res2 then res1
					else if ((res1 = Non(res2)) || (Non(res1) = res2)) then False
					else Et(res1, res2))

		| Ou(f1, f2) ->
			(match simplif f1, f2 with
				| True, res2 -> True
				| res1, True -> True
				| False, res2 -> res2
				| res1, False -> res1
				| res1, res2 ->
					if res1 = res2 then res1
					else Ou(res1, res2))

		| Non(f1) ->
			(match simplif f1 with
				| True -> False
				| False -> True
				| Non(Non(res)) -> res
				| res -> Non(res))

		| Implique(f1, f2) ->
			(match simplif f1, simplif f2 with
				| False, res2 -> True
				| res1, False -> Non(res1)
				| True, res2 -> res2
				| res1, True -> True
				| res1, res2 ->
					if res1 = res2 then True
					else Implique(res1, res2));;



(* ----- Programme VCGEN ----- *)
let rec vcgen = function (p, f) ->
	match p with
		| Skip -> f
		| Affectation(v, e) -> vcgen(p, subst_formule(f, v, e))
		| If(f1, p1, p2) -> Et(Implique(f1, vcgen(p1, f)), Implique(Non(f1), vcgen(p2, f)))
		| Bloc(p1::pl) -> vcgen(p1, vcgen(Bloc pl, f))
		| _ -> f;;



(* ----- Affiche un programme annoté entré ----- *)
let print_prog_annote = function nom_fichier ->
	match file_parse nom_fichier with
		(post, prog, pre) ->
			(print_string (formule2string pre), print_string (programme2string prog), print_string (formule2string post));;

(*print_prog_annote "test1.mlw";;*)

let _ = match file_parse "test1.mlw" with
	(post, prog, pre) ->
		(print_string (formule2string (vcgen (prog, pre))), print_string (formule2string (vcgen (prog, pre))));;

simplif f1;;