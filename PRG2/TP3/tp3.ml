(* Pour interpr�ter: ocaml graphics.cma *)
(* Pour compiler: ocamlbuild -cflags -dtypes -lib graphics *)

open Graphics;;


(* Declaration des types *)
type orientation = Nord | Sud | Est | Ouest;;
type position = int * int;;
type etat = Leve | Pose;;
type programme = Stop | TGauche | TDroite | Avancer of int | Pinceau of etat
		 | Bloc of programme list
		 | Repeat of int * programme;;
type robot = { pos: position; ori: orientation; etat: etat };;


(* Dimension de la fen�tre graphique *)

let largeur=500;;
let hauteur=500;;

(* Ouverture de la fen�tre *)

open_graph (Printf.sprintf " %dx%d" largeur hauteur);;
set_color black;;

let translation (x,y)= (x+(largeur / 2), y+(hauteur/2));;

(* D�placer la position courante (en tra�ant une ligne) jusqu'� un point de coordonn�es pos=(x,y) *)
let tracerVers pos=
  let (x,y)= translation pos in lineto x y;moveto x y;;

(* D�placer la position courante (sans tracer) jusqu'� un point de coordonn�es pos=(x,y) *)
let allerVers pos=
  let (x,y)= translation pos in moveto x y;;

(* Initialisation de la fen�tre graphique *)
let init ()= 
  clear_graph ();
  allerVers (0,0);;


init();;


let gtrace r= 
  if r.etat=Pose then tracerVers r.pos else allerVers r.pos;;



(* ######################### Ajouts ######################### *)
let gauche = function ori -> match ori with
    Nord -> Ouest
  | Sud -> Est
  | Est -> Nord
  | Ouest -> Sud;;

let droite = function ori -> match ori with
    Nord -> Est
  | Sud -> Ouest
  | Est -> Sud
  | Ouest -> Nord;;

let avancer = function ((x, y), ori, n) -> match ori with
    Nord -> (x + n, y)
  | Sud -> (x - n, y)
  | Est -> (x, y + n)
  | Ouest -> (x, y - n);;

let rec execute = function (p, r) -> match p with
    Stop -> r
  | TGauche -> { r with ori = gauche r.ori }
  | TDroite -> { r with ori = droite r.ori }
  | Avancer i -> { r with pos = avancer(r.pos, r.ori, i) }
  | Pinceau e -> { r with etat = e }
  | Bloc [] -> r
  | Bloc (p1::l) -> execute(Bloc l, execute(p1, r))
  | Repeat (i, p1) -> 
    if (i > 0) then execute(Repeat(i-1, p1), execute(p1
, r))
    else r;;
