(* #################### Asteroids game in Ocaml #################### *)
(* TP Projet - Andriamilanto Tompoariniaina - Boucherie Thomas *)

(* Import the graphics library *)
open Graphics;;



(* #################### Constants and parameters #################### *)
(* dimension fenetre graphique *)
let width = 1000;;
let height = 600;;


(* Initial parameters *)
let wsecure = 200;;
let hsecure = 100;;
let nb_base_asteroides = 10;;
let t_asteroide = 30;;
let t_missile = 2;;
let t_vaisseau = 8;;
let t_canon = 13;;
let vit_asteroides_min = 5;;
let vit_asteroides_max = 10;;
let vit_missiles = 20;;
let v_acceleration = 10;;
let v_deceleration = 1;;
let v_vit_max = 20;;
(*let scores = "0";;	not implemented*)


(* In game parameters *)
let min_nb_fragments = 2;;
let max_nb_fragments = 3;;



(* #################### Types used in the game #################### *)
(* --- All the types used as parameters --- *)
type position = int * int;;
type vitesse = int;;
type orientation = int;;
type taille = int;;
type couleur = color;;


(* --- The different components of the game --- *)
type vaisseau = { vpos: position; vvit: vitesse; vori: orientation };;
type missile = { mpos: position; mori: orientation; mvit: vitesse };;
type asteroide = { apos: position; aori: orientation; avit: vitesse; at: taille; acouleur: couleur };;
type etat = { ship: vaisseau; lasters: asteroide list; lmiss: missile list };;



(* --- Generator type --- *)
type 'a gen = unit -> 'a;;

let pi = 4. *. atan 1.;;
let sin_degres = function x -> sin(pi *. x /. 180.);;
let cos_degres = function x -> cos(pi *. x /. 180.);;



(* #################### Functions used by the game #################### *)
(* --- ### Generators functions ### --- *)
let lance (g : 'a gen) : 'a = g ();;
let genInt i j = fun () -> (Random.int (j-i+1))+i;;


(* --- Randomize the asteroids initial positions --- *)
let random_pos_asteroids = function () ->
	match lance (genInt 0 4) with  (* Appears near one of the 4 shapes of the game sides *)
		| 1 -> (lance (genInt 0 ((width/2)-wsecure)), lance (genInt 0  height))
		| 2 -> (lance (genInt 0 width), lance (genInt 0 ((height/2)-hsecure)))
		| 3 -> (lance (genInt ((width/2)+wsecure) width), lance (genInt 0  height))
		| _ -> (lance (genInt 0 width), lance (genInt ((height/2)+hsecure) height));;


(* --- Randomize the asteroids speed --- *)
let random_vit_asteroids = function () ->
	lance (genInt vit_asteroides_min vit_asteroides_max);;


(* --- Randomize the asteroids size --- *)
let random_t_asteroids = function () ->
	match lance (genInt 0 3) with
		| 0 -> t_asteroide / 2
		| _ -> t_asteroide;;


(* --- Randomize the color of asteroids --- *)
let random_color = function () ->
	match (lance (genInt 0 5)) with
		| 1 -> green
		| 2 -> red
		| 3 -> yellow
		| 4 -> magenta
		| _ -> cyan;;


(* --- Get a random orientation --- *)
let random_orientation = function () ->
	lance (genInt 0 359);;

(* --- Get a random position --- *)
let random_x_vaisseau = function () ->
	lance (genInt 0 599);;

let random_y_vaisseau = function () ->
	lance (genInt 0 999);;



(* --- ### Other functions that we need ### --- *)
(* --- The square of a number --- *)
let square = function i -> i*i;;


(* --- To know if two circles collide --- *)
let circle_collide = function (center, radius, center_bis, radius_bis) ->
	match center with (cx, cy) ->
		match center_bis with (cxb, cyb) ->
			if (sqrt(float_of_int(square(cxb-cx) + square(cyb-cy))) <= float_of_int(radius+radius_bis)) then true
			else false;;


(* --- To know if a point is contained in a circle --- *)
let in_circle = function (center, radius, point) ->
	match center with (cx, cy) ->
		match point with (px, py) ->
			if (sqrt(float_of_int(square(px-cx) + square(py-cy))) <= float_of_int(radius)) then true
			else false;;


(* --- Calculate the new position --- *)
let translate = function (pos, ori, vit) ->
	match pos with (x, y) ->
		let ori_float = (float_of_int ori) in
			let vit_float = (float_of_int vit) in
				(int_of_float((float_of_int x) +. (sin_degres ori_float) *. vit_float), int_of_float((float_of_int y) +. (cos_degres ori_float) *. vit_float));;



(* --- ### Initilizations ### --- *)
(* --- Create the initial asteroids --- *)
let rec create_asteroids = function i ->
	if i = 0 then []
	else {
		apos = lance random_pos_asteroids;
		aori = lance random_orientation;
		avit = lance random_vit_asteroids;
		at = lance random_t_asteroids;
		acouleur = lance random_color;
	} :: create_asteroids (i-1);;


(* --- Create the initial state --- *)
let init_etat () =
	set_color black;
	fill_rect 0 0 width height;
	{
		ship = {
			vpos = (width/2, height/2);
			vori = 0;
			vvit = 0
		};
		lasters = create_asteroids nb_base_asteroides;
		lmiss = []
	};;
	





(* --- ### Changing states ### --- *)
(* --- Get new ship position --- *)
let move_ship = function ship ->
	let distance = int_of_float((float_of_int ship.vvit) +. 0.05 +. (float_of_int (v_acceleration+v_deceleration)) *. 0.0025/.2.) in
		match translate(ship.vpos, ship.vori, distance) with (x, y) ->
			 let new_x =
			 	if x < 0 then (x + width) else
			 	if x > width then (x - width)
			 	else x in
					let new_y =
						if y < 0 then (y + height) else
						if y > height then (y - height)
						else y in
							(new_x, new_y);;


(* --- Accelerate the ship --- *)
let acceleration = function etat ->
	{ etat with
		ship = {
			etat.ship with
				vvit =
					if etat.ship.vvit >= v_vit_max then v_vit_max
					else etat.ship.vvit + (v_acceleration+v_deceleration);
		}
	};;


(* --- Turn the ship to the left --- *)
let rotation_gauche = function etat ->
	{ etat with
		ship = {
			etat.ship with
				vori =
					if etat.ship.vori = 0 then 315
					else etat.ship.vori - 45;
				vpos = move_ship etat.ship
		}
	};;


(* --- Turn the ship to the right --- *)
let rotation_droite = function etat ->
	{ etat with
		ship = {
			etat.ship with
				vori =
					if etat.ship.vori = 360 then 45
					else etat.ship.vori + 45;
				vpos = move_ship etat.ship
		}
	};;


(* --- Fire a missile --- *)
let tir = function etat ->
	{ etat with
		lmiss = {
			mpos = translate(etat.ship.vpos, etat.ship.vori, vit_missiles);
			mori = etat.ship.vori;
			mvit = vit_missiles
		} :: etat.lmiss
	};;

(* --- teleport ship --- *)
let teleportation = function etat ->
    {etat with
    	  ship = {
	       etat.ship with
	       vpos = (lance random_y_vaisseau ,lance random_x_vaisseau)
	       	   }
	};;	
	       

(* --- Check if an asteroid hit the ship --- *)
let rec aster_vs_ship = function (lasters, ship) ->
	match lasters with
		| [] -> false
		| aster::l ->
			if circle_collide(aster.apos, aster.at, ship.vpos, t_vaisseau) then true
			else aster_vs_ship(l, ship);;


(* --- Check if a missile hit an asteroid --- *)
let rec miss_vs_aster = function (pos, radius, lmiss) ->
	match lmiss with
		| [] -> false
		| miss::l ->
			if (in_circle(pos, radius, miss.mpos)) then true
			else miss_vs_aster(pos, radius, l);;


(* --- Create the fragments with different speed, size and direction --- *)
let rec create_random_asteroid = function (i, new_pos, color) ->
	if i = 0 then []
	else
		let new_taille = t_asteroide/2 in
			let new_ori = lance random_orientation in
				{
					apos = translate(new_pos, new_ori, new_taille);
					aori = new_ori;
					avit = lance random_vit_asteroids;
					at = new_taille;
					acouleur = color;
				} :: create_random_asteroid(i-1, new_pos, color);;


(* --- When an asteroid is hit, it's fragmented --- *)
let explode = function (aster, new_pos) ->
	if (aster.at = t_asteroide) then create_random_asteroid(lance(genInt min_nb_fragments max_nb_fragments), new_pos, aster.acouleur)
	else [];;


(* --- Get new asteroid positions --- *)
let move_asteroids = function asteroide ->
	match translate (asteroide.apos, asteroide.aori, asteroide.avit) with (x, y) ->
		 let new_x =
		 	if x < 0 then (x + width) else
		 	if x > width then (x - width)
		 	else x in
				let new_y =
					if y < 0 then (y + height) else
					if y > height then (y - height)
					else y in
						(new_x, new_y);;


(* --- Move the missiles --- *)
let rec move_missiles = function lmiss ->
	match lmiss with
		| [] -> []
		| missile::l ->
			let new_mpos = translate(missile.mpos, missile.mori, missile.mvit) in
				match new_mpos with (x, y) ->
					if ((x > width) || (y > height) || (x < 0) || (y < 0)) then
						move_missiles l
					else
						{ missile with mpos = new_mpos } :: (move_missiles l);;


(* --- Move the missiles --- *)
let rec run_asteroides = function (lasters, lmiss) ->
	match lasters with
		| [] -> []
		| aster::l ->
			let new_pos = move_asteroids aster in
				if (miss_vs_aster(new_pos, aster.at, lmiss)) then
					explode(aster, new_pos) @ run_asteroides(l, lmiss)
				else
					{ aster with apos = new_pos } :: run_asteroides(l, lmiss);;


(* --- Get the next game state --- *)
let etat_suivant = function etat ->
	if aster_vs_ship(etat.lasters, etat.ship) then (print_endline "Partie terminée"; exit 0)
	else if etat.lasters = [] then (print_endline "Gagné"; exit 0)
	else {
		lmiss = move_missiles etat.lmiss;
		lasters = run_asteroides(etat.lasters, etat.lmiss);
		ship =
			{ etat.ship with
				vpos = (move_ship etat.ship);
				vvit =
					if etat.ship.vvit > 0 then etat.ship.vvit - v_deceleration
					else 0
			}
	};;



(* #################### Graphical functions #################### *)
(* --- Display asteroids --- *)
let affiche_asteroid = function asteroide ->
	match asteroide.apos with (x, y) ->  
		set_color asteroide.acouleur;
		Graphics.fill_circle x y asteroide.at;;
		

(* --- Display the ship as a square --- *)
let affiche_ship = function ship ->
	match ship.vpos with (x, y) ->
		set_color blue;
		Graphics.fill_circle x y t_vaisseau;
		Graphics.moveto x y;
		match translate((x, y), ship.vori, t_canon) with (t_x, t_y) -> Graphics.lineto t_x t_y;;
		

(* --- Display a missile --- *)
let affiche_tir = function tir ->
	match tir.mpos with (x, y) ->
		set_color white;
		Graphics.moveto x y;
		match translate((x, y), tir.mori, t_missile) with (t_x, t_y) -> Graphics.lineto t_x t_y;;

(* --- Display the score --- 
let affiche_score = function score ->
        set_color white;
	Graphics.moveto 950 550;
	Graphics.draw_string scores ;;
*)

(* --- Display the whole game --- *)
let affiche_etat = function etat ->
	clear_graph ();
	set_color black;
	fill_rect 0 0 width height;
	List.iter affiche_asteroid etat.lasters;
	affiche_ship etat.ship;
	List.iter affiche_tir etat.lmiss;
		();;
	


(* #################### Main function and launcher #################### *)
(* --- boucle d'interaction --- *)
let rec boucle_interaction ref_etat =
	let status = wait_next_event [Key_pressed] in (* on attend une frappe clavier *)
		let etat = !ref_etat in (* on recupere l'etat courant *)
			let nouvel_etat = (* on definit le nouvel etat... *)
				match status.key with (* ...en fonction de la touche frappee *)
					| '1' | 'j' -> rotation_gauche etat (* rotation vers la gauche *)
					| '2' | 'k' -> acceleration etat (* acceleration vers l'avant *)
					| '3' | 'l' -> rotation_droite etat (* rotation vers la droite *)
					| ' ' -> tir etat (* tir d'un projectile *)
					| 't' -> teleportation etat (*teleportation du vaissaux*)
					| 'q' -> print_endline "Bye bye!"; exit 0 (* on quitte le jeux *)
					| _ -> etat in (* sinon, rien ne se passe *)
						ref_etat := nouvel_etat; (* on enregistre le nouvel etat *)
	boucle_interaction ref_etat;; (* on se remet en attente de frappe clavier *)


let main () =

	(* initialisation du generateur aleatoire *)
	Random.self_init ();
	
	(* initialisation de la fenetre graphique et de l'affichage *)
	open_graph (" " ^ string_of_int width ^ "x" ^ string_of_int height);
	auto_synchronize false;

	(* initialisation de l'etat du jeu *)
	let ref_etat = ref (init_etat ()) in
		(* programmation du refraichissement periodique de l'etat du jeu et de son affichage *)
		let _ = Unix.setitimer Unix.ITIMER_REAL
			{ Unix.it_interval = 0.05; (* tous les 1/20eme de seconde... *)
			Unix.it_value = 0.05 } in
				Sys.set_signal Sys.sigalrm
					(Sys.Signal_handle (fun _ ->
						affiche_etat !ref_etat; (* ...afficher l'etat courant... *)
						synchronize ();
						ref_etat := etat_suivant !ref_etat)); (* ...puis calculer l'etat suivant *)
		boucle_interaction ref_etat;; (* lancer la boucle d'interaction avec le joueur *)

let _ = main ();; (* demarrer le jeu *)
